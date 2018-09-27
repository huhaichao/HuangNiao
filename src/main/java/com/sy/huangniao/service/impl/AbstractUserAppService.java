package com.sy.huangniao.service.impl;

import com.sy.huangniao.common.Util.MD5Utils;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.enums.UserStatusEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.UserInfo;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.UserAppService;
import com.sy.huangniao.service.pay.IWXPaychannelsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huchao on 2018/9/24.
 */
@Slf4j
public abstract class AbstractUserAppService implements UserAppService {

    @Autowired
    protected HNContext hnContext;

    /**
     * 登陆服务
     * @return
     */
    @Override
    public JSONObject login (JSONObject jsonObject ){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(jsonObject,userInfo);
        log.info("用户登录 phoneNo{}.....",userInfo.getUserPhoneno());
        String pwd = userInfo.getPassword();
        userInfo.setPassword(null);
        IDaoService iDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
        UserInfo  userInfoResult = (UserInfo) iDaoService.selectObject(userInfo,null);
        UserInfoBody userInfoBody = new UserInfoBody();

        AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(userInfo.getUserRole());
        //校验密码
        if(!MD5Utils.getMD5String(pwd).equals(userInfoResult.getPassword())){
            log.info("用户登录密码错误");
            throw new HNException(RespondMessageEnum.PASSWORDOERROR);
        }
        //是否认证
        if (UserStatusEnum.AUTHENED.getStatus().equals(userInfoResult.getUserStatus())){
            userInfoBody = abstractUserinfoService.getRoleInfo(userInfoResult);
        }
        BeanUtils.copyProperties(userInfo,userInfoBody);

        abstractUserinfoService.handleUserInfoBody(userInfoBody);
        log.info("用户登录成功......");
        JSONObject json=  JSONObject.fromObject(userInfoBody);
        return  json;
    }

    /**
     * 注册服务
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public JSONObject registry(JSONObject jsonObject){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(jsonObject,userInfo);
        log.info("用户注册信息 phone{} ...",userInfo.getUserPhoneno());
        String pwd = userInfo.getPassword();
        //密码md5计算
        String pwdMd5 = MD5Utils.getMD5String(pwd);
        userInfo.setPassword(pwdMd5);
        userInfo.setUserStatus(UserStatusEnum.WAITAUTHEN.getStatus());
        IDaoService iDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
        AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(userInfo.getUserRole());
        int count =  iDaoService.save(userInfo,null);
        UserInfoBody userInfoBody = new UserInfoBody();
        BeanUtils.copyProperties(userInfo,userInfoBody);
        if (count == 1){
            if(!abstractUserinfoService.saveRoleInfo(userInfoBody)){
                log.info("用户注册信息 {}",userInfoBody);
                throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
            }
        }else {
            log.info("用户注册信息{}",userInfo);
            throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
        }
        userInfoBody.setUserId(userInfo.getId());
        abstractUserinfoService.handleUserInfoBody(userInfoBody);
        log.info("用户注册信息 userid{} ...",userInfo.getId());
        JSONObject json=  JSONObject.fromObject(userInfoBody);
        return  json;
    }

    @Override
    public  JSONObject  deposit(JSONObject jsonObject){
        /*String userId = jsonObject.getString("userId");
         userInfo = new UserInfo();
        IDaoService iDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
        iDaoService.selectObject()*/


        //保存充值信息

        return  null;
    }

    public abstract  JSONObject  unifiedorder(JSONObject jsonObject);



    @Override
    public  JSONObject  payQuery(JSONObject jsonObject){
        /**
         * 调用预下单接口
         */
        IWXPaychannelsService iwxPaychannelsService= hnContext.getIWXPaychannelsService(AppCodeEnum.valueOf(jsonObject.getString("appCode")));
        JSONObject orderResult =iwxPaychannelsService.orderquery(jsonObject);

        //成功修改充值信息

        //是否是订单支付--修改订单支付信息

        return  null;
    }



    @Override
    public  JSONObject  withdraw(JSONObject jsonObject){
        return  null;
    }

}
