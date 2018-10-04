package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.UserLinkmanMapper;
import com.sy.huangniao.pojo.UserLinkman;
import com.sy.huangniao.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huchao on 2018/10/4.
 */
@Component
public class UserLinkmanDaoServiceImpl  implements IDaoService<UserLinkman> {

    @Autowired
    private UserLinkmanMapper userLinkmanMapper;

    @Override
    public String tableName() {
        return UserLinkman.class.getSimpleName();
    }

    @Override
    public int save(UserLinkman userLinkman, SqlTypeEnum sqlTypeEnum) {

        if(sqlTypeEnum==SqlTypeEnum.DEAFULT){
            return userLinkmanMapper.insert(userLinkman);
        }

        return 0;
    }

    @Override
    public int deleteByObject(UserLinkman userLinkman, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userLinkmanMapper.deleteByPrimaryKey(userLinkman.getId());
        }
        return 0;
    }

    @Override
    public int updateObject(UserLinkman userLinkman, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userLinkmanMapper.updateByPrimaryKeySelective(userLinkman);
        }
        return 0;
    }

    @Override
    public List<UserLinkman> selectList(UserLinkman userLinkman, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userLinkmanMapper.selectList(userLinkman);
        }
        return null;
    }

    @Override
    public UserLinkman selectObject(UserLinkman userLinkman, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userLinkmanMapper.selectByPrimaryKey(userLinkman.getId());
        }
        return null;
    }
}
