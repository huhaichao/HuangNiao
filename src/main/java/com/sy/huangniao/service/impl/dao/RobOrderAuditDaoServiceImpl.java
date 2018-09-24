package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.RobOrderAuditMapper;
import com.sy.huangniao.pojo.RobOrderAudit;
import com.sy.huangniao.service.IDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huchao on 2018/9/17.
 */
@Slf4j
@Component
public class RobOrderAuditDaoServiceImpl implements IDaoService<RobOrderAudit> {

    @Autowired
    private RobOrderAuditMapper robOrderAuditMapper;

    @Override
    public String tableName() {
        return RobOrderAudit.class.getSimpleName();
    }

    @Override
    public int save(RobOrderAudit robOrderAudit, SqlTypeEnum sqlTypeEnum) {
        if(SqlTypeEnum.DEAFULT == sqlTypeEnum)
            return  robOrderAuditMapper.insert(robOrderAudit);
        return 0;
    }

    @Override
    public int deleteByObject(RobOrderAudit robOrderAudit, SqlTypeEnum sqlType) {
        if(SqlTypeEnum.DEAFULT == sqlType)
            return  robOrderAuditMapper.deleteByPrimaryKey(robOrderAudit.getId());
        return 0;
    }

    @Override
    public int updateObject(RobOrderAudit robOrderAudit, SqlTypeEnum sqlType) {
        if(SqlTypeEnum.DEAFULT == sqlType)
            return  robOrderAuditMapper.updateByPrimaryKeySelective(robOrderAudit);
        return 0;
    }

    @Override
    public List<RobOrderAudit> selectList(RobOrderAudit robOrderAudit, SqlTypeEnum sqlType) {
        if(SqlTypeEnum.DEAFULT == sqlType)
            return  robOrderAuditMapper.selectList(robOrderAudit);
        return null;
    }

    @Override
    public RobOrderAudit selectObject(RobOrderAudit robOrderAudit, SqlTypeEnum sqlType) {
        if(SqlTypeEnum.DEAFULT == sqlType)
            return  robOrderAuditMapper.selectByPrimaryKey(robOrderAudit.getId());
        return null;
    }
}
