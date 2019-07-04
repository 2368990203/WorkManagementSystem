package team.work;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2 {
    @Value("${swagger2.enable}")
    private boolean swagger2Enable;

    @Bean
    public Docket systemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .groupName("admin-api")
                .apiInfo(systemInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("team.work.api.admin"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket unifiedApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .groupName("unified-api")
                .apiInfo(unifiedInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("team.work.api.unified"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo systemInfo() {
        return new ApiInfoBuilder().title("作业PC 授权API管理")
                .description("  ")
                .termsOfServiceUrl("http://soft.gxun.edu.cn")
                .contact("Party")
                .version("0.11.2")
                .build();
    }

    private ApiInfo unifiedInfo() {
        return new ApiInfoBuilder().title("作业PC 通用API管理")
                .description("  ")
                .termsOfServiceUrl("http://soft.gxun.edu.cn")
                .contact("Party")
                .version("0.11.2")
                .build();
    }
}
