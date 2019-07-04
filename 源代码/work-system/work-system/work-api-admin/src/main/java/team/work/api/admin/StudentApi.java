package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.model.Student;
import team.work.core.service.impl.StudentService;
import team.work.core.service.impl.SysUserService;
import team.work.doc.StudentCreate;
import team.work.doc.StudentUpdate;
import team.work.utils.convert.*;
import team.work.utils.kit.MD5Kit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by party on 2019/06/26
 */
@Api(value = "302_学生信息管理")
@RestController
@RequestMapping("/v1/base")
public class StudentApi extends BaseApi {
    @Autowired
    private StudentService studentService;
    @Autowired
    private SysUserService sysUserService;

    @Transactional
    @PostMapping("/student")
    @ApiOperation(value = "学生信息新增")
    public Object createStudent(@RequestBody StudentCreate object,
                                @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);

        //映射对象
        Student student = o2c(object, token, Student.class);

        String number = student.getNumber();

        //数据校验
        if (V.isLength(number, 12))
            return R.error("学号必须为12位");

        JSONObject check = V.checkEmpty(verify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        Boolean exist = studentService.exist(W.f(
                W.and("number", "eq", number),
                W.and("isDel", "eq", "0"))
        );
        if (exist)
            return R.error("学号已存在");


        if (sysUserService.existLoginName(number))
            return R.error(F.s("当前学号[%s]已经被注册", number));


        //学号后四位
        String numLast4 = number.substring(number.length() - 4);

        String salt = S.randomNum();
        String pwd = MD5Kit.encode("gxun" + numLast4 + salt);

        JSONObject sysUser = new JSONObject();
        sysUser.put("loginName", number);
        sysUser.put("password", pwd);
        sysUser.put("salt", salt);
        sysUser.put("type", 2);//2-学生
        sysUser.put("creator", userId);
        sysUser.put("roleId", object.getRoleId());

        sysUser = (JSONObject) sysUserService.createUser(sysUser);
        if (sysUser == null) {
            return R.error("学生用户创建失败");
        }


        student = studentService.createStudent(student);
        if (student == null)
            return R.error("新增失败");
        return R.ok("新增成功\n登录账号为" + number + "\n密码为gxun" + numLast4, fm2(student));

    }

    @PutMapping("/student")
    @ApiOperation(value = "修改学生信息")
    public Object updateStudent(@RequestBody StudentUpdate object,
                                @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);
        //映射对象
        Student model = o2c(object, token, Student.class);
        //数据校验
        JSONObject check = V.checkEmpty(updateVerify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));
        Student data = studentService.selectById(model.getId());

        if (data == null) {
            return R.error("该信息不存在，无法修改");
        }

        if (!model.getNumber().equals(data.getNumber())) {
            Boolean exist = studentService.exist(W.f(
                    W.and("number", "eq", model.getNumber()),
                    W.and("id", "ne", model.getId()),
                    W.and("isDel", "eq", "0"))
            );
            if (exist)
                return R.error("学号已存在");

        }
        model.setReviser(userId);
        model = studentService.updateStudent(model);
        if (model == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/student/{id}")
    @ApiOperation(value = "学生信息删除")
    public Object deleteStudent(@PathVariable("id") String id,
                                @RequestHeader("token") String token) {
        if (!studentService.existId(id))
            return R.error("Id数据异常");

        if (studentService.deleteStudent(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/student/{index}-{size}-{name}")
    @ApiOperation(value = "读取学生信息分页列表")
    public Object getStudent(@PathVariable("index") int index,
                             @PathVariable("size") int size,
                             @PathVariable("name") String name,
                             @RequestHeader("token") String token) {
        String wKey = "";
        if (!V.isEmpty(name))
            wKey = S.apppend(" and name like '%", name, "%' ");
        return R.ok(studentService.page(index, size, wKey));
    }

    @GetMapping("/student")
    @ApiOperation(value = "读取学生信息所有列表")
    public Object getAllStudent(@RequestHeader("token") String token) {
        return R.ok(studentService.queryAll(""));
    }

    private Map<String, String> verify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("name", "请输入姓名");
        verify.put("number", "请输入学号");
        verify.put("classId", "请选择所属班级");
        verify.put("sex", "请输入性别");
        return verify;
    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("name", "请输入姓名");
        verify.put("number", "请输入学号");
        verify.put("classId", "请选择所属班级");
        verify.put("sex", "请输入性别");
        return verify;
    }

}
