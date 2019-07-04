package team.work.api.admin.sys;


import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.utils.convert.R;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import team.work.doc.*;
import team.work.core.model.SysMenu;
import team.work.core.model.SysMenuAuth;
import team.work.core.service.impl.SysMenuAuthService;
import team.work.core.service.impl.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.core.model.SysMenu;
import team.work.core.model.SysMenuAuth;
import team.work.doc.*;
import team.work.utils.convert.V;
import team.work.utils.convert.W;


@Api(value = "SYS2_系统权限数据管理")
@RestController
@RequestMapping("/v1/sys")
public class SysAuthApi extends BaseApi {
    //权限
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysMenuAuthService menuAuthService;

    /********************* 权限模块管理 **************************/
    @PostMapping("/menu-module")
    @ApiOperation(value = "创建权限模块")
    public Object createModule(@RequestBody SysMenuModuleAdd doc, @RequestHeader("token") String token) {

        SysMenu sysMenu = o2c(doc, token, SysMenu.class);
        if (V.isEmpty(sysMenu.getName()))
            return R.error("请输入权限模块名称");

        Boolean exist = menuService.exist(W.f(
                W.and("name", "eq", sysMenu.getName()),
                W.and("parentId", "eq", sysMenu.getParentId())
        ));
        if (exist)
            return R.error("权限模块名称已经存在请更换一个名称");

        sysMenu.setType("module");
        sysMenu = menuService.createMenu(sysMenu);
        if (sysMenu == null)
            return R.error("创建失败");
        return R.ok("创建成功", fm1(sysMenu));

    }

