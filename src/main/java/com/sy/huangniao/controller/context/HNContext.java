package com.sy.huangniao.controller.context;

import com.google.common.collect.Maps;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.UserInfoService;
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

    private Map<String,UserInfoService> cachedUserInfoService = Maps.newHashMap();

    /**
     * 获取数据库处理类
     * @param tableName
     * @return
     */
    public IDaoService  getDaoService(String tableName){
        ;
        for (Map.Entry<String,IDaoService> entry :cachedDaoService.entrySet()){

            IDaoService iDaoService=   entry.getValue();
            if (iDaoService.tableName().equals(tableName)){
                return  iDaoService;
            }
        }
        return  null;
    }

    /**
     * 获取接口处理类
     * @param role
     * @return
     */
    public UserInfoService  getUserInfoService(String role){
        ;
        for (Map.Entry<String,UserInfoService> entry :cachedUserInfoService.entrySet()){

            UserInfoService userInfoService=   entry.getValue();
            if (userInfoService.getUserRole().equals(role)){
                return  userInfoService;
            }
        }
        return  null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cachedDaoService = springApplicationContext.getBeansOfType(IDaoService.class);
        cachedUserInfoService = springApplicationContext.getBeansOfType(UserInfoService.class);
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
