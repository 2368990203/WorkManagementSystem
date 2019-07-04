package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.core.model.*;
import team.work.core.service.impl.*;
import team.work.utils.convert.*;
import team.work.core.service.impl.SysUserService;
import team.work.utils.convert.R;
import team.work.utils.kit.IdKit;
import team.work.utils.kit.MD5Kit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Api(value = "08_用户管理")
@RestController
@RequestMapping("/v1/sys")
public class SysUserApi extends BaseApi {
    @Autowired
    private SysUserService userService;


    @GetMapping("/user/exist/{number}")
    @ApiOperation(value = "学号查询用户是否存在")
    public Object exsistNumber(@PathVariable("number") String number,
                               @RequestHeader("token") String token) {

        if (!userService.existNumber(number))
            return R.error("用户不存在");
        return R.ok("用户存在");

    }

}
