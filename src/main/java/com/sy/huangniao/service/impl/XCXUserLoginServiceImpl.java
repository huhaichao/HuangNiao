package com.sy.huangniao.service.impl;

import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.pojo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by huchao on 2018/9/24.
 *
 * 小程序登陆注册处理类
 *
 *
 */
@Slf4j
@Component
public class XCXUserLoginServiceImpl extends AbstractUserLoginService {

    @Override
    public AppCodeEnum getAppCode() {
        return AppCodeEnum.XCX;
    }


    /**
     *
     * 重写小程序的登陆方法
     *
     * @param
     * @return
     */
    @Override
    public JSONObject login (Map<String,String> map){

      return  null;
    }

}
