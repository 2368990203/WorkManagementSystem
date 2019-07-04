package team.work.api.unified;

import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.core.async.TokenAsync;
import team.work.core.model.*;
import team.work.core.service.impl.*;
import team.work.core.service.impl.*;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.tool.SMS_func;
import team.work.core.tps.CacheKit;
import team.work.doc.ForgetPassword;
import team.work.doc.SysLogin;
import team.work.utils.convert.*;
import team.work.utils.kit.MD5Kit;
import team.work.utils.kit.OSKit;
import team.work.utils.kit.TimeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


@Api(value = "公共信息管理")
@RestController
@RequestMapping("/v1/common")
@CrossOrigin   //跨域服务注解
public class CommonApi extends BaseApi {
    @Autowired
    private SysGlobalConfigService configService;

    @GetMapping("/resources/{time}/{fileName}.{suffix}")
    @ApiOperation(value = "读取文件")
    public void getFile(@PathVariable("time") String time,
                        @PathVariable("fileName") String fileName,
                        @PathVariable("suffix") String suffix,
                        HttpServletResponse response) throws IOException {
        try {
            JSONObject setting = configService.getSetting();
            Boolean linux = OSKit.isLinux();
            String filePath = "";
            if (linux) {
                filePath = S.apppend(setting.getString("linuxPath"), "/", time, "/", fileName, ".", suffix);
            } else {
                filePath = S.apppend(setting.getString("basePath"), "\\", time, "\\", fileName, ".", suffix);
            }
            FileInputStream inputStream = new FileInputStream(filePath);
            int i = inputStream.available();
            //byte数组用于存放图片字节数据
            byte[] buff = new byte[i];
            inputStream.read(buff);
            //记得关闭输入流
            inputStream.close();
            //设置发送到客户端的响应内容类型
            response.setContentType("image/*");
            OutputStream out = response.getOutputStream();
            out.write(buff);
            //关闭响应输出流
            out.close();
        } catch (Exception ex) {

        }
    }



}
