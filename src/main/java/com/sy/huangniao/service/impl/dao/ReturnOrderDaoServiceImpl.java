package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.ReturnOrderMapper;
import com.sy.huangniao.pojo.ReturnOrder;
import com.sy.huangniao.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huchao on 2018/10/7.
 */
@Component
public class ReturnOrderDaoServiceImpl  implements IDaoService<ReturnOrder>{

    @Autowired
    private ReturnOrderMapper returnOrderMapper;

    @Override
    public String tableName() {
        return ReturnOrder.class.getSimpleName();
    }

    @Override
    public int save(ReturnOrder returnOrder, SqlTypeEnum sqlTypeEnum) {
        if(SqlTypeEnum.DEAFULT == sqlTypeEnum)
            return  returnOrderMapper.insert(returnOrder);
        return 0;
    }

    @Override
    public int saveBatch(List<ReturnOrder> returnOrders, SqlTypeEnum sqlTypeEnum) {
        return 0;
    }

    @Override
    public int deleteByObject(ReturnOrder returnOrder, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public int updateObject(ReturnOrder returnOrder, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT)
            return  returnOrderMapper.updateByPrimaryKeySelective(returnOrder);
        return 0;
    }

    @Override
    public List<ReturnOrder> selectList(ReturnOrder returnOrder, SqlTypeEnum sqlType) {
        if (sqlType==SqlTypeEnum.DEAFULT)
            return returnOrderMapper.selectList(returnOrder);
        return null;
    }

    @Override
    public ReturnOrder selectObject(ReturnOrder returnOrder, SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT)
            return  returnOrderMapper.selectByPrimaryKey(returnOrder.getId());
        else if (sqlType==SqlTypeEnum.SELECTOBJECTBYSELECTIVE)
            return  returnOrderMapper.selectByObjectBySelecttive(returnOrder);
        return null;
    }
}
