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

    List<RobOrder> selectList(RobOrder record);

    RobOrder selectObjectSelective(RobOrder robOrder);

    int updateByOrderId(RobOrder robOrder);
}