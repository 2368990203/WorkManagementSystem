package team.work.api.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.core.model.Exam;
import team.work.core.service.impl.ExamCandidateService;
import team.work.core.service.impl.ExamService;
import team.work.core.tps.CacheKit;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.utils.convert.S;
import team.work.utils.convert.V;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "23_试卷管理")
@RestController
@RequestMapping("/v1/base")
public class ExamApi extends BaseApi {
    @Autowired
    public CacheKit cacheKit;
    @Autowired
    private ExamCandidateService examCandidateService;

    @GetMapping("/exam/info/{id}")
    @ApiOperation(value = "读取试卷信息")
    public Object getExamByid(@PathVariable("id") String  examId, @RequestHeader("token") String token) {
        String number =getNumberByCache(token);

        JSONObject res =new JSONObject();
        String wKey = "";
        if (!V.isEmpty(examId)) {
            wKey = S.apppend(" and examId = '", examId, "' ");
        } else {
            return R.error("考试ID不存在！");
        }
        if (!V.isEmpty(number)) {
            wKey += S.apppend(" and number = '", number, "' ");
        } else {
            return R.error("请先登录");
        }
        List<JSONObject> examList = new ArrayList<>();
        String tmp_exam_examInfo=cacheKit.getVal("exam_info_tips_" + examId + "_" + number);
        if (!V.isEmpty(tmp_exam_examInfo)) {
            examList  =  JSONObject.parseArray(tmp_exam_examInfo,JSONObject.class);
            res =examList.get(0);

        } else {
            examList =  examCandidateService.queryAll( wKey);
            if(V.isEmpty(examList)){
                return R.error("考试不存在！");
            }
            res =examList.get(0);
            Integer examStatus =  res.getInteger("examStatus");
            Integer releaseStatus =  res.getInteger("releaseStatus");
            if (examStatus == 1 && releaseStatus == 2) {
                cacheKit.setVal("exam_info_tips_" + examId + "_" + number,JSONObject.toJSONString(examList),60);
            }
        }




        return R.ok(res);

    }


}
