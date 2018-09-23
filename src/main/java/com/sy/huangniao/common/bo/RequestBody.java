package com.sy.huangniao.common.bo;

import lombok.Data;

/**
 * Created by huchao on 2018/9/14.
 * 请求报文
 */
@Data
public class RequestBody {

    //用户角色
    private String role;

    //用户手机
    private String userPhone;

    //请求数据
    private Object data;

    //app代号
    private String appCode;
}
