package com.sy.huangniao.service.impl;

import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.Util.MD5Utils;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.enums.UserStatusEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.UserDeposit;
import com.sy.huangniao.pojo.UserInfo;
import com.sy.huangniao.pojo.UserWithdraw;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huchao on 2018/9/14.
 */
@Slf4j
public abstract class  AbstractUserinfoService implements UserInfoService{

    @Autowired
    protected HNContext hnContext;


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


    @Override
    public  boolean  deposit(UserDeposit userDeposit){
        return  false;
    }


    @Override
    public  boolean  withdraw(UserWithdraw userWithdraw){
        return  true;
    }
}



