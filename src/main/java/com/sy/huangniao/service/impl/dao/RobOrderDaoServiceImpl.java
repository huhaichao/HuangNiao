package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.RobOrderMapper;
import com.sy.huangniao.pojo.RobOrder;
import com.sy.huangniao.service.IDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huchao on 2018/9/12.
 */
@Slf4j
@Component
public class RobOrderDaoServiceImpl implements IDaoService<RobOrder>{

    @Autowired
    private RobOrderMapper robOrderMapper;

    @Override
    public String tableName() {
        return RobOrder.class.getSimpleName();
    }

    @Override
    public int save(RobOrder robOrder,SqlTypeEnum sqlType) {
        if(sqlType == SqlTypeEnum.DEAFULT){
            return  robOrderMapper.insert(robOrder);
        }
        return 0;
    }

    @Override
    public int deleteByObject(RobOrder robOrder,SqlTypeEnum sqlType) {
        if (sqlType == SqlTypeEnum.DEAFULT) {
            return  robOrderMapper.deleteByPrimaryKey(robOrder.getId());
        }
        return 0;
    }

    @Override
    public int updateObject(RobOrder robOrder,SqlTypeEnum sqlType) {
        if (sqlType==SqlTypeEnum.DEAFULT)
            return  robOrderMapper.updateByPrimaryKeySelective(robOrder);
        return 0;
    }

    @Override
    public List<RobOrder> selectList(RobOrder robOrder,SqlTypeEnum sqlType) {
        if (sqlType == SqlTypeEnum.DEAFULT)
            return   robOrderMapper.selectList(robOrder);
        return null;
    }

    @Override
    public RobOrder selectObject(RobOrder robOrder,SqlTypeEnum sqlType) {
        if (sqlType==SqlTypeEnum.DEAFULT)
            return   robOrderMapper.selectByPrimaryKey(robOrder.getId());
        return null;
    }
}
