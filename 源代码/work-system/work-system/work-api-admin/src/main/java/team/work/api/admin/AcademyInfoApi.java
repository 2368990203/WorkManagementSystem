package team.work.api.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.model.AcademyInfo;
import team.work.core.service.impl.AcademyInfoService;
import team.work.doc.AcademyInfoCreate;
import team.work.doc.AcademyInfoUpdate;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Api(value = "12_学院信息管理")
@RestController
@RequestMapping("/v1/base")
public class AcademyInfoApi extends BaseApi {
    @Autowired
    private AcademyInfoService academyInfoService;

    @PostMapping("/academyInfo")
    @ApiOperation(value = "添加学院信息")
    public Object createAcademyInfo(@RequestBody AcademyInfoCreate object
            , @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);//获取操作人id
        //映射对象
        AcademyInfo model = o2c(object, token, AcademyInfo.class);

        Boolean exist = academyInfoService.exist(W.f(
                W.and("code", "eq", model.getCode()),
                W.and("isDel", "eq", "0"))
        );
        if (exist)
            return R.error("代码已经存在请更换一个代码");

        Boolean nameexist = academyInfoService.exist(W.f(
                W.and("name", "eq", model.getName()),
                W.and("isDel", "eq", "0"))
        );
        if (nameexist)
            return R.error("名称已经存在请更换一个名称");

        model = academyInfoService.createAcademyInfo(model);
        if (model == null)
            return R.error("添加失败");

