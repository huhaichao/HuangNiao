package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.UserBack;

public interface UserBackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBack record);

    int insertSelective(UserBack record);

    UserBack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBack record);

    int updateByPrimaryKeyWithBLOBs(UserBack record);

    int updateByPrimaryKey(UserBack record);
}