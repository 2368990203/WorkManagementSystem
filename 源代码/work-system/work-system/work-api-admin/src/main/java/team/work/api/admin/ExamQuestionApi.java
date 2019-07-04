package team.work.api.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.model.*;
import team.work.core.service.impl.*;
import team.work.doc.ExamCustom;
import team.work.doc.ExamQuestionCreate;
import team.work.doc.ExamQuestionUpdate;
import team.work.doc.PaperMark;
import team.work.utils.convert.*;

import java.util.*;


@Api(value = "37_作业和题目关系管理")
@RestController
@RequestMapping("/v1/base")
public class ExamQuestionApi extends BaseApi {
    @Autowired
    private ExamQuestionService examQuestionService;
    @Autowired
    private ExamService examService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ExamRecordService examRecordService;
    @Autowired
    private ExamCandidateService examCandidateService;

    @PostMapping("/examQuestion")
    @ApiOperation(value = "作业和题目关系新增")
    public Object createExamQuestion(@RequestBody ExamQuestionCreate object,
                                     @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);

        //映射对象
        ExamQuestion model = o2c(object, token, ExamQuestion.class);
        //数据校验
        JSONObject check = V.checkEmpty(verify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        Boolean exist = examQuestionService.exist(W.f(
                W.and("examId", "eq", model.getExamId()),
                W.and("questionId", "eq", model.getQuestionId()),
                W.and("isDel", "eq", "0"))
        );
        if (exist)
            return R.error("请不要重复添加题目！");

        model = examQuestionService.createExamQuestion(model);
        if (model == null)
            return R.error("新增失败");
        return R.ok("新增成功", fm2(model));
    }

