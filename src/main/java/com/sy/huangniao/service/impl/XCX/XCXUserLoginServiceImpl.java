package com.sy.huangniao.service.impl.XCX;

import com.sy.huangniao.common.Util.*;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.*;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.UserInfo;
import com.sy.huangniao.pojo.UserWxinfo;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.IRedisService;
import com.sy.huangniao.service.IWXPaychannelsService;
import com.sy.huangniao.service.impl.AbstractUserLoginService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private Constant constant;

    @Autowired
    private IRedisService iRedisService;

    /**
     *
     * 重写小程序的登陆方法
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public JSONObject login (Map<String,String> map){
      //去微信获取openid
      String jsCode = map.get("code");
      log.info("userCode={} 获取openID......",jsCode);
      IWXPaychannelsService  iwxPaychannelsService =hnContext.getIWXPaychannelsService(getAppCode());
      JSONObject jsonObject = iwxPaychannelsService.code2Session(map);
      UserWxinfo userWxinfo = new UserWxinfo();
      userWxinfo.setOpenid(jsonObject.getString("openid"));
      userWxinfo.setSessionKey(jsonObject.getString("session_key"));
      userWxinfo.setUnionid(jsonObject.getString("unionid"));
      if(userWxinfo.getOpenid() == null){
          log.info("code={} 请求登陆失败 errcode={} errmsg={}",jsCode,jsonObject.getString("errcode"),jsonObject.getString("errmsg"));
          throw  new HNException(RespondMessageEnum.CODE_GET_OPENID_FAIL);
      }

      //通过openID查询登陆状
      String userid =iRedisService.get(Constant.GETUSERIDBYOPENID+getAppCode().getCode()+userWxinfo.getOpenid(),String.class);
      JSONObject jsonResult = new JSONObject();
      IDaoService iDaoService = hnContext.getDaoService(UserWxinfo.class.getSimpleName());
      if(!StringUtils.isEmpty(userid)){
          userWxinfo.setUserId(Integer.parseInt(userid));
          if(iDaoService.updateObject(userWxinfo, SqlTypeEnum.UPDATEBYUSERID)!=1){
              log.info("userid={} openid={} 修改数据失败",userid,userWxinfo.getOpenid());
              throw  new HNException(RespondMessageEnum.UPDATEWXINFOFAIL);
          }
      }else{
      //根据openid查询userid--如果没有则创建
           UserWxinfo userWxinfoSelect = new UserWxinfo();
           userWxinfoSelect.setOpenid(userWxinfo.getOpenid());
           List<UserWxinfo> list =iDaoService.selectList(userWxinfo,SqlTypeEnum.DEAFULT);
           if(list==null || list.size()==0){
              AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService(map.get("userRole"));
              UserInfo userInfo =abstractUserinfoService.createUserInfo(map);
              userid = userInfo.getId().toString();
              //保存微信信息
               userWxinfo.setUserId(userInfo.getId());
               if(iDaoService.save(userWxinfo,SqlTypeEnum.DEAFULT)!=1) {
                   log.info("userid={} openid={} userRole={} appcode ={} 保存用户微信信息失败", userid, userWxinfo.getOpenid(), userInfo.getAppCode());
                   throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
               }
           }
      }

     String salt = DateUtils.date2yyyyMMddhhmmssString(new Date())+userid;
     String loginKey = MD5Utils.getMD5String(salt).substring(0,10);
     iRedisService.set(Constant.CACHELOGINKEY+getAppCode().getCode()+userid,loginKey,Constant.loginKeyexprirTime, TimeUnit.SECONDS);
     //续租openid
     iRedisService.set(Constant.GETUSERIDBYOPENID+getAppCode().getCode()+userWxinfo.getOpenid(),userid,Constant.loginKeyexprirTime, TimeUnit.SECONDS);
     //保存session_key
     iRedisService.set(Constant.USERIDSESSIONKEY+getAppCode().getCode()+userid,userWxinfo.getSessionKey(),Constant.loginKeyexprirTime, TimeUnit.SECONDS);
     jsonResult.put("loginKey",loginKey);
     jsonResult.put("userId",userid);

     return  jsonResult;
    }

}
