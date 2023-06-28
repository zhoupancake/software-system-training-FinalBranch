package com.systemtraining.test;

import com.systemtraining.SystemTrainingApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
class MainTests {
    @Test
    void testMain() {
        String[] args = new String[0];
        log.info(Arrays.toString(args));
        System.setProperty("server.port", "0"); // 使用随机可用端口
        SystemTrainingApplication.main(args);
    }
}
