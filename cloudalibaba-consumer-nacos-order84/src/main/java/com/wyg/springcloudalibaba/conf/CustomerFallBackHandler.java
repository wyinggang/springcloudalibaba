package com.wyg.springcloudalibaba.conf;

import org.springframework.web.bind.annotation.PathVariable;

public class CustomerFallBackHandler {
    //fallback
    public static String handlerFallback(@PathVariable Long id, Throwable e) {
        return new String("兜底异常handlerFallback,exception内容" + e.getClass().getCanonicalName());
    }
}
