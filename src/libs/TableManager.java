/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cường <duongcuong96 at gmail dot com>
 * @param quan li table !
 */
public class TableManager {

//    public static DefaultTableModel tableModel = null;
//    public static JTable mainTable = null;

//    public TableManager(JTable table) {
//        TableManager.mainTable = table;
//        table.setModel(TableManager.tableModel);
//        //cho phep chon nhieu row 1 luc 
//        table.setRowSelectionAllowed(true);
//        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        //getSelectedRowsId();
//    }

    public static void addRow(DefaultTableModel table, Field currentField) {
        table.addRow(new Object[]{currentField.getMenuId(), currentField.getPhone(),
            currentField.getTime(), currentField.getContent()});
    }
    
    public static void removeRow(DefaultTableModel table, int idRow) {
        try {
            table.removeRow(idRow);
            System.out.println("Remove row id : " + idRow);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error , id row invalid ! " + e);
            e.printStackTrace();
        }
    }

    public static int[] getSelectedRowsId(JTable mainTable) {
        if (mainTable.getCellSelectionEnabled()) {
            mainTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            int[] rowIdArr = mainTable.getSelectedRows();
            return rowIdArr;
        } else {
            System.out.println("Error , no rows had selected");
            return null;
        }
    }

    public static void removeAll(JTable mainTable) {
       DefaultTableModel dtm = (DefaultTableModel) mainTable.getModel();
       dtm.setRowCount(0);
    }
    
    public static void updateRow(JTable mainTable ,String values ,  int rowId , int colId ){
        DefaultTableModel dtm = (DefaultTableModel) mainTable.getModel();
        dtm.setValueAt(values, rowId, colId);
    }
   

}//table manager 
