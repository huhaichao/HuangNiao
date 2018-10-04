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
    LACK_PARAMS("0013","缺少参数"),
    PAYSIGNFAIL("0014", "签名失败"),
    UPDATEDEPOSITSTATUSFAIL("0015", "修改钱包充值金额有误"),
    NOINFO_APPCODE("0016","缺少appCode" ),
    SIGNERROR("0017","签名失败"),
    NOINFO_SIGN("0018", "缺少签名"),
    SEND_CODE_FAIL("0019","发送验证码操作失败，请重试"),
    SMS_CODE_EXPIRE("0020","验证码已失效，请重试"),
    SMS_CODE_ERROR("0021","验证码错误，请重新输入"),
    SMS_CODE_FAIL("0022","验证码操作失败，请重试"),
    REALNAME_FAIL("0023","身份证或者姓名有误，请检查"),
    ADDCONTACTS_FAIL("0024","添加联系人失败"),
    ADDCONTACTS_REPEAT("0025","重复添加联系人"),
    CREATORDERDETAILSFAIL("0026", "创建订单明细失败"),
    /*
     调用微信外部接口以1开头
    * */
    WX_CODE_GET_OPENID_FAIL("1000", "调用微信获取openid失败"),
    WX_CODE_CALL_FAIL("1001", "微信调用通讯失败"),
    WX_CODE_NOAUTH("1002","商户未开通此接口权限"),
    WX_CODE_NOTENOUGH("1003","帐号余额不足，请用户充值或更换支付卡后再支付"),
    WX_CODE_ORDERPAID("1004","商户订单已支付，无需重复操作"),
    WX_CODE_ORDERCLOSED("1005","当前订单已关闭，请重新下单"),
    WX_CODE_APPID_NOT_EXIST("1006","请检查APPID是否正确"),
    WX_CODE_MCHID_NOT_EXIST("1007","请检查MCHID是否正确"),
    WX_CODE_APPID_MCHID_NOT_MATCH("1008","请确认appid和mch_id是否匹配"),
    WX_CODE_OUT_TRADE_NO_USED("1009","请核实商户订单号是否重复提交"),
    WX_CODE_SIGNERROR("1010","请检查签名参数和方法是否都符合签名算法要求"),
    WX_CODE_XML_FORMAT_ERROR("1011","请检查XML参数格式是否正确"),
    WX_CODE_REQUIRE_POST_METHOD("1012","请检查请求参数是否通过post方法提交"),
    WX_CODE_POST_DATA_EMPTY("1013","请检查post数据是否为空"),
    WX_CODE_NOT_UTF8("1014","请使用UTF-8编码格式"),
    WX_CODE_CALLBACK_FAIL("1015", "小程序回调异常s"),
    WX_CODE_CALLBACK_NO_DEPOSIT("1016","查询不到愿订单信息"),
    WX_CODE_CASH_FEE_NOT_EQUAL("1017","实际付款金额与充值金额不等" ),


    SUCCESS("0000","成功"),
    EXCEPTION("9999","服务器异常"), ;

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
