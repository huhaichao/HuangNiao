package com.sy.huangniao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bcloud.msg.http.HttpSender;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.service.OtherPartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huchao on 2018/10/3.
 */
@Slf4j
@Service
public class OtherPartyServiceImpl implements OtherPartyService {

    @Autowired
    protected Constant constant;

    @Override
    public boolean realName(JSONObject jsonObject) {
        return true;
    }


    /**
     * 发送验证码
     * @param jsonObject
     * @return
     */
    @Override
    public  boolean  sendPhoneCode(JSONObject jsonObject,String content,boolean batchSend){

        String uri = constant.getSMS_URL();//应用地址
        String account = constant.getSMS_ACCOUNT();//账号
        String pswd = constant.getSMS_PWD();//密码
        String mobiles = jsonObject.getString("phoneNo");//手机号码，多个号码使用","分割
        boolean needstatus = Boolean.parseBoolean(constant.getSMS_NEEDSTATUS());//是否需要状态报告，需要true，不需要false
        String product = constant.getSMS_PRODUCT();//产品ID
        String extno = constant.getSMS_EXTNO();//扩展码
        try {
            log.info("phonNo={} sendPhoneCode 发送内容={}",mobiles,content);
            String returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, "", "");
            log.info("phonNo={} sendPhoneCode returnString={}",mobiles,returnString);
            String[] returns = returnString.split("\n");
            if(returns.length!=0){
                String[] msgs = returns[0].split(",");
                if("0".equals(msgs[1])){
                   return  true;
                }else{
                    log.info("phonNo={} sendPhoneCode errCode={}",mobiles,msgs[1]);
                    throw  new HNException(RespondMessageEnum.SEND_CODE_FAIL);
                }
            }
        } catch (Exception e) {
            log.info("phonNo={} sendPhoneCode exception={}",mobiles,e.getMessage());
            throw  new HNException(RespondMessageEnum.SEND_CODE_FAIL);
        }
        /*try {
            String returnString = HttpSender.batchSend(uri, account, pswd, mobiles, content, needstatus, product, extno);
            System.out.println(returnString);
            //TODO 处理返回值,参见HTTP协议文档
        } catch (Exception e) {
            //TODO 处理异常
            e.printStackTrace();
        }*/
        return  false;
    }


}
