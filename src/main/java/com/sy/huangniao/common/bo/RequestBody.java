package com.sy.huangniao.common.bo;

import lombok.Data;

/**
 * Created by huchao on 2018/9/14.
 * 请求报文
 */
@Data
public class RequestBody {

    //用户手机
    //private String userPhone;
    //用户id
    private String userId;

    //用户角色
    private String userRole;

    //logionkey -- header中最好
    //private String loginkey;

    //请求数据
    private String data;

    //app代号
    private String appCode;

    //终端ip
   // private String termIp;
}
