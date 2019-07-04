package team.work.api.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.core.model.*;
import team.work.core.service.impl.*;
import team.work.core.service.impl.*;
import team.work.core.tps.CacheKit;
import team.work.doc.HistoryAnswerCache;
import team.work.doc.PaperMark;
import team.work.utils.convert.*;
import team.work.utils.kit.RandomKit;
import team.work.utils.kit.TimeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import team.work.core.model.Exam;
import team.work.core.model.ExamCandidate;
import team.work.core.model.ExamRecord;
import team.work.core.model.Options;
import team.work.doc.HistoryAnswerCache;
import team.work.doc.PaperMark;
import team.work.utils.convert.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Api(value = "25_试卷题目管理")
@RestController
@RequestMapping("/v1/base")
public class ExamQuestionApi extends BaseApi {
    @Autowired
    private ExamQuestionService examQuestionService;
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamRecordService examRecordService;
    @Autowired
    private ExamCandidateService examCandidateService;

    @GetMapping("/examQuestion/exam/{examId}")
    @ApiOperation(value = "读取试卷题目和选项")
    public Object getQuestionsByType(@PathVariable("examId") String examId,
                                     @RequestHeader("token") String token) {


        String number = getNumberByCache(token);//获取操作人id
        Map<String, ArrayList<JSONObject>> back = new TreeMap<>();


        JSONObject examObj = new JSONObject();

        String examWKey = "";
        if (!V.isEmpty(examId)) {
            examWKey = S.apppend(" and examId = '", examId, "' ");
        } else {
            return R.error("作业不存在！");
        }
        if (!V.isEmpty(number)) {
            examWKey += S.apppend(" and number = '", number, "' ");
        } else {
            return R.error("请先登录");
        }
        List<JSONObject> examList = new ArrayList<>();
//        String tmp_exam_examInfo = cacheKit.getVal("exam_info_tips_" + examId + "_" + number);
//        if (!V.isEmpty(tmp_exam_examInfo)) {
//            examList = JSONObject.parseArray(tmp_exam_examInfo, JSONObject.class);
//            examObj = examList.get(0);

//        } else {
            examList = examCandidateService.queryAll(examWKey);
            if (V.isEmpty(examList)) {
                return R.error("作业不存在！");
            }
            examObj = examList.get(0);
            Integer examStatus = examObj.getInteger("examStatus");
            Integer releaseStatus = examObj.getInteger("releaseStatus");
//            if (examStatus == 1 && releaseStatus == 2) {
//                cacheKit.setVal("exam_info_tips_" + examId + "_" + number, JSONObject.toJSONString(examList), 60);
//            }
//        }


        if (V.isEmpty(examObj)) {
            return R.error("试卷不存在！");
        }
//        Integer examStatus = examObj.getInteger("examStatus");
//        Integer releaseStatus = examObj.getInteger("releaseStatus");
        String name = examObj.getString("examName");

        if (V.isEmpty(examStatus) || V.isEmpty(releaseStatus)) {
            return R.error("作业未处于进行状态，无法获取试卷！");
        }
        if (!(examStatus == 1 || releaseStatus == 2)) {
            return R.error("作业处于已结束或未开始状态，无法获取试卷！");
        }

        Long nowTime = TimeKit.getTimestamp();
        Long startTime = examObj.getBigInteger("startTime").longValue();
        Long endTime = examObj.getBigInteger("endTime").longValue();

        if (nowTime < startTime) {
            return R.error("作业未开始，无法获取试卷！");
        }

        ExamCandidate examCandidate = getExamCandidate(examId, token);


        if (V.isEmpty(examCandidate)) {
            return R.error("作业无权限！");
        }

        if (examCandidate.getStatus() == 2) {
            return R.error("您已交卷，请勿重复提交作业！");
        }

        if (nowTime > endTime) {
            closeExamCandidate(examCandidate);
            return R.error("作业已结束，无法获取作业信息！");
        }


        Integer duration = null;
        Integer duration_obj = examObj.getInteger("duration");
        Integer duration_sec = duration_obj * 60;
        if (V.isEmpty(duration_obj)) {
            return R.error("作业限制时长不存在，请联系管理员维护！");
        }

        long submitTime = 0;
        long remainTime = endTime - nowTime;

        String tmp_submitTimeStr = cacheKit.getVal("examQuestion_submitTime_" + number + "_" + examId);
        if (!V.isEmpty(tmp_submitTimeStr)) {
            submitTime = Long.parseLong(tmp_submitTimeStr);
        } else {
            submitTime = nowTime;
            cacheKit.setVal("examQuestion_submitTime_" + number + "_" + examId, submitTime + "", (int) remainTime + 600);
        }


        if (duration_obj != 0) {
            duration = (int) (duration_sec - (nowTime - submitTime));
            if (duration <= 0) {
                closeExamCandidate(examCandidate);
                return R.error("作业已结束，无法获取作业信息！");
            } else {
                //迟交学生处理，当剩余时间小于时长时，取剩余时间
                if (duration >= remainTime) {
                    duration = (int) remainTime;
                }
            }

        } else {
            if (remainTime > 86400 - 1) {
                duration = 86400 - 1;
            } else {
                duration = (int) remainTime;
            }
        }


        String wKey = S.apppend(" and examId = '", examId, "' ");

        String[] types = {"trueOrFalse", "singleChoice", "multipleChoice", "gapFilling", "subjective"};

        String tmp_examQuestion_number = cacheKit.getVal("examQuestion_number_" + examId + "_" + number);
        if (!V.isEmpty(tmp_examQuestion_number)) {

            back = (Map<String, ArrayList<JSONObject>>) JSONObject.parse(tmp_examQuestion_number);
        } else {
            for (int i = 0; i < 5; i++) {
                List<JSONObject> examQuestions = new ArrayList<>();

                String tmp_examQuestion_type = cacheKit.getVal("examQuestion_type_" + examId + "_" + (i + 1));
                if (!V.isEmpty(tmp_examQuestion_type)) {

                    examQuestions = JSONObject.parseArray(tmp_examQuestion_type, JSONObject.class);
                } else {
                    examQuestions = examQuestionService.queryPCQuestion(wKey + " and type = '" + (i + 1) + "' ");
                    cacheKit.setVal("examQuestion_type_" + examId + "_" + (i + 1), JSONObject.toJSONString(examQuestions), duration);
                }

                ArrayList<JSONObject> teamPapers = new ArrayList<>();
                if (V.isEmpty(examQuestions)) {
                    continue;
                }
                int size = examQuestions.size();
                //获取题数范围内的不重复随机数
                int[] randomOrderNum = RandomKit.getRandomNum(size, size);
                for (int j = 0; j < examQuestions.size(); j++) {

                    JSONObject examQuestion = examQuestions.get(j);
                    String questionId = examQuestion.getString("questionId");
                    //设置题目顺序
                    examQuestion.put("order", randomOrderNum[j]);
                    if (i + 1 != 5) {//5-客观题没有选项

                        //获取该题目的选项
                        String where = " and questionId = '" + questionId + "' ";
                        List<Options> options = new ArrayList<>();
                        //fixme:存储未答题时的作业选项获取
                        String tmp_exam_questionId_options_unans = cacheKit.getVal("exam_questionId_options_unans_" + questionId);
                        if (!V.isEmpty(tmp_exam_questionId_options_unans)) {
                            options = JSONObject.parseArray(tmp_exam_questionId_options_unans, Options.class);
                        } else {
                            options = optionsService.queryPCByQuestionId(where);
                            cacheKit.setVal("exam_questionId_options_unans_" + questionId, JSONObject.toJSONString(options), duration);

                        }
                        List<JSONObject> optionsJSON = new ArrayList<>();
                        int optSize = options.size();
                        int[] optOrderNum = RandomKit.getRandomNum(optSize, optSize);
                        for (int k = 0; k < optSize; k++) {
                            Options o = options.get(k);
                            JSONObject optionObject = J.o2j(o);
                            optionObject.put("order", optOrderNum[k]);
                            optionsJSON.add(optionObject);
                        }

                        examQuestion.put("options", F.f2l(optionsJSON, "id", "questionId"));
                    }
                    F.f2l(examQuestions, "id");

                    teamPapers.add(examQuestion);
                }
                back.put(types[i], teamPapers);
            }
            cacheKit.setVal("examQuestion_number_" + examId + "_" + number, JSONObject.toJSONString(back), duration + 600);


        }


        ArrayList<JSONObject> examJson = new ArrayList<>();
        JSONObject examObjJson = new JSONObject();
        examObjJson.put("startTime", startTime + "");
        examObjJson.put("endTime", endTime + "");
        examObjJson.put("nowTime", nowTime + "");
        examObjJson.put("submitTime", submitTime + "");
        examObjJson.put("duration", duration + "");
        examObjJson.put("name", name);

        examJson.add(examObjJson);
        back.put("examInfo", examJson);


        return R.ok(back);

    }

