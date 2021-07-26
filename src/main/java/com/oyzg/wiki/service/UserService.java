package com.oyzg.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oyzg.wiki.domain.User;
import com.oyzg.wiki.domain.UserExample;
import com.oyzg.wiki.exception.BusinessException;
import com.oyzg.wiki.exception.BusinessExceptionCode;
import com.oyzg.wiki.mapper.UserMapper;
import com.oyzg.wiki.req.UserLoginReq;
import com.oyzg.wiki.req.UserQueryReq;
import com.oyzg.wiki.req.UserResetPasswordReq;
import com.oyzg.wiki.req.UserSaveReq;
import com.oyzg.wiki.resp.PageResp;
import com.oyzg.wiki.resp.UserLoginResp;
import com.oyzg.wiki.resp.UserQueryResp;
import com.oyzg.wiki.util.CopyUtil;
import com.oyzg.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;
    


    public PageResp<UserQueryResp> list(UserQueryReq req) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andNameLike("%"+req.getLoginName()+"%");
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数:{}",pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());

//        List<UserResp> respList = new ArrayList<UserResp>();
//        for (User user : userList) {
////            UserResp userResp = new UserResp();
////            BeanUtils.copyProperties(user,userResp);
//            //对象复制
//            UserResp userResp = CopyUtil.copy(user, UserResp.class);
//            respList.add(userResp);
//        }
        List<UserQueryResp> respList = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setList(respList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }


    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req,User.class);
        if(ObjectUtils.isEmpty(req.getId())) {

            if (ObjectUtils.isEmpty(selectByLoginName(req.getLoginName()))) {
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }

        } else {
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }

    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }


    public void resetPassword(UserResetPasswordReq req) {
        User user = CopyUtil.copy(req,User.class);

        userMapper.updateByPrimaryKeySelective(user);

    }

    public UserLoginResp login(UserLoginReq req) {

        User userDb = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDb)) {
            //用户名不存在
            LOG.info("用户名不存在，{}",req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (userDb.getPassword().equals(req.getPassword())) {
                //登录成功
                UserLoginResp userLoginResp = CopyUtil.copy(userDb, UserLoginResp.class);
                return userLoginResp;
            } else {
                //密码错误
                LOG.info("密码错误，输入密码:{},数据库密码:{}",req.getPassword(),userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }

    }
}
