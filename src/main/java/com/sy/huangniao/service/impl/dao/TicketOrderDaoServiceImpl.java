package com.sy.huangniao.service.impl.dao;


import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.TicketOrderMapper;
import com.sy.huangniao.pojo.TicketOrder;
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
public class TicketOrderDaoServiceImpl implements IDaoService<TicketOrder>{

    @Autowired
    private TicketOrderMapper ticketOrderMapper;

    @Override
    public String tableName() {
        return TicketOrder.class.getSimpleName();
    }

    @Override
    public int save(TicketOrder ticketOrder,SqlTypeEnum sqlType) {
        if (sqlType==SqlTypeEnum.DEAFULT)
         return ticketOrderMapper.insert(ticketOrder);
        return 0;
    }

    @Override
    public int deleteByObject(TicketOrder ticketOrder,SqlTypeEnum sqlType) {
        if (sqlType == SqlTypeEnum.DEAFULT)
            return  ticketOrderMapper.deleteByPrimaryKey(ticketOrder.getId());
        return 0;
    }

    @Override
    public int updateObject(TicketOrder ticketOrder,SqlTypeEnum sqlType) {
        if (sqlType==SqlTypeEnum.DEAFULT)
            return  ticketOrderMapper.updateByPrimaryKeySelective(ticketOrder);
        return 0;
    }

    @Override
    public List<TicketOrder> selectList(TicketOrder ticketOrder,SqlTypeEnum sqlType) {
        if (sqlType==SqlTypeEnum.DEAFULT)
            return  ticketOrderMapper.selectList(ticketOrder);
        return null;
    }

    @Override
    public TicketOrder selectObject(TicketOrder ticketOrder,SqlTypeEnum sqlType) {
        if (sqlType==SqlTypeEnum.DEAFULT)
            return  ticketOrderMapper.selectByPrimaryKey(ticketOrder.getId());
        else if (sqlType==SqlTypeEnum.SELECTOBJECTBYSELECTIVE)
            return  ticketOrderMapper.selectObjectBySelective(ticketOrder);
        return null;
    }
}
