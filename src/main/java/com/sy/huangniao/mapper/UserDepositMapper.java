package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.UserDeposit;

import java.util.List;

public interface UserDepositMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDeposit record);

    int insertSelective(UserDeposit record);

    UserDeposit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDeposit record);

    int updateByPrimaryKey(UserDeposit record);

    List<UserDeposit> selectList(UserDeposit userDeposit);

    UserDeposit selectObjectBySelective(UserDeposit userDeposit);

    int updateByIDAndStatus(UserDeposit userDeposit);
}