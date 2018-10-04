package com.sy.huangniao.util;

import com.alibaba.fastjson.JSON;
import com.sy.huangniao.common.Util.MD5Utils;

import java.util.Map;

/**
 * Created by huchao on 2018/10/2.
 */
public class SignUtils {


    public  static void main(String[] args){
        String json = "{\"indentity\":\"411522198911174538\",\"name\":\"胡超\",\"nonceStr\":\"123456\"}";
        Map<String,String> maps = (Map) JSON.parse(json);
        String sign = MD5Utils.encryption(maps,"319ceidcbweifbwi");
        json =  "{\"indentity\":\"411522198911174538\",\"name\":\"胡超\",\"nonceStr\":\"123456\",\"sign\":\""+sign+"\"}";
        System.out.println(json);

    }
}
