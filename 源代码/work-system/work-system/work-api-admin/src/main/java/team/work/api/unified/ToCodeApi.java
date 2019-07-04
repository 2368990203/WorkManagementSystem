package team.work.api.unified;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.work.api.BaseApi;
import team.work.core.model.SysAutoCode;
import team.work.core.service.impl.SysAutoCodeService;
import team.work.utils.convert.J;
import team.work.utils.convert.R;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import team.work.utils.kit.DESKit;
import team.work.utils.kit.TimeKit;
import team.work.utils.tool.PropertiesUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Api(value = "后台代码生成")
@RestController
@RequestMapping("/v1/other")
@CrossOrigin   //跨域服务注解
public class ToCodeApi extends BaseApi {

    PropertiesUtil db = new PropertiesUtil("db.properties");
    private final String url = db.readProperty("url");
//    private final String user = db.readProperty("user");
//    private final String password = db.readProperty("password");
    private final String user = DESKit.decrypt(db.readProperty("user"));
    private final String password = DESKit.decrypt(db.readProperty("password"));
    private final String database = db.readProperty("database");


    @Autowired
    private SysAutoCodeService autoCodeService;

    @PostMapping("/code/{table}-{type}")
    @ApiOperation(value = "自动生成代码")
    public Object createCode(@PathVariable("table") String table, @PathVariable("type") int type) throws IOException, SQLException {

        if (V.isEmpty(table)) {
            return R.error("未填写表名");
        }

        if (V.isEmpty(type)) {
            return R.error("未填写生成类型");

        }
        String tableName = table;
        String time = TimeKit.formatNow("yyyy/MM/dd");

        ResultSet rs = getTableCol(tableName);

        /***********************基础路径配置**************************/
        //实体包/路径
        String package_model = "team.work.core.model";
        String path_model = "/model/";
        //新增的实体包/路径
        String package_doc_model = "team.work.doc";

        //Mapper/路径
        String package_mapper = "team.work.core.mapper";
        String path_mapper = "/mapper/";
        //IService
        String package_iservice = "team.work.core.service";
        String path_iservice = "/service/";
        //Service
        String package_service = "team.work.core.service.impl";
        String path_service = "/service/impl/";
        //Api
        String package_api = "team.work.api.admin";
        String path_api = "/admin/";

        //项目路径
        String project = "/work-core/src/main/java/team/work/core";
        //项目doc实体类路径
        String doc = "/work-api-admin/src/main/java/team/work/doc/";
        //项目接口api路径
        String api = "/work-api-admin/src/main/java/team/work/api/admin/";
        //项目根目录
        File directory = new File(".");

        String path = directory.getCanonicalPath();

        //实体类完整路径
        String docPath = path + doc;

        //接口类路径
        String apiPath = path + api;

        //完整项目路径
        path = path + project;

        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();

        JSONObject code = new JSONObject();
        /*************************** Model ***************************/
        sb.append("package " + package_model + ";\n\n");
        sb.append("import com.baomidou.mybatisplus.activerecord.Model;\n");
        sb.append("import com.baomidou.mybatisplus.annotations.TableName;\n");
        sb.append("import team.work.utils.kit.IdKit;\n");
        sb.append("import java.io.Serializable;\n\n");
//        sb.append("/**\n");
//        sb.append(" * Created by Party on " + time + "\n");
//        sb.append(" */\n");
        sb.append("@SuppressWarnings(\"serial\")\n");
        sb.append("@TableName(value = \"" + tableName + "\")\n");
        sb.append("public class " + formatName(tableName) + " extends Model<" + formatName(tableName) + "> {\n");
        //定义属性
        boolean numberFlag = false;
        while (rs.next()) {
            String id = "";
            if (rs.getString(1).equals("id")) {
                id = " = IdKit.getId(this.getClass())";
            }
            sb.append("  private " + formatType(rs.getString(2)) + " " + rs.getString(1) + id + ";");
            sb.append("\n");

            sb1.append("  public " + formatType(rs.getString(2)) + " get" + formatGS(rs.getString(1)) + "() { \n");
            sb1.append("      return " + rs.getString(1) + ";\n");
            sb1.append("  } \n");
            sb1.append("  public void set" + formatGS(rs.getString(1)) + "(" + formatType(rs.getString(2)) + " " + rs.getString(1) + ") { \n");
            sb1.append("      this." + rs.getString(1) + " = " + rs.getString(1) + ";\n");
            sb1.append("  } \n");
            if (rs.getString(1).equals("number")) {
                numberFlag = true;
            }
        }
        if (type == 2 && !numberFlag) {
            return R.error("该数据库表不存在number字段，请选择常规模式生成代码！");
        }

        sb.append("\n");
        sb.append(sb1.toString());
        sb.append("  @Override\n");
        sb.append("  protected Serializable pkVal() {\n");
        sb.append("      return id;\n");
        sb.append("  }\n");
        sb.append("}\n");
        writeFile((path + path_model + formatName(tableName) + ".java"), sb);
        code.put("model", sb);


        /*************************** Create Model ***************************/

        sb = new StringBuffer();
        sb1 = new StringBuffer();
        StringBuffer verifyStr = new StringBuffer();
        verifyStr.append("        Map<String, String> verify = new HashMap<>();\n");

        //获取表中文注释
        String[] tableComment = getTableComment(tableName);
        String tableNumber = tableComment[0];
        String tableName_CN = tableComment[1];
        //获取字段和注释，排除id和5个公有字段:id,creator,createTime,reviser,reviseTime,isDel
        rs = null;
        rs = getTableCol(tableName);

        sb.append("package " + package_doc_model + ";\n\n");
        sb.append("import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n");
        sb.append("import com.fasterxml.jackson.annotation.JsonInclude;\n");
        sb.append("import io.swagger.annotations.ApiModel;\n");
        sb.append("import io.swagger.annotations.ApiModelProperty;\n\n");
//        sb.append("/**\n");
//        sb.append(" * Created by Party on " + time + "\n");
//        sb.append(" */\n");
        sb.append("@JsonInclude(JsonInclude.Include.NON_NULL)\n");
        sb.append("@JsonIgnoreProperties({ \"handler\",\"hibernateLazyInitializer\" })\n");
        sb.append("@ApiModel(value =\"" + tableName_CN + "新增\")\n");
        sb.append("public class " + formatName(tableName) + "Create {\n");
        while (rs.next()) {
            String data_type = rs.getString("data_type");
            String COLUMN_name = rs.getString("COLUMN_name");
            String COLUMN_COMMENT = formatComment(rs.getString("COLUMN_COMMENT"));
//            排除id和5个公有字段:id,creator,createTime,reviser,reviseTime,isDel
            if (!isPublicField(COLUMN_name) && !"id".equals(COLUMN_name)) {

                verifyStr.append("        verify.put(\"" + COLUMN_name + "\", \"请输入" + COLUMN_COMMENT + "\");\n");

                sb.append("    @ApiModelProperty(value = \"" + COLUMN_COMMENT + "\")\n");
                //字段名默认用驼峰形式，所以不用格式化
                sb.append("    private " + formatType(data_type) + " " + COLUMN_name + ";");
                sb.append("\n");

                sb1.append("    public " + formatType(data_type) + " get" + formatGS(COLUMN_name) + "() { \n");
                sb1.append("        return " + rs.getString(1) + ";\n");
                sb1.append("    } \n\n");
                sb1.append("    public void set" + formatGS(COLUMN_name) + "(" + formatType(data_type) + " " + COLUMN_name + ") { \n");
                sb1.append("        this." + COLUMN_name + " = " + COLUMN_name + ";\n");
                sb1.append("    } \n\n");
            }
        }
        verifyStr.append("        return verify;\n");
        sb.append("\n");
        sb.append(sb1.toString());
        sb.append("}\n");
        writeFile((docPath + formatName(tableName) + "Create.java"), sb);
        code.put("create", sb);


        /*************************** Update Model ***************************/

        sb = new StringBuffer();
        sb1 = new StringBuffer();

        //获取字段和注释，排除id和5个公有字段:id,creator,createTime,reviser,reviseTime,isDel
        rs = null;
        rs = getTableCol(tableName);

        sb.append("package " + package_doc_model + ";\n\n");
        sb.append("import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n");
        sb.append("import com.fasterxml.jackson.annotation.JsonInclude;\n");
        sb.append("import io.swagger.annotations.ApiModel;\n");
        sb.append("import io.swagger.annotations.ApiModelProperty;\n\n");
//        sb.append("/**\n");
//        sb.append(" * Created by Party on " + time + "\n");
//        sb.append(" */\n");
        sb.append("@JsonInclude(JsonInclude.Include.NON_NULL)\n");
        sb.append("@JsonIgnoreProperties({ \"handler\",\"hibernateLazyInitializer\" })\n");
        sb.append("@ApiModel(value =\"" + tableName_CN + "修改\")\n");
        sb.append("public class " + formatName(tableName) + "Update {\n");
        while (rs.next()) {
            String data_type = rs.getString("data_type");
            String COLUMN_name = rs.getString("COLUMN_name");
            String COLUMN_COMMENT = formatComment(rs.getString("COLUMN_COMMENT"));
//            排除id和5个公有字段:id,creator,createTime,reviser,reviseTime,isDel
            if (!isPublicField(COLUMN_name)) {
                sb.append("    @ApiModelProperty(value = \"" + COLUMN_COMMENT + "\")\n");
                //字段名默认用驼峰形式，所以不用格式化
                sb.append("    private " + formatType(data_type) + " " + COLUMN_name + ";");
                sb.append("\n");

                sb1.append("    public " + formatType(data_type) + " get" + formatGS(COLUMN_name) + "() { \n");
                sb1.append("        return " + rs.getString(1) + ";\n");
                sb1.append("    } \n\n");
                sb1.append("    public void set" + formatGS(COLUMN_name) + "(" + formatType(data_type) + " " + COLUMN_name + ") { \n");
                sb1.append("        this." + COLUMN_name + " = " + COLUMN_name + ";\n");
                sb1.append("    } \n\n");
            }
        }
        sb.append("\n");
        sb.append(sb1.toString());
        sb.append("}\n");
        writeFile((docPath + formatName(tableName) + "Update.java"), sb);
        code.put("update", sb);


        /*************************** Mapper ***************************/
        sb = new StringBuffer();

        sb.append("package " + package_mapper + ";\n\n");
        sb.append("import com.baomidou.mybatisplus.mapper.BaseMapper;\n");
        sb.append("import com.alibaba.fastjson.JSONObject;\n");
        sb.append("import org.apache.ibatis.annotations.Param;\n");
        sb.append("import org.apache.ibatis.annotations.Select;\n");
        sb.append("import " + package_model + "." + formatName(tableName) + ";\n\n");

        sb.append("import java.util.List;\n");

//        sb.append("/**\n");
//        sb.append(" * Created by Party on " + time + "\n");
//        sb.append(" */\n");
        sb.append("public interface " + formatName(tableName) + "Mapper extends BaseMapper<" + formatName(tableName) + "> {\n\n");

        sb.append("    @Select(\"SELECT a.* \" + \n");

        StringBuffer timeStr = new StringBuffer();
        rs = getTableCol(tableName);
        while (rs.next()) {
            String COLUMN_name = rs.getString("COLUMN_name");
            String COLUMN_TYPE = rs.getString("COLUMN_TYPE");
//            排除id和5个公有字段:id,creator,createTime,reviser,reviseTime,isDel
            if (!isPublicField(COLUMN_name)) {
                if (COLUMN_name.endsWith("Time") && "int(11)".equals(COLUMN_TYPE)) {
                    timeStr.append("            \",FROM_UNIXTIME(" + COLUMN_name + ") " + COLUMN_name + "Str  \" +\n");
                }
            }
        }

        sb.append(timeStr);
        sb.append("              \"FROM " + tableName + " a \" + \n");
        sb.append("            \"JOIN (SELECT id from " + tableName + " where isDel = 0 ${where} LIMIT #{index}, #{size} \" + \n");
        sb.append("            \")b ON a.id=b.id order by a.createTime asc  \") \n");
        sb.append("    List<JSONObject> getPage(@Param(\"where\") String where, \n");
        sb.append("                             @Param(\"index\") int index, \n");
        sb.append("                             @Param(\"size\") int size); \n\n");

        sb.append("    @Select(\"select count(1) total from " + tableName + " where isDel = 0 ${where} \") \n");
        sb.append("    JSONObject getPageCount(@Param(\"where\") String where); \n\n");


        sb.append("    @Select(\"SELECT a.* FROM " + tableName + " a where 1=1 and isDel=0 ${where}  order by createTime desc\")\n");
        sb.append("    List<JSONObject> queryAll(@Param(\"where\") String where);\n");

        sb.append("}");
        writeFile2((path + path_mapper + formatName(tableName) + "Mapper.java"), sb);
        code.put("mapper", sb);


        /*************************** IService *************************/
        sb = new StringBuffer();
        sb.append("package " + package_iservice + ";\n\n");
        sb.append("import java.util.List;\n");
        sb.append("import team.work.utils.bean.Where;\n");
        sb.append("import " + package_model + "." + formatName(tableName) + ";\n\n");
//        sb.append("/**\n");
//        sb.append("* Created by Party on " + time + "\n");
//        sb.append("*/\n");
        sb.append("public interface I" + formatName(tableName) + "Service {\n");

        sb.append("   /******************* CURD ********************/ \n");
        sb.append("   // 创建 \n");
        sb.append("   " + formatName(tableName) + " create" + formatName(tableName) + "(" + formatName(tableName) + " model); \n");

        sb.append("   // 删除 \n");
        sb.append("   Boolean delete" + formatName(tableName) + "(Object ids,String reviser);\n");

        sb.append("   // 修改 \n");
        sb.append("   " + formatName(tableName) + " update" + formatName(tableName) + "(" + formatName(tableName) + " model); \n");

        sb.append("   // 查询 \n");
        sb.append("   List<" + formatName(tableName) + "> findByIds(Object ids);\n");

        sb.append("   // 属于 \n");
        sb.append("   Boolean exist(List<Where> w); \n");

        sb.append("   // 查询一个id是否存在 \n");
        sb.append("   Boolean existId(Object id); \n");
        sb.append("   /******************* CURD ********************/ \n \n");
        sb.append("}");
        writeFile2((path + path_iservice + "I" + formatName(tableName) + "Service.java"), sb);
        code.put("iService", sb);

        /*************************** Service **************************/

        sb = new StringBuffer();
        sb.append("package " + package_service + ";\n\n");

        sb.append("import com.alibaba.fastjson.JSONObject;\n");
        sb.append("import team.work.core.mapper." + formatName(tableName) + "Mapper;\n");
        sb.append("import team.work.core.model." + formatName(tableName) + ";\n");
        sb.append("import team.work.core.service.I" + formatName(tableName) + "Service;\n");
        sb.append("import team.work.utils.base.TServiceImpl;\n");
        sb.append("import team.work.utils.bean.Page;\n");
        sb.append("import team.work.utils.convert.F;\n");
        sb.append("import org.springframework.stereotype.Service;\n\n");

        sb.append("import java.util.ArrayList;\n");
        sb.append("import java.util.List;\n");
        sb.append("import team.work.utils.convert.W;\n");
        sb.append("import team.work.utils.bean.Where;\n");
//        sb.append("/**\n");
//        sb.append(" * Created by Party on " + time + "\n");
//        sb.append(" */\n");
        sb.append("@Service\n");
        sb.append("public class " + formatName(tableName) + "Service extends TServiceImpl<" + formatName(tableName) + "Mapper," + formatName(tableName) + "> implements I" + formatName(tableName) + "Service {\n");
        sb.append("   /**************************CURD begin******************************/ \n");

        sb.append("   // 创建\n");
        sb.append("   @Override\n");
        sb.append("   public " + formatName(tableName) + " create" + formatName(tableName) + "(" + formatName(tableName) + " model) {\n");
        sb.append("       if(this.insert(model))\n");
        sb.append("           return this.selectById(model.getId());\n");
        sb.append("       return null;\n");
        sb.append("   }\n \n");

        sb.append("   // 删除\n");
        sb.append("   @Override\n");
        sb.append("   public Boolean delete" + formatName(tableName) + "(Object ids,String reviser) {\n");
        sb.append("       return this.delete(ids,reviser);\n");
        sb.append("   }\n \n");

        sb.append("   // 修改\n");
        sb.append("   @Override\n");
        sb.append("   public " + formatName(tableName) + " update" + formatName(tableName) + "(" + formatName(tableName) + " model) {\n");
        sb.append("       if(this.update(model))\n");
        sb.append("           return this.selectById(model.getId());\n");
        sb.append("       return null;\n");
        sb.append("   }\n \n");

        sb.append("   // 查询\n");
        sb.append("   @Override\n");
        sb.append("   public List<" + formatName(tableName) + "> findByIds(Object ids) {\n");
        sb.append("       return this.selectByIds(ids);\n");
        sb.append("   }\n \n");

        sb.append("   // 属于\n");
        sb.append("   @Override\n");
        sb.append("   public Boolean exist(List<Where> w) {\n");
        sb.append("       w.add(new Where(\"1\"));\n");
        sb.append("       return this.query(w).size() > 0;\n");
        sb.append("   }\n \n");

        sb.append("   // 查询一个id是否存在\n");
        sb.append("   @Override\n");
        sb.append("   public Boolean existId(Object id) {\n");
        sb.append("       where = W.f(\n");
        sb.append("               W.and(\"id\",\"eq\",id),\n");
        sb.append("               W.field(\"1\")\n");
        sb.append("       );\n");
        sb.append("       return this.query(where).size() > 0;\n");
        sb.append("   }\n \n");

        sb.append("   /**************************CURD end********************************/ \n");

        sb.append("    //分页查\n");
        sb.append("    public Page page(int index, int pageSize, String w) {\n");
        sb.append("        // 总记录数\n");
        sb.append("        JSONObject row = baseMapper.getPageCount(w);\n");
        sb.append("        int totalCount = row.getInteger(\"total\");\n");
        sb.append("        if(totalCount == 0)\n");
        sb.append("            return new Page(new ArrayList<JSONObject>(),pageSize,0,0,1);\n");
        sb.append("        // 分页数据\n");
        sb.append("        index = index < 0 ? 1:index;\n");
        sb.append("        int limit = (index - 1) * pageSize;\n");
        sb.append("        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize)+1;\n");
        sb.append("        int currentPage = index;\n\n");

        sb.append("        List<JSONObject> grades = baseMapper.getPage(w,limit,pageSize);\n\n");

        sb.append("            return new Page(F.f2l(grades,\"id\"), pageSize, totalCount, totalPage, currentPage);\n");
        sb.append("    }\n\n");

        sb.append("    //全查\n");
        sb.append("    public List<JSONObject> queryAll(String where) {\n");
        sb.append("        List<JSONObject> list = baseMapper.queryAll(where);\n");
        sb.append("        return F.f2l(list,\"id\",\"creator\",\"reverse\");\n");
        sb.append("    }\n");

        sb.append("\n");

        sb.append("}");
        writeFile2((path + path_service + formatName(tableName) + "Service.java"), sb);
        code.put("service", sb);


        if (type != 2) {
            /*************************** Api *************************/
            sb = new StringBuffer();

            sb.append("package " + package_api + ";\n\n");
            sb.append("import com.alibaba.fastjson.JSONObject;\n");
            sb.append("import team.work.api.BaseApi;\n");
//            sb.append("import BaseApi;\n");
            sb.append("import team.work.core.model." + formatName(tableName) + ";\n");
            sb.append("import team.work.core.service.impl." + formatName(tableName) + "Service;\n");
            sb.append("import team.work.doc." + formatName(tableName) + "Create;\n");
            sb.append("import team.work.doc." + formatName(tableName) + "Update;\n");
            sb.append("import team.work.utils.bean.Page;\n");
            sb.append("import team.work.utils.convert.R;\n");
            sb.append("import team.work.utils.convert.S;\n");
            sb.append("import team.work.utils.convert.V;\n");
            sb.append("import team.work.utils.convert.W;\n");
            sb.append("import io.swagger.annotations.Api;\n");
            sb.append("import io.swagger.annotations.ApiOperation;\n");
            sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
            sb.append("import org.springframework.web.bind.annotation.*;\n\n");

            sb.append("import java.util.HashMap;\n");
            sb.append("import java.util.List;\n");
            sb.append("import java.util.Map;\n\n");

            sb.append("/**\n");
            sb.append(" * Created by party on " + time + "\n");
            sb.append(" */\n");

            sb.append("@Api(value = \"" + tableNumber + "_" + tableName_CN + "管理\")\n");
            sb.append("@RestController\n");
            sb.append("@RequestMapping(\"/v1/base\")\n");
            sb.append("public class " + formatName(tableName) + "Api extends BaseApi {\n");
            sb.append("    @Autowired\n");
            sb.append("    private " + formatName(tableName) + "Service " + formatName2(tableName) + "Service;\n\n");

            sb.append("    @PostMapping(\"/" + formatName2(tableName) + "\")\n");
            sb.append("    @ApiOperation(value = \"" + tableName_CN + "新增\")\n");
            sb.append("    public Object create" + formatName(tableName) + "(@RequestBody " + formatName(tableName) + "Create object,\n");
            sb.append("                              @RequestHeader(\"token\") String token){\n");
            sb.append("        String userId = getUserIdByCache(token);   \n\n");
            sb.append("        //映射对象\n");
            sb.append("        " + formatName(tableName) + " model = o2c(object,token, " + formatName(tableName) + ".class);\n");
            sb.append("        //数据校验\n");
            sb.append("        JSONObject check = V.checkEmpty(verify(),object);\n");
            sb.append("        if(check.getBoolean(\"check\"))\n");
            sb.append("        return R.error(check.getString(\"message\"));\n\n");

            /*************************** 校验重复值开始*************************/

            sb.append("        //TODO 写校验重复的条件\n");
            sb.append("        //Boolean exist = " + formatName2(tableName) + "Service.exist(W.f(\n");
            sb.append("        //        W.and(\"code\",\"eq\",model.getCode()),\n");
            sb.append("        //        W.and(\"isDel\",\"eq\",\"0\"))\n");
            sb.append("        //);\n");
            sb.append("        //if(exist)\n");
            sb.append("        //    return R.error(\"代码已经存在请更换一个代码\");\n\n");

            /*************************** 校验重复值结束*************************/

            sb.append("        model = " + formatName2(tableName) + "Service.create" + formatName(tableName) + "(model);\n");
            sb.append("        if(model == null)\n");
            sb.append("            return R.error(\"新增失败\");\n");
            sb.append("        return R.ok(\"新增成功\",fm2(model));\n");
            sb.append("    }\n\n");


            sb.append("   @PutMapping(\"/" + formatName2(tableName) + "\")\n");
            sb.append("   @ApiOperation(value = \"修改" + tableName_CN + "\")\n");
            sb.append("   public Object update" + formatName(tableName) + "(@RequestBody " + formatName(tableName) + "Update object,\n");
            sb.append("                             @RequestHeader(\"token\") String token){\n");
            sb.append("        String userId = getUserIdByCache(token);\n");
            sb.append("        //映射对象\n");
            sb.append("        " + formatName(tableName) + " model = o2c(object,token, " + formatName(tableName) + ".class);\n");
            sb.append("        //数据校验\n");
            sb.append("        JSONObject check = V.checkEmpty(updateVerify(),object);\n");
            sb.append("        if(check.getBoolean(\"check\"))\n");
            sb.append("            return R.error(check.getString(\"message\"));\n");

            /*************************** 校验重复值开始*************************/
            sb.append("              " + formatName(tableName) + " data= " + formatName2(tableName) + "Service.selectById(model.getId());\n      \n");
            sb.append("             if(data==null){  \n");
            sb.append("                  return R.error(\"该信息不存在，无法修改\"); \n");
            sb.append("             } \n");

            sb.append("        //TODO 写校验重复的条件\n");
            sb.append("        //   if(!model.getCode().equals(data.getCode())) {   \n");
            sb.append("        //Boolean exist = " + formatName2(tableName) + "Service.exist(W.f(\n");
            sb.append("        //        W.and(\"code\",\"eq\",model.getCode()),\n");
            sb.append("        //        W.and(\"isDel\",\"eq\",\"0\"))\n");
            sb.append("        //);\n");
            sb.append("        //if(exist)\n");
            sb.append("        //    return R.error(\"代码已经存在请更换一个代码\");\n\n");
            sb.append("        //     }    \n");

            /*************************** 校验重复值结束*************************/

            sb.append("        model.setReviser(userId);\n");
            sb.append("        model = " + formatName2(tableName) + "Service.update" + formatName(tableName) + "(model);\n");
            sb.append("        if(model == null)\n");
            sb.append("            return R.error(\"修改失败\");\n");
            sb.append("        return R.ok(\"修改成功\",fm2(model));\n");
            sb.append("    }\n\n");

            sb.append("    @DeleteMapping(\"/" + formatName2(tableName) + "/{id}\")\n");
            sb.append("    @ApiOperation(value = \"" + tableName_CN + "删除\")\n");
            sb.append("    public Object delete" + formatName(tableName) + "(@PathVariable(\"id\") String id,\n");
            sb.append("                                                  @RequestHeader(\"token\") String token){\n");
            sb.append("        if(!" + formatName2(tableName) + "Service.existId(id))\n");
            sb.append("            return R.error(\"Id数据异常\");\n\n");
            sb.append("        if(" + formatName2(tableName) + "Service.delete" + formatName(tableName) + "(id, cacheKit.getUserId(token)))\n");
            sb.append("            return R.ok(\"删除成功\");\n");
            sb.append("        return R.error(\"删除失败\");\n");
            sb.append("    }\n\n");

            sb.append("    @GetMapping(\"/" + formatName2(tableName) + "/{index}-{size}-{key}\")\n");
            sb.append("    @ApiOperation(value = \"读取" + tableName_CN + "分页列表\")\n");
            sb.append("    public Object get" + formatName(tableName) + "(@PathVariable(\"index\") int index,\n");
            sb.append("                              @PathVariable(\"size\") int size,\n");
            sb.append("                              @PathVariable(\"key\") String key,\n");
            sb.append("                              @RequestHeader(\"token\") String token) {\n");
            sb.append("        String wKey = \"\";\n");
            sb.append("        if (!V.isEmpty(key))\n");
            sb.append("        //FIXME 修改id为需要查询的字段\n");
            sb.append("            wKey = S.apppend(\" and id like '%\", key, \"%' \");\n");
            sb.append("        return R.ok(" + formatName2(tableName) + "Service.page(index, size, wKey));\n");
            sb.append("    }\n\n");

            sb.append("    @GetMapping(\"/" + formatName2(tableName) + "\")\n");
            sb.append("    @ApiOperation(value = \"读取" + tableName_CN + "所有列表\")\n");
            sb.append("    public Object getAll" + formatName(tableName) + "(@RequestHeader(\"token\") String token) {\n");
            sb.append("        return R.ok(" + formatName2(tableName) + "Service.queryAll(\"\"));\n");
            sb.append("    }\n\n");

            //verify方法
            sb.append("    private Map<String, String> verify() {\n");
            sb.append(verifyStr);
            sb.append("    }\n\n");

            //updateVerify方法
            sb.append("    private Map<String, String> updateVerify() {\n");
            sb.append(verifyStr);
            sb.append("    }\n\n");

            sb.append("}\n");

            //API生成结束
        } else if (type == 2) {
            /*************************** Api *************************/
            sb = new StringBuffer();

            sb.append("package " + package_api + ";\n\n");
            sb.append("import com.alibaba.fastjson.JSONObject;\n");
            sb.append("import team.work.api.BaseApi;\n");
//            sb.append("import BaseApi;\n");
            sb.append("import team.work.core.model." + formatName(tableName) + ";\n");
            sb.append("import team.work.core.service.impl." + formatName(tableName) + "Service;\n");
            sb.append("import team.work.doc." + formatName(tableName) + "Create;\n");
            sb.append("import team.work.doc." + formatName(tableName) + "Update;\n");
            sb.append("import team.work.utils.bean.Page;\n");
            sb.append("import team.work.utils.convert.R;\n");
            sb.append("import team.work.utils.convert.S;\n");
            sb.append("import team.work.utils.convert.V;\n");
            sb.append("import team.work.utils.convert.W;\n");
            sb.append("import io.swagger.annotations.Api;\n");
            sb.append("import io.swagger.annotations.ApiOperation;\n");
            sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
            sb.append("import org.springframework.web.bind.annotation.*;\n\n");

            sb.append("import java.util.HashMap;\n");
            sb.append("import java.util.List;\n");
            sb.append("import java.util.Map;\n\n");

            sb.append("/**\n");
            sb.append(" * Created by party on " + time + "\n");
            sb.append(" */\n");

            sb.append("@Api(value = \"" + tableNumber + "_" + tableName_CN + "管理\")\n");
            sb.append("@RestController\n");
            sb.append("@RequestMapping(\"/v1/base\")\n");
            sb.append("public class " + formatName(tableName) + "Api extends BaseApi {\n");
            sb.append("    @Autowired\n");
            sb.append("    private " + formatName(tableName) + "Service " + formatName2(tableName) + "Service;\n\n");

            sb.append("    @PostMapping(\"/" + formatName2(tableName) + "\")\n");
            sb.append("    @ApiOperation(value = \"" + tableName_CN + "新增\")\n");
            sb.append("    public Object create" + formatName(tableName) + "(@RequestBody " + formatName(tableName) + "Create object,\n");
            sb.append("                              @RequestHeader(\"token\") String token){\n");
            sb.append("        String userId = getUserIdByCache(token);   \n\n");
            sb.append("        //映射对象\n");
            sb.append("        " + formatName(tableName) + " model = o2c(object,token, " + formatName(tableName) + ".class);\n");

            /*************************** 数据权限开始*************************/

            sb.append("        if(!checkUserDataAuth(token,model.getNumber())){\n" +
                    "            return R.error(\"您没有权限新增\"+model.getNumber()+\"的记录\");\n" +
                    "        } \n   ");

            /*************************** 数据权限结束*************************/

            sb.append("        //数据校验\n");
            sb.append("        JSONObject check = V.checkEmpty(verify(),object);\n");
            sb.append("        if(check.getBoolean(\"check\"))\n");
            sb.append("        return R.error(check.getString(\"message\"));\n\n");

            /*************************** 校验重复值开始*************************/

            sb.append("        //TODO 写校验重复的条件\n");
            sb.append("        //Boolean exist = " + formatName2(tableName) + "Service.exist(W.f(\n");
            sb.append("        //        W.and(\"code\",\"eq\",model.getCode()),\n");
            sb.append("        //        W.and(\"isDel\",\"eq\",\"0\"))\n");
            sb.append("        //);\n");
            sb.append("        //if(exist)\n");
            sb.append("        //    return R.error(\"代码已经存在请更换一个代码\");\n\n");

            /*************************** 校验重复值结束*************************/

            sb.append("        model = " + formatName2(tableName) + "Service.create" + formatName(tableName) + "(model);\n");
            sb.append("        if(model == null)\n");
            sb.append("            return R.error(\"新增失败\");\n");
            sb.append("        return R.ok(\"新增成功\",fm2(model));\n");
            sb.append("    }\n\n");


            sb.append("   @PutMapping(\"/" + formatName2(tableName) + "\")\n");
            sb.append("   @ApiOperation(value = \"修改" + tableName_CN + "\")\n");
            sb.append("   public Object update" + formatName(tableName) + "(@RequestBody " + formatName(tableName) + "Update object,\n");
            sb.append("                             @RequestHeader(\"token\") String token){\n");
            sb.append("        String userId = getUserIdByCache(token);\n");
            sb.append("        //映射对象\n");
            sb.append("        " + formatName(tableName) + " model = o2c(object,token, " + formatName(tableName) + ".class);\n");

            /*************************** 数据权限开始*************************/


            sb.append("        if(!checkUserDataAuth(token,model.getNumber())){\n" +
                    "            return R.error(\"您没有权限修改\"+model.getNumber()+\"的记录\");\n" +
                    "        } \n   ");
            /*************************** 数据权限结束*************************/

            sb.append("        //数据校验\n");
            sb.append("        JSONObject check = V.checkEmpty(updateVerify(),object);\n");
            sb.append("        if(check.getBoolean(\"check\"))\n");
            sb.append("            return R.error(check.getString(\"message\"));\n");


            /*************************** 校验重复值开始*************************/
            sb.append("              " + formatName(tableName) + "data= " + formatName2(tableName) + "Service.selectById(model.getId());\n      \n");
            sb.append("             if(data==null){  \n");
            sb.append("                  return R.error(\"该信息不存在，无法修改\"); \n");
            sb.append("             } \n");

            sb.append("        //TODO 写校验重复的条件\n");
            sb.append("        //   if(!model.getCode().equals(data.getCode())) {   \n");
            sb.append("        //Boolean exist = " + formatName2(tableName) + "Service.exist(W.f(\n");
            sb.append("        //        W.and(\"code\",\"eq\",model.getCode()),\n");
            sb.append("        //        W.and(\"isDel\",\"eq\",\"0\"))\n");
            sb.append("        //);\n");
            sb.append("        //if(exist)\n");
            sb.append("        //    return R.error(\"代码已经存在请更换一个代码\");\n\n");
            sb.append("        //     }    \n");

            /*************************** 校验重复值结束*************************/


            sb.append("        model.setReviser(userId);\n");
            sb.append("        model = " + formatName2(tableName) + "Service.update" + formatName(tableName) + "(model);\n");
            sb.append("        if(model == null)\n");
            sb.append("            return R.error(\"修改失败\");\n");
            sb.append("        return R.ok(\"修改成功\",fm2(model));\n");
            sb.append("    }\n\n");

            sb.append("    @DeleteMapping(\"/" + formatName2(tableName) + "/{id}\")\n");
            sb.append("    @ApiOperation(value = \"" + tableName_CN + "删除\")\n");
            sb.append("    public Object delete" + formatName(tableName) + "(@PathVariable(\"id\") String id,\n");
            sb.append("                                                  @RequestHeader(\"token\") String token){\n");


            /*************************** 数据权限开始*************************/

            sb.append("   " + formatName(tableName) + " obj = " + formatName2(tableName) + "Service.selectById(id);\n" +
                    "\n" +
                    "        if(V.isEmpty(obj))\n" +
                    "            return R.error(\"Id数据异常\");\n" +
                    "        String number = obj.getNumber();\n" +
                    "\n" +
                    "        if(!checkUserDataAuth(token,number)){\n" +
                    "            return R.error(\"您没有权限删除\"+number+\"的记录\");\n" +
                    "        }\n     ");

            /*************************** 数据权限结束*************************/

            sb.append("        if(" + formatName2(tableName) + "Service.delete" + formatName(tableName) + "(id, cacheKit.getUserId(token)))\n");
            sb.append("            return R.ok(\"删除成功\");\n");
            sb.append("        return R.error(\"删除失败\");\n");
            sb.append("    }\n\n");

            sb.append("    @GetMapping(\"/" + formatName2(tableName) + "/{index}-{size}-{key}\")\n");
            sb.append("    @ApiOperation(value = \"读取" + tableName_CN + "分页列表\")\n");
            sb.append("    public Object get" + formatName(tableName) + "(@PathVariable(\"index\") int index,\n");
            sb.append("                              @PathVariable(\"size\") int size,\n");
            sb.append("                              @PathVariable(\"key\") String key,\n");
            sb.append("                              @RequestHeader(\"token\") String token) {\n");
            sb.append("        String wKey = \"\";\n");
            sb.append("        if (!V.isEmpty(key))\n");
            sb.append("        //FIXME 修改id为需要查询的字段\n");
            sb.append("            wKey = S.apppend(\" and id like '%\", key, \"%' \");\n");

            /*************************** 数据权限开始*************************/
            sb.append("   Page empty = new Page(new ArrayList<JSONObject>(), 0, 0, 0, 1);\n");

            sb.append("   //获取数据可见性\n" +
                    "        Integer visible = getVisibleByCache(token);\n" +
                    "        if (V.isEmpty(visible)) {\n" +
                    "            return R.error(\"您没有权限查看\");\n" +
                    "        }\n" +
                    "\n" +
                    "        if (visible == 1) {//1-全部（组织部）\n");
            sb.append("        return R.ok(" + formatName2(tableName) + "Service.page(index, size, wKey));\n");
            sb.append("\n" +
                    "        } else if (visible == 2) {//2-院级\n" +
                    "            String academyCode = getAcademyCodeByCache(token);\n" +
                    "            if (V.isEmpty(academyCode)) {\n" +
                    "                return R.error(\"请先完善您的学院信息\");\n" +
                    "            }\n" +
                    "            String numbers = customService.queryUserNumbersByAcademyCode(academyCode);\n" +
                    "            if(V.isEmpty(numbers)){\n" +
                    "               // return R.error(\"当前账号暂无可操作的用户信息\");\n" +
                    "                            return R.ok(empty);\n" +
                    "            }\n" +
                    "            //获取该学院代码下所有的用户信息\n" +
                    "            wKey += S.apppend(\" and number in (\", numbers, \") \");\n");
            sb.append("        return R.ok(" + formatName2(tableName) + "Service.page(index, size, wKey));\n");
            sb.append("\n" +
                    "        } else if (visible == 3) {//3-个人(班主任、联络员)\n" +
                    "            //获取学号/工号\n" +
                    "            String loginNumber = getLoginNumberCache(token);\n" +
                    "            if (V.isEmpty(loginNumber)) {\n" +
                    "                return R.error(\"请先完善你的学号/工号\");\n" +
                    "            }\n" +
                    "            String numbers = customService.queryUserNumbersByLinkNumber(loginNumber);\n" +
                    "            if(V.isEmpty(numbers)){\n" +
                    "              //  return R.error(\"当前账号暂无可操作的用户信息\");\n" +
                    "                            return R.ok(empty);\n" +
                    "            }\n" +
                    "            wKey += S.apppend(\" and number in (\", numbers, \") \");\n");
            sb.append("        return R.ok(" + formatName2(tableName) + "Service.page(index, size, wKey));\n");
            sb.append("        } else {\n" +
                    "            return R.error(\"您没有权限查看\");\n" +
                    "        }\n");
            /*************************** 数据权限结束*************************/


            sb.append("    }\n\n");


            //verify方法
            sb.append("    private Map<String, String> verify() {\n");
            sb.append(verifyStr);
            sb.append("    }\n\n");

            //updateVerify方法
            sb.append("    private Map<String, String> updateVerify() {\n");
            sb.append(verifyStr);
            sb.append("    }\n\n");

            sb.append("}\n");

            //API生成结束

        }

        writeFile2((apiPath + formatName(tableName) + "Api.java"), sb);
        code.put("Api", sb);


        //代码生成记录
        List<SysAutoCode> sysAutoCodes = autoCodeService.query(W.f(W.order("name", "eq", tableName)));
        if (sysAutoCodes.size() > 0) {
            SysAutoCode sysAutoCode = sysAutoCodes.get(0);
            sysAutoCode.setCode(J.o2s(code));
            sysAutoCode.setType(1);//1-后台代码
            autoCodeService.update(sysAutoCode);
        } else {
            SysAutoCode sysAutoCode = new SysAutoCode();
            sysAutoCode.setName(tableName);
            sysAutoCode.setCode(J.o2s(code));
            sysAutoCode.setType(1);//1-后台代码
            autoCodeService.insert(sysAutoCode);
        }

        return R.ok(code);
    }


