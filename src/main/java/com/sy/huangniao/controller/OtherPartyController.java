package com.sy.huangniao.controller;

import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.common.Util.MD5Utils;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.bo.RequestBody;
import com.sy.huangniao.common.bo.RespondBody;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huchao on 2018/9/25.
 */
@Slf4j
@RestController
public class OtherPartyController {

    @Autowired
    private Constant constant;



    /**
     * 实名认证接口
     */
    @PostMapping(value="/api/v1/OtherParty/realName",produces = {"application/json;charset=utf-8"})
    public RespondBody realName(RequestBody requestBody){
        return  null;
    }





}
