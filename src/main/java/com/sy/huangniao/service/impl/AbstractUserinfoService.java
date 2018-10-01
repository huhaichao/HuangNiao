package com.sy.huangniao.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.enums.*;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.UserAccount;
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
     * 创建用户
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional
    public UserInfo createUserInfo(JSONObject jsonObject){
        Integer userid = 0;
        UserInfo userInfo = new UserInfo();
        userInfo.setAppCode(AppCodeEnum.XCX.getCode());
        String userRole = jsonObject.getString("userRole");
        userInfo.setUserRole(userRole);
        userInfo.setUserPhoneno(jsonObject.getString("userPhone"));
        userInfo.setUserWxno(jsonObject.getString("userWxno"));
        userInfo.setUserStatus(UserStatusEnum.WAITAUTHEN.getStatus());
        IDaoService userInfoDao = hnContext.getDaoService(UserInfo.class.getSimpleName());
        int i =userInfoDao.save(userInfo, SqlTypeEnum.DEAFULT);
        if(i!=1){
            log.info("userId={}  userRole={}  appcode ={} 保存用户数据失败",userid,userRole,userInfo.getAppCode());
            throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR) ;
        }
        userid = userInfo.getId();
        log.info("userId={}  userRole={} appcode ={} 保存数据成功",userid,userRole,userInfo.getAppCode());
        //保存用户成功
        UserInfoBody  userInfoBody = new UserInfoBody();
        userInfoBody.setUserId(userInfo.getId());
        userInfoBody.setUserRole(userRole);
        userInfoBody.setUserPhoneno(userInfo.getUserPhoneno());
        userInfoBody.setUserStatus(userInfo.getUserStatus());
        if(!saveRoleInfo(userInfoBody)){
            log.info("userId={} appcode ={} 保存{}数据失败",userid,userRole,userInfo.getAppCode());
            throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR) ;
        }
        log.info("userId={}  appcode ={} 保存{}数据成功",userid,userInfo.getAppCode(),userRole);
        //创建账户
        UserAccount userAccount = new UserAccount();
        userAccount.setStatus(UserAccountStatusEnum.NORMAL.getStatus());
        userAccount.setAmountBalance(0.0);
        AbstractUserAppService abstractUserAppService =hnContext.getAbstractUserAppService(AppCodeEnum.valueOf(userInfo.getAppCode()));
        userAccount.setAccountNo(abstractUserAppService.createUserAcountNo());
        userAccount.setUserId(userInfo.getId());
        IDaoService userAccountDao = hnContext.getDaoService(UserAccount.class.getSimpleName());
        if(userAccountDao.save(userAccount,SqlTypeEnum.DEAFULT)!=1){
            log.info("userId={} userRole={}  appcode ={} 保存用户账户失败",userid,userRole,userInfo.getAppCode());
            throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR) ;
        }
        return  userInfo;
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
     * 保存商户 或者客户信息
     * @param userInfoBody
     * @return
     */
    public abstract boolean  saveRoleInfo(UserInfoBody userInfoBody);


    /**
     * 修改用户信息
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional
    public UserInfoBody updateUserInfo(JSONObject jsonObject) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(jsonObject,userInfo);
        IDaoService iDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
        if(iDaoService.updateObject(userInfo,SqlTypeEnum.DEAFULT)!=1){
            log.info("userId={}  修改用户信息失败",userInfo.getId());
            throw new HNException(RespondMessageEnum.UPDATEUSERINFOERROR) ;
        }
        if(!updateRoleInfo(jsonObject)){
            log.info("userId={}  修改{}信息失败",userInfo.getId(),userInfo.getUserRole());
            throw new HNException(RespondMessageEnum.UPDATEUSERINFOERROR) ;
        }
        return getRoleInfo(userInfo);
    }

    protected abstract boolean updateRoleInfo(JSONObject jsonObject);


    /**
     * 获取商户 或者客户信息
     * @param userInfo
     * @return
     */
    public abstract UserInfoBody  getRoleInfo(UserInfo userInfo);

    /**
     * 实名认证接口
     *
     * @return
     */
    public  boolean  realName (JSONObject jsonObject){
        UserInfoBody userInfoBody = new UserInfoBody();
        BeanUtils.copyProperties(jsonObject,userInfoBody);
        //调用服务接口实名认证 -- 外部接口
        log.info("调用外部接口实名认证 userID {}",userInfoBody.getUserId());
        //todo 实名认证
        log.info("调用外部接口实名认证成功 userID {}",userInfoBody.getUserId());
        //认证成功保存信息
        userInfoBody.setUserImage(jsonObject.getString("identityImage"));
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



