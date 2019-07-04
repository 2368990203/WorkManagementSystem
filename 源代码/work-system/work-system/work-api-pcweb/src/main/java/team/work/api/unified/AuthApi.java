package team.work.api.unified;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import team.work.core.model.*;
import team.work.core.service.impl.*;
import team.work.utils.convert.*;
import team.work.utils.tool.SMS_func;
import team.work.doc.ForgetPassword;
import team.work.utils.tool.RSAEncrypt;
import team.work.utils.convert.*;
import team.work.core.async.TokenAsync;
import team.work.core.service.impl.*;
import team.work.doc.SysLogin;
import team.work.api.BaseApi;
import team.work.utils.kit.MD5Kit;
import team.work.utils.kit.TimeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;
import team.work.core.model.*;
import team.work.doc.SysLogin;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Api(value = "系统授权管理")
@RestController
@RequestMapping("/v1/common")
@CrossOrigin   //跨域服务注解
public class AuthApi extends BaseApi {
    @Autowired
    private TokenAsync tokenAsync;
    @Autowired
    public SysUserService userService;
    @Autowired
    public SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private AcademyInfoService academyInfoService;
    @Autowired
    private SysOperationRecordService sysOperationRecordService;

    @GetMapping("/getPublicKey")
    @ApiOperation(value = "获取登陆公钥")
    public Object getPublicKey(@RequestHeader("token") String token) {
        Map<Integer, String> keyMap = RSAEncrypt.genKeyPair();

        cacheKit.setVal(token + "_privateKey", keyMap.get(1), 0);

        return R.ok("获取登陆公钥成功", keyMap.get(0));

    }


    @GetMapping("/verify-code")
    @ApiOperation(value = "获取登录验证码")
    public Object getSysVerifyCode(HttpServletRequest request) {
        JSONObject verify = new JSONObject();
        String ip = getIpAddr(request);
        String token = "";
        String code = S.random(4);
        token = S.getToken();
        verify.put("ip", ip);
        verify.put("code", code);
        verify.put("token", token);
        //cacheKit.setVal(ip, token, 120);
        cacheKit.setVal(token, J.o2s(verify), 120);

        return R.ok("获取验证码成功", verify);
    }

    @Autowired
    private Producer captchaProducer;

    @GetMapping("/verify-code/jpg")
    @ApiOperation(value = "获取登录验证码")
    public Object getSysVerifyCodePic(HttpServletRequest request) {

        String capText = captchaProducer.createText();

        JSONObject verify = new JSONObject();
        String ip = getIpAddr(request);
        String token = "";
        token = S.getToken();
        verify.put("ip", ip);
        verify.put("code", capText);
        verify.put("token", token);
        //  cacheKit.setVal(ip, token, 120);
        cacheKit.setVal(token, J.o2s(verify), 120);
        JSONObject res = new JSONObject();
        res.put("token", token);

        try {
            BufferedImage bi = captchaProducer.createImage(capText);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
            ImageIO.write(bi, "jpg", baos);//写入流中
            byte[] bytes = baos.toByteArray();//转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
            res.put("jpg", "data:image/*;base64," + png_base64);

            return R.ok("获取验证码成功", res);


        } catch (IOException e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }

    }


