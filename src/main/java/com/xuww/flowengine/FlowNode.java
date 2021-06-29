package com.xuww.flowengine;

import com.google.common.collect.Maps;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class FlowNode {
    private Map<String, NodeConf> nodeMap = Maps.newLinkedHashMap();

    public void add(String groupName, Class nodeName, NodeConf nodeConf) {
        String key = null;
        if (StringUtils.isNotBlank(groupName)) {
            key = groupName + "_" + nodeName.getName();
        } else {
            key = nodeName.getName();
        }
        if (nodeMap.containsKey(key)){
            return;
        }
        nodeMap.put(key, nodeConf);
    }

    public void add(Class nodeName, NodeConf nodeConf) {
        this.add(nodeName.getName(), nodeName, nodeConf);
    }

    public void replace(String groupName, Class nodeName, NodeConf nodeConf){
        String key = null;
        if (StringUtils.isNotBlank(groupName)) {
            key = groupName + "_" + nodeName.getName();
        } else {
            key = nodeName.getName();
        }
        nodeMap.put(key, nodeConf);
    }

    public void replace(Class nodeName, NodeConf nodeConf){
        this.replace(nodeName.getName(),nodeName,nodeConf);
    }

    public void remove(String groupName,Class nodeName){
        String key = null;
        if (StringUtils.isNotBlank(groupName)) {
            key = groupName + "_" + nodeName.getName();
        } else {
            key = nodeName.getName();
        }
        nodeMap.remove(key);
    }

    public void remove(Class nodeName){
        this.remove(nodeName.getName(),nodeName);
    }

    public Set<String> getNodeList(){
        return nodeMap.keySet();
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NodeConf {
        private int timeout = 100;
    }

}