    @GetMapping("/code")
    @ApiOperation(value = "读取代码生成日志")
    public Object getCodeLog() throws SQLException {
        ResultSet rs = getTable();
        List<JSONObject> list = new ArrayList<JSONObject>();

        List<SysAutoCode> sysAutoCodes = autoCodeService.query(
                W.f(W.order("name", "asc"), W.and("type", "eq", 1))
        );

        while (rs.next()) {
            JSONObject object = new JSONObject();
            String tableName = rs.getString(1);
            object.put("name", tableName);
            object.put("createTime", 0);
            object.put("reviseTime", 0);
            object.put("code", new JSONObject());
            if (sysAutoCodes.size() > 0) {
                for (SysAutoCode sysAutoCode : sysAutoCodes) {
                    if (V.isEqual(sysAutoCode.getName(), tableName)) {
                        Object code = J.s2j(sysAutoCode.getCode());
                        object.put("createTime", sysAutoCode.getCreateTime());
                        object.put("reviseTime", sysAutoCode.getReviseTime());
                        object.put("code", code == null ? new JSONObject() : code);
                    }
                }
            }
            list.add(object);
        }
        return list;
    }

    //读取数据库结构
    private ResultSet getTableCol(String tableName) {
//        String user = "root";
//        String password = "Taller10086!";
//        String url = "jdbc:mysql://39.108.1.47:3306/ump?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false";
        ResultSet rs;
        try {
            Connection conn = null;
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstm = conn.prepareStatement("select COLUMN_name,data_type,COLUMN_COMMENT,COLUMN_TYPE from information_schema.columns where table_name = '" + tableName + "' AND table_schema='" + database + "' ");

            rs = pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            rs = null;
        }
        return rs;
    }

