package com.xuww;

import com.xuww.demo.service.IMedalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class SpringCloudDemoApplicationTests {
    @Autowired
    private Map<String, IMedalService> medalServiceMap;

    @Test
    void contextLoads() {
        medalServiceMap.get("guest").showMedal();
    }

}
