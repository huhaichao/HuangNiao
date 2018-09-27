package com.sy.huangniao.service.impl.customer;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sy.huangniao.common.Util.IdGenerator;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.*;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.pojo.*;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.customer.TicketCustomerService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huchao on 2018/9/14.
 */
@Slf4j
@Component
public class TicketCustomerServiceImpl extends AbstractUserinfoService implements TicketCustomerService{
    @Override
    public String getUserRole() {
        return UserRoleEnum.CUSTOMER.getRole();
    }

    @Override
    public boolean createOrder(JSONObject jsonObject) {
        TicketOrder ticketOrder = new TicketOrder();
        BeanUtils.copyProperties(jsonObject,ticketOrder);
        log.info("userid={} 下单中....",ticketOrder.getUserId());
        ticketOrder.setOrderNo(Constant.ORDERNOPRFIX+ IdGenerator.getInstance().generate());
        ticketOrder.setOrderStatus(OrderStatusEnum.WAITPAY.getStatus());
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        if(iDaoService.save(ticketOrder,SqlTypeEnum.DEAFULT)!=1){
            log.info("下单失败 userid={} orderid={}",ticketOrder.getUserId(),ticketOrder.getId());
            throw new HNException(RespondMessageEnum.CREATORDERFAIL);
        }
        return true;
    }

