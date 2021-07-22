package com.oyzg.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oyzg.wiki.domain.Category;
import com.oyzg.wiki.domain.CategoryExample;
import com.oyzg.wiki.mapper.CategoryMapper;
import com.oyzg.wiki.req.CategoryQueryReq;
import com.oyzg.wiki.req.CategorySaveReq;
import com.oyzg.wiki.resp.CategoryQueryResp;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;
    


    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {

        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数:{}",pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());

//        List<CategoryResp> respList = new ArrayList<CategoryResp>();
//        for (Category category : categoryList) {
////            CategoryResp categoryResp = new CategoryResp();
////            BeanUtils.copyProperties(category,categoryResp);
//            //对象复制
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//            respList.add(categoryResp);
//        }
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setList(respList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }


    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req,Category.class);
        if(ObjectUtils.isEmpty(req.getId())) {
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {

            categoryMapper.updateByPrimaryKey(category);
        }

    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
