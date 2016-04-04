/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import snaq.db.ConnectionPool;

/**
 *
 * @author Cường <duongcuong96 at gmail dot com>
 */
public class DBManager {

    private List<Field> data;
    private MyConfig dbconfig = new MyConfig();
    private ConnectionPool pool;
    private final String hosturl;
    public final String username;
    private final String password;
    private final Connection conn ;
    public DBManager(String configFileName) throws SQLException {
        data = new ArrayList<>();
        dbconfig.loadConfig(configFileName);
        hosturl = dbconfig.getConfig("poolname.url");
        username = dbconfig.getConfig("poolname.user");
        password = dbconfig.getConfig("poolname.password");
        pool = new ConnectionPool(
                dbconfig.getConfig("name"),
                Integer.parseInt(dbconfig.getConfig("poolname.minpool")),
                Integer.parseInt(dbconfig.getConfig("poolname.maxpool")),
                Integer.parseInt(dbconfig.getConfig("poolname.maxsize")),
                Long.parseLong(dbconfig.getConfig("poolname.idleTimeout")),
                dbconfig.getConfig("poolname.url"),
                dbconfig.getConfig("poolname.user"),
                dbconfig.getConfig("poolname.password")
        );
        conn = pool.getConnection();
    }

    //override sotsring :
    @Override
    public String toString() {
        return ("Field data : " + this.data);
    }

    ;

    public List<Field> getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }

    //lay size cua list data
    public int getSize() {
        return this.data.size();
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = pool.getConnection();
        } catch (SQLException e) {
            System.out.println("Error get connection " + e);
            e.printStackTrace();

        }
        return conn;
    }

//connect to sql :
    public Connection sqlConnect() {
        Connection conn = null;
        try {
            String url = dbconfig.getConfig("db_url");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error cannot connection to database ! ");
            e.printStackTrace();
        }
        return conn;
    }

    //query su dung cho moi khi SELECT * ...
    public ResultSet query(String s) {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(s);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error query : " + e);
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet query(String s, String[] args) throws SQLException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(s);
            if (args.length == 1) {
                pstmt.setString(1, args[0]);
            } else {
                for (int i = 1; i < args.length + 1; i++) {
                    pstmt.setString(i, args[i - 1]);
                }
            }
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error query : " + e);
            e.printStackTrace();
        }
        return rs;
    }

    //su dung khi update / delete 
    public boolean update(String s, String[] paras) {
        boolean isUpdated = true;
        try {
            PreparedStatement pstmt = conn.prepareStatement(s);
            for (int i = 1; i < paras.length + 1; i++) {
                pstmt.setString(i, paras[i - 1]);
//                pstmt.setString(i+1, paras[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            isUpdated = false;
            System.out.println("Error update database : " + e);
            System.out.println("Queried string :  " + s + "with paras : " + paras);
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean insert(String s, String[] paras) {
        return this.update(s, paras);
    }

    public boolean delete(int id, String table) {
        boolean isUpdated = true;
        String s = "DELETE FROM thong_tin WHERE id = ? LIMIT 1";
        try {
            PreparedStatement pstmt = conn.prepareStatement(s);
            pstmt.setInt(1, id);
//                pstmt.setString(1, table);
            pstmt.executeUpdate();
            System.out.println("print preparedstatement : " + pstmt);
        } catch (SQLException e) {
            isUpdated = false;

            System.out.println("Error update database : " + e);
            System.out.println("Error delete id :  " + id + " on table : " + table);
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean delete(String id, String table) {
        boolean isUpdated = true;
        String s = "DELETE FROM thong_tin WHERE id = ? LIMIT 1";
        try {
            PreparedStatement pstmt = conn.prepareStatement(s);
            pstmt.setString(1, id);
//                pstmt.setString(1, table);
            pstmt.executeUpdate();
            System.out.println("print preparedstatement : " + pstmt);
        } catch (SQLException e) {
            isUpdated = false;

            System.out.println("Error update database : " + e);
            System.out.println("Error delete id :  " + id + " on table : " + table);
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean batchDelete(List<String> idArr, String table) {
        PreparedStatement pstmt = null;

        boolean isUpdated = true;
        String s = "DELETE FROM thong_tin WHERE id = ? LIMIT 1";
        try {
            pstmt = conn.prepareStatement(s);
            for (int i = 0; i < idArr.size(); i++) {
                String id = idArr.get(i);
                pstmt.setString(1, id);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            isUpdated = false;
            System.out.println("Error  batch delete : " + e);
            e.printStackTrace();
        }

        return isUpdated;
    }

    public void closeConnection(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Error sql exception : " + e);
        }
    }
// import text 

    public void importText(String path) {
        try {
            int i = 0;
            Scanner scan;
            File file = new File(path);
            scan = new Scanner(file);
            while (scan.hasNext()) {
                String s = scan.nextLine();
                //System.out.println("data: " + data);
                Field field = new Field(s);
                data.add(field);
                System.out.println("id : " + (++i));
            };
            System.out.println("all data is imported ! ");
        } catch (IOException e) {
            System.out.println("DBManager IOException errors : " + e);
        }
        System.out.println("data size : " + data.size());
    }

    public List<Field> getList() {
        return this.data;
    }

    //lay danh sach n list dau tien
    public List<Field> getFirstList(int limit) {
        List<Field> limitedList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            limitedList.add(this.data.get(i));
        }
        return limitedList;
    }

    //in ra tat ca list object 
    public void printList() {

        for (int i = 0; i < this.data.size(); i++) {
            data.get(i).printAll();
        }
    }

//    public static void main(String[] args) {
//        DBManager db = new DBManager("dbconfig");
//        System.out.println("dbminpool : " + db.username);
//    }

}//db manager
