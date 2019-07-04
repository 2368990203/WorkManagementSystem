package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.core.mapper.QuestionMapper;
import team.work.core.model.Options;
import team.work.core.model.Question;
import team.work.core.service.IQuestionService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.convert.F;
import team.work.utils.convert.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import team.work.utils.convert.W;
import team.work.utils.bean.Where;
import team.work.core.mapper.QuestionMapper;
import team.work.core.model.Options;
import team.work.core.model.Question;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.J;
import team.work.utils.convert.W;


@Service
public class QuestionService extends TServiceImpl<QuestionMapper, Question> implements IQuestionService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public Question createQuestion(Question model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteQuestion(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public Question updateQuestion(Question model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<Question> findByIds(Object ids) {
        return this.selectByIds(ids);
    }

    // 属于
    @Override
    public Boolean exist(List<Where> w) {
        w.add(new Where("1"));
        return this.query(w).size() > 0;
    }

    // 查询一个id是否存在
    @Override
    public Boolean existId(Object id) {
        where = W.f(
                W.and("id", "eq", id),
                W.field("1")
        );
        return this.query(where).size() > 0;
    }

    /**************************CURD end********************************/
    //分页查
    public Page page(int index, int pageSize, String w) {
        // 总记录数
        JSONObject row = baseMapper.getPageCount(w);
        int totalCount = row.getInteger("total");
        if (totalCount == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, 0, 0, 1);
        // 分页数据
        index = index < 0 ? 1 : index;
        int limit = (index - 1) * pageSize;
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int currentPage = index;

        List<JSONObject> grades = baseMapper.getPage(w, limit, pageSize);

        return new Page(F.f2l(grades, "id"), pageSize, totalCount, totalPage, currentPage);
    }

    //全查
    public List<JSONObject> queryAll(String where) {
        List<JSONObject> list = baseMapper.queryAll(where);
        return F.f2l(list, "id", "creator", "reverse");
    }

    public List<JSONObject> getQuestions(String where) {
        List<JSONObject> list = baseMapper.getQuestions(where);
        return list;
    }


    @Autowired
    private OptionsService optionsService;

    //分页查题目信息和选项信息
    public Page getQuestionDetails(int index, int pageSize, String w) {
        // 总记录数
        JSONObject row = baseMapper.getPageCount(w);
        int totalCount = row.getInteger("total");
        if (totalCount == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, 0, 0, 1);
        // 分页数据
        index = index < 0 ? 1 : index;
        int limit = (index - 1) * pageSize;
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int currentPage = index;

        List<JSONObject> questions = baseMapper.getPage(w, limit, pageSize);

        ArrayList<Object> teamPapers = new ArrayList<>();
        for (int j = 0; j < questions.size(); j++) {

            JSONObject question = questions.get(j);
            String questionId = question.getString("id");
            //获取该题目的选项
            String where = " and questionId = '" + questionId + "' ";
            List<Options> options = optionsService.queryByQuestionId(where);
            List<JSONObject> optionsJSON = new ArrayList<>();
            for (Options o : options) {
                optionsJSON.add(J.o2j(o));
            }

            question.put("options", F.f2l(optionsJSON, "id", "questionId"));

            F.f2l(questions, "id");

            teamPapers.add(question);
        }
        return new Page(teamPapers, pageSize, totalCount, totalPage, currentPage);
    }

    //获取指定题型的题目数量
    public int getQuestionCount(String w) {
        return baseMapper.getPageCount(w).getInteger("total");
    }





}
