package team.work.api.admin.sys;

import team.work.core.async.CommonAsync;
import team.work.api.BaseApi;
import team.work.doc.SysCategoryAdd;
import team.work.doc.SysCategoryUpdate;
import team.work.core.model.SysCategory;
import team.work.core.service.impl.SysCategoryService;
import team.work.utils.convert.R;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.core.model.SysCategory;
import team.work.doc.SysCategoryAdd;
import team.work.doc.SysCategoryUpdate;
import team.work.utils.convert.V;
import team.work.utils.convert.W;

import java.util.List;


@Api(value = "SYS3_系统相关配置")
@RestController
@RequestMapping("/v1/sys")
public class SysCategoryApi extends BaseApi {
    @Autowired
    private SysCategoryService categoryService;
    @Autowired
    private CommonAsync commonAsync;

    //    @GetMapping("/category")
//    @ApiOperation(value = "读取核心数据")
//    public Object getSuperCategory(@RequestHeader("token") String token){
//        return R.ok(categoryService.getSuperCategory());
//    }
    // 组织架构 organizational 1200000000000000001
    // 年级 grade 1200000000000000002
    // 班级 class 1200000000000000003
    // 模板 template 1200000000000000004
    // 设备 device 1200000000000000005
    @GetMapping("/category/{index}-{size}-{parentId}-{key}")
    @ApiOperation(value = "读取用户列表")
    public Object getOrganizationalList(@PathVariable("index") int index,
                                        @PathVariable("size") int size,
                                        @PathVariable("parentId") String parentId,
                                        @PathVariable("key") String key,
                                        @RequestHeader("token") String token) {

        return R.ok(categoryService.page(index, size, parentId, key));

    }


    @GetMapping("/category")
    @ApiOperation(value = "读取核心数据")
    public Object getSuperCategory(@RequestHeader("token") String token) {

        return R.ok(categoryService.getSuperCategory());

    }

    @GetMapping("/category/{code}-{level}")
    @ApiOperation(value = "读取基础数据")
    public Object getCategoryByCode(@PathVariable("code") String code,
                                    @PathVariable("level") int level,
                                    @RequestHeader("token") String token) {

        return R.ok(categoryService.getCategoryByCode(code, level));

    }

    @PostMapping("/category")
    @ApiOperation(value = "创建基础数据")
    public Object createCategory(@RequestBody SysCategoryAdd object,
                                 @RequestHeader("token") String token) {

        SysCategory model = o2c(object, token, SysCategory.class);

        if (V.isEmpty(model.getName()))
            return R.error("请输入基础数据名称");
        if (V.isEmpty(model.getCode()))
            return R.error("请输入基础数据编码");
        if (V.isEmpty(model.getSuperId()))
            return R.error("上层Id不能为空");
        if (V.isEmpty(model.getParentId()))
            return R.error("父级Id不能为空");

        if (!V.isEqual(model.getParentId(), "0")) {
            if (!categoryService.existId(model.getParentId()))
                return R.error("父级Id数据异常");
        }
        if (!V.isEqual(model.getSuperId(), "0")) {
            if (!categoryService.existId(model.getSuperId()))
                return R.error("上层Id数据异常");
        }
        Boolean exist = categoryService.exist(W.f(
                W.and("code", "eq", model.getCode()),
                W.and("superId", "eq", model.getSuperId())
        ));
        if (exist)
            return R.error("基础数据编码已经存在请更换一个名称");

        model = categoryService.createCategory(model);
        if (model == null)
            return R.error("创建失败");

        return R.ok("创建成功", fm2(model));
    }

    @PutMapping("/category")
    @ApiOperation(value = "修改基础数据")
    public Object updateCategory(@RequestBody SysCategoryUpdate object,
                                 @RequestHeader("token") String token) {

        SysCategory model = o2u(object, token, SysCategory.class);
        if (!categoryService.existId(model.getId()))
            return R.error("Id数据异常");

        if (V.isEmpty(model.getName()))
            return R.error("请输入基础数据名称");
        if (V.isEmpty(model.getCode()))
            return R.error("请输入基础数据编码");
        if (V.isEmpty(model.getSuperId()))
            return R.error("上层Id不能为空");

        Boolean exist = categoryService.exist(W.f(
                W.and("code", "eq", model.getCode()),
                W.and("id", "ne", model.getId()),
                W.and("superId", "eq", model.getSuperId())
        ));
        if (exist)
            return R.error("基础数据编码已经存在请更换一个名称");

        model = categoryService.updateCategory(model);
        if (model == null)
            return R.error("修改失败");

        return R.ok("修改成功", model);


    }

    @DeleteMapping("/category/{id}")
    @ApiOperation(value = "删除基础数据")
    public Object deleteCategory(@PathVariable("id") String id, @RequestHeader("token") String token) {

        if (!categoryService.existId(id))
            return R.error("Id数据异常");
        List<SysCategory> categories = categoryService.getCategoryByParentId(id);
        if (categories.size() > 0)
            return R.error("该数据有子级数据不允许直接删除");
        if (!categoryService.delete(id, cacheKit.getUserId(token)))
            return R.error("删除失败");

        return R.ok("删除成功");


    }
}
