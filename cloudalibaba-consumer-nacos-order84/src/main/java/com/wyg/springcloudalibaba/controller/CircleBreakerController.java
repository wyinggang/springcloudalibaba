package com.wyg.springcloudalibaba.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wyg.springcloudalibaba.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {

    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;



    @RequestMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback") //没有配置
    //@SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
    //exceptionsToIgnore忽略异常当发生异常时不会调用对应fallback方法,exceptionsToIgnore = {IllegalArgumentException.class}
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler")
    public String fallback(@PathVariable Long id) {
       String result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/"+id, String.class,id);

        if (id == 4) {
            throw new IllegalArgumentException ("IllegalArgumentException,非法参数异常....");
        }else if (result == null) {
            throw new NullPointerException ("NullPointerException,该ID没有对应记录,空指针异常");
        }

        return result;
    }

    //fallback
    public String handlerFallback(@PathVariable Long id, Throwable e) {
        return new String("兜底异常handlerFallback,exception内容"+e.getClass().getCanonicalName());
    }

    //blockHandler
    public String blockHandler(@PathVariable  Long id, BlockException blockException) {
        return new String("blockHandler-sentinel限流,无此流水: blockException  "+blockException.getClass().getCanonicalName());
    }


    // OpenFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public String paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }

}