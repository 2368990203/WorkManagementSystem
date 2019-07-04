package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.core.mapper.ExamQuestionMapper;
import team.work.core.model.ExamQuestion;
import team.work.core.service.IExamQuestionService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.convert.F;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import team.work.utils.convert.W;
import team.work.utils.bean.Where;
import team.work.core.mapper.ExamQuestionMapper;
import team.work.core.model.ExamQuestion;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.W;


@Service
public class ExamQuestionService extends TServiceImpl<ExamQuestionMapper, ExamQuestion> implements IExamQuestionService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public ExamQuestion createExamQuestion(ExamQuestion model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteExamQuestion(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public ExamQuestion updateExamQuestion(ExamQuestion model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<ExamQuestion> findByIds(Object ids) {
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

        return new Page(F.f2l(grades, "id", "examId", "questionId"), pageSize, totalCount, totalPage, currentPage);
    }

    //全查
    public List<JSONObject> queryAll(String where) {
        List<JSONObject> list = baseMapper.queryAll(where);
        return F.f2l(list, "id", "examId", "questionId", "creator", "reverse");
    }

    //全查PC题目信息
    public List<JSONObject> queryPCQuestion(String where) {
        List<JSONObject> list = baseMapper.queryPCQuestion(where);
        return F.f2l(list, "id", "questionId");
    }

    //全查PC题目Id
    public String[] queryPCQuestionIds(String examId) {
        String[] list = baseMapper.queryPCQuestionIds(examId);
        return list;
    }


    //根据作业ID题目ID查询分数
    public  Double queryPCQuestionScore(String examId,String questionId) {
        Double score = baseMapper.queryPCQuestionScore(examId,questionId);
        return score;
    }
}
