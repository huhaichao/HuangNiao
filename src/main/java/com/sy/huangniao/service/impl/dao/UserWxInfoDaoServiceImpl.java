package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.UserWxinfoMapper;
import com.sy.huangniao.pojo.UserWxinfo;
import com.sy.huangniao.service.IDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huchao on 2018/9/24.
 */
@Slf4j
@Component
public class UserWxInfoDaoServiceImpl implements IDaoService<UserWxinfo> {

    @Autowired
    private UserWxinfoMapper userWxinfoMapper;

    @Override
    public String tableName() {
        return UserWxinfo.class.getSimpleName();
    }

    @Override
    public int save(UserWxinfo userWxinfo, SqlTypeEnum sqlTypeEnum) {
        if(sqlTypeEnum==SqlTypeEnum.DEAFULT){
            return userWxinfoMapper.insertSelective(userWxinfo);
        }
        return 0;
    }

    @Override
    public int deleteByObject(UserWxinfo userWxinfo, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userWxinfoMapper.deleteByPrimaryKey(userWxinfo.getId());
        }
        return 0;
    }

    @Override
    public int updateObject(UserWxinfo userWxinfo, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userWxinfoMapper.updateByPrimaryKeySelective(userWxinfo);
        }else if(sqlType==SqlTypeEnum.UPDATEBYUSERID){
            return userWxinfoMapper.updateByUserIdAndOpenid(userWxinfo);
        }
        return 0;
    }

    @Override
    public List<UserWxinfo> selectList(UserWxinfo userWxinfo, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userWxinfoMapper.selectList(userWxinfo);
        }
        return null;
    }

    @Override
    public UserWxinfo selectObject(UserWxinfo userWxinfo, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT){
            return userWxinfoMapper.selectByPrimaryKey(userWxinfo.getId());
        }else if (sqlType ==SqlTypeEnum.SELECTOBJECTBYSELECTIVE){
            return userWxinfoMapper.selectBySelective(userWxinfo);
        }
        return null;
    }
}
