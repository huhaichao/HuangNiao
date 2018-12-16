package com.sy.huangniao.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.common.Util.TrainUtil;
import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.TicketSite;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.IRedisService;
import com.sy.huangniao.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huchao on 2018/12/15.
 */
@Slf4j
@Service
public class TicketServiceImpl implements ITicketService {

    @Autowired
    HNContext hnContext;

    @Autowired
    IRedisService redisServiceImpl;

    @Override
    @PostConstruct
    public void initSite() {
        log.info(" init site start..... ");
        IDaoService<TicketSite> iDaoService = hnContext.getDaoService(TicketSite.class.getSimpleName());
        TicketSite ticketSite = new TicketSite();
        List<TicketSite> list = iDaoService.selectList(ticketSite, SqlTypeEnum.DEAFULT);
        for (TicketSite object : list) {
            StringBuilder key = new StringBuilder();
            key.append(object.getSiteZw()).append("-").append(object.getSiteDh()).append("-").append(object.getSitePy())
                .append("-").append(object.getSiteJx());
            redisServiceImpl.set(key.toString(), 1,24*60*60, TimeUnit.SECONDS);
        }
        log.info(" init site end..... ");
    }

    @Override
    public List<String[]> getSiteList(JSONObject jsonObject) {
        String  keys = jsonObject.getString("keys");
        log.info("通过keys ={}获取list",keys);
        List<String> list =redisServiceImpl.getKeys(keys,String.class);
        List<String[]> siteList = new ArrayList<>();
        for (String key:list){
            String[] keySplits = key.split("-");
            String[] sites = new String[2];
            sites[0] = keySplits[0];
            sites[1] = keySplits[1];
            siteList.add(sites);
        }
        return  siteList;
    }

    @Override
    public JSONArray getTicketInfoList(JSONObject jsonObject) {
        String departureDate=jsonObject.getString("departureDate");
        String fromSite = jsonObject.getString("fromSite");
        String toSite = jsonObject.getString("toSite");
        JSONArray tickets =TrainUtil.getTrainList(departureDate,fromSite,toSite);
        return tickets;
    }
}