    @PostMapping("/login")
    @ApiOperation(value = "用户统一登录")
    public Object login(@RequestBody SysLogin object,
                        @RequestHeader("token") String token) {

        // 操作日记初始化
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        SysOperationRecord sysOperationRecord = new SysOperationRecord();
        String ip = getIpAddr(request);
        sysOperationRecord.setIpAddr(ip);

        // 数据验证
        String str = cacheKit.getVal(token);
        cacheKit.deleteVal(token);
        if (V.isEmpty(str))
            return R.error("验证码已经过期请重新获取验证码");


        JSONObject json = J.s2j(str);
        String code = json.getString("code");

        if (V.isEmpty(code))
            return R.error("验证码已经过期请重新获取验证码2");
        if (V.isEmpty(object.getCode()))
            return R.error("请输入验证码");
        if (!V.isEqual(code, object.getCode()))
            return R.error("您输入的验证码不正确");
        if (V.isEmpty(token))
            return R.error("请先获取验证码");
        if (V.isEmpty(object.getLoginName()))
            return R.error("请输入登录名");
        if (V.isEmpty(object.getPassword()))
            return R.error("请输入登录密码");


        String passwordStr = object.getPassword().replace(" ", "+");
        String privateKey = cacheKit.getVal(token + "_privateKey");
        String password = RSAEncrypt.decrypt(passwordStr, privateKey);
        cacheKit.deleteVal(token + "_privateKey");
        object.setPassword(password);


        // 业务处理
        JSONObject user = J.o2j(sysUserService.findByLoginName(object.getLoginName()));
        if (user == null)
            return R.error("登录名或密码错误");

        //存在该用户
        SysUser sysUser = sysUserService.findByUserName(object.getLoginName());

        sysOperationRecord.setCreator(user.getString("id"));
        sysOperationRecord.setCreateTime((int) TimeKit.getTimestamp());
        sysOperationRecord.setControl("系统授权管理");
        sysOperationRecord.setFunction("用户统一登录");
        sysOperationRecord.setServer(adminServer);

        //判断是否禁用
        if (sysUser.getStatus() == 0) {
            return R.error("您的账号当前已被禁用，请联系管理员");
        }

        String userId = user.getString("id");

        // 数据处理
        user.remove("password");
        user.remove("salt");
        String userToken = S.getToken();
        user.put("token", userToken);
        user.put("role", "");
        user.put("lock", 0);
        user.put("type", sysUser.getType());
        user.put("auth", new ArrayList<>());
        // 权限
        SysUserRole ur = userRoleService.findByUserId(userId);
        if (ur != null) {
            user.put("auth", sysUserService.getUserAuth(user.getString("id")));
//            user.put("auth", roleService.getAuthInfoByRoleId(ur.getRoleId()));
            user.put("role", ur.getRoleId());
        }

        SysUserToken ut = new SysUserToken();
        ut.setUserId(userId);
        ut.setToken(userToken);
        ut.setType("redis");
        ut.setServer(adminServer);
        tokenAsync.updateToken(ut);

        List<JSONObject> sysUserRoles = sysRoleService.getUserRoleByUserId(userId);

        cacheKit.deleteVal("verify_" + object.getLoginName() + "_count");

        // 缓存处理
        if (initCacheUser(user, userToken, 0)) {
            sysUser.setLoginIp(ip);
            sysUser.setLoginTime((int) TimeKit.getTimestamp());
            sysUserService.update(sysUser);
            sysOperationRecord.setStatus(0);
            sysOperationRecordService.createSysOperationRecord(sysOperationRecord);
            return R.ok(user);
        }
        return R.ok("缓存处理失败", user);
    }


//    @GetMapping("/verify_code/{phone}")
//    @ApiOperation(value = "获取找回密码验证码")
//    public Object getVerifyCode(@PathVariable String phone) throws IOException {
//        // 验证该手机号是否已绑定
//        SysUser sysUser = sysUserService.findByPhone(phone);
//        if (sysUser == null) {
//            return R.error("该手机号未被绑定");
//        }
//
//        //当前时间
//        Long nowTime = TimeKit.getTimestamps();
//        Long unlockTime = sysUser.getUnlockTime().longValue() * 1000;
//        Integer errorCount = sysUser.getErrorCount();
//
//        //判断是否禁用
//        if (sysUser.getStatus() == 0) {
//            return R.error("您的账号当前已被禁用，请联系管理员");
//        }
//
//
//        int waitTime = Integer.parseInt(TimeKit.stampToDate(unlockTime - nowTime, "mm"));
//        //解锁时间大于当前时间
//        if (unlockTime > nowTime) {
//            return R.error("你的账号暂时被冻结，请" + waitTime + "分钟后再试");
//        }
//        //当前时间已经超过解锁时间的就重置解锁时间为0
//        sysUser.setUnlockTime(0);
//
//        //次数大于大于设定值则禁用，默认5次
//        int canError = 5;
//        List<Dictionary> dictionary = dictionaryService.findByFieldName("errorCount");
//
//        Integer status = dictionary.get(0).getStatus();
//        if (status == 1) {//1-启用
//            try {
//                canError = Integer.parseInt(dictionary.get(0).getValue());
//            } catch (Exception e) {
//
//            }
//
//            if (errorCount == canError) {
//                return R.error("您尝试登录的次数已超过" + canError + "次,请" + waitTime + "分钟后再试");
//            }
//        }
//
//
//        String verifyCode = S.randomNum();
//
//        sysUser.setVerifyCode(verifyCode);
//        sysUserService.update(sysUser);
//
//
//        String content = "验证码：" + verifyCode + "，您正在使用作业管理系统重置密码功能，该验证码仅用于身份验证，请勿泄露给他人使用。" + "(作业管理)";
//
//
//        //发送1-验证码
//        if (sysUserService.update(sysUser) == true) {
//            Note note = new Note();
//            note.setPhone(phone);
//            note.setType(1);
//            note.setDetail(content);
//            note.setSendTime((int) TimeKit.getTimestamp());
//            note.setTheme("找回密码");
//
//            if (SMS_func.DirectSendSMS(phone, content)) {
//                note.setSendStatus(2);//2-已发送-成功
//                noteService.createNote(note);
//                return R.ok("发送成功", S.getToken());
//
//            } else {
//                note.setSendStatus(1);//1-未发送-失败
//                noteService.createNote(note);
//                return R.error("发送失败");
//
//            }
//
//        } else {
//            return R.error("发送失败");
//        }
//    }


