package com.sy.huangniao.mapper;

import java.util.List;

import com.sy.huangniao.pojo.TicketSite;

public interface TicketSiteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketSite record);

    int insertSelective(TicketSite record);

    TicketSite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketSite record);

    int updateByPrimaryKey(TicketSite record);

    List<TicketSite> selectList(TicketSite ticketSite);
}