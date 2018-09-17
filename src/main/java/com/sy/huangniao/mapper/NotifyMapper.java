package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.Notify;

import java.util.List;

public interface NotifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notify record);

    int insertSelective(Notify record);

    Notify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notify record);

    int updateByPrimaryKeyWithBLOBs(Notify record);

    int updateByPrimaryKey(Notify record);

    List<Notify> selectList(Notify notify);
}