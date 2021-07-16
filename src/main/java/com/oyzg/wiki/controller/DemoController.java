package com.oyzg.wiki.controller;

import com.oyzg.wiki.domain.Demo;
import com.oyzg.wiki.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController//返回字符串
//@Controller//返回页面
public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping("/demo/list")
    public List<Demo> list() {
        return demoService.list();
    }

}
