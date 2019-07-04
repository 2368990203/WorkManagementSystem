package team.work.api.admin.sys;

import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.api.BaseApi;
import team.work.core.model.SysOperationRecord;
import team.work.core.service.impl.SysOperationRecordService;
import team.work.utils.bean.Page;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.utils.convert.S;
import team.work.utils.convert.V;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "SYS_系统操作记录管理")
@RestController
@RequestMapping("/v1/base")
public class SysOperationRecordApi extends BaseApi {
    @Autowired
    private SysOperationRecordService sysOperationRecordService;


    @GetMapping("/sysOperationRecord/{index}-{size}-{key}")
    @ApiOperation(value = "读取系统操作记录分页列表")
    public Object getSysOperationRecord(@PathVariable("index") int index,
                                        @PathVariable("size") int size,
                                        @PathVariable("key") String key,
                                        @RequestHeader("token") String token) {

        String wKey = "";
        if (!V.isEmpty(key))
            //FIXME 修改id为需要查询的字段
            wKey = S.apppend(" and id like '%", key, "%' ");
        return R.ok(sysOperationRecordService.page(index, size, wKey));

    }

    @GetMapping("/sysOperationRecord")
    @ApiOperation(value = "读取系统操作记录所有列表")
    public Object getAllSysOperationRecord(@RequestHeader("token") String token) {

        return R.ok(sysOperationRecordService.queryAll(""));

    }


}
