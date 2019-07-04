package team.work.utils.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeKit {
    /**
     * 获取当前服务器时间Date
     * */
    private static Date now(){
        return new Date();
    }

    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(long lt){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDate(long lt,String sdf){
        String res;
        if(sdf==null || sdf.length()==0){
            sdf = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
    * 将时间转换为时间戳
    */
    public static long dateToStamp(String s) {
        long res = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            res = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res > 0 ? res/1000 : 0;
    }

    public static long dateToStamp(String s,String sdf) {
        long res = 0;
        if(sdf==null || sdf.length()==0){
            sdf = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            res = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res > 0 ? res/1000 : 0;
    }

    /**
     * 判断一个字符串是否是日期格式
     * @param str
     * @param sdf
     * @return
     */
    public static boolean isValidDate(String str,String sdf) {
        boolean convertSuccess=true;
        if(sdf==null || sdf.length()==0){
            sdf = "yyyy/MM/dd HH:mm";
        }
    // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(sdf);
        try {
    // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }
    /**
     * 获取当前时间戳
     * */
    public static long getTimestamp(){
        return System.currentTimeMillis()/1000;
    }
    public static long getTimestamps(){
        return System.currentTimeMillis();
    }
    /**
     * 格式化当前时间
     * @param format
     *      时间格式 例如：yyyy-MM-dd
     * */
    public static String formatNow(String format){
        return new SimpleDateFormat(format)
                .format(now());
    }
    /**
     * 格式化当前时间
     * @param date
     *      时间
     * @param format
     *      时间格式 例如：yyyy-MM-dd
     * */
    public static String formatDate(Date date,String format){
        return new SimpleDateFormat(format)
                .format(date);
    }
    public static String formatDate(String format){
        return new SimpleDateFormat(format)
                .format(new Date().getTime());
    }
    /**
     * 获得所在星期的第一天
     */
    public static Date getFirstDateByWeek(Date date) {

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int today = now.get(Calendar.DAY_OF_WEEK);
        int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
        now.set(now.DATE, first_day_of_week);
        return now.getTime();
    }

    /**
     * 获得所在星期的最后一天
     */
    public static Date getLastDateByWeek() {
        Calendar now = Calendar.getInstance();
        now.setTime(now());
        int today = now.get(Calendar.DAY_OF_WEEK);
        int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
        int last_day_of_week = first_day_of_week + 6; // 星期日
        now.set(now.DATE, last_day_of_week);
        return now.getTime();
    }

    /**
     * 获得所在月份的最后一天
     */
    public static Date getLastDateByMonth() {
        Calendar now = Calendar.getInstance();
        now.setTime(now());
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
        now.set(Calendar.DATE, 1);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
        now.set(Calendar.HOUR, 11);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        return now.getTime();
    }

    /**
     * 获得所在月份的第一天
     */
    public static Date getFirstDateByMonth() {
        Calendar now = Calendar.getInstance();
        now.setTime(now());
        now.set(Calendar.DATE, 0);
        now.set(Calendar.HOUR, 12);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }
}
