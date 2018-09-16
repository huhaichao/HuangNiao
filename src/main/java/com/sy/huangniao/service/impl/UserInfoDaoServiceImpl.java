package com.sy.huangniao.service.impl;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.UserInfoMapper;
import com.sy.huangniao.pojo.UserInfo;
import com.sy.huangniao.service.IDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huchao on 2018/9/12.
 */
@Slf4j
@Component
public class UserInfoDaoServiceImpl implements IDaoService<UserInfo>{

    @Autowired
    private UserInfoMapper userInfoMapper ;

    @Override
    public String tableName() {
        return UserInfo.class.getSimpleName();
    }

    @Override
    public int save(UserInfo userInfo,SqlTypeEnum sqlType) {
        if (sqlType == SqlTypeEnum.DEAFULT)
           return userInfoMapper.insert(userInfo);
        return 0;
    }

    @Override
    public int deleteByObject(UserInfo userInfo, SqlTypeEnum sqlType ) {
        if (sqlType == SqlTypeEnum.DEAFULT)
            return  userInfoMapper.deleteByPrimaryKey(userInfo.getId());
        return 0;
    }

    @Override
    public int updateObject(UserInfo userInfo,SqlTypeEnum sqlType) {
        if (sqlType == SqlTypeEnum.DEAFULT)
            return  userInfoMapper.updateByPrimaryKeySelective(userInfo);
        return 0;
    }

    @Override
    public List<UserInfo> selectList(UserInfo userInfo,SqlTypeEnum sqlType) {
        if (sqlType == SqlTypeEnum.DEAFULT)
            return  userInfoMapper.selectList(userInfo);
        return null;
    }

    @Override
    public UserInfo selectObject(UserInfo userInfo,SqlTypeEnum sqlType) {
        if (sqlType == SqlTypeEnum.DEAFULT)
            return  userInfoMapper.selectByPrimaryKey(userInfo.getId());
        return null;
    }
}
