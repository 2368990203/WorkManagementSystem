package team.work.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.service.impl.ExamCandidateService;
import team.work.utils.convert.R;


@Api(value = "24_试卷和学生关系管理")
@RestController
@RequestMapping("/v1/base")
public class ExamCandidateApi extends BaseApi {
    @Autowired
    private ExamCandidateService examCandidateService;


    @GetMapping("/examCandidate/{index}-{size}")
    @ApiOperation(value = "读取试卷和学生关系分页列表")
    public Object getExamCandidate(@PathVariable("index") int index,
                                   @PathVariable("size") int size,
                                   @RequestHeader("token") String token) {

        String number = getNumberByCache(token);

        String wKey = " and releaseStatus = 2 and number = '" + number + "' ";//2-已发布

        return R.ok(examCandidateService.page(index, size, wKey));

    }

    @GetMapping("/examCandidate/count/{examId}")
    @ApiOperation(value = "统计学生作业完成情况列表")
    public Object getExamCount(@PathVariable("examId") String examId,
                               @RequestHeader("token") String token) {

        String wKey = " and examId = '" + examId + "' ";
        return R.ok(examCandidateService.queryCount(wKey));

    }
}
