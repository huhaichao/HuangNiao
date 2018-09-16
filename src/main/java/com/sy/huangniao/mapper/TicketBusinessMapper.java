package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.TicketBusiness;

import java.util.List;

public interface TicketBusinessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketBusiness record);

    int insertSelective(TicketBusiness record);

    TicketBusiness selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketBusiness record);

    int updateByPrimaryKey(TicketBusiness record);

    public List<TicketBusiness> selectList(TicketBusiness record);

    int updateByUserIdSelective (TicketBusiness ticketBusiness);

    TicketBusiness selectBySelective(TicketBusiness ticketBusiness);

}