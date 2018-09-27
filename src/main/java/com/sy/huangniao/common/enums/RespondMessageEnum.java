package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/14.
 * 返回报文
 *
 */
public enum RespondMessageEnum {

    /**
     * 内部接口业务异常吗以0开头
     */
    SAVEUSERINFOERROR("0001","用户保存信息失败"),
    PASSWORDOERROR("0002", "密码错误"),
    UPDATEUSERINFOERROR("0003","修改用户信息失败"),
    CREATROBORDERFAIL("0004", "商户抢单失败"),
    CREATORDERFAIL("0005","商户下单失败"),
    NO_LOGIN("0006","用户未登陆"),
    NOINFO_LOGINKEY("0007", "缺少loginkey"),
    NOINFO_USERID("0008","缺少userId" ),
    UPDATEWXINFOFAIL("0009","修改用户微信信息表失败"),
    CONFIREMEORDERFAIL("0010", "订单确认失败"),
    UNFREEAMOUNTFAIL("0011","解冻金额失败" ),
    CANCLEORDERFAIL("0012","该订单暂不能取消，可能原因是已有商户在提供抢票服务！"),


    /*
     调用微信外部接口以1开头
    * */
    WX_CODE_GET_OPENID_FAIL("1000", "调用微信获取openid失败"),





    SUCCESS("0000","成功"),
    EXCEPTION("9999","服务器异常");

    private  String  code;

    private  String  msg ;

    RespondMessageEnum(String code , String msg){
        this.code =code;
        this.msg =msg;
    }

    public String getCode(){
        return  this.code;
    }

    public String getMsg(){
        return  this.msg;
    }
}
