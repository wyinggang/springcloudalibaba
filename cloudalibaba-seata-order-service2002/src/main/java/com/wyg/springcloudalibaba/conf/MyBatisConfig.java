package com.wyg.springcloudalibaba.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.wyg.springcloudalibaba.mapper"})
public class MyBatisConfig {

}