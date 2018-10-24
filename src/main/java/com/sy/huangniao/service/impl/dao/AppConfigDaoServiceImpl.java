package com.sy.huangniao.service.impl.dao;

import java.util.List;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.AppConfigMapper;
import com.sy.huangniao.mapper.ReturnOrderMapper;
import com.sy.huangniao.pojo.AppConfig;
import com.sy.huangniao.pojo.ReturnOrder;
import com.sy.huangniao.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/10/7.
 */
@Component
public class AppConfigDaoServiceImpl implements IDaoService<AppConfig>{

    @Autowired
    private AppConfigMapper appConfigMapper;

    @Override
    public String tableName() {
        return AppConfig.class.getSimpleName();
    }

    @Override
    public int save(AppConfig appConfig, SqlTypeEnum sqlTypeEnum) {
        return 0;
    }

    @Override
    public int saveBatch(List<AppConfig> appConfigs, SqlTypeEnum sqlTypeEnum) {
        return 0;
    }

    @Override
    public int deleteByObject(AppConfig appConfig, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public int updateObject(AppConfig appConfig, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public List<AppConfig> selectList(AppConfig appConfig, SqlTypeEnum sqlType) {
        if (sqlType==SqlTypeEnum.DEAFULT)
            return appConfigMapper.selectList(appConfig);
        return null;
    }

    @Override
    public AppConfig selectObject(AppConfig appConfig, SqlTypeEnum sqlType) {
        return null;
    }
}
