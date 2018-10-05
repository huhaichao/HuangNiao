package com.sy.huangniao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.common.Util.MD5Utils;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.bo.RequestBody;
import com.sy.huangniao.common.bo.RespondBody;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.UserLinkman;
import com.sy.huangniao.service.customer.TicketCustomerService;
import com.sy.huangniao.service.impl.AbstractUserAppService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by huchao on 2018/9/14.
 */
@Slf4j
@RestController
public class UserInfoController {

    @Autowired
    private HNContext hnContext;

    @Autowired
    private Constant constant;

    @Autowired
    private TicketCustomerService ticketCustomerServiceImpl;


    /**
     * 目前小程序不需要--暂不开放
     */
    public void registry(){};

    /**
     * 登陆
     */
    @PostMapping(value="/api/v1/user/login",produces = {"application/json;charset=utf-8"})
    public RespondBody login(RequestBody requestBody){
       try {
           log.info("requestBody={} login......",requestBody);
           AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(AppCodeEnum.valueOf(requestBody.getAppCode()));
           JSONObject json = JSONObject.parseObject(requestBody.getData());
           String sign = json.getString("sign");
           if(StringUtils.isEmpty(sign)){
               log.info("requestBody={}  login 没有签名..... ",requestBody);
               return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
           }
           json.remove("sign");
           if(!MD5Utils.checkEncryption(json,constant.getUSERLOGINSIGNKEY(),sign)){
               log.info("requestBody={}  login 签名校验失败..... ",requestBody);
               return new RespondBody(RespondMessageEnum.SIGNERROR);
           }
           json.put("userRole",requestBody.getUserRole());
           json.put("appCode",requestBody.getAppCode());
           JSONObject jsonObject = abstractUserAppService.login(json);
           MD5Utils.encryption(jsonObject,constant.getUSERLOGINSIGNKEY());
           return new RespondBody(RespondMessageEnum.SUCCESS,jsonObject);
       }catch (HNException e){
           log.info("requestBody={} login exception code={} msg={}",requestBody,e.getCode(),e.getMsg());
           return new RespondBody(e.getRespondMessageEnum());
       }catch (Exception e){
           log.info("requestBody={} login exception={}",requestBody,e.getMessage());
           return new RespondBody(RespondMessageEnum.EXCEPTION);
       }
    }

