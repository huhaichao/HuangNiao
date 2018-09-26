package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.UserAccount;

import java.util.List;

public interface UserAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAccount record);

    int insertSelective(UserAccount record);

    UserAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAccount record);

    int updateByPrimaryKey(UserAccount record);

    List<UserAccount> selectList(UserAccount userAccount);

    UserAccount selectObjectSelective(UserAccount userAccount);

    int updateAccountAmount(UserAccount userAccount);
}