package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.TicketDetailsMapper;
import com.sy.huangniao.pojo.TicketDetails;
import com.sy.huangniao.service.IDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huchao on 2018/10/4.
 */
@Component
public class TicketDetailsServiceImpl implements IDaoService<TicketDetails> {

    @Autowired
    private TicketDetailsMapper ticketDetailsMapper;

    @Override
    public String tableName() {
        return TicketDetails.class.getSimpleName();
    }

    @Override
    public int save(TicketDetails ticketDetails, SqlTypeEnum sqlTypeEnum) {
        if (sqlTypeEnum==SqlTypeEnum.DEAFULT)
            return ticketDetailsMapper.insert(ticketDetails);
        return 0;
    }
    @Override
    public  int saveBatch(List<TicketDetails> ticketDetails, SqlTypeEnum sqlTypeEnum) {
        if (sqlTypeEnum==SqlTypeEnum.DEAFULT)
            return ticketDetailsMapper.insertBatch(ticketDetails);
        return 0;
    }

    @Override
    public int deleteByObject(TicketDetails ticketDetails, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public int updateObject(TicketDetails ticketDetails, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public List<TicketDetails> selectList(TicketDetails ticketDetails, SqlTypeEnum sqlType) {
        return null;
    }

    @Override
    public TicketDetails selectObject(TicketDetails ticketDetails, SqlTypeEnum sqlType) {
        return null;
    }
}
