package com.systemtraining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SystemTrainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemTrainingApplication.class, args);
    }

}
