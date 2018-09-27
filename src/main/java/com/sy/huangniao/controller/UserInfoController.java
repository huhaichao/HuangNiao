package com.sy.huangniao.controller;

import com.sy.huangniao.common.bo.RequestBody;
import com.sy.huangniao.common.bo.RespondBody;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.service.impl.AbstractUserLoginService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huchao on 2018/9/14.
 */
@RestController("/api/v1/user/")
@Slf4j
public class UserInfoController {

    @Autowired
    private HNContext hnContext;


    /**
     * 目前小程序不需要--暂不开放
     */
    public void registry(){};

    /**
     * 登陆
     */
    @PostMapping(name="login",produces = {"application/json;charset=utf-8"})
    public RespondBody login(RequestBody requestBody){
       try {
           log.info("requestBody={} login......",requestBody);
           AbstractUserLoginService abstractUserLoginService = hnContext.getAbstractUserLoginService(AppCodeEnum.valueOf(requestBody.getAppCode()));
           JSONObject json = new JSONObject();
           BeanUtils.copyProperties(requestBody,json);
           JSONObject jsonObject = abstractUserLoginService.login(json);
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
    @PostMapping(name="getUserInfo",produces = {"application/json;charset=utf-8"})
    public RespondBody getUserInfo(RequestBody requestBody){
        try {
            log.info("requestBody={} getUserInfo......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            UserInfoBody userInfoBody = abstractUserinfoService.getUserInfo(Integer.parseInt(requestBody.getUserId()));
            return new RespondBody(RespondMessageEnum.SUCCESS,userInfoBody);
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
    @PostMapping(name="updateUserInfo",produces = {"application/json;charset=utf-8"})
    public RespondBody updateUserInfo(RequestBody requestBody){
        try {
            log.info("requestBody={} updateUserInfo......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject = JSONObject.fromObject(requestBody.getData());
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("id",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
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
    @PostMapping(name="createOrder",produces = {"application/json;charset=utf-8"})
    public RespondBody createOrder(RequestBody requestBody){
        try {
            log.info("requestBody={} createOrder......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject = JSONObject.fromObject(requestBody.getData());
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            if(abstractUserinfoService.createOrder(jsonObject))
                 return new RespondBody(RespondMessageEnum.SUCCESS);
            else
                 return new RespondBody(RespondMessageEnum.CREATORDERFAIL);
        }catch (HNException e){
            log.info("requestBody={} createOrder  code={} msg={}",requestBody,e.getCode(),e.getMsg());
            return new RespondBody(e.getRespondMessageEnum());
        }catch (Exception e){
            log.info("requestBody={} createOrder exception={}",requestBody,e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    @PostMapping(name="payOrder",produces = {"application/json;charset=utf-8"})
    public RespondBody payOrder(RequestBody requestBody){
        try {
            log.info("requestBody={} payOrder......",requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody.getData());
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            //调用充值接口
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject result = abstractUserinfoService.deposit(jsonObject);
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
    @PostMapping(name="addContacts",produces = {"application/json;charset=utf-8"})
    public  RespondBody addContacts(RequestBody requestBody){return  null;}

    /**
     * 确认订单
     */
    @PostMapping(name="confirmeOrder",produces = {"application/json;charset=utf-8"})
    public RespondBody  confirmeOrder(RequestBody requestBody){
        try {
            log.info("requestBody={} confirmeOrder......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject = JSONObject.fromObject(requestBody.getData());
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
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
    @PostMapping(name="getOrderList",produces = {"application/json;charset=utf-8"})
    public RespondBody getOrderList(RequestBody requestBody){
        try {
            log.info("requestBody={} getOrderList......",requestBody);
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject jsonObject = JSONObject.fromObject(requestBody.getData());
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            String result =abstractUserinfoService.getOrderList(jsonObject);
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
    @PostMapping(name="cancleOrder",produces = {"application/json;charset=utf-8"})
    public  RespondBody   cancleOrder (RequestBody requestBody){try {
        log.info("requestBody={} cancleOrder......",requestBody);
        AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
        JSONObject jsonObject = JSONObject.fromObject(requestBody.getData());
       // jsonObject.put("userId",requestBody.getUserId());
       // jsonObject.put("userRole",requestBody.getUserRole());
        if(abstractUserinfoService.cancleOrder(Integer.parseInt(requestBody.getUserId()),jsonObject.getInt("orderId")))
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
    @PostMapping(name="deposit",produces = {"application/json;charset=utf-8"})
    public  RespondBody  deposit(RequestBody requestBody){
         try {
                log.info("requestBody={} deposit......",requestBody);
                JSONObject jsonObject = JSONObject.fromObject(requestBody.getData());
                jsonObject.put("userId",requestBody.getUserId());
                jsonObject.put("userRole",requestBody.getUserRole());
                jsonObject.put("appCode",requestBody.getAppCode());
                //调用充值接口
                AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
                JSONObject result = abstractUserinfoService.deposit(jsonObject);
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
    @PostMapping(name="payQuery",produces = {"application/json;charset=utf-8"})
    public  RespondBody  payQuery(RequestBody requestBody){
        try {
            log.info("requestBody={} payQuery......",requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody.getData());
            jsonObject.put("userId",requestBody.getUserId());
            jsonObject.put("userRole",requestBody.getUserRole());
            jsonObject.put("appCode",requestBody.getAppCode());
            //调用充值接口
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(requestBody.getUserRole());
            JSONObject result = abstractUserinfoService.payQuery(jsonObject);
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
    @PostMapping(name="withdraw",produces = {"application/json;charset=utf-8"})
    public RespondBody withdraw(RequestBody requestBody){return  null;}



    /**
     * 待定义接口
     * 退票  --- 暂不提供
     * @return
     */
    public void  returnTicket(){}

}
