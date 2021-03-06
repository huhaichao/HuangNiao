package com.sy.huangniao.controller.context;

import com.google.common.collect.Maps;
import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.pay.IWXPaychannelsService;
import com.sy.huangniao.service.impl.AbstractUserAppService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by huchao on 2018/9/14.
 * 项目上下文
 */
@Slf4j
@Component
public class HNContext implements InitializingBean, ApplicationContextAware {


    private  ApplicationContext  springApplicationContext;

    private Map<String,IDaoService> cachedDaoService = Maps.newHashMap();

    private Map<String,AbstractUserinfoService> cachedAbstractUserinfoService = Maps.newHashMap();

    private Map<String,AbstractUserAppService> cachedAbstractUserAppService = Maps.newHashMap();

    private Map<String,IWXPaychannelsService> cachedIWXPaychannelsService = Maps.newHashMap();


    /**
     * 获取数据库处理类
     * @param tableName
     * @return
     */
    public IDaoService  getDaoService(String tableName){

        for (Map.Entry<String,IDaoService> entry :cachedDaoService.entrySet()){

            IDaoService iDaoService=   entry.getValue();
            if (iDaoService.tableName().equals(tableName)){
                return  iDaoService;
            }
        }
        return  null;
    }

    /**
     * 获取用户信息接口处理类
     * @param role
     * @return
     */
    public AbstractUserinfoService  getAbstractUserinfoService(String role){

        for (Map.Entry<String,AbstractUserinfoService> entry :cachedAbstractUserinfoService.entrySet()){
            AbstractUserinfoService abstractUserinfoService=   entry.getValue();
            if (abstractUserinfoService.getUserRole().equals(role)){
                return  abstractUserinfoService;
            }
        }
        return  null;
    }

    /**
     * 获取微信接口处理类
     * @param appCode
     * @return
     */
    public IWXPaychannelsService  getIWXPaychannelsService(AppCodeEnum appCode){

        for (Map.Entry<String,IWXPaychannelsService> entry :cachedIWXPaychannelsService.entrySet()){
            IWXPaychannelsService iwxPaychannelsService=   entry.getValue();
            if (iwxPaychannelsService.ChannelsName().equals(appCode.getCode())){
                return  iwxPaychannelsService;
            }
        }
        return  null;
    }

    /**
     * 获取用户登陆接口处理类
     * @param appCode
     * @return
     */
    public AbstractUserAppService getAbstractUserAppService(AppCodeEnum appCode){

        for (Map.Entry<String,AbstractUserAppService> entry :cachedAbstractUserAppService.entrySet()){
            AbstractUserAppService abstractUserRoleService =   entry.getValue();
            if (abstractUserRoleService.getAppCode().equals(appCode)){
                return abstractUserRoleService;
            }
        }
        return  null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        cachedDaoService = springApplicationContext.getBeansOfType(IDaoService.class);
        cachedAbstractUserinfoService = springApplicationContext.getBeansOfType(AbstractUserinfoService.class);
        cachedAbstractUserAppService =  springApplicationContext.getBeansOfType(AbstractUserAppService.class);
        cachedIWXPaychannelsService = springApplicationContext.getBeansOfType(IWXPaychannelsService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.springApplicationContext = applicationContext;
    }

    /**
     * 获取Spring上下文
     * @return
     */
    protected  ApplicationContext getSpringApplicationContext(){
        return  this.springApplicationContext;
    }
}
