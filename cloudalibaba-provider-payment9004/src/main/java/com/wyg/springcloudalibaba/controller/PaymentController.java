package com.wyg.springcloudalibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, String> hashMap = new HashMap<>();

    static {
        hashMap.put(1L, "111111111111128a8c1e3bc2742d8848569891fb42181");
        hashMap.put(2L, "2222222222222bba8c1e3bc2742d8848569891ac32182");
        hashMap.put(3L, "33333333333336ua8c1e3bc2742d8848569891xt92183");
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public String paymentSQL(@PathVariable("id") Long id) {
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常....");
        }
        String payment = hashMap.get(id);
        return serverPort+"--->"+payment;
    }
}