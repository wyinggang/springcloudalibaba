package com.wyg.springcloudalibaba.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class CustomerBlockHandler {
    public static String handlerException(BlockException ex){
        return "自定义兜底方法";
    }
    public static String handlerException2(BlockException ex){
        return "自定义兜底方法";
    }
}
