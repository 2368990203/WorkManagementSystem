package team.work.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.service.impl.SysUserService;
import team.work.core.service.impl.UserBaseInfoService;
import team.work.utils.convert.R;

import java.util.HashMap;
import java.util.Map;


@Api(value = "01_用户基础信息管理")
@RestController
@RequestMapping("/v1/base")
public class UserBaseInfoApi extends BaseApi {
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserService userService;

//    @PutMapping("/userBaseInfo")
//    @ApiOperation(value = "保存用户基础信息")
//    public Object updateUserBaseInfo(@RequestBody UserBaseInfoSave object,
//                                     @RequestHeader("token") String token) {
//
//        String userId = getUserIdByCache(token);
//
//        List<SysUser> sysUsers = sysUserService.findByIds(userId);
//        if (sysUsers == null || sysUsers.size() == 0) {
//            return R.error("用户不存在");
//        }
//
//        SysUser sysUser = sysUsers.get(0);
//
//
//        UserBaseInfo model = o2c(object, token, UserBaseInfo.class);
//
//
//        if ((number != null && number.length() != 0) && !V.isEmpty(model.getNumber())) {
//            return R.error("请勿修改学号");
//        }
//
//        if (number != null) {
//            List<UserBaseInfo> userBaseInfoList = userBaseInfoService.findByNumber(number);
//            if (userBaseInfoList != null && userBaseInfoList.size() > 0) {
//                String idCard = userBaseInfoList.get(0).getIdCard();
//                String phone = userBaseInfoList.get(0).getPhone();
//
//                if (idCard != null && idCard.trim().length() != 0 && !idCard.equals(model.getIdCard())) {
//                    return R.error("请勿修改身份证");
//                }
//                if (idCard == null || "".equals(idCard)) {
//                    if (userBaseInfoService.existIdcard(object.getIdCard())) {
//                        return R.error("该身份证号已存在");
//                    }
//                }
//                if (phone != null && phone.trim().length() != 0 && !phone.equals(model.getPhone())) {
//                    if (userBaseInfoService.existPhone(object.getPhone())) {
//                        return R.error("该手机号已存在");
//                    }
//                }
//
//                if (phone == null) {
//                    if (userBaseInfoService.existPhone(object.getPhone())) {
//                        return R.error("该手机号已存在");
//                    }
//                }
//
//
//            }
//        }
//
//        JSONObject check = V.checkEmpty(updateVerify(), object);
//        if (check.getBoolean("check"))
//            return R.error(check.getString("message"));
//
//
//        if (number == null) {//未完善过学号，新增到基本信息表
//            //修改sysUser的学号
//            if (V.isEmpty(model.getNumber())) {
//                return R.error("学号/工号填写有误");
//            }
//            if (userBaseInfoService.existNumber(object.getNumber())) {
//                return R.error("该学号/工号已存在");
//            }
//
//            sysUser.setNumber(model.getNumber());
//            sysUser.setPhone(model.getPhone());
////            sysUser.setEmail(model.getEmail());
//            sysUserService.updateSysUser(sysUser);
//            model = userBaseInfoService.createUserBaseInfo(model);
//        } else {//更新
//            List<UserBaseInfo> userBaseInfoList = userBaseInfoService.findByNumber(number);
//            if (userBaseInfoList != null && userBaseInfoList.size() > 0) {
//                model.setId(userBaseInfoList.get(0).getId());
//                model.setReviser(userId);
//                model = userBaseInfoService.updateUserBaseInfo(model);
//            }
//        }
//
//        if (model == null) {
//            return R.error("保存失败");
//        } else {
//            SysUser updateUser = new SysUser();
//            updateUser.setId(model.getId());
//            updateUser.setPhone(model.getPhone());
//            updateUser.setNumber(model.getNumber());
//            userService.updateSysUser(updateUser);
//            return R.ok("保存成功");
//        }
//
//    }


    @GetMapping("/userBaseInfo")
    @ApiOperation(value = "读取用户基础信息所有列表")
    public Object getAllUserBaseInfo(@RequestHeader("token") String token) {

        return R.ok(userBaseInfoService.queryAll(""));

    }


    @GetMapping("/userBaseInfo/exist/{number}")
    @ApiOperation(value = "学号查询用户是否存在")
    public Object exsistNumber(@PathVariable("number") String number,
                               @RequestHeader("token") String token) {

        if (!userService.existNumber(number))
            return R.error("用户不存在");
        return R.ok("用户存在");

    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("name", "姓名填写有误");
//        verify.put("oldName", "曾用名");
        //      verify.put("number", "学号/工号填写有误");
        verify.put("sex", "性别填写有误");
        verify.put("nationCode", "民族代码填写有误");
//        verify.put("birthday", "出生日期");
        verify.put("graduate", "学历填写有误");
        verify.put("degree", "学位或职称填写有误");
        verify.put("nationality", "国籍填写有误");
//        verify.put("originPlace", "籍贯");
//        verify.put("birthPlace", "出生地");
//        verify.put("presentPesidence", "现居住地");
        verify.put("phone", "联系电话填写有误");
//        verify.put("email", "邮箱");
//        verify.put("specialty", "特长");
        verify.put("idCard", "身份证填写有误");
        return verify;
    }

}
