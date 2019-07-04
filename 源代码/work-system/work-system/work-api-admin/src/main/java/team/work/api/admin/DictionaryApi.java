package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.model.Dictionary;
import team.work.core.service.impl.DictionaryService;
import team.work.doc.DictionaryCreate;
import team.work.doc.DictionaryUpdate;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "08_数据字典管理")
@RestController
@RequestMapping("/v1/sys")
public class DictionaryApi extends BaseApi {
    @Autowired
    private DictionaryService dictionaryService;

    @PostMapping("/dictionary")
    @ApiOperation(value = "数据字典新增")
    public Object createDictionary(@RequestBody DictionaryCreate object,
                                   @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);

        //映射对象
        Dictionary model = o2c(object, token, Dictionary.class);
        //数据校验
        JSONObject check = V.checkEmpty(verify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        //校验代码是否重复
        Boolean exist = dictionaryService.exist(W.f(
                W.and("code", "eq", model.getCode()),
                W.and("isDel", "eq", "0"))
        );
        if (exist)
            return R.error("该代码已经存在请更换一个代码");


        Boolean nameexist = dictionaryService.exist(W.f(
                W.and("name", "eq", model.getName()),
                W.and("isDel", "eq", "0"))
        );
        if (nameexist)
            return R.error("名称已经存在请更换一个名称");


        model = dictionaryService.createDictionary(model);
        if (model == null)
            return R.error("新增失败");
        return R.ok("新增成功", fm2(model));
    }

    @PutMapping("/dictionary")
    @ApiOperation(value = "修改数据字典")
    public Object updateDictionary(@RequestBody DictionaryUpdate object,
                                   @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);
        //映射对象
        Dictionary model = o2c(object, token, Dictionary.class);
        //数据校验
        JSONObject check = V.checkEmpty(updateVerify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        //校验代码是否重复
        Boolean exist = dictionaryService.exist(W.f(
                W.and("code", "eq", model.getCode()),
                W.and("id", "ne", object.getId())
        ));

        List<Dictionary> dictionaryList = dictionaryService.findByIds(object.getId());


        if (exist & (dictionaryList.get(0).getCode() != model.getCode()))
            return R.error("该代码已经存在请更换一个代码");

        model.setReviser(userId);
        model = dictionaryService.updateDictionary(model);
        if (model == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/dictionary/{id}")
    @ApiOperation(value = "数据字典删除")
    public Object deleteDictionary(@PathVariable("id") String id,
                                   @RequestHeader("token") String token) {

        if (!dictionaryService.existId(id))
            return R.error("Id数据异常");

        if (dictionaryService.delete(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/dictionary/{index}-{size}-{name}")
    @ApiOperation(value = "读取数据字典分页列表")
    public Object getDictionary(@PathVariable("index") int index,
                                @PathVariable("size") int size,
                                @PathVariable("name") String name,
                                @RequestHeader("token") String token) {

        String wKey = "";
        if (!V.isEmpty(name))
            wKey = S.apppend(" and name like '%", name, "%' ");
        return R.ok(dictionaryService.page(index, size, wKey));

    }

    @GetMapping("/dictionary")
    @ApiOperation(value = "读取数据字典所有列表")
    public Object getAllDictionary(@RequestHeader("token") String token) {

        return R.ok(dictionaryService.queryAll(""));

    }


    @GetMapping("/dictionary/fieldName/{fieldName}")
    @ApiOperation(value = "通过字段名读取值")
    public Object getValueByfieldName(@PathVariable("fieldName") String fieldName,
                                      @RequestHeader("token") String token) {

        String wkey = "";
        if (!V.isEmpty(fieldName))
            wkey = S.apppend(" and fieldName = '" + fieldName + "' and status = 1 ");
        return R.ok(dictionaryService.queryAll(wkey));

    }

    private Map<String, String> verify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("name", "请输入名称");
        verify.put("fieldName", "请输入字段名");
        verify.put("rank", "请输入排序");
        verify.put("value", "请输入值");
//        verify.put("explain", "说明");
        verify.put("status", "请选择状态");
        verify.put("code", "请输入代码");
        return verify;
    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("id", "id");
        verify.put("name", "请输入名称");
        verify.put("fieldName", "请输入字段名");
        verify.put("rank", "请输入排序");
        verify.put("value", "请输入值");
//        verify.put("explain", "说明");
        verify.put("status", "请选择状态");
        verify.put("code", "请输入代码");
        return verify;
    }

}
