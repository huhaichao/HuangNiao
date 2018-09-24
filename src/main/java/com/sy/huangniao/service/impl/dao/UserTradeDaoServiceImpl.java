package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.UserTradeMapper;
import com.sy.huangniao.pojo.UserTrade;
import com.sy.huangniao.service.IDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huchao on 2018/9/23.
 */
@Slf4j
@Component
public class UserTradeDaoServiceImpl implements IDaoService<UserTrade> {

    @Autowired
    private UserTradeMapper userTradeMapper;

    @Override
    public String tableName() {
        return UserTrade.class.getSimpleName();
    }

    @Override
    public int save(UserTrade userTrade, SqlTypeEnum sqlTypeEnum) {
        if(sqlTypeEnum==SqlTypeEnum.DEAFULT){
            return userTradeMapper.insertSelective(userTrade);
        }
        return 0;
    }

    @Override
    public int deleteByObject(UserTrade userTrade, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userTradeMapper.deleteByPrimaryKey(userTrade.getId());
        }
        return 0;
    }

    @Override
    public int updateObject(UserTrade userTrade, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userTradeMapper.updateByPrimaryKeySelective(userTrade);
        }
        return 0;
    }

    @Override
    public List<UserTrade> selectList(UserTrade userTrade, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userTradeMapper.selectList(userTrade);
        }
        return null;
    }

    @Override
    public UserTrade selectObject(UserTrade userTrade, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userTradeMapper.selectByPrimaryKey(userTrade.getId());
        }
        return null;
    }
}
