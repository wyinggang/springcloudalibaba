package com.wyg.springcloudalibaba.service.impl;

import com.wyg.springcloudalibaba.mapper.AccountMapper;
import com.wyg.springcloudalibaba.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 账户业务实现类
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Resource
    AccountMapper accountDao;

    /**
     * 扣减账户余额
     */
    @Override
    @Transactional
    public void decrease(Long userId, BigDecimal money) {

        LOGGER.info("------->account-service中扣减账户余额开始");
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        accountDao.decrease(userId,money);
        LOGGER.info("------->account-service中扣减账户余额结束");
        //抛出异常，测试在2001服务调用account服务时发生异常服务降级后，会不会回滚
        throw new RuntimeException("我出错了，会被回滚吗？");
    }
}
