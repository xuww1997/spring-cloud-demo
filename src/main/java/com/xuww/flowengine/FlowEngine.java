package com.xuww.flowengine;


import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class FlowEngine {
    /**
     * 引擎执行入口
     */
    public void execute(FlowNode flowNode, RunData runData, Context context) throws Exception {
        Map<String, List<String>> nodeGroup = this.groupByGroupName(flowNode);
        Map<String, FlowNode.NodeConf> nodeMap = flowNode.getNodeMap();

        for (String groupName : nodeGroup.keySet()) {
            boolean needThrowExp = false;
            List<String> nodeNameList = nodeGroup.get(groupName);
            //只有一个node节点的，串行执行
            if (nodeNameList.size() == 1) {
                String nodeName = nodeNameList.get(0);
                FlowNodeInterface detailNode = (FlowNodeInterface) BeanService
                        .getSingleBeanByType(Class.forName(nodeName));
                NodeExecuteTask nodeParallelTask = new NodeExecuteTask(detailNode, runData, context);
                try {
                    Object result = nodeParallelTask.execute();
                    context.getAdaptorMap().put(detailNode.resultKey(), result);
                } catch (Exception e) {
                    needThrowExp = true;
                }

            } else {
                // 多个node节点，并行执行
                List<Future> resultList = Lists.newArrayList();
                List<String> executedNodeNameList = Lists.newArrayList();
                List<NodeExecuteTask> executedNodeList = Lists.newArrayList();
                for (String nodeName : nodeNameList) {
                    FlowNodeInterface detailNode = (FlowNodeInterface) BeanService
                            .getSingleBeanByType(Class.forName(nodeName));
                    NodeExecuteTask nodeParallelTask = new NodeExecuteTask(detailNode, runData, context);
                    executedNodeList.add(nodeParallelTask);
                    executedNodeNameList.add(nodeName);
                    resultList.add(threadPool.submit(nodeParallelTask));
                }
                for (int i = 0; i < resultList.size(); i++) {
                    String nodeName = executedNodeNameList.get(i);
                    String nodeKey = groupName + "_" + nodeName;
                    FlowNodeInterface detailNode = (FlowNodeInterface) BeanService
                            .getSingleBeanByType(Class.forName(nodeName));
                    FlowNode.NodeConf nodeConf = nodeMap.get(nodeKey);
                    int timeout = nodeConf.getTimeout();
                    Future future = resultList.get(i);
                    try {
                        Object o = future.get(timeout,TimeUnit.MILLISECONDS);
                        context.getAdaptorMap().put(detailNode.resultKey(),o);
                    }catch (ExecutionException e){
                        needThrowExp = true;
                    }catch (TimeoutException o){
                        needThrowExp = true;
                    }catch (Exception e){
                        needThrowExp = true;
                    }
                }
            }
            if (needThrowExp){
                throw new RuntimeException();
            }
        }
    }


    /**
     * 流程中的参数参数
     */
    @Getter
    @Setter
    public static class RunData {
        private String paramOne;
        private String paramTwo;
    }

    private Map<String, List<String>> groupByGroupName(FlowNode flowNode) {
        Map<String, List<String>> nodeGroup = Maps.newLinkedHashMap();
        for (String nodeKey : flowNode.getNodeList()) {
            String groupName = this.getGroupName(nodeKey);
            String nodeName = this.getNodeName(nodeKey);
            if (StringUtils.isBlank(groupName)) {
                List<String> nodeNameList = Lists.newArrayList();
                nodeNameList.add(nodeName);
                nodeGroup.put(nodeName, nodeNameList);
            } else {
                List<String> nodeNameList = nodeGroup.get(nodeGroup);
                nodeNameList = nodeNameList == null ? Lists.newArrayList() : nodeNameList;
                nodeNameList.add(nodeName);
                nodeGroup.put(groupName, nodeNameList);
            }
        }
        return nodeGroup;
    }

    private String getGroupName(String nodeKey) {
        String[] arr = nodeKey.split("_");
        return arr.length == 2 ? arr[0] : null;
    }

    private String getNodeName(String nodeKey) {
        String[] arr = nodeKey.split("_");
        return arr.length == 2 ? arr[1] : arr[0];
    }

    public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            5, 10,
            60L,
            TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(500),
            new NamedThreadFactory("engine processor"),
            new ThreadPoolExecutor.AbortPolicy() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {

                    throw new RejectedExecutionException("Task " + r.toString() +
                            " rejected from " +
                            e.toString());
                }
            }
    );

}
