package com.sy.huangniao.common.exception;

import com.sy.huangniao.common.enums.RespondMessageEnum;

/**
 * Created by huchao on 2018/9/14.
 */
public class HNException extends RuntimeException {

    private RespondMessageEnum respondMessageEnum;

    public HNException(RespondMessageEnum respondMessageEnum) {
        super(respondMessageEnum.getMsg());
        this.respondMessageEnum  = respondMessageEnum;
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

    public  String  getCode(){
        return  respondMessageEnum.getCode();
    }

    public  String  getMsg(){
        return  respondMessageEnum.getMsg();
    }

    public  RespondMessageEnum getRespondMessageEnum(){
        return  respondMessageEnum;
    }
}
