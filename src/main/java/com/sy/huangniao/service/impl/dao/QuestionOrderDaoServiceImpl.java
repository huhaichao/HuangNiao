package com.sy.huangniao.service.impl.dao;

import java.util.List;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.NotifyMapper;
import com.sy.huangniao.mapper.QustionOrderMapper;
import com.sy.huangniao.pojo.Notify;
import com.sy.huangniao.pojo.QustionOrder;
import com.sy.huangniao.service.IDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/9/17.
 */
@Slf4j
@Component
public class QuestionOrderDaoServiceImpl implements IDaoService<QustionOrder> {


    @Autowired
    private QustionOrderMapper qustionOrderMapper;

    @Override
    public String tableName() {
        return QustionOrder.class.getSimpleName();
    }

    @Override
    public int save(QustionOrder qustionOrder, SqlTypeEnum sqlTypeEnum) {
        if(sqlTypeEnum == SqlTypeEnum.DEAFULT){
          return   qustionOrderMapper.insert(qustionOrder);
        }
        return 0;
    }

    @Override
    public int deleteByObject(QustionOrder qustionOrder, SqlTypeEnum sqlType) {
        if(sqlType == SqlTypeEnum.DEAFULT){
            return   qustionOrderMapper.deleteByPrimaryKey(qustionOrder.getId());
        }
        return 0;
    }

    @Override
    public int updateObject(QustionOrder qustionOrder, SqlTypeEnum sqlType) {
        if(sqlType == SqlTypeEnum.DEAFULT){
            return   qustionOrderMapper.updateByPrimaryKeySelective(qustionOrder);
        }
        return 0;
    }

    @Override
    public List<QustionOrder> selectList(QustionOrder qustionOrder, SqlTypeEnum sqlType) {
        return null;
    }

    @Override
    public QustionOrder selectObject(QustionOrder qustionOrder, SqlTypeEnum sqlType) {
        return null;
    }

}
