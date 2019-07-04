package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "前端代码生成器")
public class FrontEndCode {
    @ApiModelProperty(value = "表名")
    private String table;
    @ApiModelProperty(value = "选择生成html文件")
    private int htmlSelect;

    @ApiModelProperty(value = "选择生成js文件")
    private int jsSelect;


    @ApiModelProperty(value = "选择生成ejs文件")
    private int ejsSelect;

    @ApiModelProperty(value = "html文件路径")
    private String htmlPath;

    @ApiModelProperty(value = "js文件路径")
    private String jsPath;

    @ApiModelProperty(value = "ejs文件路径")
    private String ejsPath;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getHtmlSelect() {
        return htmlSelect;
    }

    public void setHtmlSelect(int htmlSelect) {
        this.htmlSelect = htmlSelect;
    }

    public int getJsSelect() {
        return jsSelect;
    }

    public void setJsSelect(int jsSelect) {
        this.jsSelect = jsSelect;
    }

    public int getEjsSelect() {
        return ejsSelect;
    }

    public void setEjsSelect(int ejsSelect) {
        this.ejsSelect = ejsSelect;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public String getJsPath() {
        return jsPath;
    }

    public void setJsPath(String jsPath) {
        this.jsPath = jsPath;
    }

    public String getEjsPath() {
        return ejsPath;
    }

    public void setEjsPath(String ejsPath) {
        this.ejsPath = ejsPath;
    }

    @Override
    public String toString() {
        return "FrontEndCode{" +
                "table='" + table + '\'' +
                ", htmlSelect=" + htmlSelect +
                ", jsSelect=" + jsSelect +
                ", ejsSelect=" + ejsSelect +
                ", htmlPath='" + htmlPath + '\'' +
                ", jsPath='" + jsPath + '\'' +
                ", ejsPath='" + ejsPath + '\'' +
                '}';
    }
}

