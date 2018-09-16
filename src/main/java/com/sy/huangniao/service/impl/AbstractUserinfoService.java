package com.sy.huangniao.service.impl;

import com.sy.huangniao.common.UserInfoBody;
import com.sy.huangniao.common.Util.MD5Utils;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.enums.UserStatusEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.UserInfo;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by huchao on 2018/9/14.
 */
@Slf4j
public abstract class  AbstractUserinfoService implements UserInfoService{

    @Autowired
    protected HNContext hnContext;


    /**
     * 注册服务
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public UserInfoBody registry(UserInfo userInfo){
        log.info("用户注册信息 phone{} ...",userInfo.getUserPhoneno());
        String pwd = userInfo.getPassword();
        //密码md5计算
        String pwdMd5 = MD5Utils.getMD5String(pwd);
        userInfo.setPassword(pwdMd5);
        userInfo.setUserStatus(UserStatusEnum.WAITAUTHEN.getStatus());
        IDaoService iDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
        int count =  iDaoService.save(userInfo,null);
        UserInfoBody userInfoBody = new UserInfoBody();
        BeanUtils.copyProperties(userInfo,userInfoBody);
        if (count == 1){
           if(!saveRoleInfo(userInfoBody)){
                log.info("用户注册信息 {}",userInfoBody);
                throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
           }
        }else {
                log.info("用户注册信息{}",userInfo);
                throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
        }
        userInfoBody.setUserId(userInfo.getId());
        handleUserInfoBody(userInfoBody);
        log.info("用户注册信息 userid{} ...",userInfo.getId());
        return  userInfoBody;
    }

    /**
     * 保存商户 或者客户信息
     * @param userInfoBody
     * @return
     */
    public abstract boolean  saveRoleInfo(UserInfoBody userInfoBody);


    /**
     * 获取商户 或者客户信息
     * @param userInfo
     * @return
     */
    public abstract UserInfoBody  getRoleInfo(UserInfo userInfo);

    /**
     * 登陆服务
     * @return
     */
    @Override
    public  UserInfoBody  login (UserInfo userInfo){
        log.info("用户登录 phoneNo{}.....",userInfo.getUserPhoneno());
        String pwd = userInfo.getPassword();
        userInfo.setPassword(null);
        IDaoService iDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
        UserInfo  userInfoResult = (UserInfo) iDaoService.selectObject(userInfo,null);
        UserInfoBody userInfoBody = new UserInfoBody();
        //校验密码
        if(!MD5Utils.getMD5String(pwd).equals(userInfoResult.getPassword())){
            log.info("用户登录密码错误");
            throw new HNException(RespondMessageEnum.PASSWORDOERROR);
        }
        //是否认证
        if (UserStatusEnum.AUTHENED.getStatus().equals(userInfoResult.getUserStatus())){
            userInfoBody = getRoleInfo(userInfoResult);
        }
        BeanUtils.copyProperties(userInfo,userInfoBody);

        handleUserInfoBody(userInfoBody);
        log.info("用户登录成功......");
        return  userInfoBody;
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Override
    public UserInfoBody  getUserInfo(Integer userId){

        log.info("获取用户信息userid... {}",userId);
        IDaoService iDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo = (UserInfo)iDaoService.selectObject(userInfo,null);
        UserInfoBody userInfoBody= getRoleInfo(userInfo);
        BeanUtils.copyProperties(userInfo,userInfoBody);

        handleUserInfoBody(userInfoBody);
        log.info("获取用户信息成功信息脱敏userInfo... {}",userInfoBody);
        return  userInfoBody;
    }

    /**
     * 实名认证接口
     * @param userId
     * @param userName
     * @param userIdentity
     * @param identityImage
     * @return
     */
    public  boolean  realName (Integer userId,String userName,String userIdentity,String identityImage){

        //调用服务接口实名认证 -- 外部接口
        log.info("调用外部接口实名认证 userID {}",userId);
        //todo 实名认证
        log.info("调用外部接口实名认证成功 userID {}",userId);
        //认证成功保存信息
        UserInfoBody userInfoBody = new UserInfoBody();
        userInfoBody.setUserId(userId);
        userInfoBody.setRealName(userName);
        userInfoBody.setUserIdentity(userIdentity);
        userInfoBody.setUserImage(identityImage);
        userInfoBody.setUserStatus(UserStatusEnum.AUTHENED.getStatus());
        if(!saveRoleInfo(userInfoBody)){
            log.info("保存用户信息失败 {}",userInfoBody);
            throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
        }

        return  true;
    }


    /**
     * 信息脱敏处理
     * @param userInfoBody
     * @return
     */
    public UserInfoBody  handleUserInfoBody(UserInfoBody userInfoBody){
        userInfoBody.setUserIdentity(StringUtils.handleIdentity(userInfoBody.getUserIdentity()));
        userInfoBody.setRealName(StringUtils.handleRealName(userInfoBody.getRealName()));
        userInfoBody.setUserPhoneno(StringUtils.handlePhoneNo(userInfoBody.getUserPhoneno()));
        return  userInfoBody;
    }

}



