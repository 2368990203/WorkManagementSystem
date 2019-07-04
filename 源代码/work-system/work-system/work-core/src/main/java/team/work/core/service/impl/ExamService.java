package team.work.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.work.core.mapper.ExamMapper;
import team.work.core.model.Exam;
import team.work.core.model.ExamCandidate;
import team.work.core.service.IExamService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.V;
import team.work.utils.convert.W;

import java.util.ArrayList;
import java.util.List;


@Service
public class ExamService extends TServiceImpl<ExamMapper, Exam> implements IExamService {
    @Autowired
    private SysUserService userService;
    @Autowired
    private ExamCandidateService examCandidateService;
//    @Autowired
//    private ExamRoomService examRoomService;
//    @Autowired
//    public CustomService customService;

    /**************************CURD begin******************************/
    // 创建
    @Override
    public Exam createExam(Exam model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteExam(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public Exam updateExam(Exam model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<Exam> findByIds(Object ids) {
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

    //通过id查作业
    public JSONObject queryById(String id) {
        JSONObject object = baseMapper.queryById(id);
        if(!V.isEmpty(object)) {
            object.put("startTimeStr", object.getString("startTimeStr").replace(".0", ""));
            object.put("endTimeStr", object.getString("endTimeStr").replace(".0", ""));
        }
        return F.f2j(object, "id", "creator", "reverse");
    }



    //全查
    public List<JSONObject> queryByDepart(List<String> departs, String number) {
        List<JSONObject> list = baseMapper.queryAll(" and status=2 ");
        List<JSONObject> reslist = new ArrayList<>();
        for (JSONObject examobj : list) {
            if (!V.isEmpty(examobj.getJSONArray("range"))) {
                List<ExamCandidate> examCandidateList = examCandidateService.query(W.f(
                        W.and("examId", "eq", examobj.getString("id")),
                        W.and("number", "eq", number),
                        W.and("status", "eq", 2)
                ));
                if (!V.isEmpty(examCandidateList)) {
                    examobj.put("ansStatus", 2);
                    examobj.put("ansScore", examCandidateList.get(0).getScore());

                } else {
                    examobj.put("ansStatus", 1);
                }


                JSONArray rangeary = examobj.getJSONArray("range");
                for (Object object : rangeary) {
                    JSONObject range = (JSONObject) object;
                    String code = range.getString("code");
                    for (String depart : departs) {
                        if (depart.equals(code)) {
                            reslist.add(examobj);
                        }
                    }
                }

            }

        }


        return F.f2l(reslist, "id", "creator", "reverse");
    }


    //更改学生状态
    public int updateExamCandidate(String examId,String number) {
        int count = baseMapper.updateExamCandidate(examId,number);
        return count;
    }

    //删除学生答题记录
    public int deleteExamRecord(String examId,String number) {
        int count = baseMapper.deleteExamRecord(examId,number);
        return count;
    }

    @Transactional
    //通过导入用户事务，失败回滚，成功返回提示
    public JSONObject ImportUser(JSONObject obj) {
        JSONObject res = new JSONObject();
        boolean flag = false;
        String phone =obj.getString("phone");
        if(phone!=null&&phone.length()>11){
            res.put("flag", false);
            res.put("info", "手机号格式错误");
            return res;
        }
        String name =obj.getString("name");
        if( name !=null&& name .length()>20){
            res.put("flag", false);
            res.put("info", "姓名格式错误");
            return res;
        }
        String number =obj.getString("number");
        if(number!=null&&number.length()>13){
            res.put("flag", false);
            res.put("info", "学号格式错误");
            return res;
        }
        String myClass =obj.getString("myClass");
        if(myClass!=null&&myClass.length()>50){
            res.put("flag", false);
            res.put("info", "班别格式错误");
            return res;
        }

        Exam exam = this.selectById(obj.getString("examId"));
        if (exam == null) {
            flag = false;
            res.put("flag", flag);
            res.put("info", "作业不存在");
            return res;
        }

        JSONObject userObj = userService.importUser(obj);
        //导入用户
        if (userObj != null) {
            boolean userFlag = userObj.getBoolean("flag");
            if (!userFlag) {
                flag = false;
                res.put("flag", flag);
                String info = userObj.getString("info");
                res.put("info", info);
                if (!info.equals("该学号/工号已存在！")) {
                    return res;
                }
            }
            ExamCandidate model = new ExamCandidate();

            model.setExamId(obj.getString("examId"));
            model.setNumber(obj.getString("number"));
            model.setCreator(obj.getString("userid"));

//            String  room =obj.getString("room");
//            if(!V.isEmpty(room)){
//                List<ExamRoom> roomList = examRoomService.query(W.f(
//                        W.and("examId", "eq", obj.getString("examId")),
//                        W.and("name", "eq", room),
//                        W.and("isDel", "eq", "0"))
//                );
//                if(!V.isEmpty(roomList)){
//                    ExamRoom roomObj =roomList.get(0);
//                    model.setRoomId(roomObj.getId());
//                }else{
//                    ExamRoom newRoom= new ExamRoom();
//                    newRoom.setExamId(obj.getString("examId"));
//                    newRoom.setName(room);
//                    newRoom.setIsUnseal(0);
//                    newRoom.setMonitor("");
//                    newRoom.setUnsealCode("");
//                    newRoom.setStatus(0);
//                    newRoom.setRemark("");
//                    newRoom.setCreator(obj.getString("userid"));
//                    newRoom = examRoomService.createExamRoom(newRoom);
//                    if(!V.isEmpty(newRoom)){
//                        model.setRoomId(newRoom.getId());
//                    }
//                }
//            }



            Boolean exist = examCandidateService.exist(W.f(
                    W.and("examId", "eq", obj.getString("examId")),
                    W.and("number", "eq", obj.getString("number")),
                    W.and("isDel", "eq", "0"))
            );

            if (exist) {
                flag = false;
                res.put("flag", flag);
                res.put("info", "学生表已存在");
                return res;

            } else {
                //作业发布
                ExamCandidate examCandidate = examCandidateService.createExamCandidate(model);
                if (examCandidate != null) {
//                    if (obj.getString("phone") != null || obj.getString("phone") != "") {
//                        //发送消息提醒
//                        StringBuffer content = new StringBuffer();
//                        content.append("#{name}同学你好，“" + exam.getName() + "”已经发布，" + "您的登陆账号为：" + examCandidate.getNumber() + ",初始密码为："
//                                + "gxun" + examCandidate.getNumber().substring(examCandidate.getNumber().length() - 6) + ",请尽快修改密码并参加考试！");
//                        //发送短信提醒
//                        customService.sendNote(obj.getString("number"), "“" + exam.getName() + "”作业发布通知", content.toString(), 0, obj.getString("token"));
//                        Message message = new Message();
//                        message.setNumber(obj.getString("number"));
//                        message.setTopic("“" + exam.getName() + "”作业发布通知");
//                        message.setContent(content.toString());
//                        message.setType(3);
//                        message.setServer(1);//pcServer
//                        message.setLinkId(exam.getId());
//                        customService.sendMessage(message,obj.getString("token"));
//                    }
                    flag = true;
                    res.put("flag", flag);
                    res.put("info", "导入成功");
                    return res;
                } else {
                    flag = false;
                    res.put("flag", flag);
                    res.put("info", "写入学生表失败");
                    return res;
                }
            }
        } else {
            flag = false;
            res.put("flag", flag);
            res.put("info", "写入用户表失败");
            return res;
        }

    }


}
