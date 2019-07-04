package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.core.model.Course;
import team.work.core.service.impl.CourseService;
import team.work.doc.CourseCreate;
import team.work.doc.CourseUpdate;
import team.work.utils.bean.Page;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by party on 2019/06/26
 */
@Api(value = "304_课程信息管理")
@RestController
@RequestMapping("/v1/base")
public class CourseApi extends BaseApi {
    @Autowired
    private CourseService courseService;

    @PostMapping("/course")
    @ApiOperation(value = "课程信息新增")
    public Object createCourse(@RequestBody CourseCreate object,
                               @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);

        //映射对象
        Course model = o2c(object, token, Course.class);
        //数据校验
        JSONObject check = V.checkEmpty(verify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        Boolean exist = courseService.exist(W.f(
                W.and("number", "eq", model.getNumber()),
                W.and("isDel", "eq", "0"))
        );
        if (exist)
            return R.error("课程号已经存在");


        model = courseService.createCourse(model);
        if (model == null)
            return R.error("新增失败");
        return R.ok("新增成功", fm2(model));
    }

    @PutMapping("/course")
    @ApiOperation(value = "修改课程信息")
    public Object updateCourse(@RequestBody CourseUpdate object,
                               @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);
        //映射对象
        Course model = o2c(object, token, Course.class);
        //数据校验
        JSONObject check = V.checkEmpty(updateVerify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));
        Course data = courseService.selectById(model.getId());

        if (data == null) {
            return R.error("该信息不存在，无法修改");
        }
        if (!model.getNumber().equals(data.getNumber())) {
            Boolean exist = courseService.exist(W.f(
                    W.and("number", "eq", model.getNumber()),
                    W.and("id", "ne", model.getId()),
                    W.and("isDel", "eq", "0"))
            );
            if (exist)
                return R.error("课程号已经存在");

        }
        model.setReviser(userId);
        model = courseService.updateCourse(model);
        if (model == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/course/{id}")
    @ApiOperation(value = "课程信息删除")
    public Object deleteCourse(@PathVariable("id") String id,
                               @RequestHeader("token") String token) {
        if (!courseService.existId(id))
            return R.error("Id数据异常");

        if (courseService.deleteCourse(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/course/{index}-{size}-{name}")
    @ApiOperation(value = "读取课程信息分页列表")
    public Object getCourse(@PathVariable("index") int index,
                            @PathVariable("size") int size,
                            @PathVariable("name") String name,
                            @RequestHeader("token") String token) {
        String wKey = "";
        if (!V.isEmpty(name))
            wKey = S.apppend(" and name like '%", name, "%' ");
        return R.ok(courseService.page(index, size, wKey));
    }

    @GetMapping("/course")
    @ApiOperation(value = "读取课程信息所有列表")
    public Object getAllCourse(@RequestHeader("token") String token) {
        return R.ok(courseService.queryAll(""));
    }

    private Map<String, String> verify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("credit", "请输入学分");
        verify.put("name", "请输入课程名");
        verify.put("number", "请输入课程号");
        return verify;
    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("credit", "请输入学分");
        verify.put("name", "请输入课程名");
        verify.put("number", "请输入课程号");
        return verify;
    }

}
