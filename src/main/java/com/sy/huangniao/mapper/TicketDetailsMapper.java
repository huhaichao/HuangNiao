package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.TicketDetails;

import java.util.List;


public interface TicketDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketDetails record);

    int insertBatch(List<TicketDetails> record);

    int insertSelective(TicketDetails record);

    TicketDetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketDetails record);

    int updateByPrimaryKey(TicketDetails record);
}