package com.xuww.flowengine;

public class Flow{
        private static FlowNode testFlow = new FlowNode();
        static {
            testFlow.add(NodeOne.class,new FlowNode.NodeConf());
            testFlow.add(NodeTwo.class,new FlowNode.NodeConf());
            testFlow.add("three",NodeOne.class,new FlowNode.NodeConf());
            testFlow.add("three",NodeTwo.class,new FlowNode.NodeConf());
        }

        public static FlowNode getTestFlow(){
            return testFlow;
        }
    }