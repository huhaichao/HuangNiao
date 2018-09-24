package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.UserWithdrawMapper;
import com.sy.huangniao.pojo.UserWithdraw;
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
public class UserWithdrawDaoServicdeImpl implements IDaoService<UserWithdraw> {

    @Autowired
    private UserWithdrawMapper userWithdrawMapper;

    @Override
    public String tableName() {
        return UserWithdraw.class.getSimpleName();
    }

    @Override
    public int save(UserWithdraw userWithdraw, SqlTypeEnum sqlTypeEnum) {
        if(sqlTypeEnum==SqlTypeEnum.DEAFULT){
            return userWithdrawMapper.insertSelective(userWithdraw);
        }
        return 0;
    }

    @Override
    public int deleteByObject(UserWithdraw userWithdraw, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userWithdrawMapper.deleteByPrimaryKey(userWithdraw.getId());
        }
        return 0;
    }

    @Override
    public int updateObject(UserWithdraw userWithdraw, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userWithdrawMapper.updateByPrimaryKeySelective(userWithdraw);
        }
        return 0;
    }

    @Override
    public List<UserWithdraw> selectList(UserWithdraw userWithdraw, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userWithdrawMapper.selectList(userWithdraw);
        }
        return null;
    }

    @Override
    public UserWithdraw selectObject(UserWithdraw userWithdraw, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userWithdrawMapper.selectByPrimaryKey(userWithdraw.getId());
        }
        return null;
    }
}
