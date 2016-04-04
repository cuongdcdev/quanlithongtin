/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;


import java.util.Properties;
import java.io.*;

//myconfig lay file baseconfig.properties được đặt ngang hàng với cả class này để xử lí các config khác ! 
 public class MyConfig {
    private Properties prop = new Properties(); 
    private String baseconfig = "baseconfig.properties"; //file config nay de o ROOT folder ! 
    private String baseConfigFolder;

    
    public MyConfig(){
        try{
            FileReader in = new FileReader(this.baseconfig);
            prop.load(in);
            baseConfigFolder = prop.getProperty("BASE_CONFIG_FOLDER");
        }catch(IOException e){
            System.out.println("Error load file : " +  e);
            e.printStackTrace();
        }
    }
    
    //load config hàm này sẽ lấy về file config theo bas
    public void loadConfig(String filename){
        try{
            String file = this.baseConfigFolder +"/"+ filename + ".properties";
            FileReader in = new FileReader(file);
            prop.load(in);
//            System.out.println("Baseconfig : " + baseconfig);
        }catch(IOException e ){
            if(baseConfigFolder == null){
                System.out.println("MyConfig was not init() , please init() first ! ");
            }
               System.out.println("Error load config file : " + e);
               e.printStackTrace();
        }
    }
    
    public String getConfig(String configName){
        return prop.getProperty(configName,null);
    }
    
     
     public String getBaseConfigFolder(){
         return this.baseConfigFolder;
     }
     
     public static void main(String[] args) {
         MyConfig mc = new MyConfig();
         System.out.println("baseconfig :  " + mc.getConfig("BASE_CONFIG_FOLDER"));
     }
     
}
