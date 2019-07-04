package team.work.utils.tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;

public class MD5Util {
    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMd5(String string) {
        try {
            byte[] bs = md5.digest(string.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(40);
            for (byte x : bs) {
                if ((x & 0xff) >> 4 == 0) {
                    sb.append("0").append(Integer.toHexString(x & 0xff));
                } else {
                    sb.append(Integer.toHexString(x & 0xff));
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        String key = "8sv52um9xp" + "/bak/Db_gxun_ktkq_20181108_010001.sql.gz" + (date.getTime() / 1000);
        System.out.println(date.getTime() / 1000);
        System.out.println(key);
        String sign = getMd5(key);
        System.out.println(sign);


    }

}
