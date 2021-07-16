package com.oyzg.wiki.controller;

import com.oyzg.wiki.domain.Test;
import com.oyzg.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

}
