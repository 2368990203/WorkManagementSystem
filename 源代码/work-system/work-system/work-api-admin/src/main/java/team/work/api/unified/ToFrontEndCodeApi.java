package team.work.api.unified;

import com.alibaba.fastjson.JSONObject;
import team.work.api.BaseApi;
import team.work.core.model.SysAutoCode;
import team.work.core.service.impl.SysAutoCodeService;
import team.work.doc.FrontEndCode;
import team.work.utils.kit.DESKit;
import team.work.utils.tool.PropertiesUtil;
import team.work.utils.convert.J;
import team.work.utils.convert.R;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import team.work.utils.kit.TimeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.tree.Tree;
import team.work.doc.FrontEndCode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@Api(value = "前端代码生成")
@RestController
@RequestMapping("/v1/other")
@CrossOrigin   //跨域服务注解
public class ToFrontEndCodeApi extends BaseApi {

    PropertiesUtil db = new PropertiesUtil("db.properties");
    private final String url = db.readProperty("url");

//    private final String user = db.readProperty("user");
//    private final String password = db.readProperty("password");

    private final String user = DESKit.decrypt(db.readProperty("user"));
    private final String password = DESKit.decrypt(db.readProperty("password"));
    private final String database = db.readProperty("database");

    @Autowired
    private SysAutoCodeService autoCodeService;

    @PostMapping("/front_code")
    @ApiOperation(value = "自动生成前端代码")
    public Object createCode(@RequestBody FrontEndCode object) throws IOException, SQLException {

        String tableName = object.getTable();

        System.out.println(object);

//        if(1==1){
//            return R.error("阻止");
//        }

//        String time = TimeKit.formatNow("yyyy/MM/dd");
//        ResultSet rs = getTableCol(tableName);

        /***********************基础路径配置**************************/

        //html
        String html = "/party-admin/views/test/form/";

        if (!V.isEmpty(object.getHtmlPath())) {
            html = object.getHtmlPath();
        }

        //js路径
        String js = "/party-admin/views/test/js/";

        if (!V.isEmpty(object.getJsPath())) {
            js = object.getJsPath();
        }

        //ejs路径
        String ejs = "/party-admin/test/ejs/";
        if (!V.isEmpty(object.getEjsPath())) {
            ejs = object.getEjsPath();
        }

        //项目根目录
        File directory = new File(".");

        String path = directory.getCanonicalPath();

        //html完整路径
        String htmlPath = path + html;

        //js完整路径
        String jsPath = path + js;

        //完整ejs路径
        String ejsPath = path + ejs;

        JSONObject code = new JSONObject();

        //有选择的代码生成
        if (object.getHtmlSelect() == 1) {
            code.put("html", htmlGenerate(htmlPath, tableName));
        }

        //type:2-js
        if (object.getJsSelect() == 2) {
            code.put("js", jsGenerate(jsPath, tableName));
        }

        //type:3-ejs
        if (object.getEjsSelect() == 3) {
            code.put("ejs", ejsGenerate(ejsPath, tableName));
        }


        //代码生成记录
        List<SysAutoCode> sysAutoCodes = autoCodeService.query(W.f(W.order("name", "eq", tableName)));
        if (sysAutoCodes.size() > 0) {
            SysAutoCode sysAutoCode = sysAutoCodes.get(0);
            sysAutoCode.setCode(J.o2s(code));
            sysAutoCode.setType(2);//2-前端
            autoCodeService.update(sysAutoCode);
        } else {
            SysAutoCode sysAutoCode = new SysAutoCode();
            sysAutoCode.setName(tableName);
            sysAutoCode.setCode(J.o2s(code));
            sysAutoCode.setType(2);//2-前端
            autoCodeService.insert(sysAutoCode);
        }

        return R.ok(code);
    }