    @Override
    public String getOrderList(JSONObject jsonObject) {
        TicketOrder ticketOrder = new TicketOrder();
        BeanUtils.copyProperties(jsonObject,ticketOrder);
        log.info("userid={} 查询订单....",ticketOrder.getUserId());
        int pageNum  = Integer.parseInt(jsonObject.getString("pageNum"));
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        List<TicketOrder> list = iDaoService.selectList(ticketOrder,SqlTypeEnum.DEAFULT);
        JSONObject reulstList = new JSONObject();
        reulstList.put("total",page.getTotal());
        reulstList.put("pageNum",pageNum);
        reulstList.put("data",list);
        log.info("userid={} list={} 查询订单成功....",ticketOrder.getUserId(),list);
        return  reulstList.toString();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean confirmeOrder(JSONObject jsonObject) {
        TicketOrder ticketOrder = new TicketOrder();
        BeanUtils.copyProperties(jsonObject,ticketOrder);
        ticketOrder.setOrderStatus(OrderStatusEnum.SUCCESS.getStatus());
        ticketOrder.setModifyDate(new Date());
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        if(iDaoService.updateObject(ticketOrder,SqlTypeEnum.DEAFULT)==1){
            RobOrder  robOrder = new RobOrder();
            robOrder.setRobStatus(OrderStatusEnum.WAITCONFIRME.getStatus());
            robOrder.setOrderId(ticketOrder.getId());
            IDaoService iRobOrderDaoService = hnContext.getDaoService(RobOrder.class.getSimpleName());
            List<RobOrder> robOrders = iRobOrderDaoService.selectList(robOrder,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);

            if(robOrders==null || robOrders.size()!=1){
                ////todo 添加预警信息 -- 提示人工介入处理
                log.info(" orderId={} orderNo={}  订单确认失败查询失败原因可能是抢单信息有误!",ticketOrder.getId(),ticketOrder.getOrderNo());
                throw new HNException(RespondMessageEnum.CONFIREMEORDERFAIL);
            }
            robOrder = robOrders.get(0);
            IDaoService iUserAccountDaoService = hnContext.getDaoService(UserAccount.class.getSimpleName());
            //划款处理
            //付款方---处理
            Integer fkUserId = ticketOrder.getUserId();
            //订单金额
            double orderAmount = ticketOrder.getOrderAmount();
            UserAccount fkUserAccount = new UserAccount();
            fkUserAccount.setUserId(fkUserId);
            fkUserAccount = (UserAccount)iUserAccountDaoService.selectObject(fkUserAccount,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
            //付款方扣减金额处理
            UserAccount subAmount = new UserAccount();
            subAmount.setUserId(fkUserId);
            subAmount.setId(fkUserAccount.getId());
            subAmount.setAccountNo(fkUserAccount.getAccountNo());
            subAmount.setCoolAmount(-orderAmount);
            if(iUserAccountDaoService.updateObject(subAmount,SqlTypeEnum.UPDATEACCOUNTAMOUNT)!=1){
                log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} 扣款失败!",ticketOrder.getId(),ticketOrder.getOrderNo(),
                        fkUserAccount.getAccountNo(),fkUserAccount.getUserId(),orderAmount);
                throw new HNException(RespondMessageEnum.CONFIREMEORDERFAIL);
            }
            //收款方---处理
            Integer skUserId = robOrder.getUserId();
            //计算服务商分润
            TicketBusiness ticketBusiness = new TicketBusiness();
            ticketBusiness.setUserId(skUserId);
            IDaoService iTicketBusinessDaoService  = hnContext.getDaoService(TicketBusiness.class.getSimpleName());
            ticketBusiness=(TicketBusiness)iTicketBusinessDaoService.selectObject(ticketBusiness,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
            //金额 * 分润 /100
            double skAmount = orderAmount * ticketBusiness.getBenefitRate()/100;
            UserAccount skUserAccount = new UserAccount();
            skUserAccount.setUserId(skUserId);
            skUserAccount = (UserAccount)iUserAccountDaoService.selectObject(skUserAccount,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
            //收款方增加金额处理
            UserAccount addAmount = new UserAccount();
            addAmount.setUserId(skUserId);
            addAmount.setId(skUserAccount.getId());
            addAmount.setAccountNo(skUserAccount.getAccountNo());
            addAmount.setAmountBalance(skAmount);
            if(iUserAccountDaoService.updateObject(addAmount,SqlTypeEnum.UPDATEACCOUNTAMOUNT)!=1){
                log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} robOrderId={} 打款失败!",ticketOrder.getId(),ticketOrder.getOrderNo(),
                        skUserAccount.getAccountNo(),skUserAccount.getUserId(),skAmount,robOrder.getId());
                throw new HNException(RespondMessageEnum.CONFIREMEORDERFAIL);
            }
            //增加交易记录
            UserTrade userTrade = new UserTrade();
            userTrade.setCreateDate(new Date());
            userTrade.setAmount(orderAmount);
            userTrade.setFactAmount(skAmount);
            userTrade.setFee(orderAmount-skAmount);
            userTrade.setFrom(fkUserAccount.getAccountNo());
            userTrade.setTo(skUserAccount.getAccountNo());
            userTrade.setOrderNo(ticketOrder.getOrderNo());
            userTrade.setTradeNo(Constant.TRADENOPREFIX+IdGenerator.getInstance().generate());
            userTrade.setStatus(TradeStatusEnum.SUCCESS.getStatus());
            IDaoService iUserTradeDaoService = hnContext.getDaoService(UserTrade.class.getSimpleName());
            iUserTradeDaoService.save(userTrade,SqlTypeEnum.DEAFULT);
            log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} robOrderId={} 生成交易信息!",ticketOrder.getId(),ticketOrder.getOrderNo(),
                    skUserAccount.getAccountNo(),skUserAccount.getUserId(),skAmount,robOrder.getId());
           //todo 通知商户 和 其他商户已出票
            try {
                robOrder.setRobStatus(OrderStatusEnum.SUCCESS.getStatus());
                robOrder.setModifyDate(new Date());
                robOrder.setRemark("订单已完成用户已付款!");
                robOrder.setAppCode(null);
                robOrder.setCreateDate(null);
                robOrder.setModifyDate(null);
                iRobOrderDaoService.updateObject(robOrder,SqlTypeEnum.DEAFULT);
                robOrder.setRobStatus(OrderStatusEnum.TICKET_SUCCESS.getStatus());
                robOrder.setRemark("该订单已经被其他商户完成！");
                iRobOrderDaoService.updateObject(robOrder,SqlTypeEnum.UPDATEBYORDERID);
                log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} robOrderId={} 生成通知信息!",ticketOrder.getId(),ticketOrder.getOrderNo(),
                        skUserAccount.getAccountNo(),skUserAccount.getUserId(),skAmount,robOrder.getId());
                //生成通知,通知收款人
                IDaoService iUserInfoDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
                UserInfo userInfo = new UserInfo();
                userInfo.setId(skUserId);
                userInfo =(UserInfo) iUserInfoDaoService.selectObject(userInfo,SqlTypeEnum.DEAFULT);
                Notify notify = new Notify();
                notify.setTo(userInfo.getUserPhoneno());
                notify.setCreateDate(new Date());
                notify.setFrom("sys");
                notify.setTitle("手续费到账通知");
                notify.setContext("尊敬的商户，你好！你的抢单订单号为["+ticketOrder.getOrderNo()+"]订单手续费已尽被支付，请注意查收！");
                notify.setNotifyStatus(NotifyStatusEnum.WAIT_NOTIFY.getStatus());
                IDaoService iNotifyDaoService = hnContext.getDaoService(Notify.class.getSimpleName());
                iNotifyDaoService.save(notify,SqlTypeEnum.DEAFULT);
            }catch (Exception e){
                //防止异常影响打款
                log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} robOrderId={} 生成通知失败!={}",ticketOrder.getId(),ticketOrder.getOrderNo(),
                        skUserAccount.getAccountNo(),skUserAccount.getUserId(),skAmount,robOrder.getId(),e.getMessage());
            }


        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean cancleOrder(int userId, int orderId) {
        //取消订单状态
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        TicketOrder ticketOrder =new TicketOrder();
        ticketOrder.setId(orderId);
        ticketOrder.setOrderStatus(OrderStatusEnum.WAITROB.getStatus());
        ticketOrder = (TicketOrder)iDaoService.selectObject(ticketOrder,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        if(ticketOrder ==null){
            log.info(" orderId={} orderNo={} 该订单暂不能取消",ticketOrder.getId(),ticketOrder.getOrderNo());
            throw new HNException(RespondMessageEnum.CANCLEORDERFAIL);
        }
        //解冻金额
        IDaoService iUserAccountDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userId);
        userAccount = (UserAccount)iUserAccountDaoService.selectObject(userAccount,SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        //收款方增加金额处理
        UserAccount unfreeAmount = new UserAccount();
        unfreeAmount.setUserId(userId);
        unfreeAmount.setId(userAccount.getId());
        unfreeAmount.setAccountNo(userAccount.getAccountNo());
        unfreeAmount.setAmountBalance(userAccount.getCoolAmount());
        unfreeAmount.setCoolAmount(-unfreeAmount.getCoolAmount());
        if(iUserAccountDaoService.updateObject(unfreeAmount,SqlTypeEnum.UPDATEACCOUNTAMOUNT)!=1){
            log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} 解冻失败!",ticketOrder.getId(),ticketOrder.getOrderNo(),
                    userAccount.getAccountNo(),userAccount.getUserId(),userAccount.getCoolAmount());
            throw new HNException(RespondMessageEnum.UNFREEAMOUNTFAIL);
        }
        return true;
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
    protected boolean updateRoleInfo(JSONObject jsonObject) {
        TicketCustomer ticketCustomer = new TicketCustomer();
        BeanUtils.copyProperties(jsonObject,ticketCustomer);
        IDaoService iDaoService = hnContext.getDaoService(TicketCustomer.class.getSimpleName());
        if(iDaoService.updateObject(ticketCustomer,SqlTypeEnum.UPDATEBYUSERID)!=1){
            return  false;
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
