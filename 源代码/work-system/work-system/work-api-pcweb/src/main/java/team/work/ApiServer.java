package team.work;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("team.work.core.mapper")
public class ApiServer {
    public static void main(String[] args) {
        SpringApplication.run(ApiServer.class, args);
        //启动阅读数工作者
        //new HitsWorker().run();
    }
}