    @PostMapping("/forget")
    @ApiOperation(value = "忘记密码")
    public Object forgetPassword(@RequestBody ForgetPassword object,
                                 @RequestHeader("token") String token) {
        // 操作日记初始化
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        SysOperationRecord sysOperationRecord = new SysOperationRecord();
        String ip = getIpAddr(request);
        sysOperationRecord.setIpAddr(ip);

        String passwordStr1 = object.getNewPassword1().replace(" ", "+");
        String passwordStr2 = object.getNewPassword2().replace(" ", "+");

        String privateKey = cacheKit.getVal(token + "_privateKey");


        String password1 = RSAEncrypt.decrypt(passwordStr1, privateKey);
        String password2 = RSAEncrypt.decrypt(passwordStr2, privateKey);

        cacheKit.deleteVal(token + "_privateKey");

        object.setNewPassword1(password1);

        object.setNewPassword2(password2);


        if (V.isEmpty(object.getPhone()))
            return R.error("请输入手机号码");
        if (V.isEmpty(object.getVerifyCode()))
            return R.error("请输入验证码");
        if (!object.getNewPassword1().equals(object.getNewPassword2())) {
            return R.error("新密码不一致");
        }
        wheres = W.f(
                W.and("phone", "eq", object.getPhone()),
                W.and("verifyCode", "eq", object.getVerifyCode()),
                W.and("isDel", "eq", "0")
        );
        List<SysUser> users = sysUserService.query(wheres);
        if (users == null || users.size() == 0) {
            return R.error("请检查手机号或验证码");
        }
        SysUser userInfo = users.get(0);
        //存储操作日记
        sysOperationRecord.setCreator(userInfo.getId());
        sysOperationRecord.setCreateTime((int) TimeKit.getTimestamp());
        sysOperationRecord.setControl("系统授权管理");
        sysOperationRecord.setFunction("重置密码");
        sysOperationRecord.setServer(pcServer);

        JSONObject user = new JSONObject();
        String salt = S.randomNum();
        String password = MD5Kit.encode(S.apppend(object.getNewPassword1(), salt));
        user.put("id", userInfo.getId());
        user.put("salt", Integer.parseInt(salt));
        user.put("password", password);
        user.put("verifyCode", "");
        user.put("errorCount", 0);
        user.put("unlockTime", 0);
        Object o = sysUserService.updateUser(user);
        if (o == null) {
            sysOperationRecord.setStatus(1);
            sysOperationRecordService.createSysOperationRecord(sysOperationRecord);
            return R.error("密码修改失败");
        } else {
            sysOperationRecord.setStatus(0);
            sysOperationRecordService.createSysOperationRecord(sysOperationRecord);
            SMS_func.DirectSendSMS(object.getPhone(), "您已成功重置作业管理系统密码，若非本人操作，请尽快登录网站更改密码。" + "(作业管理)");
            return R.ok("密码找回成功");
        }
    }
}