        return R.ok("添加成功", fm2(model));
    }

    @PutMapping("/academyInfo")
    @ApiOperation(value = " 修改学院信息")
    public Object updateAcademyInfo(@RequestBody AcademyInfoUpdate object, @RequestHeader("token") String token) {

        String userId = getUserIdByCache(token);//获取操作人id
        //映射对象
        AcademyInfo model = o2c(object, token, AcademyInfo.class);

        AcademyInfo data = academyInfoService.selectById(model.getId());
        if (data == null) {
            return R.error("该信息不存在，无法修改");
        }
        if (!model.getCode().equals(data.getCode())) {
            Boolean exist = academyInfoService.exist(W.f(
                    W.and("code", "eq", model.getCode()),
                    W.and("isDel", "eq", "0"))
            );
            if (exist)
                return R.error("代码已经存在请更换一个代码");
        }
        if (!model.getName().equals(data.getName())) {
            Boolean nameexist = academyInfoService.exist(W.f(
                    W.and("name", "eq", model.getName()),
                    W.and("isDel", "eq", "0"))
            );
            if (nameexist)
                return R.error("名称已经存在请更换一个名称");
        }
        model.setReviser(userId);
        model = academyInfoService.updateAcademyInfo(model);
        if (model == null)
            return R.error("修改失败");

        return R.ok("修改成功", fm2(model));
    }

    @DeleteMapping("/academyInfo/{id}")
    @ApiOperation(value = "删除学院信息")
    public Object deleteAcademyInfo(@PathVariable("id") String id, @RequestHeader("token") String token) {

        if (!academyInfoService.existId(id))
            return R.error("Id数据异常");

        if (academyInfoService.delete(id, cacheKit.getUserId(token)))
            return R.ok("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/academyInfo/{index}-{size}-{name}-{code}-{campus}")
    @ApiOperation(value = "读取学院信息分页列表")
    public Object getAcademyInfo(@PathVariable("index") int index,
                                 @PathVariable("size") int size,
                                 @PathVariable("name") String name,
                                 @PathVariable("code") String code,
                                 @PathVariable("campus") String campus,
                                 @RequestHeader("token") String token) {

        //前端的搜索条件（学院代码，名称，校区）
        String wKey = "";
        if (!V.isEmpty(name))
            wKey = S.apppend(" and (name like '%", name, "%')");
        if (!V.isEmpty(code))
            wKey += S.apppend(" and (code like  '%", code, "%')");
        if (!V.isEmpty(campus))
            wKey += S.apppend(" and (campus = '", campus, "')");
        return R.ok(academyInfoService.page(index, size, wKey));

    }


    @GetMapping("/academyInfo")
    @ApiOperation(value = "读取学院所有列表")
    public Object getAllAcademyInfo(@RequestHeader("token") String token) {

        return R.ok(academyInfoService.queryAll(""));

    }

    //通过学院代码找所在校区 /*需单独配置权限*/
    @GetMapping("/academyInfo/campus/{code}")
    @ApiOperation(value = "通过学院代码找所在校区")
    public Object getAcademyInfo(
            @PathVariable("code") String code,
            @RequestHeader("token") String token) {
        String wKey = "";
        if (!V.isEmpty(code))
            wKey = S.apppend(" and code = '", code, "'");
        return R.ok(academyInfoService.getCampusByCode(wKey));

    }

    @PostMapping("/academyInfo/import/{batch}")
    @ApiOperation(value = "导入学院")
    public Object Import(
            HttpServletRequest req,
            @PathVariable("batch") String batch,
            @RequestHeader("token") String token) {
        if (V.isEmpty(batch)) {
            return R.error("导入批次有误！");
        }
        if(V.isEmpty( cacheKit.getVal("academy-import-lock-"+batch))) {
            cacheKit.setVal("academy-import-lock-"+batch, "true", 1800);
        }else{
            return  R.error("导入操作正在进行中，请耐心等待操作结果");
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
            return R.error("学院导入异常！");
        }

        Boolean flag =res.getBoolean("flag");
        if(!flag){
            return  R.error(res.getString("message"));
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

        cacheKit.deleteVal("academy-import-lock-"+batch);
        return R.ok("导入成功", result);
    }


    @PostMapping("/academyInfo/importStatus/{batch}")
    @ApiOperation(value = "导入学院状态")
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
            res.put("flag",false);
            res.put("message","上传文件有误！");
            return res;
        }
        if (obj.getString("token") == null || obj.getString("token") == "") {
            res.put("flag",false);
            res.put("message","用户授权失败！");
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
                    res.put("flag",false);
                    res.put("message","上传文件有误！");
                    return res;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet

                int firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是标题所以不读
                int lastRowIndex = sheet.getLastRowNum();

                int dealCount = 0, trueCount = 0;

                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    //  System.out.println("rIndex: " + rIndex);

                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        String name = "", code = "", campus = "";

                        if (row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null) {
                            JSONObject academyobj = new JSONObject();
                            code = row.getCell(0).toString().trim();//学院代码
                            name = row.getCell(1).toString().trim();//学院名称
                            campus = row.getCell(2).toString().trim();//所在校区
                            academyobj.put("code", code);
                            academyobj.put("name", name);
                            academyobj.put("campus", campus);
                            boolean academyFlag = false;
                            String userId = getUserIdByCache(token);
                            dealCount++;
                            Boolean nameexist = academyInfoService.exist(W.f(
                                    W.and("name", "eq", name),
                                    W.and("isDel", "eq", "0"))
                            );
                            Boolean codeexist = academyInfoService.exist(W.f(
                                    W.and("code", "eq", code),
                                    W.and("isDel", "eq", "0"))
                            );
                            if (nameexist || codeexist) {
                                academyFlag = false;
                                if (nameexist) {
                                    academyobj.put("info", "学院名称已存在");
                                }
                                if (codeexist) {
                                    academyobj.put("info", "学院代码已存在");
                                }
                            } else {
                                if (campus.equals("东校区") || campus.equals("西校区") || campus.equals("武鸣校区")) {
                                    int campus_int = -1;
                                    switch (campus) {
                                        case "东校区":
                                            campus_int = 1;
                                            break;
                                        case "西校区":
                                            campus_int = 2;
                                            break;
                                        case "武鸣校区":
                                            campus_int = 3;
                                            break;
                                    }
                                    AcademyInfo academyInfo = new AcademyInfo();
                                    academyInfo.setCampus(campus_int);
                                    academyInfo.setCode(code);
                                    academyInfo.setName(name);
                                    academyInfo.setCreator(userId);
                                    academyInfo.setStatus(0);
                                    academyInfo = academyInfoService.createAcademyInfo(academyInfo);
                                    if (academyInfo == null) {
                                        academyFlag = false;
                                        academyobj.put("info", "学院信息保存失败");
                                    } else {
                                        academyFlag = true;

                                    }

                                } else {
                                    academyFlag = false;
                                    academyobj.put("info", "校区错误");
                                }

                            }


                            if (!academyFlag) {
                                if (V.isEmpty(academyobj.getString("info"))) {
                                    academyobj.put("info", "学院信息创建失败");
                                }
                                errors.add(academyobj);
                                cacheKit.setVal(batch + "status", 2 + "", 0);
                            } else {
                                trueCount++;
                                cacheKit.setVal(batch + "status", 1 + "", 0);
                            }


                            cacheKit.setVal(batch + "now", rIndex + "", 0);
                        }

                        cacheKit.setVal(batch + "total", lastRowIndex + "", 0);

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

                res.put("flag",true);

                return res;

            } else {
                res.put("flag", false);
                res.put("message", "上传文件有误！");
                System.out.println("找不到指定的文件");
                return  res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("flag", false);
            res.put("message", "学院处理出错！");
            return  res;
        }
    }


}
