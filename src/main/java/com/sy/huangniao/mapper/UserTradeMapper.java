package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.UserTrade;

import java.util.List;

public interface UserTradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTrade record);

    int insertSelective(UserTrade record);

    UserTrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTrade record);

    int updateByPrimaryKey(UserTrade record);

    List<UserTrade> selectList(UserTrade userTrade);
}