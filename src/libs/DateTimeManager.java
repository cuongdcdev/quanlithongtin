/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Cường <duongcuong96 at gmail dot com>
 */
public class DateTimeManager {

    public static String now() {
        SimpleDateFormat formated_date = new SimpleDateFormat("dd-MMM-YYY HH:mm:ss");
        String now;
        Date date = new Date(System.currentTimeMillis());
        now = formated_date.format(date );
        return now;
    }
}
