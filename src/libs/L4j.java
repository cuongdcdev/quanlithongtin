/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import org.apache.log4j.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Cường <duongcuong96 at gmail dot com>
 */
public class L4j {

    private static final Logger logger = Logger.getLogger( L4j.class.getName() ); // chỉ log lỗi cho class L4j này ! 
    private static boolean isDebug = true;
    
    
    public void setDebug(boolean isDebug){
        this.isDebug = isDebug;
        System.out.println("L4j , app debug set to : " + this.isDebug );
    }
    public L4j(String path){
            System.out.println("Path="+path);
            loadConfig(path);
    }
    
    public void loadConfig(String path) {
        PropertyConfigurator.configure(path);
    }
    
    public void log(String type, String mess) {
        if(isDebug == false) return;
        switch (type) {
            case "debug":
                logger.debug(mess);
                break;

            case "info":
                logger.debug(mess);
                break;

            case "error":
                logger.error(mess);
                break;

            default:
                logger.info(mess);
        }
    };
    
    public void log(String mess){
        System.out.println(mess);
        logger.error(mess);
    }
    
}
