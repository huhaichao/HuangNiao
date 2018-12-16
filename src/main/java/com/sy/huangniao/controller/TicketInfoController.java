package com.sy.huangniao.controller;

import java.util.List;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.common.bo.RequestBody;
import com.sy.huangniao.common.bo.RespondBody;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huchao on 2018/12/15.
 */
@Slf4j
@RestController
public class TicketInfoController {

    @Autowired
    ITicketService ticketServiceImpl;

    @PostMapping(value = "/api/v1/ticket/getSiteList", produces = {"application/json;charset=utf-8"})
    public RespondBody getSiteList(RequestBody requestBody) {
        try {
            log.info("requestBody={} getSiteList......", requestBody);
            JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
            List<String[]> list = ticketServiceImpl.getSiteList(jsonObject);
            return new RespondBody(list);
        } catch (Exception e) {
            log.info(" getSiteList ex={}", e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

    @PostMapping(value = "/api/v1/ticket/getTicketList", produces = {"application/json;charset=utf-8"})
    public RespondBody getTicketList(RequestBody requestBody) {
        try {
            log.info("requestBody={} getTicketList......", requestBody);
            JSONObject jsonObject = JSONObject.parseObject(requestBody.getData());
            JSONArray list = ticketServiceImpl.getTicketInfoList(jsonObject);
            return new RespondBody(list);
        } catch (Exception e) {
            log.info(" getTicketList ex={}", e.getMessage());
            return new RespondBody(RespondMessageEnum.EXCEPTION);
        }
    }

}
