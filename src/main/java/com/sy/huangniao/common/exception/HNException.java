package com.sy.huangniao.common.exception;

import com.sy.huangniao.common.enums.RespondMessageEnum;

/**
 * Created by huchao on 2018/9/14.
 */
public class HNException extends RuntimeException {

    private RespondMessageEnum respondMessageEnum;

    private String code;

    private String msg;

    public HNException(RespondMessageEnum respondMessageEnum) {
        super(respondMessageEnum.getMsg());
        this.respondMessageEnum  = respondMessageEnum;
        this.code =respondMessageEnum.getCode();
        this.msg = respondMessageEnum.getMsg();
    }

    public HNException(String message) {
        super(message);
    }

    public HNException(String message, Throwable cause) {
        super(message, cause);
    }

    public HNException(Throwable cause) {
        super(cause);
    }

    public HNException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HNException(String code,String msg) {
          this.code =code;
          this.msg = msg;
    }
    public  String  getCode(){
        return  this.code;
    }

    public  String  getMsg(){
        return  this.msg;
    }

    public  RespondMessageEnum getRespondMessageEnum(){
        return  respondMessageEnum;
    }
}
