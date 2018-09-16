package com.sy.huangniao.common;

import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import lombok.Data;

/**
 * Created by huchao on 2018/9/14.
 * 返回报文体
 */
@Data
public class RespondBody {

    //返回错误吗
   private String code;

   //返回信息
   private String msg;

   //返回数据
   private Object object;


    public RespondBody(String code, String msg, Object object) {
        this.code = code;
        this.msg = msg;
        this.object = object;
    }


    public RespondBody(RespondMessageEnum respondMessageEnum, Object object) {
        this.code = respondMessageEnum.getCode();
        this.msg = respondMessageEnum.getMsg();
        this.object = object;
    }


    public RespondBody(RespondMessageEnum respondMessageEnum) {
        this.code = respondMessageEnum.getCode();
        this.msg = respondMessageEnum.getMsg();
        this.object = new JSONObject();
    }

    public RespondBody(Object object) {
        this.code = RespondMessageEnum.SUCCESS.getCode();
        this.msg =  RespondMessageEnum.SUCCESS.getMsg();
        this.object = object;
    }

}
