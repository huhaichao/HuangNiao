package com.sy.huangniao.service.impl.XCX;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.sy.huangniao.common.Util.*;
import com.sy.huangniao.common.bo.WXRespondBody;
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
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

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
      JSONObject jsonObjectData = JSONObject.fromObject(json.get("data"));
      String jsCode = jsonObjectData.getString("code");
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

      //通过openID查询登陆状
      String userid =redisServiceImpl.get(Constant.GETUSERIDBYOPENID+getAppCode().getCode()+userWxinfo.getOpenid(),String.class);
      JSONObject jsonResult = new JSONObject();
      IDaoService iDaoService = hnContext.getDaoService(UserWxinfo.class.getSimpleName());
      if(!StringUtils.isEmpty(userid)){
          userWxinfo.setUserId(Integer.parseInt(userid));
          if(iDaoService.updateObject(userWxinfo, SqlTypeEnum.UPDATEBYUSERID)!=1){
              log.info("userid={} openid={} 修改数据失败",userid,userWxinfo.getOpenid());
              throw  new HNException(RespondMessageEnum.UPDATEWXINFOFAIL);
          }
      }else{
      //根据openid查询userid--如果没有则创建
           UserWxinfo userWxinfoSelect = new UserWxinfo();
           userWxinfoSelect.setOpenid(userWxinfo.getOpenid());
           List<UserWxinfo> list =iDaoService.selectList(userWxinfo,SqlTypeEnum.DEAFULT);
           if(list==null || list.size()==0){
              AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(json.getString("userRole"));
              UserInfo userInfo =abstractUserinfoService.createUserInfo(jsonObjectData);
              userid = userInfo.getId().toString();
              //保存微信信息
               userWxinfo.setUserId(userInfo.getId());
               if(iDaoService.save(userWxinfo,SqlTypeEnum.DEAFULT)!=1) {
                   log.info("userid={} openid={} userRole={} appcode ={} 保存用户微信信息失败", userid, userWxinfo.getOpenid(), userInfo.getAppCode());
                   throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
               }
           }
      }

     String salt = DateUtils.date2yyyyMMddhhmmssString(new Date())+userid;
     String loginKey = MD5Utils.getMD5String(salt).substring(0,10);
        redisServiceImpl.set(Constant.CACHELOGINKEY+getAppCode().getCode()+userid,loginKey,constant.getLoginKeyexprirTime(), TimeUnit.SECONDS);
     //续租openid
        redisServiceImpl.set(Constant.GETUSERIDBYOPENID+getAppCode().getCode()+userWxinfo.getOpenid(),userid,constant.getLoginKeyexprirTime(), TimeUnit.SECONDS);
     //保存session_key
        redisServiceImpl.set(Constant.USERIDSESSIONKEY+getAppCode().getCode()+userid,userWxinfo.getSessionKey(),constant.getLoginKeyexprirTime(), TimeUnit.SECONDS);
     jsonResult.put("loginKey",loginKey);
     jsonResult.put("userId",userid);

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
            String sign = WXPayUtil.generateSignature(map,wxPayConfig.getKey());
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
        String tradeNo = createOrderNO();
        params.put("body","小黄妞充值中心-服务费充值");
        params.put("total_fee",jsonObject.getDouble("amount")*100);
        params.put("spbill_create_ip",jsonObject.getString("termIp"));
        params.put("trade_type",iwxPaychannelsService.getTradeType());
        params.put("openid",userWxinfo.getOpenid());
        params.put("out_trade_no",tradeNo);
        /**
         * 调用微信预下单接口
         */
        JSONObject json =  iwxPaychannelsService.unifiedorder(params);
        if("SUCCESS".equals(json.getString("return_code"))){
            if (!"SUCCESS".equals(json.getString("return_code"))){
                {
                    log.info("小程序充值失败userId={} result={} appCode ={}",userId,json,getAppCode().getCode());
                    throw new HNException(RespondMessageEnum.valueOf(Constant.ERRORCODEXCX+json.getString("return_code")));
                }
            }
        }else {
            log.info("小程序充值失败userId={} result={} appCode={} ",userId,json,getAppCode().getCode());
            throw new HNException(RespondMessageEnum.WX_CODE_CALL_FAIL);
        }
        json.put("outTradeNo",tradeNo);
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
    //@Transactional(rollbackFor = {Exception.class}) 事务过大
    public String callback(HttpServletRequest request) {
        log.info("小程序回调接口调用.....");
        try {
            String result= HttpClientUtils.respondString(request.getInputStream());
            Map<String,String> map =WXPayUtil.xmlToMap(result);
            String sign = map.get("sign");
            String signResult =WXPayUtil.generateSignature(map,wxPayConfig.getKey(), WXPayConstants.SignType.valueOf(map.get("sign_type")));

            if(!signResult.equals(sign)){
                log.info("xcx 回调接口签名错误  sign={} != signResult={}",sign,signResult);
                throw  new HNException(RespondMessageEnum.WX_CODE_CALLBACK_NO_DEPOSIT);
            }

            WXRespondBody wxRespondBody = new WXRespondBody();
            BeanUtils.copyProperties(map,wxRespondBody);
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
                        throw  new HNException(RespondMessageEnum.WX_CODE_CALLBACK_NO_DEPOSIT);
                    }
                    //修改充值表
                    if(wxRespondBody.getCash_fee() != (userDeposit.getAmount()*100)){
                        log.info("xcx 实际付款情况金额与充值金额不一致cash_fee={} != amount={} wxRespondBody={}",
                                wxRespondBody.getCash_fee(),userDeposit.getAmount()*100,wxRespondBody);
                        throw  new HNException(RespondMessageEnum.WX_CODE_CASH_FEE_NOT_EQUAL);
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
                        //如果是车票订单 -- 修改车票状态为待抢票
                        TicketOrder ticketOrder = new TicketOrder();
                        ticketOrder.setOrderNo(orderNo);
                        ticketOrder.setUserId(userId);
                        ticketOrder.setOrderStatus(OrderStatusEnum.WAITROB.getStatus());
                        IDaoService<TicketOrder> iTicketOrderDaoService= hnContext.getDaoService(TicketOrder.class.getSimpleName());
                        if (iTicketOrderDaoService.updateObject(ticketOrder,SqlTypeEnum.UPDATEBYUSERID)!=1){
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


}