    //生成html表单代码
    private StringBuffer htmlGenerate(String htmlPath, String tableName) throws SQLException, IOException {

        ResultSet rs = getTableCol(tableName);

        StringBuffer htmlCode = new StringBuffer();
        StringBuffer scriptCode = new StringBuffer();
        StringBuffer editorCode = new StringBuffer();

        htmlCode.append("<div class=\"table-responsive\">\n");
        htmlCode.append("    <form id=\"opt-form\">\n");
        htmlCode.append("        <table class=\"table table-bordered\">\n");


        scriptCode.append("<script>\n");


        while (rs.next()) {

            //数据类型:如int、float
            String data_type = rs.getString("data_type");
            //字段名
            String COLUMN_name = rs.getString("COLUMN_name");
            //字段中文注释【全部】
            String COLUMN_COMMENT_ALL = rs.getString("COLUMN_COMMENT");
            //字段的中文注释【冒号前】
            String COLUMN_COMMENT = formatComment(rs.getString("COLUMN_COMMENT"));
            //数据类型：如int(11)、bigint(20)
            String COLUMN_TYPE = rs.getString("COLUMN_TYPE");
            //lw190310增加
            //数据长度
            String COLUMN_LENGTH = rs.getString("COLUMN_LENGTH");


            //公有字段则不渲染到弹窗
            if (isPublicField(COLUMN_name)) {
                continue;
            }

            htmlCode.append("            <tr>\n");
            htmlCode.append("                <td class=\"form-item-title\">\n");
            htmlCode.append("                    " + COLUMN_COMMENT + "\n");
            htmlCode.append("                </td>\n");
            htmlCode.append("                <td>\n");


            //time类型判断
            if (COLUMN_name.endsWith("Time") && "int(11)".equals(COLUMN_TYPE)) {

                htmlCode.append("                    <input required type=\"text\" name=\"" + COLUMN_name + "\" id=\"" + COLUMN_name + "Str\" maxlength=\"20\" class=\"form-control\"\n");
                htmlCode.append("                           placeholder=\"请输入" + COLUMN_COMMENT + "\"  tips-id = \"" + COLUMN_name + "-tips\"  date-type=\"datetime\"  onblur=\"checkRegular(this);\" >\n");

                scriptCode.append("    laydate.render({\n");
                scriptCode.append("        elem: \"#" + COLUMN_name + "Str\",\n");
                scriptCode.append("    });\n");

            }
            //longtext类型判断：富文本
            else if ("longtext".equals(data_type) || "longtext".equals(COLUMN_TYPE)) {

                htmlCode.append("                    <textarea required type=\"text\" name=\"" + COLUMN_name + "\" id=\"" + COLUMN_name + "\"\n");
                htmlCode.append("                              placeholder=\"请输入" + COLUMN_COMMENT + "\"  tips-id = \"" + COLUMN_name + "-tips\"   data-length=\"" + COLUMN_LENGTH + "\" onblur=\"checkRegular(this);\" ></textarea>\n");

                editorCode.append("        editor.render('#" + COLUMN_name + "');\n");

            }
            //其他普通字段：input
            else {


                TreeMap<String, String> helpers = getHelpers(COLUMN_COMMENT_ALL);

                Set<String> keySet = helpers.keySet();
                Iterator<String> iter = keySet.iterator();

                //属于下拉框字段
                if (iter.hasNext()) {
                    for (int i = 0; iter.hasNext(); i++) {
                        String key = iter.next();
                        //渲染select
                        if (i == 0) {
                            htmlCode.append("                    <select required name=\"" + COLUMN_name + "\" id=\"" + COLUMN_name + "\" tips-id = \"" + COLUMN_name + "-tips\" data-length=\"" + COLUMN_LENGTH + "\"  onblur=\"checkRegular(this);\"" +
                                    " placeholder=\"请输入" + COLUMN_COMMENT + "\" class=\"form-control\"> \n");
                        }

                        if (key != null || key.length() != 0) {
                            htmlCode.append("                    <option value=\"" + key + "\">" + helpers.get(key) + "</option>\n");
                        }

                        if (!iter.hasNext()) {
                            htmlCode.append("                    </select>\n");
                        }
                    }
                } else {
                    htmlCode.append("                    <input required type=\"text\" name=\"" + COLUMN_name + "\" id=\"" + COLUMN_name + "\"  tips-id = \"" + COLUMN_name + "-tips\"  data-length=\"" + COLUMN_LENGTH + "\" maxlength=\"" + (Integer.parseInt(COLUMN_LENGTH) > 20 ? 20 : COLUMN_LENGTH) + "\"  onblur=\"checkRegular(this);\"  class=\"form-control\"\n");
                    htmlCode.append("                           placeholder=\"请输入" + COLUMN_COMMENT + "\">\n");
                }
            }


            htmlCode.append("                </td>\n");
            htmlCode.append("                <td class=\"form-item-remark\" > <span id = \"" + COLUMN_name + "-tips\"></span></td>\n");
            htmlCode.append("            </tr>\n");


        }


        htmlCode.append("        </table>\n");
        htmlCode.append("    </form>\n");
        htmlCode.append("</div>\n");


        //如果有富文本则渲染并拼接到script
        if (editorCode.length() != 0) {
            scriptCode.append("    $(function () {\n");
            scriptCode.append(editorCode);
            scriptCode.append("    })\n");
        }

        scriptCode.append("</script>");

        //把script拼接到html后面
        htmlCode.append(scriptCode);

        writeFile((htmlPath + "_" + formatName2(tableName) + ".html"), htmlCode);

        return htmlCode;
    }


