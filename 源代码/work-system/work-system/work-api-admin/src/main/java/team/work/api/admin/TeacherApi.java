package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.core.model.Teacher;
import team.work.core.service.impl.SysUserService;
import team.work.core.service.impl.TeacherService;
import team.work.doc.TeacherCreate;
import team.work.doc.TeacherUpdate;
import team.work.utils.bean.Page;
import team.work.utils.convert.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.utils.kit.MD5Kit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by party on 2019/06/26
 */
@Api(value = "303_教师信息管理")
@RestController
@RequestMapping("/v1/base")
public class TeacherApi extends BaseApi {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/teacher")
    @ApiOperation(value = "教师信息新增")
    public Object createTeacher(@RequestBody TeacherCreate object,
                                @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);

        //映射对象
        Teacher teacher = o2c(object, token, Teacher.class);
        //数据校验
        JSONObject check = V.checkEmpty(verify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        Boolean exist = teacherService.exist(W.f(
                W.and("number", "eq", teacher.getNumber()),
                W.and("isDel", "eq", "0"))
        );
        if (exist)
            return R.error("工号已存在");

        String number = teacher.getNumber();

        if (sysUserService.existLoginName(number))
            return R.error(F.s("当前工号[%s]已经被注册", number));


        //工号后四位
        String numLast4 = number.substring(number.length() - 4);

        String salt = S.randomNum();
        String pwd = MD5Kit.encode("gxun" + numLast4 + salt);

        JSONObject sysUser = new JSONObject();
        sysUser.put("loginName", number);
        sysUser.put("password", pwd);
        sysUser.put("salt", salt);
        sysUser.put("type", 1);//1-教师
        sysUser.put("creator", userId);
        sysUser.put("roleId", object.getRoleId());

        sysUser = (JSONObject) sysUserService.createUser(sysUser);
        if (sysUser == null) {
            return R.error("教师用户创建失败");
        }

        teacher = teacherService.createTeacher(teacher);
        if (teacher == null)
            return R.error("新增失败");
        return R.ok("新增成功\n登录账号为" + number + "\n密码为gxun" + numLast4, fm2(teacher));
    }

    @PutMapping("/teacher")
    @ApiOperation(value = "修改教师信息")
    public Object updateTeacher(@RequestBody TeacherUpdate object,
                                @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);
        //映射对象
        Teacher model = o2c(object, token, Teacher.class);
        //数据校验
        JSONObject check = V.checkEmpty(updateVerify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));
        Teacher data = teacherService.selectById(model.getId());

        if (data == null) {
            return R.error("该信息不存在，无法修改");
        }
        if (!model.getNumber().equals(data.getNumber())) {
            Boolean exist = teacherService.exist(W.f(
                    W.and("number", "eq", model.getNumber()),
                    W.and("id", "eq", model.getId()),
                    W.and("isDel", "eq", "0"))
            );
            if (exist)
                return R.error("代码已经存在请更换一个代码");

        }
        model.setReviser(userId);
        model = teacherService.updateTeacher(model);
        if (model == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/teacher/{id}")
    @ApiOperation(value = "教师信息删除")
    public Object deleteTeacher(@PathVariable("id") String id,
                                @RequestHeader("token") String token) {
        if (!teacherService.existId(id))
            return R.error("Id数据异常");

        if (teacherService.deleteTeacher(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/teacher/{index}-{size}-{key}")
    @ApiOperation(value = "读取教师信息分页列表")
    public Object getTeacher(@PathVariable("index") int index,
                             @PathVariable("size") int size,
                             @PathVariable("key") String key,
                             @RequestHeader("token") String token) {
        String wKey = "";
        if (!V.isEmpty(key))
            //FIXME 修改id为需要查询的字段
            wKey = S.apppend(" and id like '%", key, "%' ");
        return R.ok(teacherService.page(index, size, wKey));
    }

    @GetMapping("/teacher")
    @ApiOperation(value = "读取教师信息所有列表")
    public Object getAllTeacher(@RequestHeader("token") String token) {
        return R.ok(teacherService.queryAll(""));
    }

    private Map<String, String> verify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("name", "请输入姓名");
        verify.put("number", "请输入工号");
        verify.put("academyId", "请输入所属学院id");
        verify.put("sex", "请输入性别");
        return verify;
    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("name", "请输入姓名");
        verify.put("number", "请输入工号");
        verify.put("academyId", "请输入所属学院id");
        verify.put("sex", "请输入性别");
        return verify;
    }

}
