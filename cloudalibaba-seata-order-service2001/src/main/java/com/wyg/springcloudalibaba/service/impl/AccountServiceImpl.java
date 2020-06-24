package com.wyg.springcloudalibaba.service.impl;

import com.wyg.springcloudalibaba.domain.CommonResult;
import com.wyg.springcloudalibaba.service.AccountService;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class AccountServiceImpl implements AccountService {
    @Override
    public CommonResult decrease(Long userId, BigDecimal money) {
        System.out.println("AccountService降级方法");
        return new CommonResult(200,"AccountService降级方法");
    }
}