    /**
     * 获取用户个人信息
     */
    @PostMapping(value="/api/v1/user/getUserInfo",produces = {"application/json;charset=utf-8"})
    public RespondBody getUserInfo(RequestBody requestBody){
        try {
            log.info("requestBody={} getUserInfo......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            UserInfoBody userInfoBody = abstractUserinfoService.getUserInfo(Integer.parseInt(requestBody.getUserId()));
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userInfoBody);
            MD5Utils.encryption(jsonObject,constant.getUSERLOGINSIGNKEY());
            return new RespondBody(RespondMessageEnum.SUCCESS,jsonObject);
        }catch (HNException e){
            log.info("requestBody={} getUserInfo exception code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} getUserInfo exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    /**
     * 获取修改个人信息
     */
    @PostMapping(value="/api/v1/user/updateUserInfo",produces = {"application/json;charset=utf-8"})
    public RespondBody updateUserInfo(RequestBody requestBody){
        try {
            log.info("requestBody={} updateUserInfo......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
            String sign = jsonObject.getString("sign");
            if(StringUtils.isEmpty(sign)){
                log.info("requestBody={}  login 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            jsonObject.remove("sign");
            if(!MD5Utils.checkEncryption(jsonObject,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("requestBody={}  deposit 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("id",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            UserInfoBody userInfoBody = abstractUserinfoService.updateUserInfo(jsonObject);
            return new RespondBody(RespondMessageEnum.SUCCESS,userInfoBody);
        }catch (HNException e){
            log.info("requestBody={} updateUserInfo exception code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} updateUserInfo exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    /**
     * 下单
     */
    @PostMapping(value="/api/v1/user/createOrder",produces = {"application/json;charset=utf-8"})
    public RespondBody createOrder(RequestBody requestBody){
        try {
            log.info("requestBody={} createOrder......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject = (JSONObject)JSON.parse(requestBody.getData());
            String sign = jsonObject.getString("sign");
            String ticketDetails =jsonObject.getString("ticketDetails");
            if(StringUtils.isEmpty(sign)){
                log.info("requestBody={}  login 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            jsonObject.remove("sign");
            jsonObject.remove("ticketDetails");
            Map<String,String> maps =(Map) JSON.parse(jsonObject.toJSONString());
            maps.put("ticketDetails",ticketDetails);
            if(!MD5Utils.checkEncryption(maps,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("requestBody={}  deposit 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            jsonObject.put("ticketDetails", JSONArray.parseArray(ticketDetails));
            JSONObject result =abstractUserinfoService.createOrder(jsonObject);
            return new RespondBody(RespondMessageEnum.SUCCESS,result);
        }catch (HNException e){
            log.info("requestBody={} createOrder  code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} createOrder exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    @PostMapping(value="/api/v1/user/payOrder",produces = {"application/json;charset=utf-8"})
    public RespondBody payOrder(RequestBody requestBody){
        try {
            log.info("requestBody={} payOrder......",requestBody);
            JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
            String sign = jsonObject.getString("sign");
            if(StringUtils.isEmpty(sign)){
                log.info("requestBody={}  login 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            jsonObject.remove("sign");
            if(!MD5Utils.checkEncryption(jsonObject,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("requestBody={}  deposit 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            //调用充值接口
            AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(AppCodeEnum.valueOf(requestBody.getAppCode()));
            JSONObject result = abstractUserAppService.deposit(jsonObject);
            return new RespondBody(RespondMessageEnum.SUCCESS,result);
        }catch (HNException e){
            log.info("requestBody={} payOrder  code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} payOrder exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }



    /**
     * 添加联系人
     */
    @PostMapping(value="/api/v1/user/addContacts",produces = {"application/json;charset=utf-8"})
    public  RespondBody addContacts(RequestBody requestBody){
        try {
            log.info("requestBody={} addContacts......",requestBody);
            JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
            String sign = jsonObject.getString("sign");
            if(StringUtils.isEmpty(sign)){
                log.info("addContacts={}  addContacts 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            jsonObject.remove("sign");
            if(!MD5Utils.checkEncryption(jsonObject,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("addContacts={}  addContacts 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            if(ticketCustomerServiceImpl.addContacts(jsonObject))
                return new RespondBody(RespondMessageEnum.SUCCESS);
            else
                return new RespondBody(RespondMessageEnum.ADDCONTACTS_FAIL);
        }catch (HNException e){
            log.info("requestBody={} addContacts exception code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} addContacts exception={}",requestBody,e.getMessage());
            if(e.getMessage().indexOf("SQLIntegrityConstraintViolationException")>0){
                return new RespondBody(RespondMessageEnum.ADDCONTACTS_REPEAT);
            }
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }

    }

    /**
     * 添加联系人
     */
    @PostMapping(value="/api/v1/user/selectContacts",produces = {"application/json;charset=utf-8"})
    public  RespondBody selectContacts(RequestBody requestBody){
        try {
            log.info("requestBody={} selectContacts......",requestBody);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",requestBody.getUserId());
            List<UserLinkman> list =ticketCustomerServiceImpl.selectContacts(jsonObject);
            return new RespondBody(RespondMessageEnum.SUCCESS,list);
        }catch (HNException e){
            log.info("requestBody={} selectContacts exception code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} selectContacts exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }

    }

    /**
     * 确认订单
     */
    @PostMapping(value="/api/v1/user/confirmeOrder",produces = {"application/json;charset=utf-8"})
    public RespondBody  confirmeOrder(RequestBody requestBody){
        try {
            log.info("requestBody={} confirmeOrder......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
            String sign = jsonObject.getString("sign");
            if(StringUtils.isEmpty(sign)){
                log.info("requestBody={}  login 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            jsonObject.remove("sign");
            if(!MD5Utils.checkEncryption(jsonObject,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("requestBody={}  deposit 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            if(abstractUserinfoService.confirmeOrder(jsonObject))
                return new RespondBody(RespondMessageEnum.SUCCESS);
            else
                return new RespondBody(RespondMessageEnum.CREATORDERFAIL);
        }catch (HNException e){
            log.info("requestBody={} confirmeOrder exception code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} confirmeOrder exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    /**
     * 获取订单列表
     * @return
     */
    @PostMapping(value="/api/v1/user/getOrderList",produces = {"application/json;charset=utf-8"})
    public RespondBody getOrderList(RequestBody requestBody){
        try {
            log.info("requestBody={} getOrderList......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
            String sign = jsonObject.getString("sign");
            if(StringUtils.isEmpty(sign)){
                log.info("requestBody={}  login 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            jsonObject.remove("sign");
            if(!MD5Utils.checkEncryption(jsonObject,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("requestBody={}  deposit 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            JSONObject result =abstractUserinfoService.getOrderList(jsonObject);
            return new RespondBody(RespondMessageEnum.SUCCESS,result);
        }catch (HNException e){
            log.info("requestBody={} getOrderList exception code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} getOrderList exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    /**
     * 取消订单
     * @param
     * @return
     */
    @PostMapping(value="/api/v1/user/cancleOrder",produces = {"application/json;charset=utf-8"})
    public  RespondBody   cancleOrder (RequestBody requestBody){try {
        log.info("requestBody={} cancleOrder......",requestBody);
        AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
        JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
        String sign = jsonObject.getString("sign");
        if(StringUtils.isEmpty(sign)){
            log.info("requestBody={}  login 没有签名..... ",requestBody);
            return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
        }
        jsonObject.remove("sign");
        if(!MD5Utils.checkEncryption(jsonObject,constant.getUSERLOGINSIGNKEY(),sign)){
            log.info("requestBody={}  deposit 签名校验失败..... ",requestBody);
            return new RespondBody(RespondMessageEnum.SIGNERROR);
        }
        jsonObject.put("userId",requestBody.getUserId());
        jsonObject.put("userRole",requestBody.getUserRole());
        jsonObject.put("appCode",requestBody.getAppCode());
       // jsonObject.put("userId",requestBody.getUserId());
       // jsonObject.put("userRole",requestBody.getUserRole());
        if(abstractUserinfoService.cancleOrder(Integer.parseInt(requestBody.getUserId()),jsonObject.getIntValue("orderId")))
            return new RespondBody(RespondMessageEnum.SUCCESS);
        else
            return new RespondBody(RespondMessageEnum.CANCLEORDERFAIL);
    }catch (HNException e){
        log.info("requestBody={} cancleOrder exception code={} msg={}",requestBody,e.getCode(),e.getMsg());
        return new RespondBody(e.getRespondMessageEnum());
    }catch (Exception e){
        log.info("requestBody={} cancleOrder exception={}",requestBody,e.getMessage());
        return new RespondBody(RespondMessageEnum.EXCEPTION);
    }}

    /**
     *
     * 充值
     * @param
     * @return
     */
    @PostMapping(value="/api/v1/user/deposit",produces = {"application/json;charset=utf-8"})
    public  RespondBody  deposit(RequestBody requestBody){
         try {
                log.info("requestBody={} deposit......",requestBody);
                JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
                String sign = jsonObject.getString("sign");
                 if(StringUtils.isEmpty(sign)){
                     log.info("requestBody={}  login 没有签名..... ",requestBody);
                     return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
                 }
                jsonObject.remove("sign");
                if(!MD5Utils.checkEncryption(jsonObject,constant.getUSERLOGINSIGNKEY(),sign)){
                    log.info("requestBody={}  deposit 签名校验失败..... ",requestBody);
                    return new RespondBody(RespondMessageEnum.SIGNERROR);
                }
                jsonObject.put("userId",requestBody.getUserId());
                jsonObject.put("userRole",requestBody.getUserRole());
                jsonObject.put("appCode",requestBody.getAppCode());
                //调用充值接口
                AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(AppCodeEnum.valueOf(requestBody.getAppCode()));
                JSONObject result = abstractUserAppService.deposit(jsonObject);
                return new RespondBody(RespondMessageEnum.SUCCESS,result);
            }catch (HNException e){
                log.info("requestBody={} deposit  code={} msg={}",requestBody,e.getCode(),e.getMsg());
                return new RespondBody(e.getRespondMessageEnum());
            }catch (Exception e){
                log.info("requestBody={} deposit exception={}",requestBody,e.getMessage());
                return new RespondBody(RespondMessageEnum.EXCEPTION);
            }
   }


    /**
     *
     * 支付结果查询
     * @param
     * @return
     */
    @PostMapping(value="/api/v1/user/payQuery",produces = {"application/json;charset=utf-8"})
    public  RespondBody  payQuery(RequestBody requestBody){
        try {
            log.info("requestBody={} payQuery......",requestBody);
            JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
            String sign = jsonObject.getString("sign");
            if(StringUtils.isEmpty(sign)){
                log.info("requestBody={}  login 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            jsonObject.remove("sign");
            if(!MD5Utils.checkEncryption(jsonObject,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("requestBody={}  deposit 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            //调用充值接口
            AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(AppCodeEnum.valueOf(requestBody.getAppCode()));
            JSONObject result = abstractUserAppService.payQuery(jsonObject);
            return new RespondBody(RespondMessageEnum.SUCCESS,result);
        }catch (HNException e){
            log.info("requestBody={} payQuery  code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} payQuery exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }
    /**
     *
     * 提现接口
     * @param
     * @return
     */
    @PostMapping(value="/api/v1/user/withdraw",produces = {"application/json;charset=utf-8"})
    public RespondBody withdraw(RequestBody requestBody){return  null;}

    /**
     * 手机号验证接口
     */
    @PostMapping(value="/api/v1/user/checkPhoneCode",produces = {"application/json;charset=utf-8"})
    public RespondBody checkPhoneCode(RequestBody requestBody){
        try {
            log.info("requestBody={} checkPhoneCode",requestBody);
            JSONObject json = JSONObject.parseObject(requestBody.getData());
            String sign = json.getString("sign");
            if(StringUtils.isEmpty(sign)){
                log.info("requestBody={}  checkPhoneCode 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            json.remove("sign");
            if(!MD5Utils.checkEncryption(json,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("requestBody={}  checkPhoneCode 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            json.put("userId",requestBody.getUserId());
            json.put("userRole",requestBody.getUserRole());
            json.put("appCode",requestBody.getAppCode());
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            abstractUserinfoService.checkPhoneCode(json);
            return  new RespondBody(RespondMessageEnum.SUCCESS);
        }catch (HNException e){
            log.info("requestBody={} checkPhoneCode  code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} checkPhoneCode exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    /**
     * 发送验证码接口
     */
    @PostMapping(value="/api/v1/user/sendPhoneCode",produces = {"application/json;charset=utf-8"})
    public RespondBody sendPhoneCode(RequestBody requestBody){
        try {
            log.info("requestBody={} sendPhoneCode",requestBody);
            JSONObject json = JSONObject.parseObject(requestBody.getData());
            String sign = json.getString("sign");
            if(StringUtils.isEmpty(sign)){
                log.info("requestBody={}  sendPhoneCode 没有签名..... ",requestBody);
                return new RespondBody(RespondMessageEnum.NOINFO_SIGN);
            }
            json.remove("sign");
            if(!MD5Utils.checkEncryption(json,constant.getUSERLOGINSIGNKEY(),sign)){
                log.info("requestBody={}  sendPhoneCodein 签名校验失败..... ",requestBody);
                return new RespondBody(RespondMessageEnum.SIGNERROR);
            }
            json.remove("nonceStr");
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject =abstractUserinfoService.sendPhoneCode(json);
            MD5Utils.encryption(jsonObject,constant.getUSERLOGINSIGNKEY());
            return  new RespondBody(RespondMessageEnum.SUCCESS,jsonObject);
        }catch (HNException e){
            log.info("requestBody={} sendPhoneCode  code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} sendPhoneCode exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    /**
     * 实名认证接口
     */
    @PostMapping(value="/api/v1/user/realName",produces = {"application/json;charset=utf-8"})
    public RespondBody realName(RequestBody requestBody){
        return  null;
    }


    /**
     * 待定义接口
     * 退票  --- 暂不提供
     * @return
     */
    public void  returnTicket(){}

}
