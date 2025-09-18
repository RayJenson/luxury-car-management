package cn.xzlei;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@Service
@SpringBootApplication
@MapperScan("cn.xzlei.mapper")
public class CarManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarManagerApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }
}
