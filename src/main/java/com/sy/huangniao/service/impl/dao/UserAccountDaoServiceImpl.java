package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.UserAccountMapper;
import com.sy.huangniao.pojo.UserAccount;
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
public class UserAccountDaoServiceImpl implements IDaoService<UserAccount>{

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public String tableName() {
        return UserAccount.class.getSimpleName();
    }

    @Override
    public int save(UserAccount userAccount, SqlTypeEnum sqlTypeEnum) {

        if(sqlTypeEnum==SqlTypeEnum.DEAFULT){
            return userAccountMapper.insertSelective(userAccount);
        }

        return 0;
    }

    @Override
    public int deleteByObject(UserAccount userAccount, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userAccountMapper.deleteByPrimaryKey(userAccount.getId());
        }

        return 0;
    }

    @Override
    public int updateObject(UserAccount userAccount, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userAccountMapper.updateByPrimaryKeySelective(userAccount);
        }
        return 0;
    }

    @Override
    public List<UserAccount> selectList(UserAccount userAccount, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userAccountMapper.selectList(userAccount);
        }
        return null;
    }

    @Override
    public UserAccount selectObject(UserAccount userAccount, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userAccountMapper.selectByPrimaryKey(userAccount.getId());
        }
        return null;
    }
}
