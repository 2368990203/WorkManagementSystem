package team.work.api.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.model.Options;
import team.work.core.service.impl.ExamCandidateService;
import team.work.core.service.impl.ExamRecordService;
import team.work.core.service.impl.OptionsService;
import team.work.utils.convert.J;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Api(value = "26_考试答题记录管理")
@RestController
@RequestMapping("/v1/base")
public class ExamRecordApi extends BaseApi {
    @Autowired
    private ExamRecordService examRecordService;
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ExamCandidateService examCandidateService;


    @GetMapping("/examRecord/record/{examId}-{number}")
    @ApiOperation(value = "读取学生答题记录")
    public Object getRecord(@PathVariable("examId") String examId,
                            @PathVariable("number") String number,
                            @RequestHeader("token") String token) {

        String wKey = "";
        if (!V.isEmpty(examId)) {
            wKey = S.apppend(" and examId = '", examId, "' ");
        } else {
            return R.error("作业数据异常！");
        }

        if (!V.isEmpty(number)) {
            wKey += S.apppend(" and number = '", number, "' ");
        } else {
            return R.error("学生学号数据异常");
        }

//        List<JSONObject> examRecordList =  examRecordService.queryAll(wKey);

        String[] types = {"trueOrFalse", "singleChoice", "multipleChoice", "gapFilling", "subjective"};

        Map<String, ArrayList<JSONObject>> back = new TreeMap<>();

        ArrayList<JSONObject> candidates = (ArrayList<JSONObject>) examCandidateService.queryAll(wKey);

        if (V.isEmpty(candidates)) {
            return R.error("考试无权限！");
        }

        JSONObject candidate = candidates.get(0);

        back.put("candidate", candidates);

        for (int i = 0; i < types.length; i++) {
            List<JSONObject> examRecord = new ArrayList<>();

//            String tmp_examQuestion_type = cacheKit.getVal("examRecord_type_" + examId + "_" + (i + 1) + "_" + number);
//            if (!V.isEmpty(tmp_examQuestion_type)) {
//                examRecord = JSONObject.parseArray(tmp_examQuestion_type, JSONObject.class);
//            } else {
            examRecord = examRecordService.queryAll(wKey + " and type = '" + (i + 1) + "' ");
//                if (!V.isEmpty(examRecord)) {
//                    cacheKit.setVal("examRecord_type_" + examId + "_" + (i + 1) + "_" + number, JSONObject.toJSONString(examRecord), 900);
//                }
//            }

//            if (V.isEmpty(examRecord)) {
//                cacheKit.deleteVal("examRecord_type_" + examId + "_" + (i + 1) + "_" + number);
//            }
            ArrayList<JSONObject> teamPapers = new ArrayList<>();
            for (int j = 0; j < examRecord.size(); j++) {

                JSONObject examQuestion = examRecord.get(j);
                String questionId = examQuestion.getString("questionId");
                //获取该题目的选项
                if (i + 1 != 5) {
                    List<Options> options = new ArrayList<>();
//                    String tmp_tf_Options = cacheKit.getVal("exam_questionId_Options_" + questionId);
//                    if (!V.isEmpty(tmp_tf_Options)) {
//                        options = JSONObject.parseArray(tmp_tf_Options, Options.class);
//
//                    } else {
                    String where = " and questionId = '" + questionId + "' ";
                    options = optionsService.queryByQuestionId(where);
//                        cacheKit.setVal("exam_questionId_Options_" + questionId, JSONObject.toJSONString(options), 900);
//                    }

                    JSONObject optionsOrder = examQuestion.getJSONObject("optionOrdered");
                    examQuestion.remove("optionOrdered");
                    examQuestion.remove("examId");
                    examQuestion.remove("createTime");
                    examQuestion.remove("number");

                    List<JSONObject> optionsJSON = new ArrayList<>();
                    for (Options o : options) {
                        JSONObject optionJSON = J.o2j(o);
                        if (!V.isEmpty(optionsOrder)) {
                            Integer order = optionsOrder.getInteger(o.getId());
                            if (!V.isEmpty(order)) {
//                                optionJSON.remove("optionNumber");
//                                optionJSON.put("optionNumber", order + 1);
                                optionJSON.put("ordered", order + 1);
                            }
                        } else {
                            optionJSON.put("ordered", optionJSON.getInteger("optionNumber"));
                        }
                        optionsJSON.add(optionJSON);
                    }
                    examQuestion.put("options", optionsJSON);
                }

                teamPapers.add(examQuestion);
            }
            back.put(types[i], teamPapers);
        }


        return R.ok(back);

    }


}
