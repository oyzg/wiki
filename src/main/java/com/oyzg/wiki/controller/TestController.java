package com.oyzg.wiki.controller;

import com.oyzg.wiki.domain.Test;
import com.oyzg.wiki.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController//返回字符串
//@Controller//返回页面
public class TestController {

    //对应配置文件application.properties中的test.hello=Hello
    @Value("${test.hello}")
    private String testHello;

    @Resource
    private TestService testService;

    @GetMapping("/hello")
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello world!";
    }
    @PostMapping("/hello/post")
    public String hello(String name) {
        return "Hello world!" + name;
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }

    @Resource
    RedisTemplate redisTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
        LOG.info("key: {}, value: {}", key, value);
        return "success";
    }

    @RequestMapping("/redis/get/{key}")
    public Object get(@PathVariable String key) {
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key: {}, value: {}", key, object);
        return object;
    }

}
