package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.TicketCustomer;

import java.util.List;

public interface TicketCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketCustomer record);

    int insertSelective(TicketCustomer record);

    TicketCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketCustomer record);

    int updateByPrimaryKey(TicketCustomer record);

    public List<TicketCustomer> selectList(TicketCustomer record);
}