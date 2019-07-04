package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.model.Classes;
import team.work.core.service.impl.ClassesService;
import team.work.doc.ClassesCreate;
import team.work.doc.ClassesUpdate;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by party on 2019/06/25
 */
@Api(value = "314_班级信息管理")
@RestController
@RequestMapping("/v1/base")
public class ClassesApi extends BaseApi {
    @Autowired
    private ClassesService classesService;

    @PostMapping("/classes")
    @ApiOperation(value = "班级信息新增")
    public Object createClasses(@RequestBody ClassesCreate object,
                                @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);

        //映射对象
        Classes model = o2c(object, token, Classes.class);
        //数据校验
        JSONObject check = V.checkEmpty(verify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        //TODO 写校验重复的条件
        //Boolean exist = classesService.exist(W.f(
        //        W.and("code","eq",model.getCode()),
        //        W.and("isDel","eq","0"))
        //);
        //if(exist)
        //    return R.error("代码已经存在请更换一个代码");

        model = classesService.createClasses(model);
        if (model == null)
            return R.error("新增失败");
        return R.ok("新增成功", fm2(model));
    }

    @PutMapping("/classes")
    @ApiOperation(value = "修改班级信息")
    public Object updateClasses(@RequestBody ClassesUpdate object,
                                @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);
        //映射对象
        Classes model = o2c(object, token, Classes.class);
        //数据校验
        JSONObject check = V.checkEmpty(updateVerify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));
        Classes data = classesService.selectById(model.getId());

        if (data == null) {
            return R.error("该信息不存在，无法修改");
        }
        //TODO
        //   if(!model.getCode().equals(data.getCode())) {
        //Boolean exist = classesService.exist(W.f(
        //        W.and("code","eq",model.getCode()),
        //        W.and("isDel","eq","0"))
        //);
        //if(exist)
        //    return R.error("代码已经存在请更换一个代码");

        //     }
        model.setReviser(userId);
        model = classesService.updateClasses(model);
        if (model == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/classes/{id}")
    @ApiOperation(value = "班级信息删除")
    public Object deleteClasses(@PathVariable("id") String id,
                                @RequestHeader("token") String token) {
        if (!classesService.existId(id))
            return R.error("Id数据异常");

        if (classesService.deleteClasses(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/classes/{index}-{size}-{key}")
    @ApiOperation(value = "读取班级信息分页列表")
    public Object getClasses(@PathVariable("index") int index,
                             @PathVariable("size") int size,
                             @PathVariable("key") String key,
                             @RequestHeader("token") String token) {
        String wKey = "";
        if (!V.isEmpty(key))
            //FIXME 修改id为需要查询的字段
            wKey = S.apppend(" and id like '%", key, "%' ");
        return R.ok(classesService.page(index, size, wKey));
    }

    @GetMapping("/classes")
    @ApiOperation(value = "读取班级信息所有列表")
    public Object getAllClasses(@RequestHeader("token") String token) {
        return R.ok(classesService.queryAll(""));
    }



    @GetMapping("/classes/byMajorCode/{majorCode}")
    @ApiOperation(value = "读取班级信息列表by专业code")
    public Object getAllMajorInfoByAcademyCode(@PathVariable("majorCode") String majorCode,
                                               @RequestHeader("token") String token) {

        if (!V.isEmpty(majorCode))
            majorCode = S.apppend(" and majorCode = '", majorCode, "' ");
        return R.ok(classesService.queryAll(majorCode));

    }

    private Map<String, String> verify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("classNo", "请输入班号");
        verify.put("grade", "请输入年级");
        verify.put("majorCode", "请输入所属专业");
        return verify;
    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("classNo", "请输入班号");
        verify.put("grade", "请输入年级");
        verify.put("majorCode", "请输入所属专业");
        return verify;
    }

}
