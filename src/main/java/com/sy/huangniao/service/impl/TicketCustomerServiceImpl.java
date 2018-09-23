package com.sy.huangniao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.enums.OrderStatusEnum;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.common.enums.UserRoleEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.pojo.*;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.TicketCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huchao on 2018/9/14.
 */
@Slf4j
@Component
public class TicketCustomerServiceImpl extends  AbstractUserinfoService implements TicketCustomerService{
    @Override
    public String getUserRole() {
        return UserRoleEnum.CUSTOMER.getRole();
    }

    @Override
    public boolean createOrder(Map<String, String> m) {
        TicketOrder ticketOrder = new TicketOrder();
        BeanUtils.copyProperties(m,ticketOrder);
        log.info("userid={} 下单中....",ticketOrder.getUserId());
        ticketOrder.setOrderStatus(OrderStatusEnum.WAITROB.getStatus());
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        if(iDaoService.save(ticketOrder,SqlTypeEnum.DEAFULT)!=1){
            log.info("下单失败 userid={} orderid={}",ticketOrder.getUserId(),ticketOrder.getId());
            throw new HNException(RespondMessageEnum.CREATORDERFAIL);
        }
        return true;
    }

    @Override
    public String getOrderList(Map<String, String> m) {
        TicketOrder ticketOrder = new TicketOrder();
        BeanUtils.copyProperties(m,ticketOrder);
        log.info("userid={} 查询订单....",ticketOrder.getUserId());
        int pageNum  = Integer.parseInt(m.get("pageNum"));
        int pageSize = Integer.parseInt(m.get("pageSize"));
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        List<TicketOrder> list = iDaoService.selectList(ticketOrder,SqlTypeEnum.DEAFULT);
        JSONObject reulstList = new JSONObject();
        reulstList.put("total",page.getTotal());
        reulstList.put("pageNum",pageNum);
        reulstList.put("data",list);
        log.info("userid={} list={} 查询订单成功....",ticketOrder.getUserId(),list);
        return  reulstList.toJSONString();
    }

    @Override
    public boolean confirmeOrder(Map<String, String> m) {
        TicketOrder ticketOrder = new TicketOrder();
        BeanUtils.copyProperties(m,ticketOrder);
        ticketOrder.setOrderStatus(OrderStatusEnum.SUCCESS.getStatus());
        ticketOrder.setModifyDate(new Date());
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        if(iDaoService.updateObject(ticketOrder,SqlTypeEnum.DEAFULT)==1){
           //todo 通知平台付款
           //todo 通知商户 和 其他商户已出票

        }
        return true;
    }

    @Override
    public boolean cancleOrder(int userId, int orderId) {
        return false;
    }


    @Override
    public boolean addContacts() {
        return false;
    }


    @Override
    public boolean returnTicket() {
        return false;
    }

    @Override
    public boolean saveRoleInfo(UserInfoBody userInfoBody) {
        TicketCustomer ticketCustomer = new TicketCustomer();
        IDaoService iDaoService = hnContext.getDaoService(TicketCustomer.class.getSimpleName());
        if (StringUtils.isEmpty(userInfoBody.getUserIdentity())){
            log.info("保存用户信息...userId{} ",userInfoBody.getUserId());
            ticketCustomer.setCustomerStatus(userInfoBody.getUserStatus());
            ticketCustomer.setUserId(userInfoBody.getUserId());
            ticketCustomer.setCustomerAccount(userInfoBody.getCustomerAccount());
            ticketCustomer.setCustomerPassword(userInfoBody.getCustomerAccount());
            if(iDaoService.save(ticketCustomer, SqlTypeEnum.DEAFULT)!=1){
                log.info("保存用户信息失败userId{} 保存成功多条",userInfoBody.getUserId());
                throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
            }
        }else {
            log.info("修改用户信息...userId{} ",userInfoBody.getUserId());
            ticketCustomer.setCustomerStatus(userInfoBody.getUserStatus());
            ticketCustomer.setUserId(userInfoBody.getUserId());
            ticketCustomer.setCustomerName(userInfoBody.getRealName());
            ticketCustomer.setIdentityImage(userInfoBody.getUserImage());
            ticketCustomer.setCustomerIdentity(userInfoBody.getUserIdentity());
            ticketCustomer.setModifyDate(new Date());
            ticketCustomer.setCustomerAccount(userInfoBody.getCustomerAccount());
            ticketCustomer.setCustomerPassword(userInfoBody.getCustomerPassword());
            if(iDaoService.updateObject(ticketCustomer, SqlTypeEnum.UPDATEBYUSERID)!=1) {
                log.info("修改商户信息失败userId{} 修改成功多条", userInfoBody.getUserId());
                throw new HNException(RespondMessageEnum.UPDATEUSERINFOERROR);
            }
        }
        return true;
    }

    @Override
    public UserInfoBody getRoleInfo(UserInfo userInfo) {
        log.info("获取用户实名信息 userID={} ",userInfo.getId());
        TicketCustomer  ticketCustomer = new TicketCustomer();
        ticketCustomer.setUserId(userInfo.getId());
        ticketCustomer.setCustomerStatus(userInfo.getUserStatus());
        IDaoService iDaoService = hnContext.getDaoService(TicketCustomer.class.getSimpleName());
        ticketCustomer = (TicketCustomer) iDaoService.selectObject(ticketCustomer,
                SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        UserInfoBody  userInfoBody = new UserInfoBody();
        userInfoBody.setRealName(ticketCustomer.getCustomerName());
        userInfoBody.setUserIdentity(ticketCustomer.getCustomerIdentity());
        userInfoBody.setCustomerAccount(ticketCustomer.getCustomerAccount());
        userInfoBody.setCustomerPassword(ticketCustomer.getCustomerPassword());
        return userInfoBody;
    }
}
