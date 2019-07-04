package team.work.utils.tool;


import com.alibaba.fastjson.JSONObject;

public class WeixinPushUtil {
    /**
     * 向微信平台发送推送信息
     *
     * @param openid  接收人id
     * @param title   标题
     * @param content 通知详情
     * @param url     跳转链接
     * @param type    模板类型
     * @return boolean 所代表远程资源的响应结果
     */
    public static boolean sendPush(String openid, String title, String content, String url, String type) {
        //微信推送流程 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
        PropertiesUtil db = new PropertiesUtil("conf.properties");
        String weixin_appId = db.readProperty("weixin.appId");
        String weixin_appsecret = db.readProperty("weixin.appsecret");
        String weixin_template_info = db.readProperty("weixin.template_info");
        String weixin_template_work = db.readProperty("weixin.template_work");
        String weixin_template_id = "";
        if (type.equals("work")) {
            weixin_template_id = weixin_template_work;
        } else if (type.equals("info")) {
            weixin_template_id = weixin_template_info;
        }

        //1、获取作业管理系统应用授权密钥
        String tokenStr = HttpTookit.sendPost("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" + weixin_appId + "&secret=" + weixin_appsecret);
        //appid和secret从微信后台获取
        if (tokenStr.indexOf("access_token") != -1) {
            JSONObject tokenData = JSONObject.parseObject(tokenStr.replace("\\", ""));
            String token = tokenData.getString("access_token");
            System.out.println(token);

            //2、推送模板消息
            //后台模板内容 尊敬的{{name.DATA}}： {{result.DATA}}
            JSONObject postData = new JSONObject();
            postData.put("touser", openid);//接收人识别码（每个微信号对不同公众号有不同识别码）
            postData.put("template_id", weixin_template_id);//模板ID（公众平台后台获取）
            postData.put("url", url);//通知链接地址

            JSONObject data = new JSONObject();//推送数据包生成
            JSONObject titleObj = new JSONObject();
            titleObj.put("value", title);
            titleObj.put("color", "#FF0000");
            data.put("title", titleObj);
            JSONObject contentObj = new JSONObject();
            contentObj.put("value", content);
            data.put("content", contentObj);
            postData.put("data", data);//推送数据

            System.out.println(postData.toString());

            String sendStr = HttpTookit.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token, postData.toString());
            JSONObject sendData = JSONObject.parseObject(sendStr.replace("\\", ""));
            System.out.println(sendStr);
            int code = sendData.getInteger("errcode");
            if (code == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 向微信平台发送推送信息
     *
     * @param openid 接收人id
     * @param url    跳转链接
     * @param type   模板类型
     * @param data   推送数据包：格式 {"name":{"value":"李文"},"result":{"value":"您已成功注册作业管理系统服务平台"}}
     * @return boolean 所代表远程资源的响应结果
     */
    public static boolean sendPush(String openid, String url, String type, JSONObject data) {
        //微信推送流程 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
        PropertiesUtil db = new PropertiesUtil("conf.properties");
        String weixin_appId = db.readProperty("weixin.appId");
        String weixin_appsecret = db.readProperty("weixin.appsecret");
        String weixin_template_info = db.readProperty("weixin.template_info");
        String weixin_template_work = db.readProperty("weixin.template_work");
        String weixin_template_id = "";
        if (type.equals("work")) {
            weixin_template_id = weixin_template_work;
        } else if (type.equals("info")) {
            weixin_template_id = weixin_template_info;
        }

        //1、获取作业管理系统应用授权密钥
        String tokenStr = HttpTookit.sendPost("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" + weixin_appId + "&secret=" + weixin_appsecret);
        //appid和secret从微信后台获取
        if (tokenStr.indexOf("access_token") != -1) {
            JSONObject tokenData = JSONObject.parseObject(tokenStr.replace("\\", ""));
            String token = tokenData.getString("access_token");
            System.out.println(token);

            //2、推送模板消息
            //后台模板内容 尊敬的{{name.DATA}}： {{result.DATA}}
            JSONObject postData = new JSONObject();
            postData.put("touser", openid);//接收人识别码（每个微信号对不同公众号有不同识别码）
            postData.put("template_id", weixin_template_id);//模板ID（公众平台后台获取）
            postData.put("url", url);//通知链接地址


            postData.put("data", data);//推送数据

            System.out.println(postData.toString());

            String sendStr = HttpTookit.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token, postData.toString());
            JSONObject sendData = JSONObject.parseObject(sendStr.replace("\\", ""));
            System.out.println(sendStr);
            int code = sendData.getInteger("errcode");
            if (code == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static void main(String[] args) {
        sendPush("owVgRxIk3yTZBmWo7e9aqcw-VZbw", "黄廷程", "您已成功注册作业管理系统服务平台", "http://mengwp.gxun.edu.cn/partyweb/", "info");
        JSONObject data = new JSONObject();//推送数据包生成
        JSONObject titleObj = new JSONObject();
        titleObj.put("value", "老王");
        data.put("title", titleObj);
        JSONObject contentObj = new JSONObject();
        contentObj.put("value", "您已成功注册作业管理系统服务平台");
        data.put("content", contentObj);
        sendPush("owVgRxIk3yTZBmWo7e9aqcw-VZbw", "http://mengwp.gxun.edu.cn/partyweb/", "info", data);
        //owVgRxIk3yTZBmWo7e9aqcw-VZbw


    }

}
