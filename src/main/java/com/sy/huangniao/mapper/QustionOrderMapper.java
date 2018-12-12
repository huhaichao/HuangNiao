package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.QustionOrder;

public interface QustionOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QustionOrder record);

    int insertSelective(QustionOrder record);

    QustionOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QustionOrder record);

    int updateByPrimaryKey(QustionOrder record);
}