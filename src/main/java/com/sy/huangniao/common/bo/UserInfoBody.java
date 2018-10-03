package com.sy.huangniao.common.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Created by huchao on 2018/9/14.
 *
 */
@Data
public class UserInfoBody {

    private String userId;

    /**
     * 用户手机号
     */
    private String userPhoneno;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户角色
     */
    private String userRole;

    /**
     * 用户微信号
     */
    private String userWxno;

    /**
     * 用户状态
     */
    private String userStatus;

    /**
     * 用户生日
     */
    @JSONField(format = "yyyy-mm-dd")
    private Date userBirthday;

    /** 身份证
     */
    private String userIdentity;

    /**
     * 姓名
     */
    private String realName;


    /**
     * 如果分润比率 -- 商户返回
     */
    private String benefitRate;

    /**
     * 客户12306账号 -- 客户返回
     */
    private String customerAccount;

    /**
     * 客户12306密码 -- 客户返回
     */
   // private String customerPassword;

}