    @PutMapping("/examQuestion")
    @ApiOperation(value = "修改作业和题目关系")
    public Object updateExamQuestion(@RequestBody ExamQuestionUpdate object,
                                     @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);
        //映射对象
        ExamQuestion model = o2c(object, token, ExamQuestion.class);
        //数据校验
        JSONObject check = V.checkEmpty(updateVerify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));
        model.setReviser(userId);
        model = examQuestionService.updateExamQuestion(model);
        if (model == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/examQuestion/{id}")
    @ApiOperation(value = "作业和题目关系删除")
    public Object deleteExamQuestion(@PathVariable("id") String id,
                                     @RequestHeader("token") String token) {

        if (!examQuestionService.existId(id))
            return R.error("Id数据异常");

        if (examQuestionService.deleteExamQuestion(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/examQuestion/{index}-{size}-{examId}")
    @ApiOperation(value = "读取作业和题目关系分页列表")
    public Object getExamQuestion(@PathVariable("index") int index,
                                  @PathVariable("size") int size,
                                  @PathVariable("examId") String examId,
                                  @RequestHeader("token") String token) {

        String wKey = "";
        if (!V.isEmpty(examId))
            wKey = S.apppend(" and examId = '", examId, "' ");
        return R.ok(examQuestionService.page(index, size, wKey));

    }

    @GetMapping("/examQuestion")
    @ApiOperation(value = "读取作业和题目关系所有列表")
    public Object getAllExamQuestion(@RequestHeader("token") String token) {

        return R.ok(examQuestionService.queryAll(""));

    }


    @GetMapping("/examQuestion/type")
    @ApiOperation(value = "读取题库的题目数量")
    public Object getQuestionByType(@RequestHeader("token") String token) {

        JSONObject back = new JSONObject();
        String[] type = {"trueOrFalse", "single", "multiple", "gap"};
        for (int i = 0; i < 4; i++) {
            back.put(type[i], questionService.getQuestionCount(" and type = " + (i + 1) + " "));
        }
        return R.ok(back);

    }


    @PutMapping("/examQuestion/custom")
    @ApiOperation(value = "自定义组卷")
    public Object customExam(@RequestBody ExamCustom object,
                             @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);

        //通过id获取作业信息
        String examId = object.getExamId();

        System.out.println(object);

        List<Exam> examList = examService.findByIds(examId);
        if (examList == null || examList.size() == 0)
            return R.error("作业id异常");
        Exam exam = examList.get(0);

        Double single = Double.parseDouble(exam.getSingle());
        Double multiple = Double.parseDouble(exam.getMultiple());
        Double gap = Double.parseDouble(exam.getGap());
        Double trueOrFalse = Double.parseDouble(exam.getTrueOrFalse());
        Double subjective = Double.parseDouble(exam.getSubjective());


        ArrayList<String> trueFalseIds = object.getTrueFalseIds();
        ArrayList<String> singleIds = object.getSingleIds();
        ArrayList<String> multipleIds = object.getMultipleIds();
        ArrayList<String> gapIds = object.getGapIds();
        ArrayList<String> subjectiveIds = object.getSubjectiveIds();


        if (single == 0 && !V.isEmpty(singleIds)) {
            return R.error("单选题分值为0，不可添加题目");
        }

        if (multiple == 0 && !V.isEmpty(multipleIds)) {
            return R.error("多选题分值为0，不可添加题目");
        }

        if (trueOrFalse == 0 && !V.isEmpty(trueFalseIds)) {
            return R.error("判断题分值为0，不可添加题目");
        }

        if (gap == 0 && !V.isEmpty(gapIds)) {
            return R.error("填空题分值为0，不可添加题目");
        }
        if (subjective == 0 && !V.isEmpty(subjectiveIds)) {
            return R.error("客观题分值为0，不可添加题目");
        }

        if (V.isEmpty(singleIds) && V.isEmpty(multipleIds) && V.isEmpty(gapIds) && V.isEmpty(trueFalseIds)) {
            return R.error("请选择题目");
        }

        if (gap != 0 && V.isEmpty(gapIds)) {
            return R.error("请选择填空题");
        }

        if (single != 0 && V.isEmpty(singleIds)) {
            return R.error("请选择单选题");
        }

        if (multiple != 0 && V.isEmpty(multipleIds)) {
            return R.error("请选择多选题");
        }

        if (trueOrFalse != 0 && V.isEmpty(trueFalseIds)) {
            return R.error("请选择判断题");
        }
        if (subjective != 0 && V.isEmpty(subjectiveIds)) {
            return R.error("请选择客观题");
        }

        String trueFalseScore = "";
        if (!V.isEmpty(trueFalseIds)) {
            trueFalseScore = String.format("%.2f", trueOrFalse / trueFalseIds.size());
            if (!checkScore(trueOrFalse.intValue(), trueFalseIds.size())) {
                return R.error("判断题单题分数必须为整数或者0.5分结尾！");
            }
        }

        String multipleScore = "";
        if (!V.isEmpty(multipleIds)) {
            multipleScore = String.format("%.2f", multiple / multipleIds.size());
            if (!checkScore(multiple.intValue(), multipleIds.size())) {
                return R.error("多选题单题分数必须为整数或者0.5分结尾！");
            }
        }

        String gapScore = "";
        if (!V.isEmpty(gapIds)) {
            gapScore = String.format("%.2f", gap / gapIds.size());
            if (!checkScore(gap.intValue(), gapIds.size())) {
                return R.error("填空题单题分数必须为整数或者0.5分结尾！");
            }
        }

        String singleScore = "";
        if (!V.isEmpty(singleIds)) {
            singleScore = String.format("%.2f", single / singleIds.size());
            if (!checkScore(single.intValue(), singleIds.size())) {
                return R.error("单选题单题分数必须为整数或者0.5分结尾！");
            }
        }
        String subjectiveScore = "";
        if (!V.isEmpty(subjectiveIds)) {
            subjectiveScore = String.format("%.2f", subjective / subjectiveIds.size());
            if (!checkScore(subjective.intValue(), subjectiveIds.size())) {
                return R.error("客观题单题分数必须为整数或者0.5分结尾！");
            }
        }

        //删除旧题
        List<JSONObject> examQuestions = examQuestionService.queryAll(" and examId = '" + examId + "' ");
        for (JSONObject eq : examQuestions) {
            examQuestionService.deleteExamQuestion(eq.getString("id"), userId);
        }
        for (int i = 0; trueFalseIds != null && i < trueFalseIds.size(); i++) {
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExamId(examId);
            examQuestion.setQuestionId(trueFalseIds.get(i));
            examQuestion.setScore(trueFalseScore);
            examQuestionService.createExamQuestion(examQuestion);
        }

        for (int i = 0; singleIds != null && i < singleIds.size(); i++) {

            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExamId(examId);
            examQuestion.setQuestionId(singleIds.get(i));
            examQuestion.setScore(singleScore);
            examQuestionService.createExamQuestion(examQuestion);
        }

        for (int i = 0; multipleIds != null && i < multipleIds.size(); i++) {

            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExamId(examId);
            examQuestion.setQuestionId(multipleIds.get(i));
            examQuestion.setScore(multipleScore);
            examQuestionService.createExamQuestion(examQuestion);
        }

        for (int i = 0; gapIds != null && i < gapIds.size(); i++) {
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExamId(examId);
            examQuestion.setQuestionId(gapIds.get(i));
            examQuestion.setScore(gapScore);
            examQuestionService.createExamQuestion(examQuestion);
        }

        for (int i = 0; subjectiveIds != null && i < subjectiveIds.size(); i++) {
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExamId(examId);
            examQuestion.setQuestionId(subjectiveIds.get(i));
            examQuestion.setScore(subjectiveScore);
            examQuestionService.createExamQuestion(examQuestion);
        }

        examService.updateExam(exam);

        return R.ok("组卷成功,请确认后发布");

    }

    public boolean checkScore(Integer score, Integer length) {
        if (score == null || length == null) {
            return false;
        }
        if (score % length == 0 || score % length == length / 2) {
            return true;
        } else {
            return false;
        }
    }


//    @PutMapping("/examQuestion/random")
//    @ApiOperation(value = "随机组卷")
//    @Transactional
//    public Object randomExam(@RequestBody ExamRandom object,
//                             @RequestHeader("token") String token) {
//
//        String userId = getUserIdByCache(token);
//
//        //通过id获取作业信息
//        String examId = object.getExamId();
//
//        System.out.println(object);
//
//        List<Exam> examList = examService.findByIds(examId);
//        if (examList == null || examList.size() == 0)
//            return R.error("作业id异常");
//        //删除旧题
//        List<JSONObject> examQuestions = examQuestionService.queryAll(" and examId = '" + examId + "' ");
//        for (JSONObject eq : examQuestions) {
//            examQuestionService.deleteExamQuestion(eq.getString("id"), userId);
//        }
//
//        Exam exam = examList.get(0);
//        //选择题总分
//        Double singleSumScore = new Double(exam.getSingle());
//        //多选题总分
//        Double multipleSumScore = new Double(exam.getMultiple());
//        //判断题总分
//        Double TFSumScore = new Double(exam.getTrueOrFalse());
//        //填空题总分
//        Double gapSumScore = new Double(exam.getGap());
//
//        //验证题库题目数量是否足够
//        String[] typeStr = {"判断", "单选", "多选", "填空"};
//        int[] counts = {0, 0, 0, 0};
//        int TFNumber = counts[0] = object.getTrueOrFalseNumber();
//        int singleNumber = counts[1] = object.getSingleNumber();
//        int multipleNumber = counts[2] = object.getMultipleNumber();
//        int gapNumber = counts[3] = object.getGapNumber();
//
//        for (int i = 0; i < 4; i++) {
//            int count = questionService.getQuestionCount(" and type = " + (i + 1) + " ");
//            if (count == 0 || count < counts[i]) {
//                return R.error("题库的" + typeStr[i] + "题不足" + counts[i] + "题");
//            }
//        }
//
//
//        //抽取判断题到指定判断题总分数
//        double oneTFScore = TFSumScore / TFNumber;//一题选择题所占分值 = 题型所占分值 / 题数
//        if(!checkScore(TFSumScore.intValue(),TFNumber)){
//            return  R.error("判断题单题分数必须为整数或者0.5分结尾！");
//        }
//
//        //抽取单选题到指定单选题总分数
//        double oneSingleScore = singleSumScore / singleNumber;//一题选择题所占分值 = 题型所占分值 / 题数
//        if(!checkScore(singleSumScore.intValue(),singleNumber)){
//            return  R.error("单选题单题分数必须为整数或者0.5分结尾！");
//        }
//
//        //抽取多选题到指定多选题总分数
//        double oneMultipleScore = multipleSumScore / multipleNumber;//一题选择题所占分值 = 题型所占分值 / 题数
//        if(!checkScore(multipleSumScore.intValue(), multipleNumber)){
//            return  R.error("多选题单题分数必须为整数或者0.5分结尾！");
//        }
//
//        //抽取填空题到指定填空题总分数
//        double oneGapScore = gapSumScore / gapNumber;//一题选择题所占分值 = 题型所占分值 / 题数
//        if(!checkScore(gapSumScore.intValue(), gapNumber)){
//            return  R.error("填空题单题分数必须为整数或者0.5分结尾！");
//        }
//
//
//        List<JSONObject> TFQuestions = questionService.queryAll(" and type = 1 ");//1-判断题
//
//        int TFSize = TFQuestions.size();
//        //1-若题库多选题题数等于所需抽取的题数 -> 全部遍历新增到作业
//        if (TFSize == TFNumber) {
//            for (int i = 0; i < TFSize; i++) {
//
//                ExamQuestion examQuestion = new ExamQuestion();
//                examQuestion.setExamId(examId);
//                examQuestion.setQuestionId(TFQuestions.get(i).getString("id"));
//
//                //若存在该关系，continue
//                boolean exist = examQuestionService.exist(W.f(
//                        W.and("examId", "eq", examId),
//                        W.and("questionId", "eq", examQuestion.getQuestionId())
//                ));
//                if (exist)
//                    continue;
//
//                examQuestion.setScore(oneTFScore + "");
//                examQuestionService.createExamQuestion(examQuestion);
//            }
//        }
//        //2-否则从中随机不重复抽取TFNumber题
//        else {
//            int[] num = RandomKit.getRandomNum(TFSize, TFNumber);
//            for (int i = 0; i < num.length; i++) {
//                ExamQuestion examQuestion = new ExamQuestion();
//                examQuestion.setExamId(examId);
//                examQuestion.setQuestionId(TFQuestions.get(num[i]).getString("id"));
//
//                boolean exist = examQuestionService.exist(W.f(
//                        W.and("examId", "eq", examId),
//                        W.and("questionId", "eq", examQuestion.getQuestionId())
//                ));
//                if (exist)
//                    continue;
//
//                examQuestion.setScore(oneTFScore + "");
//                examQuestionService.createExamQuestion(examQuestion);
//            }
//        }
//
//
//        List<JSONObject> singleQuestions = questionService.queryAll(" and type = 2 ");//2-单选题
//
//
//        int singleSize = singleQuestions.size();
//        //1-若题库单选题题数等于所需抽取的题数 -> 全部遍历新增到作业
//        if (singleSize == singleNumber) {
//            for (int i = 0; i < singleSize; i++) {
//
//                ExamQuestion examQuestion = new ExamQuestion();
//                examQuestion.setExamId(examId);
//                examQuestion.setQuestionId(singleQuestions.get(i).getString("id"));
//
//                //若存在该关系，continue
//                boolean exist = examQuestionService.exist(W.f(
//                        W.and("examId", "eq", examId),
//                        W.and("questionId", "eq", examQuestion.getQuestionId())
//                ));
//                if (exist)
//                    continue;
//
//                examQuestion.setScore(oneSingleScore + "");
//                examQuestionService.createExamQuestion(examQuestion);
//            }
//        }
//        //2-否则从中随机不重复抽取singleNumber题
//        else {
//            int[] num = RandomKit.getRandomNum(singleSize, singleNumber);
//            for (int i = 0; i < num.length; i++) {
//                ExamQuestion examQuestion = new ExamQuestion();
//                examQuestion.setExamId(examId);
//                examQuestion.setQuestionId(singleQuestions.get(num[i]).getString("id"));
//
//                boolean exist = examQuestionService.exist(W.f(
//                        W.and("examId", "eq", examId),
//                        W.and("questionId", "eq", examQuestion.getQuestionId())
//                ));
//                if (exist)
//                    continue;
//
//                examQuestion.setScore(oneSingleScore + "");
//                examQuestionService.createExamQuestion(examQuestion);
//            }
//        }
//
//
//
//        List<JSONObject> multipleQuestions = questionService.queryAll(" and type = 3 ");//3-多选题
//
//        int multipleSize = multipleQuestions.size();
//        //1-若题库多选题题数等于所需抽取的题数 -> 全部遍历新增到作业
//        if (multipleSize == multipleNumber) {
//            for (int i = 0; i < multipleSize; i++) {
//
//                ExamQuestion examQuestion = new ExamQuestion();
//                examQuestion.setExamId(examId);
//                examQuestion.setQuestionId(multipleQuestions.get(i).getString("id"));
//
//                //若存在该关系，continue
//                boolean exist = examQuestionService.exist(W.f(
//                        W.and("examId", "eq", examId),
//                        W.and("questionId", "eq", examQuestion.getQuestionId())
//                ));
//                if (exist)
//                    continue;
//
//                examQuestion.setScore(oneMultipleScore + "");
//                examQuestionService.createExamQuestion(examQuestion);
//            }
//        }
//        //2-否则从中随机不重复抽取multipleNumber题
//        else {
//            int[] num = RandomKit.getRandomNum(multipleSize, multipleNumber);
//            for (int i = 0; i < num.length; i++) {
//                ExamQuestion examQuestion = new ExamQuestion();
//                examQuestion.setExamId(examId);
//                examQuestion.setQuestionId(multipleQuestions.get(num[i]).getString("id"));
//
//                boolean exist = examQuestionService.exist(W.f(
//                        W.and("examId", "eq", examId),
//                        W.and("questionId", "eq", examQuestion.getQuestionId())
//                ));
//                if (exist)
//                    continue;
//
//                examQuestion.setScore(oneMultipleScore + "");
//                examQuestionService.createExamQuestion(examQuestion);
//            }
//        }
//
//
//
//        List<JSONObject> gapQuestions = questionService.queryAll(" and type = 4 ");//4-填空题
//
//        int gapSize = gapQuestions.size();
//        //1-若题库多选题题数等于所需抽取的题数 -> 全部遍历新增到作业
//        if (gapSize == gapNumber) {
//            for (int i = 0; i < gapSize; i++) {
//
//                ExamQuestion examQuestion = new ExamQuestion();
//                examQuestion.setExamId(examId);
//                examQuestion.setQuestionId(gapQuestions.get(i).getString("id"));
//
//                //若存在该关系，continue
//                boolean exist = examQuestionService.exist(W.f(
//                        W.and("examId", "eq", examId),
//                        W.and("questionId", "eq", examQuestion.getQuestionId())
//                ));
//                if (exist)
//                    continue;
//
//                examQuestion.setScore(oneGapScore + "");
//                examQuestionService.createExamQuestion(examQuestion);
//            }
//        }
//        //2-否则从中随机不重复抽取gapNumber题
//        else {
//            int[] num = RandomKit.getRandomNum(gapSize, gapNumber);
//            for (int i = 0; i < num.length; i++) {
//                ExamQuestion examQuestion = new ExamQuestion();
//                examQuestion.setExamId(examId);
//                examQuestion.setQuestionId(gapQuestions.get(num[i]).getString("id"));
//
//                boolean exist = examQuestionService.exist(W.f(
//                        W.and("examId", "eq", examId),
//                        W.and("questionId", "eq", examQuestion.getQuestionId())
//                ));
//                if (exist)
//                    continue;
//
//                examQuestion.setScore(oneGapScore + "");
//                examQuestionService.createExamQuestion(examQuestion);
//            }
//        }
//        examService.updateExam(exam);
//
//        return R.ok("组卷成功请去发布");
//
//    }


    @GetMapping("/examQuestion/exam/{examId}")
    @ApiOperation(value = "读取试卷题目和选项")
    public Object getQuestionsByType(@PathVariable("examId") String examId,
                                     @RequestHeader("token") String token) {

        String wKey = "";
        if (!V.isEmpty(examId))
            wKey = S.apppend(" and examId = '", examId, "' ");

        String[] types = {"trueOrFalse", "singleChoice", "multipleChoice", "gapFilling", "subjective"};

        Map<String, ArrayList<JSONObject>> back = new TreeMap<>();

        for (int i = 0; i < 5; i++) {
            List<JSONObject> examQuestions = examQuestionService.queryAll(wKey + " and type = '" + (i + 1) + "' ");
            ArrayList<JSONObject> teamPapers = new ArrayList<>();
            for (int j = 0; j < examQuestions.size(); j++) {

                JSONObject examQuestion = examQuestions.get(j);
                String questionId = examQuestion.getString("questionId");
                if (i + 1 != 5) {//5-客观题没有选项
                    //获取该题目的选项
                    String where = " and questionId = '" + questionId + "' ";
                    List<Options> options = optionsService.queryByQuestionId(where);
                    List<JSONObject> optionsJSON = new ArrayList<>();
                    for (Options o : options) {
                        optionsJSON.add(J.o2j(o));
                    }

                    examQuestion.put("options", F.f2l(optionsJSON, "id", "questionId"));
                }
//                examQuestion.put("questionId",questionId);
                F.f2l(examQuestions, "id");

                teamPapers.add(examQuestion);
            }
            back.put(types[i], teamPapers);
        }
        //单独封装考试时长
        List<JSONObject> examList = examService.queryAll(" and id = '" + examId + "' ");
        ArrayList<JSONObject> examJson = new ArrayList<>();

        if (examList != null && examList.size() > 0)
            examJson.add(examList.get(0));

        back.put("examInfo", examJson);
        return R.ok(back);

    }

    //读取某个学生作业答题记录，若有客观题则由教师阅卷
    @GetMapping("/examQuestion/history/{examId}-{studentNumber}")
    @ApiOperation(value = "读取答题记录")
    public Object getExamQuestionHistory(@PathVariable("examId") String examId,
                                         @PathVariable("studentNumber") String studentNumber,
                                         @RequestHeader("token") String token) {

        if (V.isEmpty(examId) || V.isEmpty(studentNumber)) {
            return R.error("作业或学生信息异常");
        }

        String number = getNumberByCache(token);
        if (null == number || number.equals("")) {
            return R.error("登录状态异常，请尝试更新缓存！");
        }
        JSONObject res = new JSONObject();
        boolean flag = false;
        //判断缓存里是否存在答题信息，fixme 修改缓存时间，缓存在pc端设置
        String tmp_examQuestion_history = cacheKit.getVal("examQuestion-history-" + examId + "-" + number);
        if (!V.isEmpty(tmp_examQuestion_history)) {
            res = JSON.parseObject(tmp_examQuestion_history);
            flag = true;
        }
        res.put("flag", flag);
        return R.ok(res);
    }


    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("批改主观题")
    @PutMapping("/examQuestion/markSubjective")
    public Object markSubjective(@RequestBody PaperMark object,
                                 @RequestHeader("token") String token) throws Exception {

        String examId = object.getExamId();
        String number = object.getNumber();
        Integer type = object.getType();

        if (V.isEmpty(examId)) {
            return R.error("作业数据异常！");
        }
        if (V.isEmpty(number)) {
            return R.error("学生数据异常！");
        }


        //用来记录客观题得分，总分=主观题+客观题
        double subjectiveScore = 0.0;

        //获取前台传过来的教师批改信息
        //Map<id, Map<name, value>>
        //如：
        //  {"123":{"remark":"<span>好！</span>","subjectiveScore":"21","isExample":"2"}}
        Map<String, Map<String, String>> subjectiveMarkList = object.getSubjectiveMark();


        //通过学号和examId查询主观题的所有答题记录,type:5-主观题
        String where = " and number = '" + number + "' and examId = " + examId + " and type = 5 ";

        List<JSONObject> examRecordJsonList = examRecordService.queryAll(where);


        //通过id循环更新isExample，remark
        for (JSONObject record : examRecordJsonList) {
            //通过record的id更新
            ExamRecord examRecord = o2c(record, token, ExamRecord.class);
            String recordId = examRecord.getId();
            Map<String, String> subjectiveKV = subjectiveMarkList.get(recordId);
            if (V.isEmpty(subjectiveKV)) {
                //提交过来的数据没有该id，即没有评阅，跳过。但理论上无论批不批阅都需传过来（需要考虑暂存的情况）
                continue;
            }

            String remark = subjectiveKV.getOrDefault("remark", "");
            String score = subjectiveKV.getOrDefault("subjectiveScore", "0.0");
            String isExampleStr = subjectiveKV.getOrDefault("isExample", "2");//1-范例，2-不是范例

            int isExample = 2;
            try {
                subjectiveScore += Double.parseDouble(score);
                examRecord.setScores(Double.parseDouble(score));
            } catch (NumberFormatException nfe) {

            }
            try {
                isExample = Integer.parseInt(isExampleStr);
            } catch (NumberFormatException nfe) {

            }
            examRecord.setRemark(remark);
            examRecord.setIsExample(isExample);

            examRecord = examRecordService.updateExamRecord(examRecord);
            if (V.isEmpty(examRecord)) {
                throw new Exception();
            }
        }


        //如果type=2则同时更新exam_candidate里的markStatus
        List<JSONObject> examCandidateList = examCandidateService.queryAll(" and number = '" + number + "' and examId = " + examId);
        if (V.isEmpty(examCandidateList)) {
            throw new Exception();
        }
        ExamCandidate examCandidate = o2c(examCandidateList.get(0), token, ExamCandidate.class);


        List<Exam> examList = examService.findByIds(examId);
        if (examList == null || examList.size() == 0)
            return R.error("作业id异常");
        Exam exam = examList.get(0);

        if (type == 2) {
            Double allScore = subjectiveScore + examCandidate.getScore();//客观题+主观题
            examCandidate.setMarkStatus(2);



            //更新总分数
            examCandidate.setScore(allScore);
            examCandidate = examCandidateService.updateExamCandidate(examCandidate);
            if (!V.isEmpty(examCandidate)) {
                return R.ok("批改成功！", subjectiveMarkList);
            }
        }

        return R.ok("暂存成功！");


    }


    private Map<String, String> verify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("questionId", "请输入题目id");
        verify.put("examId", "请输入试卷id");
        verify.put("score", "请输入分值");
        return verify;
    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("questionId", "请输入题目id");
        verify.put("examId", "请输入试卷id");
        verify.put("score", "请输入分值");
        return verify;
    }

}
