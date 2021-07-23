package com.oyzg.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oyzg.wiki.domain.Doc;
import com.oyzg.wiki.domain.DocExample;
import com.oyzg.wiki.mapper.DocMapper;
import com.oyzg.wiki.req.DocQueryReq;
import com.oyzg.wiki.req.DocSaveReq;
import com.oyzg.wiki.resp.DocQueryResp;
import com.oyzg.wiki.resp.PageResp;
import com.oyzg.wiki.util.CopyUtil;
import com.oyzg.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;
    


    public List<DocQueryResp> all() {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        return respList;
    }

    public PageResp<DocQueryResp> list(DocQueryReq req) {

        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        docExample.setOrderByClause("sort asc");
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数:{}",pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());

//        List<DocResp> respList = new ArrayList<DocResp>();
//        for (Doc doc : docList) {
////            DocResp docResp = new DocResp();
////            BeanUtils.copyProperties(doc,docResp);
//            //对象复制
//            DocResp docResp = CopyUtil.copy(doc, DocResp.class);
//            respList.add(docResp);
//        }
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setList(respList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }


    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req,Doc.class);
        if(ObjectUtils.isEmpty(req.getId())) {
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        } else {

            docMapper.updateByPrimaryKey(doc);
        }

    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }
}
