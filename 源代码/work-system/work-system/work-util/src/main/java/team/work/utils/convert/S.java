package team.work.utils.convert;

import java.util.Random;
import java.util.UUID;


public class S {
    /**
     * 随机生成字符串
     * @param length 表示生成字符串的长度
     * */
    public static String random(int length) {
        String base = "abcdefghjkmnpqrstuvwxyz23456789";
        Random random = new Random();
        String r = "";
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            r = r + (base.charAt(number));
        }
        return r;
    }
    /**
     * 随机生成6位数数字
     * */
    public static String randomNum() {
        Random r = new Random();
        return (r.nextInt(900000)+100000) +"";
    }
    public static int randomNums() {
        Random r = new Random();
        return (r.nextInt(900000)+100000);
    }
    /**
     * 利用UUID生成Token字符串
     * */
    public static String getToken(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }

    /**
     * 数字格式化 前面补0
     * */
    public static String supplyNumber(int len,Object number){
        String supply = "";
        int size = number.toString().length();
        if(len > size){
            int x = len - size;
            for(int i=0;i<x;i++){
                supply += "0";
            }
            supply += number.toString();
            return supply;
        }
        return number.toString();
    }
    /**
     * 拼接字符串
     * */
    public static String apppend(String... str) {
        StringBuffer buf = new StringBuffer();
        for(String val:str){
            buf.append(val) ;
        }
        return buf.toString();
    }
    public static String apppend(Object... str) {
        StringBuffer buf = new StringBuffer();
        for(Object val:str){
            buf.append(val.toString()) ;
        }
        return buf.toString();
    }
    public static String format(String format,String... str) {
        return String.format(format,str);
    }
    public static String toIdsString(String str){
        return str ==null||"0".equals(str)?"":str;
    }
}

