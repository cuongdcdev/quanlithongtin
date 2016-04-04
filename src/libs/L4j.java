/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Cường <duongcuong96 at gmail dot com>
 */
public class L4j {

    private static final Logger logger = Logger.getLogger(L4j.class);
    private static boolean isDebug = true;
    
    
    public void setDebug(boolean isDebug){
        this.isDebug = isDebug;
        System.out.println("L4j , app debug set to : " + this.isDebug );
    }
    public L4j(){
    }
    public void loadConfig(String path) throws IOException {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(path);
            prop.load(in);

        } catch (IOException e) {
            BasicConfigurator.configure();
            logger.error("cannot open L4j.properties file , so use default config ! , this log file in <app_root>/L4j.properties !  ");
        } finally {
            in.close();
        }
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
    
}
