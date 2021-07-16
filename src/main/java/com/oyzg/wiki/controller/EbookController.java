package com.oyzg.wiki.controller;

import com.oyzg.wiki.domain.Ebook;
import com.oyzg.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController//返回字符串
//@Controller//返回页面
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/ebook/list")
    public List<Ebook> list() {
        return ebookService.list();
    }

}
