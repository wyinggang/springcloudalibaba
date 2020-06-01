package com.wyg.springcloudalibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PaymentMain9011 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9011.class,args);
    }
}