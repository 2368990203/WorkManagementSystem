package team.work.utils.tool;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpTookit {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static void main(String[] args) {
        //微信推送流程 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
        //1、获取作业管理系统应用授权密钥
        String appid = "wxe31c11e57e7dc6e5";
        String secret = "be35c4eeedbcf82beab6884d830f78ee";

        String s1 = HttpTookit.sendPost("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" + appid + "&secret=" + secret);
        //appid和secret从微信后台获取
        System.out.println(s1);
        JSONObject tokenData = JSONObject.parseObject(s1.replace("\\", ""));
        String token = tokenData.getString("access_token");
        System.out.println(token);
        //2、设置推送模板
        //后台测试模板内容 尊敬的{{name.DATA}}： {{result.DATA}}
        JSONObject postData = new JSONObject();
        postData.put("touser", "owVgRxIk3yTZBmWo7e9aqcw-VZbw");//接收人识别码（每个微信号对不同公众号有不同识别码）
        postData.put("template_id", "moUJGeRGkuFRI6OLmZnDRvlFcFx490aIMBf_gVJLCuM");//模板ID（公众平台后台获取）
        postData.put("url", "http://mengwp.gxun.edu.cn/partyweb/");//通知链接地址
        JSONObject data = new JSONObject();//推送数据包生成
        JSONObject name = new JSONObject();
        name.put("value", "李文");
        data.put("name", name);
        JSONObject result = new JSONObject();
        result.put("value", "您已成功注册作业管理系统服务平台");
        data.put("result", result);
        postData.put("data", data);//推送数据
        System.out.println(postData.toString());

        //3、发送推送信息
        String s2 = HttpTookit.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token, postData.toString());
        JSONObject sendData = JSONObject.parseObject(s2.replace("\\", ""));
        System.out.println(s2);
        int code = sendData.getInteger("errcode");
        if (code == 0) {
            System.out.println("推送成功！");
        }

        //https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN


    }

}
