package team.work.utils.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import team.work.utils.convert.V;
import team.work.utils.convert.V;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SMS_func {
    private static String Activeid;
    private static String jobId;
    private static String[] sessionId;


    public static void main(String[] args) throws Exception {


        System.out.println(DirectSendSMSObj("17677256676", "测试"));


    }

    /*************新版API方法*****************/


    //直接发送短信：true为成功，false为失败(默认调用此方法)
    public static boolean DirectSendSMS(String numbers, String Content) {
        try {
            URL url1 = new URL("http://dxjk.51lanz.com/LANZGateway/DirectSendSMSs.asp");
            HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
            String result = Post(url1, connection1, "ReturnXJ=1&UserID=851637&Account=wangxin&Password=20DADCF9C2B06704F943183B1DD6FE1E2C27AAB1&ActiveID=" + Activeid + "&SMSType=1&Phones=" + numbers + "&Content=" + Content + "【民大网信中心】");

            JSONObject obj = JSON.parseObject(result);
            if (!V.isEmpty(obj)) {
                JSONObject res = obj.getJSONObject("LANZ_ROOT");
                if (res.getString("ErrorNum").equals("0")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }


    //直接发送短信：true为成功，false为失败(默认调用此方法)
    public static JSONObject DirectSendSMSObj(String numbers, String Content) {
        try {
            URL url1 = new URL("http://dxjk.51lanz.com/LANZGateway/DirectSendSMSs.asp");
            HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
            String result = Post(url1, connection1, "ReturnXJ=1&UserID=851637&Account=wangxin&Password=20DADCF9C2B06704F943183B1DD6FE1E2C27AAB1&ActiveID=" + Activeid + "&SMSType=1&Phones=" + numbers + "&Content=" + Content + "【民大网信中心】");

            JSONObject obj = JSON.parseObject(result);
            if (!V.isEmpty(obj)) {
                JSONObject res = obj.getJSONObject("LANZ_ROOT");
                return res;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    //直接获取上行短信
    public static JSONObject DirectFetchSMS() {
        try {
            URL url1 = new URL("http://dxjk.51lanz.com/LANZGateway/DirectFetchSMS.asp");
            HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
            String result = Post(url1, connection1, "ReturnXJ=1&UserID=851637&Account=wangxin&Password=20DADCF9C2B06704F943183B1DD6FE1E2C27AAB1");

            JSONObject obj = JSON.parseObject(result);
            if (!V.isEmpty(obj)) {
                JSONObject res = obj.getJSONObject("LANZ_ROOT");
                return res;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    //直接查询剩余短信量
    public static int DirectGetSMSStock() {
        int Stock = 0;
        try {
            URL url1 = new URL("http://dxjk.51lanz.com/LANZGateway/DirectGetSMSStock.asp");
            HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
            String result = Post(url1, connection1, "ReturnXJ=1&UserID=851637&Account=wangxin&Password=20DADCF9C2B06704F943183B1DD6FE1E2C27AAB1");

            JSONObject obj = JSON.parseObject(result);
            if (!V.isEmpty(obj)) {
                JSONObject res = obj.getJSONObject("LANZ_ROOT");
                if (!V.isEmpty(res.getString("Stock"))) {
                    Stock = Integer.parseInt(res.getString("Stock"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Stock;
    }


    /*************旧版API方法*****************/


    //登陆短信平台：设置Activeid，sessionId
    public static void Login() throws IOException {
        URL url = new URL("http://dxjk.51lanz.com/LANZGateway/Login.asp");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String sTotalString = Post(url, connection, "UserID=851637&Account=wangxin&Password=20DADCF9C2B06704F943183B1DD6FE1E2C27AAB1");
        String session_value = connection.getHeaderField("Set-Cookie");
        sessionId = session_value.split(";");
        Activeid = sTotalString.substring(sTotalString.indexOf("<ActiveID>") + 10, sTotalString.indexOf("</ActiveID>"));
    }

    //发送单条短信：true为成功，false为失败
    public static boolean SendSMS(String number, String Content) throws IOException {
        URL url1 = new URL("http://dxjk.51lanz.com/LANZGateway/SendSMS.asp");
        HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
        connection1.setRequestProperty("Cookie", sessionId[0]);   //再次连接的时候设置 session相同
        String result = Post(url1, connection1, "ActiveID=" + Activeid + "&SMSType=1&Phone=" + number + "&Content=" + Content + "【民大网信中心】");
        String checksend = result.substring(result.indexOf("<ErrorNum>") + 10, result.indexOf("</ErrorNum>"));
        if (checksend.equals("0")) {
            return true;
        } else {
            return false;
        }
    }


    //注销短信平台
    public static void Logoff() throws IOException {
        URL url4 = new URL("http://dxjk.51lanz.com/LANZGateway/Logoff.asp");           // 注销
        HttpURLConnection connection4 = (HttpURLConnection) url4.openConnection();
        connection4.setRequestProperty("Cookie", sessionId[0]);                       //再次连接的时候设置 session相同
        Post(url4, connection4, "ActiveID=" + Activeid);
    }

    //查询短信平台余额
    public static String GetSMSStock() throws IOException {
        URL url3 = new URL("http://dxjk.51lanz.com/LANZGateway/GetSMSStock.asp");      // 查询余额
        HttpURLConnection connection3 = (HttpURLConnection) url3.openConnection();
        connection3.setRequestProperty("Cookie", sessionId[0]);                       //再次连接的时候设置 session相同
        String result = Post(url3, connection3, "ActiveID=" + Activeid);
        String checksend = result.substring(result.indexOf("<ErrorNum>") + 10, result.indexOf("</ErrorNum>"));
        if (checksend.equals("0")) {
            return result.substring(result.indexOf("<Stock>") + 7, result.indexOf("</Stock>"));
        } else {
            return null;
        }

    }

    // 心跳 每分钟做一次 需要做一个心跳线程:true为登陆成功，false为未登录
    public static boolean HeartBeat() throws IOException {
        URL url2 = new URL("http://dxjk.51lanz.com/LANZGateway/HeartBeat.asp");        // 心跳 每分钟做一次 需要做一个心跳线程
        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
        connection2.setRequestProperty("Cookie", sessionId[0]);                       //再次连接的时候设置 session相同
        String result = Post(url2, connection2, "ActiveID=" + Activeid);
        String checksend = result.substring(result.indexOf("<ErrorNum>") + 10, result.indexOf("</ErrorNum>"));
        if (checksend.equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    //查询短信回复
    public static String FetchSMS() throws IOException {
        URL url5 = new URL("http://dxjk.51lanz.com/LANZGateway/FetchSMS.asp");        // 上行短信
        HttpURLConnection connection5 = (HttpURLConnection) url5.openConnection();
        connection5.setRequestProperty("Cookie", sessionId[0]);                       //再次连接的时候设置 session相同
        String result = Post(url5, connection5, "ActiveID=" + Activeid);
        String checksend = result.substring(result.indexOf("<ErrorNum>") + 10, result.indexOf("</ErrorNum>"));
        if (checksend.equals("0")) {
            return result.substring(result.indexOf("<rsSMS>") + 7, result.indexOf("</rsSMS>"));
        } else {
            return null;
        }

    }

    //查询短信发送结果
    public static String FetchSendStat() throws IOException {
        URL url6 = new URL("http://dxjk.51lanz.com/LANZGateway/FetchSendStat.asp");    // 状态报告
        HttpURLConnection connection6 = (HttpURLConnection) url6.openConnection();
        connection6.setRequestProperty("Cookie", sessionId[0]);                       //再次连接的时候设置 session相同
        String result = Post(url6, connection6, "ActiveID=" + Activeid);
        String checksend = result.substring(result.indexOf("<ErrorNum>") + 10, result.indexOf("</ErrorNum>"));
        if (checksend.equals("0")) {
            return result.substring(result.indexOf("<rsResult>") + 10, result.indexOf("</rsResult>"));
        } else {
            return null;
        }

    }


    //向短信平台发出请求
    public static String Post(URL url, HttpURLConnection connection, String SMSContent) throws IOException {
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GBK");
        out.write(SMSContent);
        out.flush();
        out.close();
        String sCurrentLine;
        String sTotalString;
        sCurrentLine = "";
        sTotalString = "";
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString += sCurrentLine + "\r\n";
        }
        //System.out.println(sTotalString);
        return sTotalString;
    }


}