    //读取数据库表
    private ResultSet getTable() {
//        String user = "root";
//        String password = "Taller10086!";
//        String url = "jdbc:mysql://39.108.1.47:3306/ump?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false";
        ResultSet rs;
        try {
            Connection conn = null;
            conn = DriverManager.getConnection(url, user, password);
            String sql = "select table_name from information_schema.tables where table_schema='" + database + "' and lower(table_type)='base table' order by table_name ";
            System.out.print("sql:" + sql);
            PreparedStatement pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            rs = null;
        }
        return rs;
    }

    //读取数据库表注释
    private String[] getTableComment(String tableName) {
        ResultSet rs;
        String[] tableComment = new String[10];
        //[0]:表序号
        tableComment[0] = "00";
        //[1]:表名
        tableComment[1] = "";
        try {
            Connection conn = null;
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_name='" + tableName + "' and table_schema='" + database + "';";
            System.out.print("table comment sql:" + sql);
            PreparedStatement pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            //找到该表
            if (rs.next()) {
                String comment = rs.getString("TABLE_COMMENT");

                if (comment == null || comment.length() == 0) {
                    throw new ArrayIndexOutOfBoundsException("请输入表注释");
                }

                //通过空格分割字符串,[0]为表名,[1]为表序号
                tableComment = comment.split(" ");
                //去掉[1]中的"表"字
                tableComment[1] = tableComment[1].split("表")[0];

                //截取[1]中表序号，不截取百位的数字,只需十位和个位
                if (tableComment.length == 3) {
                    tableComment[0] = tableComment[0].substring(1, 3);
                }

            } else {
                //找不到该表，默认用英文表名,序号默认00
                tableComment[1] = tableName;
                tableComment[0] = "00";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            tableComment[1] = tableName;
            tableComment[0] = "00";
        } catch (ArrayIndexOutOfBoundsException ae) {
            ae.printStackTrace();
            tableComment[1] = tableName;
            tableComment[0] = "00";
        }

        return tableComment;
    }

    //写文件
    private static void writeFile(String path, StringBuffer sb) throws IOException {
        File file = new File(path);
        System.out.println("path=" + path);
        FileWriter fw = null;
        BufferedWriter writer = null;
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        fw = new FileWriter(file);
        writer = new BufferedWriter(fw);
        writer.write(sb.toString());

        writer.flush();
        writer.close();
        fw.close();
    }

    private static void writeFile2(String path, StringBuffer sb) throws IOException {
        File file = new File(path);
        FileWriter fw = null;
        BufferedWriter writer = null;
        if (!file.exists()) {
            file.createNewFile();
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            fw.close();
        }
    }

    private static String formatType(String db) {
        String type = "String";
        if (db.equals("bigint")) {
            type = "String";
        }
        if (db.equals("tinyint")) {
            type = "Integer";
        }
        if (db.equals("smallint")) {
            type = "Integer";
        }
        if (db.equals("int")) {
            type = "Integer";
        }
        return type;
    }

    private static String formatName(String name) {
        String[] item = name.split("_");
        int len = item.length;
        String fileName = "";
//        if(item[0].equals("sys")){
//            for(int i=1;i<len;i++){
//                fileName += item[i].substring(0, 1).toUpperCase() + item[i].substring(1);
//            }
//        }else{
//
//        }
        for (int i = 0; i < len; i++) {
            fileName += item[i].substring(0, 1).toUpperCase() + item[i].substring(1);
        }
        return fileName;
    }


    //首字母小写的驼峰命名
    private static String formatName2(String name) {
        String[] item = name.split("_");
        int len = item.length;
        String fileName = item[0];
        for (int i = 1; i < len; i++) {
            fileName += item[i].substring(0, 1).toUpperCase() + item[i].substring(1);
        }
        return fileName;
    }

    private static String formatGS(String field) {
        return field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
    }


    //获取字段注释
    //如 状态:0-有效，1-无效
    //截取"状态"二字
    private String formatComment(String columnComment) {
        String[] comment = new String[20];

        //无注释
        if (columnComment == null || "".equals(columnComment)) {
            return "";
        }

        //注释包含:或：分隔，截取分隔符前面内容
        if (columnComment.indexOf(":") != -1) {
            comment = columnComment.split(":");
        } else if (columnComment.indexOf("：") != -1) {
            comment = columnComment.split("：");
        } else {
            comment = columnComment.split(" ");
        }
        return comment[0];
    }

    //判断是否是5个公有字段：creator,createTime,reviser,reviseTime,isDel
    //不包括id，另外判断，因为id在更新时用到
    private boolean isPublicField(String column_name) {
        return "creator".equals(column_name) || "createTime".equals(column_name) ||
                "reviser".equals(column_name) || "reviseTime".equals(column_name) ||
                "isDel".equals(column_name);
    }


    private String[] getHelperValue() {


        return null;
    }

}
