package com.wyg.springcloudalibaba.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wyg.springcloudalibaba.conf.CustomerBlockHandler;
import com.wyg.springcloudalibaba.conf.CustomerFallBackHandler;
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
    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler")
    public String fallback(@PathVariable Long id) {
        String result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, String.class, id);

        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常....");
        } else if (result == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录,空指针异常");
        }
        return result;
    }

    //fallback
    public String handlerFallback(@PathVariable Long id, Throwable e) {
        return new String("兜底异常handlerFallback,exception内容" + e.getClass().getCanonicalName());
    }

    //blockHandler
    public String blockHandler(@PathVariable Long id, BlockException blockException) {
        return new String("blockHandler-sentinel限流,无此流水: blockException  " + blockException.getClass().getCanonicalName());
    }


    // OpenFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    @SentinelResource(value = "feignFallBack", blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException",fallbackClass = CustomerFallBackHandler.class,fallback = "handlerFallback")
    public String paymentSQL(@PathVariable("id") Long id) {

        String result = paymentService.paymentSQL(id);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常....");
        } else if (result == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录,空指针异常");
        }
        return  result;
    }

    /**
     * 在@SentinelResource注解里面配置了fallback方法时，该方法里面通过feign调用其他服务时，发生异常不会走对应降级方法，
     * 会走@SentinelResource指定的fallback方法
     */


    /**
     * blockHandler:
     * 1. 必须是 public
     * 2.返回类型与原方法一致
     * 3. 参数类型需要和原方法相匹配，并在最后加 BlockException 类型的参数。
     * 4. 默认需和原方法在同一个类中。若希望使用其他类的函数，可配置 blockHandlerClass ，并指定blockHandlerClass里面的方法
     *
     * blockHandlerClass:存放blockHandler的类。对应的处理函数必须static修饰，否则无法解析，其他要求：同blockHandler。
     *
     * fallback:用于在抛出异常的时候提供fallback处理逻辑。fallback函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。函数要求：
     * 1. 返回类型与原方法一致
     * 2. 参数类型需要和原方法相匹配，Sentinel 1.6开始，也可在方法最后加 Throwable 类型的参数。
     * 3.默认需和原方法在同一个类中。若希望使用其他类的函数，可配置 fallbackClass ，并指定fallbackClass里面的方法。
     *
     * fallbackClass:存放fallback的类。对应的处理函数必须static修饰，否则无法解析，其他要求：同fallback。
     *
     * exceptionsToIgnore:指定排除掉哪些异常。排除的异常不会计入异常统计，也不会进入fallback逻辑，而是原样抛出
     *
     */

}