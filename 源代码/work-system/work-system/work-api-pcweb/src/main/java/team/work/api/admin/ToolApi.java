package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.exception.AbstractCosException;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import team.work.api.BaseApi;
import team.work.core.service.impl.SysTpsConfigService;
import team.work.core.service.impl.SysUserService;
import team.work.utils.convert.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;



@Api(value = "00_系统工具")
@RestController
@RequestMapping("/v1/tool")
public class ToolApi extends BaseApi {

    @Autowired
    private SysTpsConfigService tpsConfigService;


    @GetMapping("/savePicUrl/{url}/")
    @ApiOperation(value = "存储外网图片")
    public Object saveUrl(@PathVariable("url") String url) throws AbstractCosException {
        JSONObject cos = tpsConfigService.getByCode("cos");
        Credentials cred = new Credentials(cos.getLong("appId"),
                cos.getString("secretId"),
                cos.getString("secretKey"));
        ClientConfig clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
        clientConfig.setRegion(cos.getString("region"));
        COSClient cosClient = new COSClient(clientConfig, cred);
        Date date = new Date();
        String now = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

        String path = "/partySystem/picture/" + "pic_" + now + "_" + generateString(8) + "." + getSuffix(url);

        String decodeUrl = decode(url);

        try {
            URL downurl = new URL(decodeUrl);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) downurl.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);

            UploadFileRequest uploadFileRequest = new UploadFileRequest(cos.getString("bucket"), path, data);
            String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
            JSONObject json = JSONObject.parseObject(uploadFileRet);
            return R.ok("上传数据", json);


        } catch (MalformedURLException e) {
            e.printStackTrace();
            return R.error("上传失败");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("上传失败");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("上传失败");

        }

    }

    String getSuffix(String _fileName) {
        String suffix = "jpg";
        String[] item = _fileName.split(".");
        if (item.length > 0) {
            suffix = item[(item.length - 1)];
        }
        return suffix;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static String decode(String url) {
        //   try {
        String prevURL = "";
        String decodeURL = url;
        while (!prevURL.equals(decodeURL)) {
            prevURL = decodeURL;
            decodeURL = decodeURL.replace("*2", ":");
            decodeURL = decodeURL.replace("*1", "/");
            decodeURL = decodeURL.replace("*3", "?");
            decodeURL = decodeURL.replace("*4", "=");
            decodeURL = decodeURL.replace("*5", "&");
            decodeURL = decodeURL.replace("*6", ".");

        }
        return decodeURL;

    }

    public String generateString(int length) {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    @Autowired
    private SysUserService userService;


    @GetMapping("/user/exist/{number}")
    @ApiOperation(value = "学号查询用户是否存在")
    public Object exsistNumber(@PathVariable("number") String number,
                               @RequestHeader("token") String token) {

        if (!userService.existNumber(number))
            return R.error("用户不存在");
        return R.ok("用户存在");

    }

}
