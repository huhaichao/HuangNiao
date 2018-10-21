package com.sy.huangniao.mapper;

import java.util.List;

import com.sy.huangniao.pojo.ReturnOrder;

public interface ReturnOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReturnOrder record);

    int insertSelective(ReturnOrder record);

    ReturnOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReturnOrder record);

    int updateByPrimaryKey(ReturnOrder record);

    ReturnOrder selectByObjectBySelecttive(Integer id);

    List<ReturnOrder> selectList(ReturnOrder returnOrder);
}