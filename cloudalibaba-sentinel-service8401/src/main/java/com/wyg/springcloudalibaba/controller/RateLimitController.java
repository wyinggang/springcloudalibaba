package com.wyg.springcloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public String byResource() {
        return "按资源名称限流测试OK";
    }

    public String handleException(BlockException exception) {
        return "服务不可用"+exception.getClass().getCanonicalName()+"";
    }


    @GetMapping("/retaLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public String byUrl()
    {
        return  "按url限流测试OK";
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException2")
    public String customerBlockHandler()
    {
        return "按客戶自定义";
    }
}