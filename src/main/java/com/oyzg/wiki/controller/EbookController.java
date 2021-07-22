package com.oyzg.wiki.controller;

import com.oyzg.wiki.req.EbookQueryReq;
import com.oyzg.wiki.req.EbookSaveReq;
import com.oyzg.wiki.resp.CommonResp;
import com.oyzg.wiki.resp.EbookQueryResp;
import com.oyzg.wiki.resp.PageResp;
import com.oyzg.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController//返回字符串
//@Controller//返回页面
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }

}