    //生成js代码
    private StringBuffer jsGenerate(String jsPath, String tableName) throws SQLException, IOException {

        ResultSet rs = getTableCol(tableName);

        StringBuffer jsCode = new StringBuffer();
        StringBuffer helperCode = new StringBuffer();
        StringBuffer editorCode = new StringBuffer();
        StringBuffer timeCode = new StringBuffer();

        helperCode.append("var helper = {\n");

        while (rs.next()) {
            //数据类型:如int、float
            String data_type = rs.getString("data_type");
            //字段名
            String COLUMN_name = rs.getString("COLUMN_name");
            //字段的中文注释
            String COLUMN_COMMENT = rs.getString("COLUMN_COMMENT");
            //数据长度：如int(11)、bigint(20)
            String COLUMN_TYPE = rs.getString("COLUMN_TYPE");

            if (isPublicField(COLUMN_name))
                continue;

            //helper
            TreeMap<String, String> helpers = getHelpers(COLUMN_COMMENT);

            Set<String> keySet = helpers.keySet();
            Iterator<String> iter = keySet.iterator();
            for (int i = 0; iter.hasNext(); i++) {
                String key = iter.next();
                //渲染helper函数
                if (i == 0) {
                    helperCode.append("    " + COLUMN_name + ": function (_" + COLUMN_name + ") {\n");
                    helperCode.append("        switch (parseInt(_" + COLUMN_name + ")) {\n");
                }

                if (key != null || key.length() != 0) {
                    helperCode.append("             case " + key + ":\n" +
                            "                 return \"" + helpers.get(key) + "\";\n");
                }

                if (!iter.hasNext()) {
                    helperCode.append("         }\n");
                    helperCode.append("    },\n");
                }
            }


            if (COLUMN_name.endsWith("Time") && "int(11)".equals(COLUMN_TYPE)) {
                timeCode.append("        data['" + COLUMN_name + "'] = time.date2timestamp(data['" + COLUMN_name + "']);\n");
            } else if ("longtext".equals(data_type) || "longtext".equals(COLUMN_TYPE)) {
                editorCode.append("        editor.set('#" + COLUMN_name + "',model['" + COLUMN_name + "']);\n");
            }
        }

        helperCode.append("};\n\n");

        jsCode.append("var index = 1, size = 6, key = '', totalPage = 0, totalCount = 0;\n");
        jsCode.append("var baseData = [], pageData = [];\n");
        jsCode.append("var optId = '';\n\n");

        jsCode.append("var config = {\n");
        jsCode.append("    form: '/form/_" + formatName2(tableName) + ".html',\n");
        jsCode.append("    title: '" + getTableComment(tableName)[1] + "管理',\n");
        jsCode.append("};\n\n");

        jsCode.append("$(function () {\n");
        jsCode.append("    //自适应\n");
        jsCode.append("    view.initHeight();\n");
        jsCode.append("    $(window).resize(function () {\n");
        jsCode.append("        view.initHeight();\n");
        jsCode.append("    });\n");
        jsCode.append("    " + formatName2(tableName) + ".get();\n");
        jsCode.append("});\n\n");

        jsCode.append("var " + formatName2(tableName) + " = {\n");
        jsCode.append("    get: function () {\n");
        jsCode.append("        var param = {url: baseModule." + formatName2(tableName) + "Api + '/' + index + '-' + size + '-' + key};\n");
        jsCode.append("        var request = ajax.get(param);\n");
        jsCode.append("        request.done(function (d) {\n");
        jsCode.append("            pageData = d.result.data;\n");
        jsCode.append("            render.page();\n");
        jsCode.append("            totalPage = d.result.totalPage;\n");
        jsCode.append("            totalCount = d.result.totalCount;\n");
        jsCode.append("            if (d.result.totalPage>1) {\n");
        jsCode.append("                  page.init(d.result.totalPage, d.result.totalCount);\n");
        jsCode.append("            }else{\n");
        jsCode.append("                 $('.list-page').empty();\n");
        jsCode.append("            }\n");
        jsCode.append("        })\n");
        jsCode.append("    },\n\n");

        jsCode.append("    create: function (event) {\n");
        jsCode.append("        if (auth.refuse(event))\n");
        jsCode.append("            return false;\n");
        jsCode.append("        openLay({url: config.form, fun: 'opt.create();', title: config.title});\n");
        jsCode.append("    },\n\n");

        jsCode.append("    delete: function (event) {\n");
        jsCode.append("        if (auth.refuse(event))\n");
        jsCode.append("            return false;\n");
        jsCode.append("        optId = getId(event);\n");
        jsCode.append("        tips.confirm({message: '是否要删除" + getTableComment(tableName)[1] + "数据？', fun: \"opt.delete();\"});\n\n");
        jsCode.append("    },\n\n");

        jsCode.append("    update: function (event) {\n");
        jsCode.append("        if (auth.refuse(event))\n");
        jsCode.append("            return false;\n");
        jsCode.append("        optId = getId(event);//获取当前id的值\n");
        jsCode.append("        openLay({url: config.form, fun: \"opt.update();\", title: config.title});\n");
        jsCode.append("        var model = result.get(pageData, optId);\n\n");
        //富文本渲染
        jsCode.append(editorCode);
        jsCode.append("        form.set(model);\n");
        jsCode.append("    },\n");
        jsCode.append("};\n\n");


        jsCode.append("var render = {\n" +
                "    page: function () {\n" +
                "        var template = doT.template($(\"#" + formatName2(tableName) + "-template\").text());//获取的模板\n" +
                "        $('#item-list').html(template(pageData));//模板装入数据\n" +
                "    },\n" +
                "};\n\n");

        jsCode.append("var opt = {\n" +
                "    create: function () {\n" +
                "        var data = form.get(\"#opt-form\");\n" +
                "        if (form.verify(data))\n" +
                "            return false;\n" +
                "\n" +
                "        var param = {url: baseModule." + formatName2(tableName) + "Api, data: data};\n" +
                "        var request = ajax.post(param);\n" +
                "        request.done(function (d) {\n" +
                "            tips.done(d);\n" +
                "            pageData.push(d.result);\n" +
                "            totalCount = totalCount + 1;\n" +
                "            page.init(totalPage, totalCount);\n" +
                "            render.page();\n" +
                "        })\n" +
                "    },\n" +
                "    delete: function () {\n" +
                "        var request = ajax.delete({url: baseModule." + formatName2(tableName) + "Api + '/' + optId});\n" +
                "        request.done(function (d) {\n" +
                "            tips.ok(d.message);\n" +
                "            pageData = result.delete(pageData, optId);\n" +
                "            render.page();\n" +
                "            totalCount = totalCount - 1;\n" +
                "            page.init(totalPage, totalCount);\n" +
                "        })\n" +
                "    },\n" +
                "    update: function () {\n" +
                "        var data = form.get(\"#opt-form\");\n" +
                "        if (form.verify(data))\n" +
                "            return false;\n" +
                "        data['id'] = optId;\n\n" +
                //时间格式转换
                timeCode +

                "        var param = {url: baseModule." + formatName2(tableName) + "Api, data: data};\n" +
                "        var request = ajax.put(param); //加一条记录\n" +
                "        request.done(function (d) {\n" +
                "            tips.ok(d.message);\n" +
                "            //更新对象\n" +
                "            pageData = result.update(pageData, d.result, 'id');\n" +
                "            " + formatName2(tableName) + ".get();\n" +
                "            closeLay();\n" +
//                "            render.page(); //更新数据后重新渲染\n" +
                "        })\n" +
                "    },\n" +
                "    close: function () {   //关闭按钮\n" +
                "        closeLay();\n" +
                "    }\n" +
                "};\n\n");

        jsCode.append("var page = {\n" +
                "    init: function (_pageSize, _total) {\n" +
                "        $('.list-page').pagination({\n" +
                "            pageCount: _pageSize,\n" +
                "            current: index,\n" +
                "            jump: true,\n" +
                "            coping: true,\n" +
                "            homePage: '首页',\n" +
                "            endPage: '末页',\n" +
                "            prevContent: '上页',\n" +
                "            nextContent: '下页',\n" +
                "            pageSize: size,\n" +
                "            pageArray: [6, 12, 24, 48],\n" +
                "            totalCount: _total,\n" +
                "            id: '" + formatName2(tableName) + "-page',\n" +
                "            callback: function (api) {\n" +
                "                index = api.getCurrent();\n" +
                "                " + formatName2(tableName) + ".get();\n" +
                "            }\n" +
                "        });\n" +
                "        if (_pageSize > 0)\n" +
                "            $('.pages').show();\n" +
                "    }\n" +
                "};\n\n");

        jsCode.append("var view = {\n" +
                "    initHeight: function () {\n" +
                "        $('.data-view').css('height', (parent.adaptable().h) - 80);\n" +
                "        $('.date-table').css('height', (parent.adaptable().h) - 180);\n" +
                "        size = Math.floor(((parent.adaptable().h) - 180) / 40);\n" +
        "    }\n" +
                "};\n\n");

        jsCode.append("\n" +
                "function pageChange(event) {\n" +
                "    size = $(event).val();\n" +
                "    index = 1;\n" +
                "    " + formatName2(tableName) + ".get();\n" +
                "};\n\n");

        //渲染helper
        jsCode.append(helperCode);

        //批量调用helper方法
        jsCode.append("var tool = {\n" +
                "    translate:function (model) {\n" +
                "        var data = [];\n" +
                "        for (var variable in model) {\n" +
                "            data[variable] = model[variable];\n" +
                "            //判断helper里是否存在该函数，存在则执行转换\n" +
                "            if (typeof eval('helper.' + variable) == 'function')\n" +
                "                model[variable] = eval('helper.' + variable + '(' + model[variable] + ')');\n" +
                "        }\n" +
                "        form.set(model);\n" +
                "        //恢复回转换前数据\n" +
                "        for (var variable in data) {\n" +
                "            model[variable] = data[variable];\n" +
                "        }\n" +
                "    }\n" +
                "};");

        writeFile2((jsPath + "_" + formatName2(tableName) + ".js"), jsCode);

        return jsCode;

    }


