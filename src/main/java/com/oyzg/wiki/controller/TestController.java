package com.oyzg.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController//返回字符串
//@Controller//返回页面
public class TestController {

    @GetMapping("/hello")
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello world!";
    }
    @PostMapping("/hello/post")
    public String hello(String name) {
        return "Hello world!" + name;
    }


}
