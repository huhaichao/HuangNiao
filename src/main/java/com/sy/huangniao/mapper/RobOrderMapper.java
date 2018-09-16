package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.RobOrder;

import java.util.List;

public interface RobOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RobOrder record);

    int insertSelective(RobOrder record);

    RobOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RobOrder record);

    int updateByPrimaryKey(RobOrder record);

    public List<RobOrder> selectList(RobOrder record);
}