    @Transactional
    @PutMapping("/examQuestion/mark")
    @ApiOperation(value = "评卷")
    public Object markExamQuestion(@RequestBody PaperMark object,
                                   @RequestHeader("token") String token) {

        String number = getNumberByCache(token);//获取操作人id

        String userId = getUserIdByCache(token);//获取操作人id

        String examId = object.getExamId();
        if (V.isEmpty(examId)) {
            return R.error("作业不存在！");
        }


        String tmp_examQuestion_number = cacheKit.getVal("examQuestion_number_" + examId + "_" + number);

        Map<String, Integer> questionMap = new HashMap<>();
        Map<String, Map<String, Integer>> singleOptionsMap = new HashMap<>();
        Map<String, Map<String, Integer>> mulOptionsMap = new HashMap<>();

        if (!V.isEmpty(tmp_examQuestion_number)) {
            Map<String, ArrayList<JSONObject>> back = (Map<String, ArrayList<JSONObject>>) JSONObject.parse(tmp_examQuestion_number);
            List<JSONObject> singleChoice = back.get("singleChoice");
            for (JSONObject jsonObject : singleChoice) {
                questionMap.put(jsonObject.getString("questionId"), jsonObject.getInteger("order"));
                Map<String, Integer> map = new HashMap<>();
                JSONArray optionsJson = jsonObject.getJSONArray("options");
                for (int i = 0; i < optionsJson.size(); i++) {
                    JSONObject optObject = optionsJson.getJSONObject(i);
                    String id = optObject.getString("id");
                    Integer order = optObject.getInteger("order");
                    map.put(id, order);
                }
                singleOptionsMap.put(jsonObject.getString("questionId"), map);
//                System.out.println(JSONArray.toJSONString(map));
            }

            List<JSONObject> mulChoice = back.get("multipleChoice");
            for (JSONObject jsonObject : mulChoice) {
                questionMap.put(jsonObject.getString("questionId"), jsonObject.getInteger("order"));
                Map<String, Integer> map = new HashMap<>();
                JSONArray optionsJson = jsonObject.getJSONArray("options");
                for (int i = 0; i < optionsJson.size(); i++) {
                    JSONObject optObject = optionsJson.getJSONObject(i);
                    String id = optObject.getString("id");
                    Integer order = optObject.getInteger("order");
                    map.put(id, order);
                }
                mulOptionsMap.put(jsonObject.getString("questionId"), map);
//                System.out.println(JSONArray.toJSONString(map));
            }


            List<JSONObject> trueOrFalse = back.get("trueOrFalse");
            for (JSONObject jsonObject : trueOrFalse) {
                questionMap.put(jsonObject.getString("questionId"), jsonObject.getInteger("order"));
            }

            List<JSONObject> gapFilling = back.get("gapFilling");
            for (JSONObject jsonObject : gapFilling) {
                questionMap.put(jsonObject.getString("questionId"), jsonObject.getInteger("order"));
            }
        }

        String tmp_exam_info = cacheKit.getVal("exam_info_" + examId);
        Exam examObj = new Exam();
        if (!V.isEmpty(tmp_exam_info)) {
            examObj = JSON.parseObject(tmp_exam_info, Exam.class);
        } else {
            examObj = examService.queryById(examId).toJavaObject(Exam.class);
            Integer examStatus = examObj.getStatus();
            Integer releaseStatus = examObj.getReleaseStatus();
            if (examStatus == 1 && releaseStatus == 2) {
                cacheKit.setVal("exam_info_" + examId, JSONObject.toJSONString(examObj), 60);
            }
        }

        if (V.isEmpty(examObj)) {
            return R.error("试卷不存在！");
        }

        //fixme:允许延时十分钟交卷


        Integer examStatus = examObj.getStatus();
        Integer releaseStatus = examObj.getReleaseStatus();
        if (V.isEmpty(examStatus) || V.isEmpty(releaseStatus)) {
            return R.error("未处于开考状态，无法提交试卷！");
        }
        if (!(examStatus == 1 || releaseStatus == 2)) {
            return R.error("未处于开考状态，无法提交试卷！");
        }

        ExamCandidate examCandidate = getExamCandidate(examId, token);

        if (V.isEmpty(examCandidate)) {
            return R.error("无权限！");
        }


        if (examCandidate.getStatus() == 2) {
            return R.error("您已交卷，请勿重复提交作业！");
        }
        Long nowTime = TimeKit.getTimestamp();
        Long startTime = examObj.getStartTime().longValue();
        Long endTime = examObj.getEndTime().longValue();
        if (nowTime < startTime) {
            return R.error("作业未开始，无法提交试卷！");
        }
        //fixme:允许延时十分钟交卷
        if (nowTime > endTime + 600) {
            closeExamCandidate(examCandidate);
            return R.error("作业已结束，无法提交试卷！");
        }

        String tmp_submitTimeStr = cacheKit.getVal("examQuestion_submitTime_" + number + "_" + examId);
        if (V.isEmpty(tmp_submitTimeStr)) {
            return R.error("非法访问，无法提交试卷！");
        }

        Integer duration_obj = examObj.getDuration();
        Integer duration_sec = duration_obj * 60;
        if (V.isEmpty(duration_obj)) {
            return R.error("作业时长不存在，请联系管理员维护！");
        }

        Integer duration = duration_sec;
        long submitTime = Long.parseLong(tmp_submitTimeStr);


        long remainTime = endTime - nowTime;

        if (duration_obj != 0) {
            //fixme:允许延时十分钟交卷
            duration = (int) (duration_sec - (nowTime - submitTime)) + 600;//延时十分钟交卷
            if (duration <= 0) {
                closeExamCandidate(examCandidate);
                return R.error("作业已结束，无法提交试卷！");
            } else {
                //迟到学生处理，当剩余时间小于时长时，取剩余时间
                if (duration >= remainTime) {
                    duration = (int) remainTime;
                }
            }

        } else {
            if (remainTime > 86400 - 1) {
                duration = 86400 - 1;
            } else {
                duration = (int) remainTime;
            }
        }


        String tmp_exam_AnsStatus = cacheKit.getVal("exam_ans_lock_" + number + "_" + examId);
        if (tmp_exam_AnsStatus != null && tmp_exam_AnsStatus.length() > 0) {
            return R.error("正在判卷中，请勿重复交卷！");
        }
        cacheKit.setVal("exam_ans_lock_" + number + "_" + examId, "true", duration);


        Double score = 0.0;

        Map<Integer, Map<String, String>> tfAnswerList = object.getTrueFalseAnswer();
        Map<Integer, Map<String, String>> singleAnswerList = object.getSingleAnswer();
        Map<Integer, Map<String, String[]>> multipleAnswerList = object.getMultipleAnswer();
        Map<Integer, Map<String, String[]>> gapAnswerList = object.getGapAnswer();
        Map<Integer, Map<String, String>> subjectiveAnswerList = object.getSubjectiveAnswer();

        List<ExamRecord> examRecords = new ArrayList<>();

        //一、单选题
        if (singleAnswerList != null) {
            for (Integer index : singleAnswerList.keySet()) {

                String singleOptionId = singleAnswerList.get(index).get("optionId");
                String questionId = singleAnswerList.get(index).get("questionId");

                //1 在options中通过optionId查找所属question和isAnswer=1
                List<Options> optionsList = new ArrayList<>();

                String tmp_single_Options = cacheKit.getVal("exam_questionId_Options_" + questionId);
                if (!V.isEmpty(tmp_single_Options)) {
                    optionsList = JSONObject.parseArray(tmp_single_Options, Options.class);

                } else {
                    String where = " and questionId = '" + questionId + "' ";
                    optionsList = optionsService.queryByQuestionId(where);
                    cacheKit.setVal("exam_questionId_Options_" + questionId, JSONObject.toJSONString(optionsList), duration);
                }

                boolean isTrue = false;//答错
                Map<String, Integer> map = singleOptionsMap.get(questionId);


                //新增到答题记录表
                ExamRecord examRecord = new ExamRecord();
                examRecord.setExamId(examId);
                examRecord.setNumber(number);
                examRecord.setQuestionId(questionId);

                //通过optionId获取optionNumber,转换：1-A,2-B...?
                String answerNum = "";
                for (Options option : optionsList) {
                    if (option.getId().equals(singleOptionId)) {
                        answerNum += (char) (map.get(singleOptionId) + 1 + 64) + " ";
                        if (option.getIsAnswer() == 1)
                            isTrue = true;//答对
                        break;
                    }
                }
                examRecord.setAnswerContent(answerNum);//存学生选择的项A/B/C/D

                //  1.1 查不到 -> 0分
                if (!isTrue) {
                    //记录错误答案
                    examRecord.setSituation(2);//答错
                    examRecord.setScores(0d);
                    examRecords.add(examRecord);
                    continue;
                }

                // 记录答题情况
                examRecord.setSituation(1);//答对


                Double examQuestionScore = null;
                String tmp_exam_question_score = cacheKit.getVal("exam_question_score_" + examId + "_" + questionId);
                if (!V.isEmpty(tmp_exam_question_score)) {
                    examQuestionScore = Double.parseDouble(tmp_exam_question_score);

                } else {
                    examQuestionScore = examQuestionService.queryPCQuestionScore(examId, questionId);
                    cacheKit.setVal("exam_question_score_" + examId + "_" + questionId, examQuestionScore.toString(), duration);
                }


                if (!V.isEmpty(examQuestionScore)) {
                    examRecord.setScores(examQuestionScore);
                    examRecords.add(examRecord);
                    score += examQuestionScore;
                }
            }
        }

        //二、判断题
        if (tfAnswerList != null) {
            for (Integer index : tfAnswerList.keySet()) {
                String tfAnswerId = tfAnswerList.get(index).get("optionId");
                String questionId = tfAnswerList.get(index).get("questionId");

                List<Options> optionsList = new ArrayList<>();
                String tmp_tf_Options = cacheKit.getVal("exam_questionId_Options_" + questionId);
                if (!V.isEmpty(tmp_tf_Options)) {
                    optionsList = JSONObject.parseArray(tmp_tf_Options, Options.class);

                } else {
                    String where = " and questionId = '" + questionId + "' ";
                    optionsList = optionsService.queryByQuestionId(where);
                    cacheKit.setVal("exam_questionId_Options_" + questionId, JSONObject.toJSONString(optionsList), duration);
                }


                boolean isTrue = false;//答错

                //新增到答题记录表
                ExamRecord examRecord = new ExamRecord();
                examRecord.setExamId(examId);
                examRecord.setNumber(number);
                examRecord.setQuestionId(questionId);

                //通过optionId获取optionNumber,转换：1-A,2-B...
                String answerNum = "";
                for (Options option : optionsList) {
                    if (option.getId().equals(tfAnswerId)) {
                        answerNum += (char) (option.getOptionNumber() + 64) + " ";
                        if (option.getIsAnswer() == 1)
                            isTrue = true;//答对
                        break;
                    }
                }
                examRecord.setAnswerContent(answerNum);//存学生选择的项A/B/C/D


                //  1.1 查不到 -> 0分,继续判断下一题
                if (!isTrue) {
                    //记录错误答案
                    examRecord.setSituation(2);//答错
                    examRecord.setScores(0d);
                    examRecords.add(examRecord);
                    continue;
                }


                // 记录题目
                examRecord.setSituation(1);//答对

                Double examQuestionScore = null;

                String tmp_exam_question_score = cacheKit.getVal("exam_question_score_" + examId + "_" + questionId);
                if (!V.isEmpty(tmp_exam_question_score)) {
                    examQuestionScore = Double.parseDouble(tmp_exam_question_score);
                } else {
                    examQuestionScore = examQuestionService.queryPCQuestionScore(examId, questionId);
                    cacheKit.setVal("exam_question_score_" + examId + "_" + questionId, examQuestionScore.toString(), duration);
                }

                if (!V.isEmpty(examQuestionScore)) {
                    examRecord.setScores(examQuestionScore);
                    examRecords.add(examRecord);
                    score += examQuestionScore;
                }
            }
        }

        //三、多选题
        if (multipleAnswerList != null) {
            for (Integer index : multipleAnswerList.keySet()) {
                String questionId = multipleAnswerList.get(index).get("questionId")[0];//[0] - questionId
                String[] options = multipleAnswerList.get(index).get("optionIds");

                //String[]转化List
                List<String> selectOptions = Arrays.asList(options);

                //1-通过questionId获取正确答案的optionId数组
                String where = " and questionId = '" + questionId + "' and isAnswer = 1 ";
                List<String> trueOptionIds = new ArrayList<>();

                String tmp_multiple_trueOptions = cacheKit.getVal("exam_questionId_trueOptionIds_" + questionId);

                if (!V.isEmpty(tmp_multiple_trueOptions)) {
                    trueOptionIds = JSONObject.parseArray(tmp_multiple_trueOptions, String.class);

                } else {
                    trueOptionIds = optionsService.queryTrueIdByQuestionId(where);

                    cacheKit.setVal("exam_questionId_trueOptionIds_" + questionId, JSONObject.toJSONString(trueOptionIds), duration);
                }


                List<Options> optionsList = new ArrayList<>();

                String tmp_multiple_Options = cacheKit.getVal("exam_questionId_Options_" + questionId);

                if (!V.isEmpty(tmp_multiple_Options)) {
                    optionsList = JSONObject.parseArray(tmp_multiple_Options, Options.class);

                } else {
                    String where2 = " and questionId = '" + questionId + "' ";
                    optionsList = optionsService.queryByQuestionId(where2);
                    cacheKit.setVal("exam_questionId_Options_" + questionId, JSONObject.toJSONString(optionsList), duration);
                }

                Map<String, Integer> map = mulOptionsMap.get(questionId);

//            boolean isTrue = false;//答错
                //新增到答题记录表
                ExamRecord examRecord = new ExamRecord();
                examRecord.setExamId(examId);
                examRecord.setNumber(number);
                examRecord.setQuestionId(questionId);
//                examRecord.setOrdered(questionMap.get(questionId));
//                examRecord.setOptionOrdered(JSON.toJSONString(map));

                //通过optionId获取optionNumber,转换：1-A,2-B...
                String[] answerAry = new String[optionsList.size()];
                for (Options option : optionsList) {
                    if (selectOptions.contains(option.getId())) {
                        Integer order = map.get(option.getId());
                        answerAry[order] = (char) (order + 1 + 64) + "";
                    }
                }
                StringBuffer answerNum = new StringBuffer();
                for (String str : answerAry) {
                    if (str != null && !str.equals("") && !str.equals("null")) {
                        answerNum.append(str + "  ");
                    }
                }

                examRecord.setAnswerContent(answerNum.toString());//存学生选择的项A/B/C/D


                //2-判断学生选择的答案是否都在optionId数组里
                if (trueOptionIds.containsAll(selectOptions) && selectOptions.containsAll(trueOptionIds)) {

                    //  2.2全对 -> 在exam_question中通过examId和question查找题目，加分
                    // 记录答对记录
                    examRecord.setSituation(1);//答对

                    Double examQuestionScore = null;

                    String tmp_exam_question_score = cacheKit.getVal("exam_question_score_" + examId + "_" + questionId);
                    if (!V.isEmpty(tmp_exam_question_score)) {
                        examQuestionScore = Double.parseDouble(tmp_exam_question_score);
                    } else {
                        examQuestionScore = examQuestionService.queryPCQuestionScore(examId, questionId);
                        cacheKit.setVal("exam_question_score_" + examId + "_" + questionId, examQuestionScore.toString(), duration);
                    }

                    if (!V.isEmpty(examQuestionScore)) {
                        examRecord.setScores(examQuestionScore);
                        examRecords.add(examRecord);
                        score += examQuestionScore;
                    }

                }
                //  2.1 不填、少填、多填都不得分
                // 记录错误答案
                else {
                    examRecord.setSituation(2);//答错
                    examRecord.setScores(0d);
                    examRecords.add(examRecord);
                }
            }
        }

        //四、填空题
        if (gapAnswerList != null) {
            for (Integer index : gapAnswerList.keySet()) {
                String questionId = gapAnswerList.get(index).get("questionId")[0];//[0] - questionId
                String[] inputInfo = gapAnswerList.get(index).get("gapInput");
//                Integer ordered = -1;
//                try {
//                    ordered = Integer.parseInt(gapAnswerList.get(index).get("ordered")[0]);
//                } catch (NumberFormatException e) {
//
//                }
                //1-通过questionId获取正确答案的optionId数组

                List<Options> gapAnswer = new ArrayList<>();
                String tmp_gap_Options = cacheKit.getVal("exam_questionId_Options_" + questionId);

                if (!V.isEmpty(tmp_gap_Options)) {
                    gapAnswer = JSONObject.parseArray(tmp_gap_Options, Options.class);

                } else {
                    String where = " and questionId = '" + questionId + "' ";
                    gapAnswer = optionsService.queryByQuestionId(where);
                    cacheKit.setVal("exam_questionId_Options_" + questionId, JSONObject.toJSONString(gapAnswer), duration);
                }


                //新增到答题记录表
                ExamRecord examRecord = new ExamRecord();
                examRecord.setExamId(examId);
                examRecord.setNumber(number);
                examRecord.setQuestionId(questionId);

                boolean hasTrue = false;
                boolean notAllTrue = false;
                examRecord.setSituation(1);//默认全答对
                StringBuffer answerContent = new StringBuffer();

                Double questionScore = 0d;
                //2-判断学生输入的答案是否正确
                for (int i = 0; inputInfo != null && i < inputInfo.length; i++) {
                    String trueAnswer = gapAnswer.get(i).getOptionInfo().trim();
                    if (trueAnswer.equals(inputInfo[i].trim())) {
                        hasTrue = true;
                        //答案正确，加分
                        //获取该题的分值
                        Double examQuestionScore = null;

                        String tmp_exam_question_score = cacheKit.getVal("exam_question_score_" + examId + "_" + questionId);
                        if (!V.isEmpty(tmp_exam_question_score)) {
                            examQuestionScore = Double.parseDouble(tmp_exam_question_score);
                        } else {
                            examQuestionScore = examQuestionService.queryPCQuestionScore(examId, questionId);
                            cacheKit.setVal("exam_question_score_" + examId + "_" + questionId, examQuestionScore.toString(), duration);
                        }

                        if (!V.isEmpty(examQuestionScore)) {
                            //取每空得分，如若2空对1空，取分值的1/2
                            questionScore += examQuestionScore / gapAnswer.size();
                            score += examQuestionScore / gapAnswer.size();
                        }
                    } else {
                        if (hasTrue)
                            notAllTrue = true;//有对的也有错的
                    }
                    if (i != inputInfo.length - 1) {
                        answerContent.append(inputInfo[i] + "；");
                    } else {
                        answerContent.append(inputInfo[i]);
                    }
                }
                String answerContentStr = answerContent.toString();
                if (answerContentStr.length() > 50) {
                    answerContentStr = answerContentStr.substring(0, 50);
                }

                examRecord.setAnswerContent(answerContentStr);//存学生填入的答案


                //不完全对的情况
                if (notAllTrue) {
                    examRecord.setSituation(3);//3-不完全对
                }else if (!hasTrue) {
                    examRecord.setSituation(2);//没有对的，全错
                }
                examRecord.setScores(questionScore);
                examRecords.add(examRecord);

            }
        }


        //五、客观题，不在自动改卷范围内，只存储到答题记录
        if (subjectiveAnswerList != null) {
            for (Integer index : subjectiveAnswerList.keySet()) {
                String questionId = subjectiveAnswerList.get(index).get("questionId");
                String inputInfo = subjectiveAnswerList.get(index).getOrDefault("gapInput", "");

                //新增到答题记录表
                ExamRecord examRecord = new ExamRecord();
                examRecord.setExamId(examId);
                examRecord.setNumber(number);
                examRecord.setQuestionId(questionId);
                examRecord.setSituation(0);//客观题默认答对，可以考虑置为null，但要解决批量插入问题
                examRecord.setAnswerContent(inputInfo);//存学生填入的答案

                examRecords.add(examRecord);

            }
        }

        //记录答题
        try {
            if (!V.isEmpty(examRecords)) {
                int userCount = examRecordService.insertRows(examRecords, userId);
                if (userCount == 0) {
                    cacheKit.deleteVal("exam_ans_lock_" + number + "_" + examId);
                }
            }

            //记录成绩
            if (score == 0.0) {
                examCandidate.setScore(0d);
            } else {
                //当成绩小数部分大于等于0.5分时，记为0.5分；低于0.5分时，记为0.0分。
                score = (double) Math.round(score * 100) / 100;
                Double score_final = 0.0;
                Integer score_int = score.intValue();
                Double score_dec = score - score_int;
                if (score_dec >= 0.5) {
                    score_final = score_int + 0.5;
                } else {
                    score_final = score_int.doubleValue();
                }
                examCandidate.setScore(score_final);
            }
            examCandidate.setStatus(2);//2-已考
            examCandidateService.updateExamCandidate(examCandidate);
            cacheKit.deleteVal("exam_ans_lock_" + number + "_" + examId);
            cacheKit.deleteVal("examQuestion_submitTime_" + number + "_" + examId);
            cacheKit.deleteVal("examQuestion_Options_" + examId + "_" + number);
            return R.ok("提交成功！", score + "");
        } catch (Exception e) {
            e.printStackTrace();
            cacheKit.deleteVal("exam_ans_lock_" + number + "_" + examId);
            return R.error("提交失败，请联系管理员处理！");
        }

    }

