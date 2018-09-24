package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.UserWithdraw;

import java.util.List;

public interface UserWithdrawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserWithdraw record);

    int insertSelective(UserWithdraw record);

    UserWithdraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserWithdraw record);

    int updateByPrimaryKey(UserWithdraw record);

    List<UserWithdraw> selectList(UserWithdraw userWithdraw);
}