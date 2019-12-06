package vn.fit.hcmus.truyenfulldata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
public class TruyenfulldataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TruyenfulldataApplication.class, args);
    }

}
