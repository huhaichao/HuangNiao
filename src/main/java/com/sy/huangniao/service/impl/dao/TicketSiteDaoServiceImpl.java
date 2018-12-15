package com.sy.huangniao.service.impl.dao;

import java.util.List;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.TicketSiteMapper;
import com.sy.huangniao.pojo.TicketSite;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.impl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/12/15.
 */
@Component
public class TicketSiteDaoServiceImpl implements IDaoService<TicketSite> {

    @Autowired
    TicketSiteMapper ticketSiteMapper;


    @Override
    public String tableName() {
        return TicketSite.class.getSimpleName();
    }

    @Override
    public int save(TicketSite ticketSite, SqlTypeEnum sqlTypeEnum) {
        return 0;
    }

    @Override
    public int saveBatch(List<TicketSite> ticketSites, SqlTypeEnum sqlTypeEnum) {
        return 0;
    }

    @Override
    public int deleteByObject(TicketSite ticketSite, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public int updateObject(TicketSite ticketSite, SqlTypeEnum sqlType) {
        return 0;
    }

    @Override
    public List<TicketSite> selectList(TicketSite ticketSite, SqlTypeEnum sqlType) {
        if(sqlType == SqlTypeEnum.DEAFULT)
         return ticketSiteMapper.selectList(ticketSite);
        return  null;
    }

    @Override
    public TicketSite selectObject(TicketSite ticketSite, SqlTypeEnum sqlType) {
        return null;
    }
}
