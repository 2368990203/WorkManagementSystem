package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.core.mapper.ExamCandidateMapper;
import team.work.core.model.ExamCandidate;
import team.work.core.service.IExamCandidateService;
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


@Service
public class ExamCandidateService extends TServiceImpl<ExamCandidateMapper, ExamCandidate> implements IExamCandidateService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public ExamCandidate createExamCandidate(ExamCandidate model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteExamCandidate(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public ExamCandidate updateExamCandidate(ExamCandidate model) throws Exception {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<ExamCandidate> findByIds(Object ids) {
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


    public int insertRows(List<ExamCandidate> examCandidates) {
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < examCandidates.size(); i++) {
            ExamCandidate exam = examCandidates.get(i);
            exam.setId(IdKit.getId(exam.getClass()));//自动生成id
            exam.setCreateTime((int) TimeKit.getTimestamp());
            String value = "";
            value += "('" + exam.getId() + "',";
            value += "'" + exam.getNumber() + "',";
            value += "'" + exam.getExamId() + "',";
            value += "'" + exam.getCreator() + "',";
            value += "'" + exam.getCreateTime() + "')";

            if (i + 1 != examCandidates.size()) {
                value += ",";
            }

            values.append(value);
        }
        System.out.println(values);

        int count = baseMapper.insertRows(values.toString());

        return count;
    }

    //分页查作业详情
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

        List<JSONObject> examStudents  = baseMapper.getPage(w, limit, pageSize);

        return new Page(F.f2l(examStudents , "id","examId"), pageSize, totalCount, totalPage, currentPage);
    }


    //全查
    public List<JSONObject> queryAll(String where) {
        List<JSONObject> list = baseMapper.queryAll(where);
        return F.f2l(list, "id","examId", "creator", "reverse","startTimeStr","endTimeStr");
    }

    //分页全查某课程下面的坐作业情况
    public Page gradePage(int index, int pageSize, String examWhere,String ubiWhere) {
        // 总记录数
        JSONObject row = new JSONObject();
        if(V.isEmpty(ubiWhere)&&V.isEmpty( ubiWhere)){
            row =   baseMapper.getPageGradeCountExamSelect(examWhere);
        }else{
            row =   baseMapper.getPageGradeCountUserSelect(examWhere,ubiWhere);
        }

        int totalCount = row.getInteger("total");
        if (totalCount == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, 0, 0, 1);
        // 分页数据
        index = index < 0 ? 1 : index;
        int limit = (index - 1) * pageSize;
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int currentPage = index;

        List<JSONObject> examStudents  = new ArrayList<>();

        if(V.isEmpty(ubiWhere)){
            examStudents   =    baseMapper.getPageGradeExamSelect(examWhere, limit, pageSize);
        }else{
            examStudents   =   baseMapper.getPageGrade_userSelect(examWhere,ubiWhere, limit, pageSize);
        }


        return new Page(F.f2l(examStudents , "id","examId","roomId"), pageSize, totalCount, totalPage, currentPage);
    }


    //全查
    public List<JSONObject> queryAllGrade(String where,String userWhere) {
        List<JSONObject> list = new ArrayList<>();
        if(V.isEmpty(userWhere)){
            list = baseMapper.queryAllGrade(where);
        }else{
            list = baseMapper.queryAllGradeUser(where,userWhere);
        }
        return F.f2l(list, "score","examId","roomId");
    }

    //全查个人的作业排名
    public int getRank(String examId, String number) {
        int rank = baseMapper.getRank(examId, number);
        return rank;
    }


    //根据作业Id查询全部学生
    public String[] queryStudentsByExamId(String examId) {
        String[] list = baseMapper.queryStudentsByExamId(examId);
        return list;
    }

    //根据作业Id统计学生完成情况
    public Integer queryCount(String examId) {
        Integer count = baseMapper.queryCount(examId);
        return count;
    }

}
