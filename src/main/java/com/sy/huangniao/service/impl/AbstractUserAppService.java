package com.sy.huangniao.service.impl;

import com.sy.huangniao.common.Util.MD5Utils;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.*;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.UserDeposit;
import com.sy.huangniao.pojo.UserInfo;
import com.sy.huangniao.pojo.UserWxinfo;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.UserAppService;
import com.sy.huangniao.service.pay.IWXPaychannelsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by huchao on 2018/9/24.
 */
@Slf4j
public abstract class AbstractUserAppService implements UserAppService {

    @Autowired
    protected HNContext hnContext;

    @Autowired
    protected Constant constant;

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
        String userId = jsonObject.getString("userId");
        String appCode = jsonObject.getString("appCode");
        JSONObject result = unifiedorder(jsonObject);
        //保存充值信息
        UserDeposit userDeposit = new UserDeposit();
        userDeposit.setAppCode(appCode);
        userDeposit.setUserId(Integer.parseInt(userId));
        userDeposit.setCreateDate(new Date());
        userDeposit.setOrderNo(jsonObject.getString("orderNo"));
        userDeposit.setAmount(jsonObject.getDouble("amount"));
        userDeposit.setIp(jsonObject.getString("termIp"));
        userDeposit.setPrepayId(result.getString("prepay_id"));
        userDeposit.setTradeType(result.getString("trade_type"));
        userDeposit.setDepositNo(result.getString("outTradeNo"));
        userDeposit.setStatus(WalletStatusEnum.DEPOSITING.getStatus());
        IDaoService iDaoService = hnContext.getDaoService(UserDeposit.class.getSimpleName());
        iDaoService.save(userDeposit,SqlTypeEnum.DEAFULT);
        return  handleUnifiedorder(result);
    }

    /**
     * 统一下单返回数据加工
     * @param result
     * @return
     */
    protected abstract JSONObject handleUnifiedorder(JSONObject result);

    /**
     * 统一下单业务处理
     * @param jsonObject
     * @return
     */
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
