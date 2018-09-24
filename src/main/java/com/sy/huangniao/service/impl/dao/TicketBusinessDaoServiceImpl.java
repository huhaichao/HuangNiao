package com.sy.huangniao.service.impl.dao;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.mapper.TicketBusinessMapper;
import com.sy.huangniao.pojo.TicketBusiness;
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
public class TicketBusinessDaoServiceImpl implements IDaoService<TicketBusiness>{

    @Autowired
    private TicketBusinessMapper ticketBusinessMapper;

    @Override
    public String tableName() {
        return TicketBusiness.class.getSimpleName();
    }

    @Override
    public int save(TicketBusiness ticketBusiness,SqlTypeEnum sqlType) {
        if (sqlType ==SqlTypeEnum.DEAFULT )
           return ticketBusinessMapper.insert(ticketBusiness);
        return  0;
    }

    @Override
    public int deleteByObject(TicketBusiness ticketBusiness,SqlTypeEnum sqlType) {
        if (sqlType ==SqlTypeEnum.DEAFULT )
          return   ticketBusinessMapper.deleteByPrimaryKey(ticketBusiness.getId());
        return 0;
    }

    @Override
    public int updateObject(TicketBusiness ticketBusiness,SqlTypeEnum sqlType) {
        if (sqlType ==SqlTypeEnum.DEAFULT )
            return   ticketBusinessMapper.updateByPrimaryKeySelective(ticketBusiness);
        else  if(sqlType == SqlTypeEnum.UPDATEBYUSERID)
            return   ticketBusinessMapper.updateByUserIdSelective(ticketBusiness);
        return 0;
    }

    @Override
    public List<TicketBusiness> selectList(TicketBusiness ticketBusiness,SqlTypeEnum sqlType) {
        if (sqlType ==SqlTypeEnum.DEAFULT )
            return   ticketBusinessMapper.selectList(ticketBusiness);
        return null;
    }

    @Override
    public TicketBusiness selectObject(TicketBusiness ticketBusiness,SqlTypeEnum sqlType) {
        if (sqlType ==SqlTypeEnum.DEAFULT )
            return   ticketBusinessMapper.selectByPrimaryKey(ticketBusiness.getId());
        else if (sqlType == SqlTypeEnum.SELECTOBJECTBYSELECTIVE)
            return   ticketBusinessMapper.selectBySelective(ticketBusiness);
        return null;
    }
}
