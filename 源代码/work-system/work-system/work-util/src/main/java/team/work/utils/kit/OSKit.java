package team.work.utils.kit;

import java.util.Properties;


public class OSKit {
    public static Boolean isLinux(){
        Properties props = System.getProperties();
        String os = props.getProperty("os.name").toLowerCase();
        if(os.indexOf("windows") < 0){
            return true;
        }else {
            return false;
        }
    }
}
