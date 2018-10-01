package com.sy.huangniao.controller;


import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.service.impl.AbstractUserAppService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by huchao on 2018/9/25.
 */
@RestController("/api/")
public class PayInfoController {

    @Autowired
    private HNContext hnContext;


    /**
     * 统一回调接口
     * @return
     */
    @RequestMapping(value = "/{appCode}/callback",method = {RequestMethod.GET,RequestMethod.POST}
                     ,produces = {"application/json;charset=utf-8"})
    public  String  callback(@PathVariable("appCode") String appCode,HttpServletRequest
             request){
          AbstractUserAppService abstractUserAppService =  hnContext.getAbstractUserAppService(AppCodeEnum.valueOf(appCode));
          return  abstractUserAppService.callback(request);
          /*Map<String,String[]> maps = request.getParameterMap();
          JSONObject  jsonObject = new JSONObject();
          for (Map.Entry<String,String[]> entry : maps.entrySet()){
              String key = entry.getKey();
              String[] string = entry.getValue();
              jsonObject.put(key,string[0]);
          }
          AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(AppCodeEnum.valueOf(appCode.toUpperCase()));
          return  abstractUserAppService.callback(jsonObject);*/
    }


}