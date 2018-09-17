package com.sy.huangniao.service.impl;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.NotifyMapper;
import com.sy.huangniao.pojo.Notify;
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
public class NotifyDaoServiceImpl implements IDaoService<Notify> {


    @Autowired
    private NotifyMapper notifyMapper;

    @Override
    public String tableName() {
        return Notify.class.getSimpleName();
    }

    @Override
    public int save(Notify notify, SqlTypeEnum sqlTypeEnum) {
        if(SqlTypeEnum.DEAFULT == sqlTypeEnum)
            return  notifyMapper.insert(notify);
        return 0;
    }

    @Override
    public int deleteByObject(Notify notify, SqlTypeEnum sqlType) {
        if(SqlTypeEnum.DEAFULT == sqlType)
            return  notifyMapper.deleteByPrimaryKey(notify.getId());
        return 0;
    }

    @Override
    public int updateObject(Notify notify, SqlTypeEnum sqlType) {
        if(SqlTypeEnum.DEAFULT == sqlType)
            return  notifyMapper.updateByPrimaryKeySelective(notify);
        return 0;
    }

    @Override
    public List<Notify> selectList(Notify notify, SqlTypeEnum sqlType) {
        if(SqlTypeEnum.DEAFULT == sqlType)
            return  notifyMapper.selectList(notify);
        return null;
    }

    @Override
    public Notify selectObject(Notify notify, SqlTypeEnum sqlType) {
        if(SqlTypeEnum.DEAFULT == sqlType)
            return  notifyMapper.selectByPrimaryKey(notify.getId());
        return null;
    }
}
