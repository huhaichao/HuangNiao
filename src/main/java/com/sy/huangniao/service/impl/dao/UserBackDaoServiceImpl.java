package com.sy.huangniao.service.impl.dao;

import java.util.List;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.UserBackMapper;
import com.sy.huangniao.pojo.UserBack;
import com.sy.huangniao.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/10/27.
 */
@Component
public class UserBackDaoServiceImpl implements IDaoService<UserBack> {

    @Autowired
    private UserBackMapper userBackMapper;

    @Override
    public String tableName() {
        return UserBack.class.getSimpleName();
    }

    @Override
    public int save(UserBack userBack, SqlTypeEnum sqlTypeEnum) {
        if(sqlTypeEnum==SqlTypeEnum.DEAFULT)
          return userBackMapper.insert(userBack);
        return 0;
    }

    @Override
    public int deleteByObject(UserBack userBack, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public int updateObject(UserBack userBack, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public List<UserBack> selectList(UserBack userBack, SqlTypeEnum sqlType) {
        return null;
    }

    @Override
    public UserBack selectObject(UserBack userBack, SqlTypeEnum sqlType) {
        return null;
    }
}