    @PostMapping("/examQuestion/history")
    @ApiOperation(value = "保存答题记录")
    public Object saveExamQuestionHistory(@RequestBody HistoryAnswerCache object,
                                          @RequestHeader("token") String token) {
        String userId = getUserIdByCache(token);
        String examId = object.getExamId();
        if (null == examId || examId.equals("")) {
            return R.error("试卷不存在");
        }
        String number = getNumberByCache(token);
        if (null == number || number.equals("")) {
            return R.error("登录状态异常，请尝试更新缓存！");
        }

        String tmp_exam_info = cacheKit.getVal("exam_info_" + examId);
        Exam examObj = new Exam();
        if (!V.isEmpty(tmp_exam_info)) {
            examObj = JSON.parseObject(tmp_exam_info, Exam.class);
        } else {
            examObj = examService.queryById(examId).toJavaObject(Exam.class);
            Integer examStatus = examObj.getStatus();
            Integer releaseStatus = examObj.getReleaseStatus();
            if (examStatus == 1 && releaseStatus == 2) {
                cacheKit.setVal("exam_info_" + examId, JSONObject.toJSONString(examObj), 60);
            }
        }

        if (V.isEmpty(examObj)) {
            return R.error("试卷不存在！");

        }

        Integer examStatus = examObj.getStatus();
        Integer releaseStatus = examObj.getReleaseStatus();
        if (V.isEmpty(examStatus) || V.isEmpty(releaseStatus)) {
            return R.error("未处于开考状态，无法保存试卷！");
        }
//        if (!(examStatus == 1 && releaseStatus == 2)) {
//            return R.error("未处于开考状态，无法保存试卷！");
//        }
        ExamCandidate examCandidate = getExamCandidate(examId, token);

        if (V.isEmpty(examCandidate)) {
            return R.error("作业不存在！");
        }

        if (examCandidate.getStatus() == 2) {
            return R.error("您已提交，请勿重复提交作业！");
        }
        Long nowTime = TimeKit.getTimestamp();
        Long startTime = examObj.getStartTime().longValue();
        Long endTime = examObj.getEndTime().longValue();
        if (nowTime < startTime) {
            return R.error("作业未开始，无法保存！");
        }
        //fixme:允许延时十分钟交卷
        if (nowTime > endTime + 600) {
            closeExamCandidate(examCandidate);
            return R.error("作业已结束，无法保存试卷！");
        }
        String tmp_submitTimeStr = cacheKit.getVal("examQuestion_submitTime_" + number + "_" + examId);
        if (V.isEmpty(tmp_submitTimeStr)) {
            return R.error("非法访问，无法保存！");
        } else {


            Integer duration_obj = examObj.getDuration();
            Integer duration_sec = duration_obj * 60;
            if (V.isEmpty(duration_obj)) {
                return R.error("作业限制时长不存在，请联系管理员维护！");
            }

            Integer duration = duration_sec;
            long submitTime = Long.parseLong(tmp_submitTimeStr);


            long remainTime = endTime - nowTime;

            if (duration_obj != 0) {
                //fixme:允许延时十分钟交卷
                duration = (int) (duration_sec - (nowTime - submitTime)) + 600;//延时十分钟交卷
                if (duration <= 0) {
                    closeExamCandidate(examCandidate);
                    return R.error("作业已结束，无法保存！");
                } else {
                    //迟到学生处理，当剩余时间小于时长时，取剩余时间
                    if (duration >= remainTime) {
                        duration = (int) remainTime;
                    }
                }

            } else {
                if (remainTime > 60) {
                    duration = 60;
                } else {
                    duration = (int) remainTime;
                }
            }


            JSONObject save = new JSONObject();
            save.put("examId", examId);
            save.put("number", number);
            Map<String, String> singleChoice = object.getSingleChoice();
            if (singleChoice != null) {
                //System.out.println(singleChoice.toString());
            }
            save.put("singleChoice", singleChoice);
            Map<String, Map<String, String>> gapChoice = object.getGapChoice();
            if (gapChoice != null) {
                //  System.out.println(gapChoice.toString());
            }
            save.put("singleChoice", singleChoice);
            Map<String, String[]> multiChoice = object.getMultiChoice();
            if (multiChoice != null) {
                // System.out.println(multiChoice.toString());
            }
            save.put("multiChoice", multiChoice);
            cacheKit.setVal("examQuestion-history-" + examId + "-" + number, save.toJSONString(), duration);

            return R.ok("存储成功");


        }

    }


