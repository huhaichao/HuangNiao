package com.sy.huangniao.controller;

import com.sy.huangniao.common.bo.RequestBody;
import com.sy.huangniao.common.bo.RespondBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huchao on 2018/9/25.
 */
@RestController
public class OtherPartyController {


    /**
     * 手机号验证码接口
     */
    @PostMapping(value="/api/v1/OtherParty/checkPhoneCode",produces = {"application/json;charset=utf-8"})
    public RespondBody checkPhoneCode(RequestBody requestBody){
        return  null;
    }


    /**
     * 实名认证接口
     */
    @PostMapping(value="/api/v1/OtherParty/realName",produces = {"application/json;charset=utf-8"})
    public RespondBody realName(RequestBody requestBody){
        return  null;
    }





}
