package com.oyzg.wiki.controller;

import com.oyzg.wiki.req.EbookReq;
import com.oyzg.wiki.resp.CommonResp;
import com.oyzg.wiki.resp.EbookResp;
import com.oyzg.wiki.resp.PageResp;
import com.oyzg.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController//返回字符串
//@Controller//返回页面
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

}
