package com.wyg.springcloudalibaba.conf;

import io.seata.rm.datasource.DataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@MapperScan({"com.wyg.springcloudalibaba.mapper"})
public class MyBatisConfig {

    @Bean
    public PlatformTransactionManager txManager(DataSourceProxy dataSource) {
        log.info("初始化事务管理器:PlatformTransactionManager");
        return new DataSourceTransactionManager(dataSource);
    }
}