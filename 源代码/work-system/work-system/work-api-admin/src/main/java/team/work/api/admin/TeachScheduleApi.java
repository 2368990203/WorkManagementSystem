package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.model.TeachSchedule;
import team.work.core.service.impl.TeachScheduleService;
import team.work.doc.TeachScheduleCreate;
import team.work.doc.TeachScheduleUpdate;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;

import java.util.HashMap;
import java.util.Map;

@Api(value = "305_教师授课管理")
@RestController
@RequestMapping("/v1/base")
public class TeachScheduleApi extends BaseApi {
    @Autowired
    private TeachScheduleService teachScheduleService;

    @PostMapping("/teachSchedule")
    @ApiOperation(value = "教师授课新增")
    public Object createTeachSchedule(@RequestBody TeachScheduleCreate object,
                                      @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);

        //映射对象
        TeachSchedule model = o2c(object, token, TeachSchedule.class);
        //数据校验
        JSONObject check = V.checkEmpty(verify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        model = teachScheduleService.createTeachSchedule(model);
        if (model == null)
            return R.error("新增失败");
        return R.ok("新增成功", fm2(model));
    }

    @PutMapping("/teachSchedule")
    @ApiOperation(value = "修改教师授课")
    public Object updateTeachSchedule(@RequestBody TeachScheduleUpdate object,
                                      @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);
        //映射对象
        TeachSchedule model = o2c(object, token, TeachSchedule.class);
        //数据校验
        JSONObject check = V.checkEmpty(updateVerify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));
        TeachSchedule data = teachScheduleService.selectById(model.getId());

        if (data == null) {
            return R.error("该信息不存在，无法修改");
        }
        model.setReviser(userId);
        model = teachScheduleService.updateTeachSchedule(model);
        if (model == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/teachSchedule/{id}")
    @ApiOperation(value = "教师授课删除")
    public Object deleteTeachSchedule(@PathVariable("id") String id,
                                      @RequestHeader("token") String token) {
        if (!teachScheduleService.existId(id))
            return R.error("Id数据异常");

        if (teachScheduleService.deleteTeachSchedule(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/teachSchedule/{index}-{size}-{key}")
    @ApiOperation(value = "读取教师授课分页列表")
    public Object getTeachSchedule(@PathVariable("index") int index,
                                   @PathVariable("size") int size,
                                   @PathVariable("key") String key,
                                   @RequestHeader("token") String token) {
        String wKey = "";
        Integer type = getTypeByCache(token);
        if (type == 1) {//教师只能查自己的授课安排
            String teacherNumber = getNumberByCache(token);
            wKey += " and teacherNumber = '" + teacherNumber + "' ";
        }

        if (!V.isEmpty(key))
            wKey += S.apppend(" and id like '%", key, "%' ");
        return R.ok(teachScheduleService.page(index, size, wKey));
    }

    @GetMapping("/teachSchedule")
    @ApiOperation(value = "读取教师授课所有列表")
    public Object getAllTeachSchedule(@RequestHeader("token") String token) {
        return R.ok(teachScheduleService.queryAll(""));
    }

    @GetMapping("/teachSchedule/course")
    @ApiOperation(value = "读取教师自己的授课列表")
    public Object getScheduleByTeacher(@RequestHeader("token") String token) {
        String teacherNumber = getNumberByCache(token);
        return R.ok(teachScheduleService.queryAll(" and teacherNumber ='" + teacherNumber + "' "));
    }

    private Map<String, String> verify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("courseNumber", "请选择课程");
        verify.put("classId", "请选择班级");
        verify.put("teacherNumber", "请选择授课教师");
        return verify;
    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("courseNumber", "请输入课程号");
        verify.put("classId", "请选择班级");
        verify.put("teacherNumber", "请选择授课教师");
        return verify;
    }

}
