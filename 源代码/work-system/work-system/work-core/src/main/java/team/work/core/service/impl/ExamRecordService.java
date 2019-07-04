package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.core.mapper.ExamRecordMapper;
import team.work.core.model.ExamRecord;
import team.work.core.service.IExamRecordService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.convert.F;
import team.work.utils.convert.V;
import team.work.utils.kit.IdKit;
import team.work.utils.kit.TimeKit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import team.work.utils.convert.W;
import team.work.utils.bean.Where;
import team.work.core.mapper.ExamRecordMapper;
import team.work.core.model.ExamRecord;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import team.work.utils.kit.IdKit;
import team.work.utils.kit.TimeKit;


@Service
public class ExamRecordService extends TServiceImpl<ExamRecordMapper, ExamRecord> implements IExamRecordService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public ExamRecord createExamRecord(ExamRecord model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteExamRecord(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public ExamRecord updateExamRecord(ExamRecord model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<ExamRecord> findByIds(Object ids) {
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
        return F.f2l(list, "id","questionId","remark", "creator", "reverse");
    }

    public int insertRows(List<ExamRecord> examRecords, String userId) throws Exception {
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < examRecords.size(); i++) {
            ExamRecord exam = examRecords.get(i);
            exam.setId(IdKit.getId(exam.getClass()));//自动生成id
            exam.setCreateTime((int) TimeKit.getTimestamp());

            String value = "";
            value += "('" + exam.getId() + "',";
            value += "'" + exam.getNumber() + "',";
            value += "'" + exam.getExamId() + "',";
            value += "'" + exam.getQuestionId() + "',";
            value += "'" + exam.getSituation() + "',";
            value += "'" + exam.getAnswerContent() + "',";
            value += "'" + userId + "',";
            value += "'" + (exam.getScores()==null?0.0:exam.getScores()) + "',";
            value += "'" + exam.getCreateTime() + "')";

            if (i + 1 != examRecords.size()) {
                value += ",";
            }

            values.append(value);
        }

        int count = baseMapper.insertRows(values.toString());

        return count;
    }

}