    //生成ejs代
    private StringBuffer ejsGenerate(String ejsPath, String tableName) throws SQLException, IOException {

        ResultSet rs = getTableCol(tableName);

        StringBuffer ejsCode = new StringBuffer();
        StringBuffer listCode_CN = new StringBuffer();
        StringBuffer listCode_EN = new StringBuffer();

        boolean hasTime = false;
        boolean hasEditor = false;


        while (rs.next()) {
            //数据类型:如int、float
            String data_type = rs.getString("data_type");
            //字段名
            String COLUMN_name = rs.getString("COLUMN_name");
            //字段的中文注释【冒号前】
            String COLUMN_COMMENT = formatComment(rs.getString("COLUMN_COMMENT"));
            //字段的中文注释【全部】
            String COLUMN_COMMENT_ALL = rs.getString("COLUMN_COMMENT");
            //数据长度：如int(11)、bigint(20)
            String COLUMN_TYPE = rs.getString("COLUMN_TYPE");

            if (!isPublicField(COLUMN_name)) {
                //中文表头
                listCode_CN.append("                                <th>" + formatComment(COLUMN_COMMENT) + "</th>\n");
                //渲染列表数据


                if (COLUMN_name.endsWith("Time") && "int(11)".equals(COLUMN_TYPE)) {
                    listCode_EN.append("        <td>\n" +
                            "            {{= it[i]." + COLUMN_name + "Str}}\n" +
                            "        </td>\n");
                } else {

                    TreeMap<String, String> helpers = getHelpers(COLUMN_COMMENT_ALL);

                    Set<String> keySet = helpers.keySet();
                    Iterator<String> iter = keySet.iterator();

                    //属于下拉框字段
                    if (iter.hasNext()) {

                        listCode_EN.append("        <td>\n" +
                                "            {{= helper." + COLUMN_name + "(it[i]." + COLUMN_name + ") }}\n" +
                                "        </td>\n");
                    } else {
                        listCode_EN.append("        <td>\n" +
                                "            {{= it[i]." + COLUMN_name + "}}\n" +
                                "        </td>\n");
                    }
                }
            }

            if (COLUMN_name.endsWith("Time") && "int(11)".equals(COLUMN_TYPE)) {
                hasTime = true;
            } else if ("longtext".equals(data_type) || "longtext".equals(COLUMN_TYPE)) {
                hasEditor = true;
            }
        }

        ejsCode.append("<% include ../inc/header.ejs%>\n" +
                "<style>\n" +
                "    .date-table {\n" +
                "        overflow-y: auto;\n" +
                "    }\n" +
                "</style>\n" +
                "\n");

        if (hasEditor) {
            ejsCode.append("<link href=\"/assets/js/summernote/summernote.css\" rel=\"stylesheet\">\n\n");
        }

        ejsCode.append("<% include ../inc/body.ejs%>\n" +
                "<!-- html主体代码 start -->\n" +
                "<div class=\"row\">\n" +
                "    <div class=\"col-sm-12\">\n" +
                "        <div class=\"ibox float-e-margins\">\n" +
                "            <div class=\"ibox-title\" style=\"padding-top: 3px !important;\">\n" +
                "                <div style=\"width: 140px; display: block;float: left;padding-top: 10px;\">\n" +
                "                    " + getTableComment(tableName)[1] + "列表\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"ibox-tools\">\n" +
                "                    <button style=\"margin-top: 8px;\" class=\"btn btn-primary btn-xs\" type=\"button\"\n" +
                "                            onclick=\"" + formatName2(tableName) + ".create(this);\">\n" +
                "                        <i class=\"fa fa-plus\"></i>&nbsp;新增\n" +
                "                    </button>\n" +
                "                </div>\n" +
                "\n" +
                "            </div>\n" +
                "            <div class=\"ibox-content data-view\" style=\"overflow-y: auto;\">\n" +
                "                <div class=\"col-sm-12\">\n" +
                "                    <div class=\" col-sm-12 date-table\">\n" +
                "                        <table class=\"table table-striped table-hover\">\n" +
                "                            <thead>\n" +
                "                            <tr>\n" +
                "                                <th>序号</th>\n");


        ejsCode.append(listCode_CN);

        ejsCode.append("                                <th></th>\n" +
                "                            </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody id=\"item-list\">\n" +
                "\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <div class=\"col-sm-12 pages M-box3 list-page\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n");

        ejsCode.append("<!-- html主体代码 end -->\n" +
                "<!-- 管理的" + getTableComment(tableName)[1] + "模板 -->\n" +
                "<script id=\"" + formatName2(tableName) + "-template\" type=\"text/x-dot-template\">\n" +
                "    {{ if(it.length == 0) { }}\n" +
                "    暂无数据\n" +
                "    {{ }else{ }}\n" +
                "    {{ for(var i=0;i < it.length;i++){ }}\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            {{=(i+1)}}\n" +
                "        </td>\n");

        ejsCode.append(listCode_EN);

        ejsCode.append("        <td>\n" +
                "            <div class=\"btn-group\">\n" +
                "                <button data-toggle=\"dropdown\" class=\"btn btn-primary btn-xs dropdown-toggle\">操作 <span\n" +
                "                            class=\"caret\"></span>\n" +
                "                </button>\n" +
                "                <ul class=\"dropdown-menu\">\n" +
                "                    <li>\n" +
                "                        <a href=\"#\" data-id=\"{{= it[i].id}}\" onclick=\"" + formatName2(tableName) + ".update(this);\">修改</a>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <a href=\"#\" data-id=\"{{= it[i].id}}\" onclick=\"" + formatName2(tableName) + ".delete(this);\">删除</a>\n" +
                "                    </li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    {{ } }}\n" +
                "    {{ } }}\n" +
                "</script>\n");

        ejsCode.append("<% include ../inc/js.ejs %>\n\n");


        ejsCode.append("<!-- 私有脚本 start -->\n" +
                "<script src=\"/js/baseModule/_" + formatName2(tableName) + ".js\"></script>\n" +
                "<!-- 私有脚本 end -->\n" +
                "<% include ../inc/footer.ejs %>");


        writeFile((ejsPath + formatName2(tableName) + ".ejs"), ejsCode);

        return ejsCode;

    }

