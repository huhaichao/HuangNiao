package com.sy.huangniao.service.impl.XCX;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.sy.huangniao.common.Util.*;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.bo.WXRespondBody;
import com.sy.huangniao.common.bo.WXReturnRespondBody;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.*;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.config.wx.WxPayConfig;
import com.sy.huangniao.pojo.*;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.IRedisService;
import com.sy.huangniao.service.impl.AbstractUserAppService;
import com.sy.huangniao.service.pay.IWXPaychannelsService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by huchao on 2018/9/24.
 *
 * 小程序业务处理类
 *
 */
@Slf4j
@Component
public class XCXUserAppServiceImpl extends AbstractUserAppService {

    @Override
    public AppCodeEnum getAppCode() {
        return AppCodeEnum.XCX;
    }

    @Autowired
    private IRedisService redisServiceImpl;

    @Autowired
    private WxPayConfig wxPayConfig;

    /**
     *
     * 重写小程序的登陆方法
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public JSONObject login (JSONObject json){
      //去微信获取openid
      String jsCode = json.getString("code");
      log.info("userCode={} 获取openID......",jsCode);
      IWXPaychannelsService  iwxPaychannelsService =hnContext.getIWXPaychannelsService(getAppCode());
      JSONObject jsonObject = iwxPaychannelsService.code2Session(json);
      UserWxinfo userWxinfo = new UserWxinfo();
      userWxinfo.setOpenid(jsonObject.getString("openid"));
      userWxinfo.setSessionKey(jsonObject.getString("session_key"));
      userWxinfo.setUnionid(jsonObject.getString("unionid"));
      if(userWxinfo.getOpenid() == null){
          log.info("code={} 请求登陆失败 errcode={} errmsg={}",jsCode,jsonObject.getString("errcode"),jsonObject.getString("errmsg"));
          throw  new HNException(RespondMessageEnum.WX_CODE_GET_OPENID_FAIL);
      }
      UserInfo userInfo =null;
        //通过openID查询登陆状
      String userid =redisServiceImpl.get(Constant.GETUSERIDBYOPENID+getAppCode().getCode()+userWxinfo.getOpenid(),String.class);

      IDaoService iDaoService = hnContext.getDaoService(UserWxinfo.class.getSimpleName());
        AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(json.getString("userRole"));

        if(!StringUtils.isEmpty(userid)){
          userWxinfo.setUserId(Integer.parseInt(userid));
          userWxinfo.setModifyDate(new Date());
          if(iDaoService.updateObject(userWxinfo, SqlTypeEnum.UPDATEBYUSERID)!=1){
              log.info("userid={} openid={} 修改数据失败",userid,userWxinfo.getOpenid());
              throw  new HNException(RespondMessageEnum.UPDATEWXINFOFAIL);
          }
      }else{
      //根据openid查询userid--如果没有则创建
           UserWxinfo userWxinfoSelect = new UserWxinfo();
           userWxinfoSelect.setOpenid(userWxinfo.getOpenid());
           List<UserWxinfo> list =iDaoService.selectList(userWxinfoSelect,SqlTypeEnum.DEAFULT);
           if(list==null || list.size()==0){
              userInfo =abstractUserinfoService.createUserInfo(json);
              userid = userInfo.getId().toString();
              //保存微信信息
               userWxinfo.setUserId(userInfo.getId());
               userWxinfo.setCreateDate(new Date());
               userWxinfo.setModifyDate(new Date());
               if(iDaoService.save(userWxinfo,SqlTypeEnum.DEAFULT)!=1) {
                   log.info("userid={} openid={} userRole={} appcode ={} 保存用户微信信息失败", userid, userWxinfo.getOpenid(), userInfo.getAppCode());
                   throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
               }
           }else {
               UserWxinfo userWxinfo2 = list.get(0);
               userid = userWxinfo2.getUserId()+"";
               userWxinfo.setUserId(Integer.parseInt(userid));
               userWxinfo.setModifyDate(new Date());
               if(iDaoService.updateObject(userWxinfo, SqlTypeEnum.UPDATEBYUSERID)!=1){
                   log.info("userid={} openid={} 修改数据失败",userid,userWxinfo.getOpenid());
                   throw  new HNException(RespondMessageEnum.UPDATEWXINFOFAIL);
               }
           }
      }

     UserInfoBody userInfoBody = abstractUserinfoService.getUserInfo(Integer.parseInt(userid));
     JSONObject jsonResult = (JSONObject) JSONObject.toJSON(userInfoBody);
     String salt = DateUtils.date2yyyyMMddhhmmssString(new Date())+userid;
     String loginKey = MD5Utils.getMD5String(salt).substring(0,10);
        redisServiceImpl.set(Constant.CACHELOGINKEY+getAppCode().getCode()+userid,loginKey,constant.getLoginKeyexprirTime(), TimeUnit.SECONDS);
     //续租openid
        redisServiceImpl.set(Constant.GETUSERIDBYOPENID+getAppCode().getCode()+userWxinfo.getOpenid(),userid,constant.getLoginKeyexprirTime(), TimeUnit.SECONDS);
     //保存session_key
        redisServiceImpl.set(Constant.USERIDSESSIONKEY+getAppCode().getCode()+userid,userWxinfo.getSessionKey(),constant.getLoginKeyexprirTime(), TimeUnit.SECONDS);
     jsonResult.put("loginKey",loginKey);
     //jsonResult.put("userId",userid);
     return  jsonResult;
    }

    @Override
    protected JSONObject handleUnifiedorder(JSONObject result) {
        String prepay_id =  result.getString("prepay_id");
        String nonceStr = WXPayUtil.generateNonceStr();
        long timeStamp = WXPayUtil.getCurrentTimestamp();
        Map<String,String> map = new HashMap<String,String>();
        map.put("appId", wxPayConfig.getAppID());
        map.put("timeStamp",timeStamp+"");
        map.put("nonceStr",nonceStr);
        map.put("package","prepay_id="+prepay_id);
        map.put("signType","HMAC-SHA256");
        try {
            String sign = WXPayUtil.generateSignature(map,wxPayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
            map.remove("appId");
            map.put("paySign",sign);
            JSONObject json = new JSONObject();
            json.putAll(map);
            return  json;
        } catch (Exception e) {
            log.info("小程序生成签名信息失败 prepay_id={}",prepay_id);
            throw  new HNException(RespondMessageEnum.PAYSIGNFAIL);
        }
    }

    @Override
    public JSONObject unifiedorder(JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        log.info("小程序预下单.....userId={}",userId);
        UserWxinfo userWxinfo = new UserWxinfo();
        userWxinfo.setUserId(Integer.parseInt(userId));
        IDaoService iDaoService = hnContext.getDaoService(UserWxinfo.class.getSimpleName());
        userWxinfo = (UserWxinfo)iDaoService.selectObject(userWxinfo, SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        IWXPaychannelsService iwxPaychannelsService= hnContext.getIWXPaychannelsService(AppCodeEnum.valueOf(jsonObject.getString("appCode")));
        JSONObject params = new JSONObject();
        String outTradeNo = createDepositNO();//微信支付商户订单号
        params.put("body",constant.getWX_XCX_BODY());
        params.put("total_fee", BigDecimal.valueOf(jsonObject.getDouble("orderAmount")).multiply(BigDecimal.valueOf(100)).longValue()+"");
        params.put("spbill_create_ip",jsonObject.getString("termIp"));
        params.put("trade_type",iwxPaychannelsService.getTradeType());
        params.put("openid",userWxinfo.getOpenid());
        params.put("out_trade_no",outTradeNo);
        /**
         * 调用微信预下单接口
         */
        JSONObject json =  iwxPaychannelsService.unifiedorder(params);
        if("SUCCESS".equals(json.getString("return_code"))){
            if (!"SUCCESS".equals(json.getString("result_code"))){
                {
                    log.info("小程序充值失败userId={} result={} appCode ={}",userId,json,getAppCode().getCode());
                    throw new HNException(RespondMessageEnum.valueOf(Constant.ERRORCODEXCX+json.getString("result_code")));
                }
            }
        }else {
            log.info("小程序充值失败userId={} result={} appCode={} ",userId,json,getAppCode().getCode());
            throw new HNException(RespondMessageEnum.WX_CODE_CALL_FAIL);
        }
        json.put("outTradeNo",outTradeNo);
        return  json;
    }

    @Override
    public String createUserAcountNo() {
        return constant.getUSERACCOUNTXCX()+IdGenerator.getInstance().generate();
    }

    @Override
    public String createTradeNo() {
        return constant.getTRADENOXCX()+IdGenerator.getInstance().generate();
    }

    @Override
    public String createOrderNO() {
        return constant.getORDERNOXCX()+IdGenerator.getInstance().generate();
    }

    @Override
    public String createDepositNO() {
        return constant.getDEPOSITNOXCX()+IdGenerator.getInstance().generate();
    }


    @Override
    public String createReturnNO() {
        return constant.getRETURNNOXCX()+IdGenerator.getInstance().generate();
    }

    @Override
    public JSONObject returned(JSONObject jsonObject) {
        //商户订单号
        log.info("小程序退款开始userID={},orderNo={} ......",jsonObject.getString("userId"),jsonObject.getString("orderNo"));

        IWXPaychannelsService iwxPaychannelsService= hnContext.getIWXPaychannelsService(AppCodeEnum.valueOf(jsonObject.getString("appCode")));
        //组装参数
        JSONObject params = new JSONObject();
        //商户订单号
        params.put("out_trade_no",jsonObject.getString("depositNo"));
        //商户退款单号
        params.put("out_refund_no",jsonObject.getString("returnNo"));
        //订单金额
        params.put("total_fee",BigDecimal.valueOf(jsonObject.getDouble("orderAmount")).multiply(BigDecimal.valueOf(100)).longValue()+"");
        //退款金额
        params.put("refund_fee",BigDecimal.valueOf(jsonObject.getDouble("returnAmount")).multiply(BigDecimal.valueOf(100)).longValue()+"");
        //退款通知接口
        params.put("notify_url",constant.getWX_XCX_RETURNURL_NOTIFY());
        JSONObject  returnJson = iwxPaychannelsService.refund(params);

        if("SUCCESS".equals(returnJson.getString("return_code"))){
            if (!"SUCCESS".equals(returnJson.getString("result_code"))){
                {
                    log.info("小程序退款失败userId={} orderNo={} result={} appCode ={} err_code_des={}",jsonObject.getString("userId"),
                        jsonObject.getString("orderNo"),returnJson,getAppCode().getCode(),returnJson.getString("err_code_des"));
                    throw new HNException(Constant.ERRORCODRETURNEXCX+returnJson.getString("err_code"),returnJson.getString("err_code_des"));
                }
            }
        }else {
            log.info("小程序退款失败userId={} orderNo={} result={} appCode={} ",jsonObject.getString("userId"),jsonObject.getString("orderNo"),returnJson,getAppCode().getCode());
            throw new HNException(RespondMessageEnum.WX_CODE_CALL_FAIL);
        }
        return  returnJson;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String payCallback(HttpServletRequest request) {
        log.info("小程序回调接口调用.....");
        try {
            String result= HttpClientUtils.respondString(request.getInputStream());
            log.info("小程序回调返回数据={}",result);
            Map<String,String> map =WXPayUtil.xmlToMap(result);
            String sign = map.get("sign");
            String signResult =WXPayUtil.generateSignature(map,wxPayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);

            if(!signResult.equals(sign)){
                log.info("xcx 回调接口签名错误  sign={} != signResult={}",sign,signResult);
                throw  new HNException(RespondMessageEnum.WX_CODE_SIGNERROR);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(map);
            WXRespondBody wxRespondBody =  JSONObject.toJavaObject(jsonObject,WXRespondBody.class);
            if ("SUCCESS".equals(wxRespondBody.getReturn_code())){
                if("SUCCESS".equals(wxRespondBody.getResult_code())){
                    UserDeposit    userDeposit = new UserDeposit();
                    userDeposit.setAppCode(getAppCode().getCode());
                    //只能修改充值状态时是充值中的订单---以状态作为一个乐观锁防止重放攻击
                    userDeposit.setStatus(WalletStatusEnum.DEPOSITING.getStatus());
                    //商户订单号
                    userDeposit.setDepositNo(wxRespondBody.getOut_trade_no());
                    IDaoService<UserDeposit> iDaoService= hnContext.getDaoService(UserDeposit.class.getSimpleName());
                    userDeposit =iDaoService.selectObject(userDeposit,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
                    if(userDeposit==null){
                        log.info("xcx 重复通知情况是否存在 wxRespondBody={}",wxRespondBody);
                        throw  new HNException(RespondMessageEnum.NOTIFY_REPEAT);
                    }
                    //修改充值表
                    if(wxRespondBody.getCash_fee() != (userDeposit.getAmount()*100)){
                        log.info("xcx 实际付款情况金额与充值金额不一致cash_fee={} != amount={} wxRespondBody={}",
                                wxRespondBody.getCash_fee(),userDeposit.getAmount()*100,wxRespondBody);
                        throw  new HNException(RespondMessageEnum.AMOUNT_NOTEQUEAL);
                    }
                    //修改为充值成功
                    UserDeposit updateDeposit = new UserDeposit();
                    updateDeposit.setStatus(WalletStatusEnum.SUCCESS.getStatus());//充值到账
                    updateDeposit.setBankType(wxRespondBody.getBank_type());
                    updateDeposit.setTimeEnd(wxRespondBody.getTime_end());
                    updateDeposit.setTradeChannelsNo(wxRespondBody.getTransaction_id());
                    updateDeposit.setModifyDate(new Date());
                    updateDeposit.setId(userDeposit.getId());
                    if(iDaoService.updateObject(updateDeposit,SqlTypeEnum.UPADTEBYIDANDBYSTATUS)!=1){
                        log.info("xcx 修改钱包充值状态有误请检查原因 depositNO={} userId={}",updateDeposit.getDepositNo(),updateDeposit.getUserId()
                        );
                        throw  new HNException(RespondMessageEnum.UPDATEDEPOSITSTATUSFAIL);
                    }
                    int userId = userDeposit.getUserId();
                    String  orderNo   = userDeposit.getOrderNo(); //是否是下单充值

                    IDaoService<UserAccount> iUserAccountDaoService= hnContext.getDaoService(UserAccount.class.getSimpleName());
                    UserAccount userAccount = new UserAccount();
                    userAccount.setUserId(userId);
                    userAccount =iUserAccountDaoService.selectObject(userAccount,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
                     UserAccount updateUserAccount = new UserAccount();
                    if(!StringUtils.isEmpty(orderNo)){
                        updateUserAccount.setCoolAmount(userDeposit.getAmount());
                        //如果是车票订单 -- 修改车票状态为已完成支付--给服务商打款
                        TicketOrder ticketOrder = new TicketOrder();
                        ticketOrder.setOrderNo(orderNo);
                        ticketOrder.setUserId(userId);
                        ticketOrder.setModifyDate(new Date());
                        ticketOrder.setOrderStatus(OrderStatusEnum.TICKET_SUCCESS.getStatus());
                        IDaoService<TicketOrder> iTicketOrderDaoService= hnContext.getDaoService(TicketOrder.class.getSimpleName());
                        if (iTicketOrderDaoService.updateObject(ticketOrder,SqlTypeEnum.UPDATEBYUSERIDANDORDERNO)!=1){
                            log.info("xcx 修改订单状态有误请检查原因 orderNo={} userId={}",orderNo,userId
                                    );
                            //加入预警信息
                        }
                    }else {
                        updateUserAccount.setAmountBalance(userDeposit.getAmount());
                    }
                    //增加余额  -- 或者冻结余额
                    updateUserAccount.setId(userAccount.getId());
                    updateUserAccount.setAccountNo(userAccount.getAccountNo());
                    updateUserAccount.setUserId(userId);
                    updateUserAccount.setModifyDate(new Date());
                    if(iUserAccountDaoService.updateObject(updateUserAccount,SqlTypeEnum.UPDATEACCOUNTAMOUNT)!=1){
                        log.info("xcx 账户修改余额失败 acountNo={} userId={} amount={}",userAccount.getAccountNo(),userId,updateDeposit.getAmount()
                        );
                        //加入预警信息
                    }

                    Map<String,String> returnStr = new HashMap<String,String>();
                    returnStr.put("return_code","SUCCESS");
                    returnStr.put("return_msg","OK");
                    return  WXPayUtil.mapToXml(returnStr);
                }else {
                    log.info("小程序回调返回错误吗err_code={},err_code_des={}",wxRespondBody.getErr_code(),wxRespondBody.getErr_code_des());
                }

            }

        } catch (Exception e) {
            log.info("小程序回调接口调用异常={}.....",e.getMessage());
            throw  new HNException(RespondMessageEnum.WX_CODE_CALLBACK_FAIL);
        }
        return null;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String returnCallback(HttpServletRequest request) {
        log.info("小程序退款回调接口调用.....");
        try {
            String result= HttpClientUtils.respondString(request.getInputStream());
            log.info("小程序退款回调接口返回数据={}",result);
            Map<String,String> encodeStr = WXPayUtil.xmlToMap(result);
            Map<String,String> map =WXPayUtil.decodeReturnRespond(encodeStr.get("req_info"),wxPayConfig.getKey());
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(map);
            WXReturnRespondBody wxReturnRespondBody =  JSONObject.toJavaObject(jsonObject,WXReturnRespondBody.class);
            if ("SUCCESS".equals(wxReturnRespondBody.getRefund_status())){
                //修改退款订单状态
                ReturnOrder  returnOrder = new ReturnOrder();
                returnOrder.setReturnNo(wxReturnRespondBody.getOut_refund_no());
                returnOrder.setReturnStatus(OrderStatusEnum.RETURNING_CHANNELS.getStatus());
                IDaoService<ReturnOrder> iReturnOrderService= hnContext.getDaoService(ReturnOrder.class.getSimpleName());
                ReturnOrder returnReuslt = iReturnOrderService.selectObject(returnOrder,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
                Integer  userId = returnReuslt.getUserId();
                String  orderNo   = returnReuslt.getOrderNo();
                //修改退款订单状态
                ReturnOrder  updateReturnOrder = new ReturnOrder();
                //BeanUtils.copyProperties(wxReturnRespondBody,updateReturnOrder);
                updateReturnOrder.setId(returnReuslt.getId());
                updateReturnOrder.setModifyDate(new Date());
                updateReturnOrder.setTradeChannelsNo(wxReturnRespondBody.getTransaction_id());
                updateReturnOrder.setTradeChannelsReturnNo(wxReturnRespondBody.getRefund_id());
                updateReturnOrder.setReturnStatus(OrderStatusEnum.RETURNED_AMOUNT.getStatus());
                updateReturnOrder.setRefundRecvAccout(wxReturnRespondBody.getRefund_recv_accout());
                updateReturnOrder.setRefundRequestSource(wxReturnRespondBody.getRefund_request_source());
                //updateReturnOrder.setRetunTime(wxReturnRespondBody.getSuccess_time());
                if(iReturnOrderService.updateObject(updateReturnOrder,SqlTypeEnum.DEAFULT)!=1){
                    log.info("xcx 修改退款状态有误请检查原因 returnNo={} orderNo={} userId={}",returnReuslt.getReturnNo(),orderNo,userId
                    );
                    throw new HNException(RespondMessageEnum.UPDATERETURNFAIL);
                }

                IDaoService<UserAccount> iUserAccountDaoService= hnContext.getDaoService(UserAccount.class.getSimpleName());
                UserAccount userAccount = new UserAccount();
                userAccount.setUserId(userId);
                userAccount =iUserAccountDaoService.selectObject(userAccount,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
                //修改订单状态
                TicketOrder ticketOrder = new TicketOrder();
                ticketOrder.setOrderNo(orderNo);
                ticketOrder.setUserId(userId);
                ticketOrder.setModifyDate(new Date());
                ticketOrder.setOrderStatus(OrderStatusEnum.RETURNED_AMOUNT.getStatus());
                IDaoService<TicketOrder> iTicketOrderDaoService= hnContext.getDaoService(TicketOrder.class.getSimpleName());
                if (iTicketOrderDaoService.updateObject(ticketOrder,SqlTypeEnum.UPDATEBYUSERIDANDORDERNO)!=1){
                    log.info("xcx 退款回调修改订单状态有误请检查原因 returnNo={} orderNo={} userId={}",returnReuslt.getReturnNo(),orderNo,userId
                    );
                    //加入预警信息
                }
                //减去冻结余额
                UserAccount updateUserAccount = new UserAccount();
                updateUserAccount.setCoolAmount(-returnReuslt.getReturnAmount());
                updateUserAccount.setId(userAccount.getId());
                updateUserAccount.setAccountNo(userAccount.getAccountNo());
                updateUserAccount.setUserId(userId);
                updateUserAccount.setModifyDate(new Date());
                if(iUserAccountDaoService.updateObject(updateUserAccount,SqlTypeEnum.UPDATEACCOUNTAMOUNT)!=1){
                    log.info("xcx 退款回调账户修改余额失败 acountNo={} userId={} amount={}",userAccount.getAccountNo(),userId,returnReuslt.getRefundAccount()
                    );
                    //加入预警信息
                }

                Map<String,String> returnStr = new HashMap<String,String>();
                returnStr.put("return_code","SUCCESS");
                returnStr.put("return_msg","OK");
                return  WXPayUtil.mapToXml(returnStr);
            }else {
                log.info("小程序退款回调返回错误吗err_code={}",wxReturnRespondBody.getRefund_status());
            }

        } catch (Exception e) {
            log.info("小程序退款接口调用异常={}.....",e.getMessage());
            throw  new HNException(RespondMessageEnum.WX_CODE_CALLBACK_FAIL);
        }
        return null;
    }


}
