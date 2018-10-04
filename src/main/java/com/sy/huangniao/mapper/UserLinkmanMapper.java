package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.UserLinkman;

import java.util.List;

public interface UserLinkmanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLinkman record);

    int insertSelective(UserLinkman record);

    UserLinkman selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLinkman record);

    int updateByPrimaryKey(UserLinkman record);

    List<UserLinkman> selectList(UserLinkman userLinkman);
}