package com.sy.huangniao.service.impl.business;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.enums.*;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.pojo.*;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.business.TicketBusinessService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by huchao on 2018/9/14.
 */
@Slf4j
@Component
public class TicketBusinessServiceImpl extends AbstractUserinfoService implements TicketBusinessService {

    @Value("${huangniao.benefitRate}")
    private Integer benefitRate;


    @Override
    public String getUserRole() {
        return UserRoleEnum.BUSINESS.getRole();
    }


    @Override
    public boolean cancleOrder(JSONObject jsonObject) {
        return false;
    }

    @Override
    public JSONObject createOrder(JSONObject jsonObject) {
        RobOrder robOrder = jsonObject.toJavaObject(RobOrder.class);
        log.info("userid={} 抢单中....",robOrder.getUserId());
        robOrder.setRobStatus(OrderStatusEnum.ROBING.getStatus());
        IDaoService iDaoService = hnContext.getDaoService(RobOrder.class.getSimpleName());
        if(iDaoService.save(robOrder,SqlTypeEnum.DEAFULT)==1){
            TicketOrder ticketOrder = new TicketOrder();
            ticketOrder.setId(robOrder.getOrderId());
            ticketOrder.setOrderStatus(OrderStatusEnum.ROBING.getStatus());
            IDaoService ticketOrderDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
            ticketOrderDaoService.updateObject(ticketOrder,SqlTypeEnum.DEAFULT);
        }else {
            log.info("抢单失败 userid={} orderid={}",robOrder.getUserId(),robOrder.getOrderId());
             throw new HNException(RespondMessageEnum.CREATROBORDERFAIL);
        }
        return null;
    }

    @Override
    public JSONObject getOrderList(JSONObject jsonObject) {
        RobOrder robOrder = jsonObject.toJavaObject(RobOrder.class);
        log.info("userid={} 查询订单....",robOrder.getUserId());
        int pageNum  = Integer.parseInt(jsonObject.getString("pageNum"));
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        IDaoService iDaoService = hnContext.getDaoService(RobOrder.class.getSimpleName());
        List<RobOrder> list = iDaoService.selectList(robOrder,SqlTypeEnum.DEAFULT);
        JSONObject reulstList = new JSONObject();
        reulstList.put("total",page.getTotal());
        reulstList.put("pageNum",pageNum);
        reulstList.put("datas",list);
        log.info("userid={} list={} 查询抢单订单成功....",robOrder.getUserId(),list);
        return  reulstList;
    }

    @Override
    public boolean confirmeOrder(JSONObject jsonObject) {
        RobOrder robOrder = jsonObject.toJavaObject(RobOrder.class);
        robOrder.setRobStatus(OrderStatusEnum.ORDER_AUDIT.getStatus());
        robOrder.setModifyDate(new Date());
        IDaoService iDaoService = hnContext.getDaoService(RobOrder.class.getSimpleName());
        if(iDaoService.updateObject(robOrder,SqlTypeEnum.DEAFULT)==1){
            //通知审核人员进行人工审核
            //todo
            RobOrderAudit robOrderAudit = new RobOrderAudit();
            robOrderAudit.setRobOrderId(robOrder.getOrderId());
            robOrder.setUserId(robOrder.getUserId());
            robOrder.setProofImage(robOrder.getProofImage());
            //robOrder.setRobStatus();
            //审核通过通知用户付款
            //todo
        }
        return true;
    }


    @Override
    public boolean saveRoleInfo(UserInfoBody userInfoBody) {
        TicketBusiness  ticketBusiness = new TicketBusiness();
        IDaoService iDaoService = hnContext.getDaoService(TicketBusiness.class.getSimpleName());
        if (StringUtils.isEmpty(userInfoBody.getUserIdentity())){
            log.info("保存商户信息...userId{} ",userInfoBody.getUserId());
            ticketBusiness.setBusinessStatus(userInfoBody.getUserStatus());
            ticketBusiness.setUserId(Integer.parseInt(userInfoBody.getUserId()));
            ticketBusiness.setBenefitRate(benefitRate);
            if(iDaoService.save(ticketBusiness,SqlTypeEnum.DEAFULT)!=1){
                log.info("保存商户信息失败userId{} 保存成功多条",userInfoBody.getUserId());
                throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
            }
        }else {
            log.info("修改商户信息...userId{} ",userInfoBody.getUserId());
            ticketBusiness.setBusinessStatus(userInfoBody.getUserStatus());
            ticketBusiness.setUserId(Integer.parseInt(userInfoBody.getUserId()));
            ticketBusiness.setBusinessName(userInfoBody.getRealName());
            ticketBusiness.setIdentityImage(userInfoBody.getUserImage());
            ticketBusiness.setBusinessIdentity(userInfoBody.getUserIdentity());
            ticketBusiness.setModifyDate(new Date());
            if(iDaoService.updateObject(ticketBusiness, SqlTypeEnum.UPDATEBYUSERID)!=1) {
                log.info("修改商户信息失败userId{} 修改成功多条", userInfoBody.getUserId());
                throw new HNException(RespondMessageEnum.UPDATEUSERINFOERROR);
            }
        }
        return true;
    }

    @Override
    protected boolean updateRoleInfo(JSONObject jsonObject) {
        TicketBusiness ticketBusiness = new TicketBusiness();
        BeanUtils.copyProperties(jsonObject,ticketBusiness);
        IDaoService iDaoService = hnContext.getDaoService(TicketBusiness.class.getSimpleName());
        if(iDaoService.updateObject(ticketBusiness,SqlTypeEnum.UPDATEBYUSERID)!=1){
            return  false;
        }
        return true;
    }

    @Override
    public UserInfoBody getRoleInfo(UserInfo userInfo) {
        TicketBusiness  ticketBusiness = new TicketBusiness();
        ticketBusiness.setUserId(userInfo.getId());
        ticketBusiness.setBusinessStatus(userInfo.getUserStatus());
        IDaoService iDaoService = hnContext.getDaoService(TicketBusiness.class.getSimpleName());
        ticketBusiness = (TicketBusiness) iDaoService.selectObject(ticketBusiness,
                SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        UserInfoBody  userInfoBody = new UserInfoBody();
        userInfoBody.setRealName(ticketBusiness.getBusinessName());
        userInfoBody.setUserIdentity(ticketBusiness.getBusinessIdentity());
        userInfoBody.setBenefitRate(ticketBusiness.getBenefitRate()+"");
        return userInfoBody;
    }
}
