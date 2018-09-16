package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.TicketOrder;

import java.util.List;

public interface TicketOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketOrder record);

    int insertSelective(TicketOrder record);

    TicketOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketOrder record);

    int updateByPrimaryKey(TicketOrder record);

    public List<TicketOrder> selectList(TicketOrder record);

    int updateByOrderIdSelective(TicketOrder ticketOrder);
}