package com.xuww.flowengine;

public interface FlowNodeInterface<T> {
    /**
     * Node的执行方法
     */
    T invokeNode(FlowEngine.RunData nodeData, Context context);

    /**
     * Node执行完成后执行的方法
     */
    void afterInvoke(FlowEngine.RunData nodeData, Context context);

    /**
     * 从context中获取该node结果的key
     */
    String resultKey();
}
