package team.work.api.admin;

import team.work.api.BaseApi;
import team.work.core.service.impl.AcademyInfoService;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "05_学院信息管理")
@RestController
@RequestMapping("/v1/base")
public class AcademyInfoApi extends BaseApi {
    @Autowired
    private AcademyInfoService academyInfoService;

    @GetMapping("/academyInfo")
    @ApiOperation(value = "读取学院所有列表")
    public Object getAllAcademyInfo(@RequestHeader("token") String token) {


        return R.ok(academyInfoService.queryAll(""));

    }
}
