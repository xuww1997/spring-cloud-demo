package com.xuww;

import com.xuww.demo.service.IMedalService;
import com.xuww.flowengine.Context;
import com.xuww.flowengine.Flow;
import com.xuww.flowengine.FlowEngine;
import com.xuww.flowengine.FlowNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class SpringCloudDemoApplicationTests {

    @Autowired
    private FlowEngine flowEngine;

    @Test
    void testFlow() throws Exception {
        FlowNode flowNode = Flow.getTestFlow();
        FlowEngine.RunData runData = new FlowEngine.RunData();
        runData.setParamOne("one");
        runData.setParamTwo("two");
        Context context = new Context();
        flowEngine.execute(flowNode,runData,context);
        Map<String,Object> adaptorMap = context.getAdaptorMap();
        //返回值结果
        System.out.println(adaptorMap.get("NodeOne"));
        System.out.println(adaptorMap.get("NodeTwo"));
    }

}
