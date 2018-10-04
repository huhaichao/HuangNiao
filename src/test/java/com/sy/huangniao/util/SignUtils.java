package com.sy.huangniao.util;

import com.alibaba.fastjson.JSON;
import com.sy.huangniao.common.Util.MD5Utils;

import java.util.Map;

/**
 * Created by huchao on 2018/10/2.
 */
public class SignUtils {


    public  static void main(String[] args){
        String json = "{\"ticketIdentity\":\"411522198911174538\",\"ticketName\":\"胡超\",\"nonceStr\":\"123456\",\"from\":\"北京\",\"to\":\"河南\",\"departureDate\":\"2018-10-01\",\"trainNum\":\"k180\",\"seatType\":\"二等座\",\"orderAmount\":\"80\",\"termIp\":\"113.46.163.196\"}";
        Map<String,String> maps = (Map) JSON.parse(json);
        String sign = MD5Utils.encryption(maps,"319ceidcbweifbwi");
        json =  "{\"ticketIdentity\":\"411522198911174538\",\"ticketName\":\"胡超\",\"nonceStr\":\"123456\",\"from\":\"北京\",\"to\":\"河南\",\"departureDate\":\"2018-10-01\",\"trainNum\":\"k180\",\"seatType\":\"二等座\",\"orderAmount\":\"80\",\"termIp\":\"113.46.163.196\",\"sign\":\""+sign+"\"}";
        System.out.println(json);

    }
}
