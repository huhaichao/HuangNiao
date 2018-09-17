package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.RobOrderAudit;

import java.util.List;

public interface RobOrderAuditMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RobOrderAudit record);

    int insertSelective(RobOrderAudit record);

    RobOrderAudit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RobOrderAudit record);

    int updateByPrimaryKey(RobOrderAudit record);

    List<RobOrderAudit> selectList(RobOrderAudit robOrderAudit);
}