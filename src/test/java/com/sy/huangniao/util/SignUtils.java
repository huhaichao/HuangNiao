package com.sy.huangniao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.common.Util.MD5Utils;

import java.util.Map;

/**
 * Created by huchao on 2018/10/2.
 */
public class SignUtils {


    public  static void main(String[] args){
        JSONArray jsonArray = JSONArray.parseArray("[{\"seatType\":\"硬座,硬卧\",\"departureDate\":\"2018-01-02\",\"trainNum\":\"K180,K190\"},{\"seatType\":\"硬座,硬卧\",\"departureDate\":\"2018-01-05\",\"trainNum\":\"K180,K190\"}]");
        String json = "{\"ticketIdentity\": \"411522198911174538\",\"ticketName\": \"胡超\",\"nonceStr\": \"123456\",\"fromSite\":\"北京\",\"toSite\":\"河南\",\"ticketDetails\":"+jsonArray.toString()+",\"orderAmount\": \"80\",\"termIp\": \"113.46.163.196\"}";
        JSONObject jsonObject =  (JSONObject) JSON.parse(json);
        String ticketDetails =jsonObject.getString("ticketDetails");
        jsonObject.remove("ticketDetails");
        Map<String,String> maps =(Map)JSON.parse(jsonObject.toJSONString());
        maps.put("ticketDetails",ticketDetails);
        String sign = MD5Utils.encryption(maps,"319ceidcbweifbwi");
        json = "{\"ticketIdentity\": \"411522198911174538\",\"ticketName\": \"胡超\",\"nonceStr\": \"123456\",\"fromSite\":\"北京\",\"toSite\":\"河南\",\"ticketDetails\":"+jsonArray.toString()+",\"orderAmount\": \"80\",\"termIp\": \"113.46.163.196\",\"sign\":"+sign+"}";
        System.out.println(json);
    }
}
