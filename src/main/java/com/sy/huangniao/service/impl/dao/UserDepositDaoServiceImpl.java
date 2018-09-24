package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.UserDepositMapper;
import com.sy.huangniao.pojo.UserDeposit;
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
public class UserDepositDaoServiceImpl implements IDaoService<UserDeposit>{

    @Autowired
    private UserDepositMapper userDepositMapper;

    @Override
    public String tableName() {
        return UserDeposit.class.getSimpleName();
    }

    @Override
    public int save(UserDeposit userDeposit, SqlTypeEnum sqlTypeEnum) {
        if(sqlTypeEnum==SqlTypeEnum.DEAFULT){
            return userDepositMapper.insertSelective(userDeposit);
        }
        return 0;
    }

    @Override
    public int deleteByObject(UserDeposit userDeposit, SqlTypeEnum sqlType)
    {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userDepositMapper.deleteByPrimaryKey(userDeposit.getId());
        }
        return 0;
    }

    @Override
    public int updateObject(UserDeposit userDeposit, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userDepositMapper.updateByPrimaryKey(userDeposit);
        }
        return 0;
    }

    @Override
    public List<UserDeposit> selectList(UserDeposit userDeposit, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userDepositMapper.selectList(userDeposit);
        }
        return null;
    }

    @Override
    public UserDeposit selectObject(UserDeposit userDeposit, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userDepositMapper.selectByPrimaryKey(userDeposit.getId());
        }
        return null;
    }
}