    @DeleteMapping("/menu-module/{id}")
    @ApiOperation(value = "删除权限模块")
    public Object deleteModule(@PathVariable("id") String id, @RequestHeader("token") String token) {

        if (!menuService.existId(id))
            return R.error("Id数据异常");
        Boolean exist = menuService.exist(W.f(
                W.and("parentId", "eq", id)
        ));
        if (exist)
            return R.error("权限模块下有子集权限不能直接删除");
        if (menuService.delete(id, cacheKit.user(token).getString("id")))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @PutMapping("/menu-module")
    @ApiOperation(value = "修改权限模块")
    public Object updateModule(@RequestBody SysMenuModuleUpdate doc, @RequestHeader("token") String token) {

        SysMenu sysMenu = o2u(doc, token, SysMenu.class);
        if (!menuService.existId(sysMenu.getId()))
            return R.error("Id数据异常");
        if (V.isEmpty(sysMenu.getName()))
            return R.error("请输入权限模块名称");


        Boolean exist = menuService.exist(W.f(
                W.and("name", "eq", sysMenu.getName()),
                W.and("parentId", "eq", sysMenu.getParentId()),
                W.and("id", "ne", sysMenu.getId())
        ));
        if (exist)
            return R.error("权限模块名称已经存在请更换一个名称");

        sysMenu = menuService.updateMenu(sysMenu);
        if (sysMenu == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm1(sysMenu));

    }

    @GetMapping("/menu-module")
    @ApiOperation(value = "读取权限模块")
    public Object getModule(@RequestHeader("token") String token) {

        return R.ok(fl2(menuService.getModules()));

    }


    /********************* 权限菜单管理 **************************/
    @PostMapping("/menu")
    @ApiOperation(value = "创建权限菜单")
    public Object createMenu(@RequestBody SysMenuAdd doc,
                             @RequestHeader("token") String token) {

        SysMenu sysMenu = o2c(doc, token, SysMenu.class);

        if (V.isEmpty(sysMenu.getName()))
            return R.error("请输入权限菜单名称");
        if (V.isEmpty(sysMenu.getParentId()))
            return R.error("父级Id不能为空");

        Boolean exist = menuService.exist(W.f(
                W.and("name", "eq", sysMenu.getName()),
                W.and("parentId", "eq", sysMenu.getParentId())
        ));
        if (exist)
            return R.error("权限菜单名称已经存在请更换一个名称");

        sysMenu.setType("menu");
        sysMenu = menuService.createMenu(sysMenu);
        if (sysMenu == null)
            return R.error("创建失败");
        return R.ok("创建成功", fm1(sysMenu));

    }

    @PutMapping("/menu")
    @ApiOperation(value = "修改权限菜单")
    public Object updateMenu(@RequestBody SysMenuUpdate doc, @RequestHeader("token") String token) {

        SysMenu sysMenu = o2u(doc, token, SysMenu.class);
        if (!menuService.existId(sysMenu.getId()))
            return R.error("Id数据异常");

        if (V.isEmpty(sysMenu.getParentId()))
            return R.error("父级Id不能为空");
        if (V.isEmpty(sysMenu.getName()))
            return R.error("请输入权限菜单名称");

        Boolean exist = menuService.exist(W.f(
                W.and("name", "eq", sysMenu.getName()),
                W.and("parentId", "eq", sysMenu.getParentId()),
                W.and("id", "ne", sysMenu.getId())
        ));
        if (exist)
            return R.error("权限菜单名称已经存在请更换一个名称");

        sysMenu = menuService.updateMenu(sysMenu);
        if (sysMenu == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm1(sysMenu));

    }

    @DeleteMapping("/menu/{id}")
    @ApiOperation(value = "删除权限菜单")
    public Object deleteMenu(@PathVariable("id") String id, @RequestHeader("token") String token) {

        if (!menuService.existId(id))
            return R.error("Id数据异常");
        Boolean exist = menuService.exist(W.f(
                W.and("parentId", "eq", id)
        ));
        if (exist)
            return R.error("权限菜单下有子集菜单不能直接删除");
        //fixme:删除操作清库！！！！！！！！
        if (menuService.delete(id, cacheKit.user(token).getString("id")))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/menu/{parentId}")
    @ApiOperation(value = "读取权限菜单")
    public Object getMenu(@PathVariable("parentId") String parentId) {

        return R.ok(fl2(menuService.getMenu(parentId)));
    }

    /********************** 子菜单管理 **************************/
    @PostMapping("/sub-menu")
    @ApiOperation(value = "创建子菜单")
    public Object createSubMenu(@RequestBody SysSubMenuAdd doc, @RequestHeader("token") String token) {

        SysMenu sysMenu = o2c(doc, token, SysMenu.class);

        if (V.isEmpty(sysMenu.getName()))
            return R.error("请输入子菜单名称");
        if (V.isEmpty(sysMenu.getCode()))
            return R.error("请输入子菜单对应函数");
        if (V.isEmpty(sysMenu.getParentId()))
            return R.error("父级Id不能为空");

        Boolean exist = menuService.exist(W.f(
                W.and("name", "eq", sysMenu.getName()),
                W.and("code", "eq", sysMenu.getCode()),
                W.and("parentId", "eq", sysMenu.getParentId())
        ));
        if (exist)
            return R.error("权限子菜单名称已经存在请更换一个名称");

        sysMenu.setType("subMenu");
        sysMenu = menuService.createMenu(sysMenu);
        if (sysMenu == null)
            return R.error("创建失败");
        return R.ok("创建成功", fm1(sysMenu));

    }

    @PutMapping("/sub-menu")
    @ApiOperation(value = "修改子菜单")
    public Object updateSubMenu(@RequestBody SysSubMenuUpdate doc, @RequestHeader("token") String token) {

        SysMenu sysMenu = o2u(doc, token, SysMenu.class);
        logger.info("sysMenu:" + JSONObject.toJSONString(sysMenu));

        if (!menuService.existId(sysMenu.getId()))
            return R.error("Id数据异常");

        if (V.isEmpty(sysMenu.getCode()))
            return R.error("请输入子菜单对应函数");
        if (V.isEmpty(sysMenu.getName()))
            return R.error("请输入权限菜单名称");
        if (V.isEmpty(sysMenu.getParentId()))
            return R.error("父级Id不能为空");

        Boolean exist = menuService.exist(W.f(
                W.and("name", "eq", sysMenu.getName()),
                W.and("parentId", "eq", sysMenu.getParentId()),
                W.and("id", "ne", sysMenu.getId())
        ));
        if (exist)
            return R.error("权限子菜单名称已经存在请更换一个名称");


        sysMenu = menuService.updateMenu(sysMenu);
        if (sysMenu == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm1(sysMenu));

    }

    @DeleteMapping("/sub-menu/{id}")
    @ApiOperation(value = "删除子菜单")
    public Object deleteSubMenu(@PathVariable("id") String id, @RequestHeader("token") String token) {

        if (!menuService.existId(id))
            return R.error("Id数据异常");
        if (menuService.deleteSubMenu(id, cacheKit.user(token).getString("id")))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    /********************** 子菜单功能权限管理 **********************/
    @PostMapping("/sub-menu-auth")
    @ApiOperation(value = "创建子菜单功能权限")
    public Object createSubMenuAuth(@RequestBody SysSubMenuAuthAdd doc, @RequestHeader("token") String token) {

        SysMenuAuth auth = o2c(doc, token, SysMenuAuth.class);

        if (V.isEmpty(auth.getName()))
            return R.error("请输入功能权限名称");
        if (V.isEmpty(auth.getMenuId()))
            return R.error("所属菜单Id不能为空");

        Boolean exist = menuAuthService.exist(W.f(
                W.and("name", "eq", auth.getName()),
                W.and("menuId", "eq", auth.getMenuId())
        ));
        if (exist)
            return R.error("功能权限名称已经存在请更换一个名称");
        auth = menuAuthService.createMenuAuth(auth);
        if (auth == null)
            return R.error("创建失败");
        return R.ok("创建成功", fm1(auth));

    }

    @PutMapping("/sub-menu-auth")
    @ApiOperation(value = "修改子菜单功能权限")
    public Object updateSubMenuAuth(@RequestBody SysSubMenuAuthUpdate doc, @RequestHeader("token") String token) {

        SysMenuAuth auth = o2u(doc, token, SysMenuAuth.class);
        if (!menuAuthService.existId(auth.getId()))
            return R.error("Id数据异常");

        if (V.isEmpty(auth.getName()))
            return R.error("请输入功能权限名称");
        if (V.isEmpty(auth.getMenuId()))
            return R.error("所属菜单Id不能为空");

        Boolean exist = menuAuthService.exist(W.f(
                W.and("name", "eq", auth.getName()),
                W.and("menuId", "eq", auth.getMenuId()),
                W.and("id", "ne", auth.getId())
        ));
        if (exist)
            return R.error("权限子菜单名称已经存在请更换一个名称");


        auth = menuAuthService.updateMenuAuth(auth);
        if (auth == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm1(auth));

    }

    @DeleteMapping("/sub-menu-auth/{id}")
    @ApiOperation(value = "删除子菜单功能权限")
    public Object deleteSubMenuAuth(@PathVariable("id") String id, @RequestHeader("token") String token) {

        if (!menuAuthService.existId(id))
            return R.error("Id数据异常");
        if (menuAuthService.delete(id, cacheKit.user(token).getString("id")))
            return R.ok("删除成功");
        return R.error("删除失败");
    }


}