    @GetMapping("/front_code")
    @ApiOperation(value = "读取前端代码生成日志")
    public Object getCodeLog() throws SQLException {
        ResultSet rs = getTable();
        List<JSONObject> list = new ArrayList<JSONObject>();

        List<SysAutoCode> sysAutoCodes = autoCodeService.query(
                W.f(W.order("name", "asc"), W.and("type", "eq", 2))
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
        ResultSet rs;
        try {
            Connection conn = null;
            conn = DriverManager.getConnection(url, user, password);

            //lw 20190310增加字符段长度

            PreparedStatement pstm = conn.prepareStatement(
                    "select COLUMN_name,data_type,COLUMN_COMMENT,COLUMN_TYPE,NUMERIC_PRECISION COLUMN_LENGTH " +
                            "from information_schema.columns where table_name = '" + tableName +
                            "' AND table_schema='" + database + "' AND NUMERIC_SCALE=0 " +
                            "  UNION  " +
                            "select COLUMN_name,data_type,COLUMN_COMMENT,COLUMN_TYPE,(NUMERIC_PRECISION+NUMERIC_SCALE+1) COLUMN_LENGTH " +
                            "from information_schema.columns where table_name = '" + tableName +
                            "' AND table_schema='" + database + "' AND NUMERIC_SCALE>0" +
                            "  UNION   " +
                            "select COLUMN_name,data_type,COLUMN_COMMENT,COLUMN_TYPE,CHARACTER_MAXIMUM_LENGTH COLUMN_LENGTH " +
                            "from information_schema.columns where table_name = '" + tableName +
                            "' AND table_schema='" + database + "' AND NUMERIC_SCALE is null "
            );
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
                "isDel".equals(column_name) || "id".equals(column_name);
    }


    private TreeMap<String, String> getHelpers(String COLUMN_name) {
        //1-获取冒号:后的注释内容
        String[] columns = COLUMN_name.replaceAll(" ", "").split(":|：| ");

        TreeMap<String, String> helper = new TreeMap<>();
        if (columns.length >= 2) {

            for (int i = 1; i < columns.length; i++) {

                String[] kvs = columns[i].split(",|，");

                for (String kv : kvs) {
                    String[] k_v = kv.split("-");
                    if (k_v.length == 2) {
                        helper.put(k_v[0], k_v[1]);
                    }
                }
            }
        }
        return helper;
    }

}
