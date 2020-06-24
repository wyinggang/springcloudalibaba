package com.wyg.springcloudalibaba.service;


import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public String paymentSQL(Long id) {
        return new String("服务降级返回,---PaymentFallbackService");
    }
}