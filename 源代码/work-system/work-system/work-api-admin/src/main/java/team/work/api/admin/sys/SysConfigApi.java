package team.work.api.admin.sys;

import team.work.doc.KV;
import team.work.api.BaseApi;
import team.work.doc.SysTpsConfigSet;
import team.work.core.model.SysGlobalConfig;
import team.work.core.model.SysTpsConfig;
import team.work.core.service.impl.SysGlobalConfigService;
import team.work.core.service.impl.SysTpsConfigService;
import team.work.utils.convert.J;
import team.work.utils.convert.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.core.model.SysGlobalConfig;
import team.work.core.model.SysTpsConfig;
import team.work.doc.KV;
import team.work.doc.SysTpsConfigSet;
import team.work.utils.convert.J;

import java.util.List;

@Api(value = "SYS3_系统相关配置")
@RestController
@RequestMapping("/v1/sys")
public class SysConfigApi extends BaseApi {

    @Autowired
    private SysGlobalConfigService globalConfigService;
    @Autowired
    private SysTpsConfigService tpsConfigService;

    @GetMapping("/config/global")
    @ApiOperation(value = "读取配置")
    public Object getConfig(@RequestHeader("token") String token) {

        return R.ok(globalConfigService.getSetting());

    }

    @PutMapping("/config/global")
    @ApiOperation(value = "配置基础信息")
    public Object setConfig(@RequestBody List<KV> models,
                            @RequestHeader("token") String token) {

        List<SysGlobalConfig> configs = J.o2l(models, SysGlobalConfig.class);
        return R.ok(globalConfigService.config(configs));

    }

    @GetMapping("/config/tps/{code}")
    @ApiOperation(value = "第三方配置读取")
    public Object getOssConfig(@PathVariable("code") String code,
                               @RequestHeader("token") String token) {

        return R.ok(tpsConfigService.getByCode(code));

    }

    @PutMapping("/config/tps/{code}")
    @ApiOperation(value = "配置第三方")
    public Object setTpsConfig(@PathVariable("code") String code,
                               @RequestBody List<SysTpsConfigSet> models,
                               @RequestHeader("token") String token) {

        List<SysTpsConfig> configs = J.o2l(models, SysTpsConfig.class);
        return R.ok(tpsConfigService.config(code, configs));

    }

}
