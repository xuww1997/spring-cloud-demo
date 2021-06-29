package com.xuww.flowengine;

import com.google.common.collect.Maps;

import java.util.Map;

public class Context {
    /*
    结果缓存
     */
    private Map<String,Object> resultMap = Maps.newHashMap();

    public Map<String,Object> getAdaptorMap(){
        return resultMap;
    }

    public void setAdaptorMap(Map<String,Object> resultMap){
        this.resultMap = resultMap;
    }
}
