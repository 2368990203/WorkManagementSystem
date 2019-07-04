package team.work.api.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.core.model.Options;
import team.work.core.model.Question;
import team.work.core.service.impl.OptionsService;
import team.work.core.service.impl.QuestionService;
import team.work.doc.*;
import team.work.utils.convert.*;
import team.work.utils.kit.OSKit;
import team.work.utils.kit.TimeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.work.core.model.Options;
import team.work.core.model.Question;
import team.work.doc.MakeQuestionAndOptions;
import team.work.doc.QuestionCreate;
import team.work.doc.QuestionUpdate;
import team.work.doc.UpdateQuestionAndOptions;
import team.work.utils.convert.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Api(value = "31_题库管理")
@RestController
@RequestMapping("/v1/base")
public class QuestionApi extends BaseApi {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private OptionsService optionsService;

    @PostMapping("/question")
    @ApiOperation(value = "题目新增")
    public Object createQuestion(@RequestBody QuestionCreate object,
                                 @RequestHeader("token") String token) {

        //映射对象
        Question model = o2c(object, token, Question.class);

        //数据校验
        JSONObject check = V.checkEmpty(verify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));

        model = questionService.createQuestion(model);
        if (model == null)
            return R.error("新增失败");
        return R.ok("新增成功", fm2(model));
    }

    @PutMapping("/question")
    @ApiOperation(value = "修改题目")
    public Object updateQuestion(@RequestBody QuestionUpdate object,
                                 @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);
        //映射对象
        Question model = o2c(object, token, Question.class);
        //数据校验
        JSONObject check = V.checkEmpty(updateVerify(), object);
        if (check.getBoolean("check"))
            return R.error(check.getString("message"));
        model.setReviser(userId);
        model = questionService.updateQuestion(model);
        if (model == null)
            return R.error("修改失败");
        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/question/{id}")
    @ApiOperation(value = "题目删除")
    public Object deleteQuestion(@PathVariable("id") String id,
                                 @RequestHeader("token") String token) {

        if (!questionService.existId(id))
            return R.error("Id数据异常");

        if (questionService.deleteQuestion(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/question/{index}-{size}-{key}")
    @ApiOperation(value = "读取题库分页列表")
    public Object getQuestion(@PathVariable("index") int index,
                              @PathVariable("size") int size,
                              @PathVariable("key") String key,
                              @RequestHeader("token") String token) {

        //前端的搜索条件（学院部门代码，名称，校区）
        String wKey = "";


        if (!V.isEmpty(key))
            wKey += S.apppend(" and (id = '", key, "')");
        return R.ok(questionService.page(index, size, wKey));

    }

    @GetMapping("/question")
    @ApiOperation(value = "读取题库所有列表")
    public Object getAllQuestion(@RequestHeader("token") String token) {

        return R.ok(questionService.queryAll(""));

    }


    @PostMapping("/question/make")
    @ApiOperation(value = "题目和选项创建")
    @Transactional
    public Object makeQuestions(@RequestBody MakeQuestionAndOptions object,
                                @RequestHeader("token") String token) {

        System.out.println("题目和选项创建:" + object);
        String userId = getUserIdByCache(token);

        List<String> allOptionInfo = object.getOptionInfo();
        List<Integer> allOptionNumber = object.getOptionNumber();
        List<Integer> allAnswerNumber = object.getAnswerNumber();

        Boolean exist = questionService.exist(W.f(
                W.and("questionName", "eq", object.getQuestionName()),
                W.and("type", "eq", object.getType()),
                W.and("isDel", "eq", "0"))
        );
        if (exist) {
            return R.error("题目名称已存在");
        }
        //新增题目
        Question question = o2c(object, token, Question.class);
        int type = question.getType();
        question.setCreator(userId);
        question = questionService.createQuestion(question);
        if (question == null)
            return R.error("题目" + question.getQuestionName() + "创建失败");

        //判断题
        if (type == 1) {
            Options[] options = new Options[2];

            //判断题的的optionNumber只有1和2，1-答案是“正确”，2-答案是“错误”
            options[0] = new Options();
            options[0].setQuestionId(question.getId());
            options[0].setOptionInfo("正确");
            options[0].setOptionNumber(1);

            options[1] = new Options();
            options[1].setQuestionId(question.getId());
            options[1].setOptionInfo("错误");
            options[1].setOptionNumber(2);

            if (allAnswerNumber.get(0) == 1) {
                //正确答案选择了第1个，则第1个选项“正确”为正确答案，第2个选项“错误”为错误答案
                options[0].setIsAnswer(1);
                options[1].setIsAnswer(2);
            } else if (allAnswerNumber.get(0) == 2) {
                //正确答案选择了第2个，则第1个选项“正确”为错误答案，第2个选项“错误”为正确答案
                options[0].setIsAnswer(2);
                options[1].setIsAnswer(1);
            }

            options[0].setCreator(userId);
            optionsService.createOptions(options[0]);

            options[1].setCreator(userId);
            optionsService.createOptions(options[1]);
        }
        //单选题
        else if (type == 2) {
            Options[] options = new Options[allOptionInfo.size()];
            int answerNumber = allAnswerNumber.get(0);

            for (int i = 0; i < allOptionInfo.size(); i++) {
//                Boolean optionExist = questionService.exist(W.f(
//                        W.and("questionId", "eq", question.getId()),
//                        W.and("optionInfo", "eq", allOptionInfo.get(i)),
//                        W.and("isDel", "eq", "0"))
//                );
//                if (optionExist) {
//                    return R.ok("选项已存在");
//                }
                int optionNumber = allOptionNumber.get(i);

                options[i] = new Options();
                options[i].setQuestionId(question.getId());
                options[i].setOptionInfo(allOptionInfo.get(i));
                options[i].setOptionNumber(i + 1);
                if (optionNumber == answerNumber) {
                    options[i].setIsAnswer(1);
                } else {
                    options[i].setIsAnswer(2);
                }
                options[i].setCreator(userId);
                optionsService.createOptions(options[i]);
            }

        }
        //3-多选题
        else if (type == 3) {

            Options[] options = new Options[allOptionInfo.size()];

//            int optionNumberIndex = 0;
            int answerNumberIndex = 0;
//            int optionNumber = allOptionNumber.get(optionNumberIndex);
            int answerNumber = allAnswerNumber.get(answerNumberIndex);

            for (int i = 0; i < allOptionInfo.size(); i++) {
//                Boolean optionExist = questionService.exist(W.f(
//                        W.and("questionId", "eq", question.getId()),
//                        W.and("optionInfo", "eq", allOptionInfo.get(i)),
//                        W.and("isDel", "eq", "0"))
//                );
//                if (optionExist) {
//                    return R.ok("选项已存在");
//                }
                int optionNumber = allOptionNumber.get(i);

                options[i] = new Options();
                options[i].setQuestionId(question.getId());
                options[i].setOptionInfo(allOptionInfo.get(i));
                options[i].setOptionNumber(i + 1);
                if (answerNumber == optionNumber) {
                    options[i].setIsAnswer(1);
                    answerNumberIndex++;
                    if (answerNumberIndex < allAnswerNumber.size()) {
                        answerNumber = allAnswerNumber.get(answerNumberIndex);
                    }
                } else {
                    options[i].setIsAnswer(2);
                }
                options[i].setCreator(userId);
                optionsService.createOptions(options[i]);
            }
        }
        //填空题
        else if (type == 4) {
            //填空题传来的optionInfo都是答案
            for (int i = 0; i < allOptionInfo.size(); i++) {
//                Boolean optionExist = questionService.exist(W.f(
//                        W.and("questionId", "eq", question.getId()),
//                        W.and("optionInfo", "eq", allOptionInfo.get(i)),
//                        W.and("isDel", "eq", "0"))
//                );
//                if (optionExist) {
//                    return R.ok("选项已存在");
//                }
                Options option = new Options();
                option.setQuestionId(question.getId());
                option.setOptionInfo(allOptionInfo.get(i));
                option.setIsAnswer(1);
                option.setOptionNumber(i + 1);
                option.setCreator(userId);
                optionsService.createOptions(option);
            }
        }

        return R.ok("题库创建成功", fm2(question));

    }


    @GetMapping("/question/details/{index}-{size}-{type}-{name}")
    @ApiOperation(value = "读取题目和选项分页列表")
    public Object getQuestionsByType(@PathVariable("index") int index,
                                     @PathVariable("size") int size,
                                     @PathVariable("type") String type,
                                     @PathVariable("name") String name,
                                     @RequestHeader("token") String token) {

        String wKey = "";
        if (!V.isEmpty(name))
            wKey = S.apppend(" and (questionName like '%", name, "%')");

        if (!V.isEmpty(type))
            wKey += S.apppend(" and (type = '", type, "')");
        return R.ok(questionService.getQuestionDetails(index, size, wKey));


    }


    @PutMapping("/question/update")
    @ApiOperation(value = "修改题目和选项")
    @Transactional
    public Object updateQuestionAndOptions(@RequestBody UpdateQuestionAndOptions object,
                                           @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);

        List<Question> questions = questionService.findByIds(object.getQuestionId());
        if (questions == null || questions.size() == 0)
            return R.error("题目id异常");
        Question question = questions.get(0);
        if (!object.getQuestionName().equals(object.getQuestionName()) || !object.getType().equals(object.getType())) {
            Boolean exist = questionService.exist(W.f(
                    W.and("questionName", "eq", object.getQuestionName()),
                    W.and("type", "eq", object.getType()),
                    W.and("isDel", "eq", "0"))
            );
            if (exist) {
                return R.error("题目名称已存在");
            }
        }
        question.setQuestionName(object.getQuestionName());
        question.setType(object.getType());
        question.setAnswerKeys(object.getAnswerKeys());
        question.setReviser(userId);
        if (question == questionService.updateQuestion(question))
            return R.error("修改失败");

        //删除所有旧选项数据
        List<Options> optionsList = optionsService.queryByQuestionId(" and questionId = '" + object.getQuestionId() + "' ");
        for (Options option : optionsList) {
            optionsService.deleteByQuestionId(option.getId());
        }

        //新增新选项数据

        List<String> allOptionInfo = object.getOptionInfo();
        List<Integer> allOptionNumber = object.getOptionNumber();
        List<Integer> allAnswerNumber = object.getAnswerNumber();

        //新增题目
        int type = object.getType();
        //判断题
        if (type == 1) {
            Options[] options = new Options[2];

            //判断题的的optionNumber只有1和2，1-答案是“正确”，2-答案是“错误”
            options[0] = new Options();
            options[0].setQuestionId(question.getId());
            options[0].setOptionInfo("正确");
            options[0].setOptionNumber(1);

            options[1] = new Options();
            options[1].setQuestionId(question.getId());
            options[1].setOptionInfo("错误");
            options[1].setOptionNumber(2);

            if (allAnswerNumber.get(0) == 1) {
                //正确答案选择了第1个，则第1个选项“正确”为正确答案，第2个选项“错误”为错误答案
                options[0].setIsAnswer(1);
                options[1].setIsAnswer(2);
            } else if (allAnswerNumber.get(0) == 2) {
                //正确答案选择了第2个，则第1个选项“正确”为错误答案，第2个选项“错误”为正确答案
                options[0].setIsAnswer(2);
                options[1].setIsAnswer(1);
            }

            options[0].setCreator(userId);
            optionsService.createOptions(options[0]);

            options[1].setCreator(userId);
            optionsService.createOptions(options[1]);
        }
        //单选题
        else if (type == 2) {
            Options[] options = new Options[allOptionInfo.size()];
            int answerNumber = allAnswerNumber.get(0);

            for (int i = 0; i < allOptionInfo.size(); i++) {
//                Boolean optionExist = optionsService.exist(W.f(
//                        W.and("questionId", "eq", question.getId()),
//                        W.and("optionInfo", "eq", allOptionInfo.get(i)),
//                        W.and("isDel", "eq", "0"))
//                );
//                if (optionExist) {
//                    return R.ok("选项已存在");
//                }
                int optionNumber = allOptionNumber.get(i);

                options[i] = new Options();
                options[i].setQuestionId(question.getId());
                options[i].setOptionInfo(allOptionInfo.get(i));
                options[i].setOptionNumber(i + 1);
                if (optionNumber == answerNumber) {
                    options[i].setIsAnswer(1);
                } else {
                    options[i].setIsAnswer(2);
                }
                options[i].setCreator(userId);
                optionsService.createOptions(options[i]);
            }

        }
        //3-多选题
        else if (type == 3) {

            Options[] options = new Options[allOptionInfo.size()];

//            int optionNumberIndex = 0;
            int answerNumberIndex = 0;
//            int optionNumber = allOptionNumber.get(optionNumberIndex);
            int answerNumber = allAnswerNumber.get(answerNumberIndex);

            for (int i = 0; i < allOptionInfo.size(); i++) {
//                Boolean optionExist = optionsService.exist(W.f(
//                        W.and("questionId", "eq", question.getId()),
//                        W.and("optionInfo", "eq", allOptionInfo.get(i)),
//                        W.and("isDel", "eq", "0"))
//                );
//                if (optionExist) {
//                    return R.ok("选项已存在");
//                }
                int optionNumber = allOptionNumber.get(i);

                options[i] = new Options();
                options[i].setQuestionId(question.getId());
                options[i].setOptionInfo(allOptionInfo.get(i));
                options[i].setOptionNumber(i + 1);
                if (answerNumber == optionNumber) {
                    options[i].setIsAnswer(1);
                    answerNumberIndex++;
                    if (answerNumberIndex < allAnswerNumber.size()) {
                        answerNumber = allAnswerNumber.get(answerNumberIndex);
                    }
                } else {
                    options[i].setIsAnswer(2);
                }
                options[i].setCreator(userId);
                optionsService.createOptions(options[i]);
            }
        }
        //填空题
        else if (type == 4) {
            //填空题传来的optionInfo都是答案
            for (int i = 0; i < allOptionInfo.size(); i++) {
//                Boolean optionExist = questionService.exist(W.f(
//                        W.and("questionId", "eq", question.getId()),
//                        W.and("optionInfo", "eq", allOptionInfo.get(i)),
//                        W.and("isDel", "eq", "0"))
//                );
//                if (optionExist) {
//                    return R.ok("选项已存在");
//                }
                Options option = new Options();
                option.setQuestionId(question.getId());
                option.setOptionInfo(allOptionInfo.get(i));
                option.setIsAnswer(1);
                option.setOptionNumber(i + 1);
                option.setCreator(userId);
                optionsService.createOptions(option);
            }
        }

        return R.ok("题库修改成功");


    }

    @DeleteMapping("/question/delete/{id}")
    @ApiOperation(value = "题目和选项删除")
    public Object deleteQuestionAndOptions(@PathVariable("id") String id,
                                           @RequestHeader("token") String token) {

        if (!questionService.existId(id))
            return R.error("Id数据异常");

        if (!questionService.deleteQuestion(id, cacheKit.getUserId(token)))
            return R.error("删除失败");

        List<Options> options = optionsService.query(W.f(
                W.and("questionId", "eq", id),
                W.and("isDel", "eq", "0"))
        );
        if (options != null && options.size() > 0) {
            for (Options option : options) {
                if (!optionsService.deleteOptions(option.getId(), cacheKit.getUserId(token)))//isDel = 1
//                if (!optionsService.deleteByQuestionId(option.getId()))//物理删除,delete
                    return R.error("删除选项失败");
            }
        }
        return R.ok("删除成功");

    }


    @GetMapping("/question/list/{type}-{name}")
    @ApiOperation(value = "通过题目和类型读取题库列表")
    public Object getQuestionByType(@PathVariable("type") String type,
                                    @PathVariable("name") String name,
                                    @RequestHeader("token") String token) {

        String wKey = "";
        if (!V.isEmpty(type))
            wKey = S.apppend(" and type = ", type, " ");
        if (!V.isEmpty(name))
            wKey += S.apppend(" and questionName like '%", name, "%' ");
        return R.ok(questionService.queryAll(wKey));


    }


    @GetMapping("/question/get")
    @ApiOperation(value = "读取所有题目和选项")
    public Object getQuestionsByType(@RequestHeader("token") String token) {

        String[] types = {"trueOrFalse", "singleChoice", "multipleChoice", "gapFilling", "subjective"};

        Map<String, ArrayList<JSONObject>> back = new TreeMap<>();

        for (int i = 0; i < 5; i++) {
            List<JSONObject> questions = questionService.queryAll(" and type = '" + (i + 1) + "' ");
            ArrayList<JSONObject> teamPapers = new ArrayList<>();
            for (int j = 0; j < questions.size(); j++) {

                JSONObject examQuestion = questions.get(j);
                String questionId = examQuestion.getString("id");
                //获取该题目的选项
                if (i + 1 != 5) {//5-客观题没有选项
                    String where = " and questionId = '" + questionId + "' ";
                    List<Options> options = optionsService.queryByQuestionId(where);
                    List<JSONObject> optionsJSON = new ArrayList<>();
                    for (Options o : options) {
                        optionsJSON.add(J.o2j(o));
                    }

                    examQuestion.put("options", F.f2l(optionsJSON, "id", "questionId"));
                }
                examQuestion.put("questionId", questionId);

                F.f2l(questions, "id");

                teamPapers.add(examQuestion);
            }
            back.put(types[i], teamPapers);
        }
        return R.ok(back);

    }


    @PostMapping("/question/import/{batch}")
    @ApiOperation(value = "导入题库")
    public Object Import(
            HttpServletRequest req,
            @PathVariable("batch") String batch,
            @RequestHeader("token") String token) {
        if (V.isEmpty(batch)) {
            return R.error("导入批次有误！");
        }
        if (V.isEmpty(cacheKit.getVal("question-import-lock-" + batch))) {
            cacheKit.setVal("question-import-lock-" + batch, "true", 1800);
        } else {
            return R.error("导入操作正在进行中，请耐心等待操作结果");
        }

        String filepath = uploadFile(req);
        if (V.isEmpty(filepath)) {
            return R.error("文件上传失败！");
        }


        JSONObject result = new JSONObject();

        JSONObject obj = new JSONObject();

        obj.put("token", token);
        obj.put("filepath", filepath);


        JSONObject res = SolvedFile(obj, batch);
        if (res == null) {
            return R.error("题库导入异常！");
        }

        Boolean flag = res.getBoolean("flag");
        if (!flag) {
            return R.error(res.getString("message"));
        }

        result.put("total", res.getInteger("total"));
        result.put("truecount", res.getInteger("truecount"));
        result.put("errcount", res.getInteger("errcount"));

        if (res.getInteger("truecount") == 0 && res.getInteger("errcount") == 0) {
            return R.error("导入数据为空！");
        }


        if (res.getInteger("errcount") > 0) {
            result.put("errlist", res.getJSONArray("errlist"));
            result.put("success", 0);
        } else {
            result.put("success", 1);

        }

        cacheKit.deleteVal("question-import-lock-" + batch);
        return R.ok("导入成功", result);
    }

    @PostMapping("/question/importStatus/{batch}")
    @ApiOperation(value = "导入题库状态")
    public Object getImportStatus(@PathVariable("batch") String batch) {

        if (V.isEmpty(batch)) {
            return R.error("导入批次有误！");
        }

        JSONObject obj = getImportStatusObj(batch);

        if (V.isEmpty(batch)) {
            return R.error("导入状态异常！");
        }

        return R.ok(obj);

    }


    public JSONObject SolvedFile(JSONObject obj, String batch) {
        JSONObject res = new JSONObject();
        JSONArray errors = new JSONArray();
        if (obj.getString("filepath") == null || obj.getString("filepath") == "") {
            res.put("flag", false);
            res.put("message", "上传文件有误！");
            return res;
        }
        if (obj.getString("token") == null || obj.getString("token") == "") {
            res.put("flag", false);
            res.put("message", "用户授权失败！");
            return res;
        }
        String token = obj.getString("token");


        try {
            File excel = new File(obj.getString("filepath"));
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    res.put("flag", false);
                    res.put("message", "上传文件有误！");
                    return res;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet

                int firstRowIndex = sheet.getFirstRowNum() + 6;   //第一行是填写说明，第二行为标题,第三到六行为示例所以不读
                int lastRowIndex = sheet.getLastRowNum();

                int dealCount = 0, trueCount = 0;

                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    //  System.out.println("rIndex: " + rIndex);

                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        //String examId,String academyCode,String myclass,String name,String number,String phone,String token
                        String name = "", type = "", trueAnswer = "", detail = "";
                        int optionNum = -1;
                        if (row.getCell(0) != null && row.getCell(2) != null && row.getCell(3) != null) {
                            JSONObject questionobj = new JSONObject();
                            name = row.getCell(0).toString().trim();//题目名称
                            type = row.getCell(2).toString().trim();//题目类型
                            trueAnswer = row.getCell(3).toString().trim();//正确答案
                            questionobj.put("name", name);
                            if (!V.isEmpty(row.getCell(1))) {
                                detail = row.getCell(1).toString().trim();//题目解析（非必填）
                                questionobj.put("detail", detail);
                            }
                            if (!V.isEmpty(row.getCell(4))) {
                                optionNum = (int) row.getCell(4).getNumericCellValue();//题目选项（非必填）
                            }
                            questionobj.put("type", type);
                            questionobj.put("trueAnswer", trueAnswer);


                            boolean questionFlag = false;//创建题库是否成功标志
                            String userId = getUserIdByCache(token);

                            dealCount++;
                            JSONObject resObject = dealQuestionData(type, name, detail, userId, trueAnswer, optionNum, row);

                            if (!V.isEmpty(resObject)) {
                                questionFlag = resObject.getBoolean("questionFlag");
                                if (!questionFlag) {
                                    questionFlag = false;
                                    questionobj.put("info", resObject.getString("info"));
                                }

                            } else {
                                questionFlag = false;
                                questionobj.put("info", "题目导入失败");
                            }


                            if (!questionFlag) {
                                if (V.isEmpty(questionobj.getString("info"))) {
                                    questionobj.put("info", "题库创建失败");
                                }
                                errors.add(questionobj);
                                cacheKit.setVal(batch + "status", 2 + "", 0);
                            } else {
                                trueCount++;
                                cacheKit.setVal(batch + "status", 1 + "", 0);
                            }

                            cacheKit.setVal(batch + "now", rIndex - 5 + "", 0);
                        }

                        cacheKit.setVal(batch + "total", lastRowIndex - 5 + "", 0);

                        if (rIndex != lastRowIndex) {
                            cacheKit.setVal(batch + "res", 2 + "", 0);
                        }

                    }


                }

                cacheKit.setVal(batch + "res", 1 + "", 0);

                res.put("truecount", trueCount);

                res.put("errcount", errors.size());

                res.put("total", dealCount);

                res.put("errlist", errors);

                res.put("flag", true);


                return res;

            } else {
                res.put("flag", false);
                res.put("message", "上传文件有误！");
                System.out.println("找不到指定的文件");
                return res;

            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("flag", false);
            res.put("message", "题库处理出错！");
            return res;

        }
    }


    public JSONObject dealQuestionData(String type, String name, String detail, String userId, String trueAnswer, int optionNum, Row row) {
        //关闭事务自动提交
        //2.获取事务定义
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //3.设置事务隔离级别，开启新事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //4.获得事务状态
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);
        Boolean questionFlag = false;
        JSONObject questionobj = new JSONObject();
        if (type.equals("填空题") || type.equals("判断题") || type.equals("单选题") || type.equals("多选题")) {
            int type_int = -1;
            switch (type) {
                case "填空题":
                    type_int = 4;
                    break;
                case "判断题":
                    type_int = 1;
                    break;
                case "单选题":
                    type_int = 2;
                    break;
                case "多选题":
                    type_int = 3;
                    break;
            }

            Boolean exist = questionService.exist(W.f(
                    W.and("questionName", "eq", name),
                    W.and("type", "eq", type_int),
                    W.and("isDel", "eq", "0"))
            );
            if (exist) {
                questionFlag = false;
                questionobj.put("info", "题目名称已存在");
            } else {
                questionFlag = true;
            }
            ;

            if (questionFlag) {
                Question question = new Question();
                question.setType(type_int);
                question.setQuestionName(name);
                question.setAnswerKeys(detail);
                question.setCreator(userId);
                question = questionService.createQuestion(question);


                if (question == null) {
                    questionFlag = false;
                    questionobj.put("info", "题目保存失败");
                } else {


                    if (type.equals("填空题") || type.equals("判断题")) {
                        //新增题目
                        if (type_int == 1) {//判断题
                            Options[] options = new Options[2];
                            Options check = new Options();

                            //判断题的的optionNumber只有1和2，1-答案是“正确”，2-答案是“错误”
                            options[0] = new Options();
                            options[0].setQuestionId(question.getId());
                            options[0].setOptionInfo("正确");
                            options[0].setOptionNumber(1);

                            options[1] = new Options();
                            options[1].setQuestionId(question.getId());
                            options[1].setOptionInfo("错误");
                            options[1].setOptionNumber(2);

                            int trueAnswer_int = (int) Double.parseDouble(trueAnswer);

                            if (trueAnswer_int == 1) {
                                //正确答案选择了第1个，则第1个选项“正确”为正确答案，第2个选项“错误”为错误答案
                                options[0].setIsAnswer(1);
                                options[1].setIsAnswer(2);
                            } else if (trueAnswer_int == 2) {
                                //正确答案选择了第2个，则第1个选项“正确”为错误答案，第2个选项“错误”为正确答案
                                options[0].setIsAnswer(2);
                                options[1].setIsAnswer(1);
                            }
                            questionFlag = false;

                            options[0].setCreator(userId);
                            check = optionsService.createOptions(options[0]);
                            if (check != null) {
                                options[1].setCreator(userId);
                                check = optionsService.createOptions(options[1]);
                                if (check != null) {
                                    questionFlag = true;
                                }
                            }

                            if (!questionFlag) {
                                if (V.isEmpty(questionobj.getString("info"))) {
                                    questionobj.put("info", "选项保存失败");
                                }
                            }
                        }


                        if (type_int == 4) {//填空题
                            questionFlag = true;
                            String[] optionArr = trueAnswer.split("#|＃");
                            for (int i = 0; i < optionArr.length; i++) {
                                Boolean optionExist = optionsService.exist(W.f(
                                        W.and("questionId", "eq", question.getId()),
                                        W.and("optionInfo", "eq", optionArr[i]),
                                        W.and("isDel", "eq", "0"))
                                );
                                if (optionExist) {
                                    questionFlag = false;
                                    questionobj.put("info", "选项已存在");
                                    break;
                                }
                                Options option = new Options();
                                option.setQuestionId(question.getId());
                                option.setOptionInfo(optionArr[i].trim());
                                if (optionArr[i].length() > 50) {
                                    questionFlag = false;
                                    questionobj.put("info", "填空题答案长度大于50");
                                    break;
                                }
                                option.setIsAnswer(1);
                                option.setOptionNumber(i + 1);
                                option.setCreator(userId);
                                option = optionsService.createOptions(option);
                                if (option == null) {
                                    questionFlag = false;
                                    questionobj.put("info", "选项保存失败");
                                    break;
                                }
                            }
                            if (!questionFlag) {
                                if (V.isEmpty(questionobj.getString("info"))) {
                                    questionobj.put("info", "选项保存失败");
                                }
                            }
                        }


                    }


                    if (type.equals("单选题") || type.equals("多选题")) {
                        if (!V.isEmpty(optionNum) && optionNum >= 2) {
                            questionFlag = true;
                            ArrayList<String> optionArray = new ArrayList<String>();

                            for (int i = 5; i < 5 + optionNum; i++) {
                                if (!V.isEmpty(row.getCell(i))) {
                                    optionArray.add(row.getCell(i).toString().trim());
                                } else {
                                    questionFlag = false;
                                    questionobj.put("info", "选项保存失败");
                                    break;
                                }
                            }


                            if (questionFlag) {

                                questionFlag = true;

                                String[] answerArr = trueAnswer.split("#|＃");

                                for (int i = 0; i < optionArray.size(); i++) {
                                    Boolean optionExist = optionsService.exist(W.f(
                                            W.and("questionId", "eq", question.getId()),
                                            W.and("optionInfo", "eq", optionArray.get(i)),
                                            W.and("isDel", "eq", "0"))
                                    );
                                    if (optionExist) {
                                        questionFlag = false;
                                        questionobj.put("info", "选项已存在");
                                        break;
                                    }
                                    Options option = new Options();
                                    option.setQuestionId(question.getId());
                                    option.setOptionInfo(optionArray.get(i));
                                    option.setIsAnswer(2);
                                    for (int j = 0; j < answerArr.length; j++) {
                                        int answerId = (int) Double.parseDouble(answerArr[j].trim()) - 1;
                                        if (answerId == i) {
                                            option.setIsAnswer(1);
                                        }
                                    }
                                    option.setOptionNumber(i + 1);
                                    option.setCreator(userId);
                                    option = optionsService.createOptions(option);
                                    if (option == null) {
                                        questionFlag = false;
                                        break;
                                    }
                                }

                                if (!questionFlag) {
                                    if (V.isEmpty(questionobj.getString("info"))) {
                                        questionobj.put("info", "选项保存失败");
                                    }
                                }

                            }
                        } else {
                            questionFlag = false;
                            questionobj.put("info", "选项数量有误");
                        }
                    }
                }
            }
        } else {
            questionFlag = false;
            questionobj.put("info", "题目类型错误");
        }
        questionobj.put("questionFlag", questionFlag);

        if (questionFlag) {
            transactionManager.commit(transactionStatus);
        } else {
            transactionManager.rollback(transactionStatus);
        }
        return questionobj;
    }


    private Map<String, String> verify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("questionName", "请输入题目名称");
        verify.put("type", "请输入类型");
        verify.put("answerKeys", "请输入答案解析");
        return verify;
    }

    private Map<String, String> updateVerify() {
        Map<String, String> verify = new HashMap<>();
        verify.put("questionName", "请输入题目名称");
        verify.put("type", "请输入类型");
        verify.put("answerKeys", "请输入答案解析");
        return verify;
    }

}
