package team.work.api.admin;

import team.work.api.BaseApi;
import team.work.core.service.impl.MajorInfoService;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.utils.convert.S;
import team.work.utils.convert.V;

@Api(value = "06_专业信息管理")
@RestController
@RequestMapping("/v1/base")
public class MajorInfoApi extends BaseApi {

    @Autowired
    private MajorInfoService majorInfoService;


    //通过学院代码找该学院所有专业
    @GetMapping("/majorInfo/majors/{academyCode}")
    @ApiOperation(value = "通过学院代码找该学院所有专业")
    public Object getAcademyInfoByCode(@PathVariable("academyCode") String academyCode,
                                       @RequestHeader("token") String token) {
        String wKey = "";
        if (!V.isEmpty(academyCode))
            wKey = S.apppend(" and academyCode = '", academyCode, "'");
        return R.ok(majorInfoService.getMajorByAcademyCode(wKey));

    }


}
