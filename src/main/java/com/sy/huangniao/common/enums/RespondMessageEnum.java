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
    CANCLEORDERFAIL("0012","订单取消失败"),
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
    ADDCONTACTS_REPEAT("0025","该联系人已存在"),
    CREATORDERDETAILSFAIL("0026", "创建订单明细失败"),
    CANCLEORDERNOEXSIT("0027","该订单不存在"),
    CANCLEORDERNOSUPPORT("0028","该订单不支持取消"),
    ORDERPAYREPEAT("0029","该订单已支付，请检查不要重复支付！"),
    DEPOSITPAYREPEAT("0030","该订单充值状态异常，请联系客服人员或者重新下单！"),
    RETURN_FAIL("0031","退款处理失败！"),
    NOTIFY_REPEAT("0032","重复通知！" ),
    AMOUNT_NOTEQUEAL("0033","充值金额和订单金额不一致！" ),
    CONFIRMORDERNOEXIST("0034","原订单不存在"),
    UPDATERETURNFAIL("0035", "修改退款订单状态失败"),
    USERBACKFAIL("0036", "保存用户反馈失败！"),
    SAVENOTIFYINFOERROR("0037","保存通知信息失败"),
    UPDATENOTIFYINFOERROR("0038","修改通知信息失败"),

    /*
     调用微信外部接口以1开头
    * */
    WX_CODE_GET_OPENID_FAIL("1000", "调用微信获取openid失败"),
    WX_CODE_CALL_FAIL("1001", "微信调用通讯失败"),
    WX_CODE_NOAUTH("1002","商户未开通此接口权限"),
    WX_CODE_NOTENOUGH("1003","帐号余额不足"),
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
    WX_CODE_CALLBACK_FAIL("1015", "小程序回调异常"),
    WX_CODE_CALLBACK_NO_DEPOSIT("1016","查询不到愿订单信息"),
    WX_CODE_CASH_FEE_NOT_EQUAL("1017","实际付款金额与充值金额不等" ),
    WX_CODE_TRADE_OVERDUE("1018","订单已经超过可退款的最大期限(支付后一年内可退款)"),
    WX_CODE_ERROR("1019","申请退款业务发生错误"),
    WX_CODE_USER_ACCOUNT_ABNORMAL("1020","退款请求失败"),
    WX_CODE_INVALID_REQ_TOO_MUCH("1021","连续错误请求数过多被系统短暂屏蔽"),
    WX_CODE_INVALID_TRANSACTIONID("1023","无效transaction_id"),
    WX_CODE_PARAM_ERROR("1024","参数错误"),
    WX_CODE_FREQUENCY_LIMITED("1025","2个月之前的订单申请退款有频率限制"),

    SUCCESS("0000","成功"),
    EXCEPTION("9999","服务器异常"),
    ;

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
