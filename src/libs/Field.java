/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.text.SimpleDateFormat;

/**
 *
 * @author Cường <duongcuong96 at gmail dot com>
 */
public class Field {

    public String phone;
    public String menuId;
    public String time;
    public String content;
    public String currentLine;
    public String sqlId;

    public Field(String s) {
        String[] lineArray = s.split(",");
        menuId = lineArray[0];
        phone = lineArray[1];
        time = lineArray[2];
        content = lineArray[3];
        currentLine = s;

    }
    public Field(String phoneNumber , String sendTo , String contentt){
        SimpleDateFormat time_formatter = new SimpleDateFormat("dd-MMM-YYYY HH:mm:ss");
        String current_time_str = time_formatter.format(System.currentTimeMillis());
        menuId = phoneNumber;
        phone = sendTo;
        content = contentt;
        time = current_time_str;
    }
    
     public Field(String sqlIdd , String phoneNumber , String sendTo    , String timee , String contentt){
        menuId = phoneNumber;
        phone = sendTo;
        content = contentt;
        time = timee;
        sqlId = sqlIdd;
        
    }

    public String getPhone() {
        return phone;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getCurrentLine() {
        return currentLine;
    }


    public void printAll() {
        System.out.println("\n --------------------------------------");
        System.out.println("this line : " + this.currentLine);
        System.out.println("phone : " + this.phone);
        System.out.println("menuID : " + this.menuId);
        System.out.println("time : " + this.time);
        System.out.println("content :  " + this.content);
        System.out.println("----------------------------------------- \n");
    }
    
    @Override
        public String toString(){
            return "Field : sqlID : " + this.sqlId + " content : " + this.content;
        }
}
