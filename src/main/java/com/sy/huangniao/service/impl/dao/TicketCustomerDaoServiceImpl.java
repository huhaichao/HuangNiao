package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.TicketCustomerMapper;
import com.sy.huangniao.pojo.TicketCustomer;
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
public class TicketCustomerDaoServiceImpl implements IDaoService<TicketCustomer>{

    @Autowired
    private TicketCustomerMapper ticketCustomerMapper;

    @Override
    public String tableName() {
        return TicketCustomer.class.getSimpleName();
    }

    @Override
    public int save(TicketCustomer ticketCustomer, SqlTypeEnum sqlType) {
        if(sqlType ==  SqlTypeEnum.DEAFULT)
          return ticketCustomerMapper.insert(ticketCustomer);
        return  0;
    }

    @Override
    public int deleteByObject(TicketCustomer ticketCustomer,SqlTypeEnum sqlType) {
        if(sqlType ==  SqlTypeEnum.DEAFULT)
            return   ticketCustomerMapper.deleteByPrimaryKey(ticketCustomer.getId());
        return 0;
    }

    @Override
    public int updateObject(TicketCustomer ticketCustomer,SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT)
            return ticketCustomerMapper.updateByPrimaryKeySelective(ticketCustomer);
        return 0;
    }

    @Override
    public List<TicketCustomer> selectList(TicketCustomer ticketCustomer,SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT)
            return ticketCustomerMapper.selectList(ticketCustomer);
        return null;
    }

    @Override
    public TicketCustomer selectObject(TicketCustomer ticketCustomer,SqlTypeEnum sqlType) {
        if(sqlType==SqlTypeEnum.DEAFULT)
            return ticketCustomerMapper.selectByPrimaryKey(ticketCustomer.getId());
        return null;
    }
}