    @GetMapping("/examQuestion/history/{examId}")
    @ApiOperation(value = "读取答题记录")
    public Object getExamQuestionHistory(@PathVariable("examId") String examId,
                                         @RequestHeader("token") String token) {

        if (null == examId || examId.equals("")) {
            return R.error("试卷不存在");
        }
        String number = getNumberByCache(token);
        if (null == number || number.equals("")) {
            return R.error("登录状态异常，请尝试更新缓存！");
        }
        JSONObject res = new JSONObject();
        Boolean flag = false;
        String tmp_examQuestion_history = cacheKit.getVal("examQuestion-history-" + examId + "-" + number);
        if (null != tmp_examQuestion_history && !tmp_examQuestion_history.equals("")) {
            res = JSON.parseObject(tmp_examQuestion_history);
            flag = true;
        }
        res.put("flag", flag);

        return R.ok(res);

    }

    public Boolean closeExamCandidate(ExamCandidate model) {

        String[] examQuestions = null;
        String examId = model.getExamId();
        String number = model.getNumber();
        if (V.isEmpty(examId) || V.isEmpty(number)) {
            return false;
        }


        List<ExamRecord> examRecords = new ArrayList<>();

        String tmp_exam_question = cacheKit.getVal("exam_question_" + examId);

        if (!V.isEmpty(tmp_exam_question)) {
            JSONArray examQuestionArray = JSONArray.parseArray(tmp_exam_question);
            examQuestions = new String[examQuestionArray.size()];
            examQuestions = examQuestionArray.toArray(examQuestions);

        } else {
            examQuestions = examQuestionService.queryPCQuestionIds(examId);
            cacheKit.setVal("exam_question_" + examId, JSONObject.toJSONString(examQuestions), 1800);
        }

        for (int i = 0; examQuestions != null && i < examQuestions.length; i++) {
            ExamRecord examRecord = new ExamRecord();
            examRecord.setExamId(examId);
            examRecord.setNumber(number);
            examRecord.setQuestionId(examQuestions[i]);
            examRecord.setSituation(2);
            examRecord.setAnswerContent("");
            examRecords.add(examRecord);

        }

        //记录答题
        try {
            if (!V.isEmpty(examRecords)) {
                int userCount = examRecordService.insertRows(examRecords, model.getCreator());
            }

            model.setScore(0d);
            model.setStatus(2);
            model = examCandidateService.updateExamCandidate(model);
            if (model == null) {
                return false;
            } else {
                cacheKit.deleteVal("examQuestion_submitTime_" + number + "_" + examId);
                cacheKit.deleteVal("examQuestion_Options_" + examId + "_" + number);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public ExamCandidate getExamCandidate(String examId, String token) {
        String number = getNumberByCache(token);
        String userId = getUserIdByCache(token);
        List<ExamCandidate> examCandidateList = examCandidateService.query(W.f(
                W.and("examId", "eq", examId),
                W.and("number", "eq", number),
                W.and("isDel", "eq", 0)
        ));
        ExamCandidate examCandidate = new ExamCandidate();

        if (examCandidateList == null || examCandidateList.size() == 0) {
            examCandidate.setExamId(examId);
            examCandidate.setNumber(number);
            examCandidate.setCreator(userId);

            examCandidate = examCandidateService.createExamCandidate(examCandidate);
        } else {
            examCandidate = examCandidateList.get(0);

        }

        return examCandidate;
    }


}
