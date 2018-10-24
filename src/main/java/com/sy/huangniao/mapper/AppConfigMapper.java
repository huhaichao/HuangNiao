package com.sy.huangniao.mapper;

import java.util.List;

import com.sy.huangniao.pojo.AppConfig;

public interface AppConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppConfig record);

    int insertSelective(AppConfig record);

    AppConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppConfig record);

    int updateByPrimaryKey(AppConfig record);

    List<AppConfig> selectList(AppConfig appConfig);
}