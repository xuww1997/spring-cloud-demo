package com.xuww.flowengine;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class NodeExecuteTask implements Callable {
    private FlowNodeInterface flowNodeInterface;
    private FlowEngine.RunData runData;
    private Context context;

    public Object execute() {
        try {
            Object o = flowNodeInterface.invokeNode(runData, context);
            flowNodeInterface.afterInvoke(runData, context);
            return o;
        }catch (Throwable ex){
            throw ex;
        }

    }

    @Override
    public Object call() throws Exception {
        return this.execute();
    }
}
