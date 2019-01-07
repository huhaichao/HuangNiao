package com.sy.huangniao.service.impl.customer;

import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sy.huangniao.common.bo.RespondBody;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.common.Util.StringUtils;

import com.sy.huangniao.common.enums.*;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.pojo.*;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.customer.TicketCustomerService;
import com.sy.huangniao.service.impl.AbstractUserAppService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by huchao on 2018/9/14.
 */
@Slf4j
@Component
public class TicketCustomerServiceImpl extends AbstractUserinfoService implements TicketCustomerService {
    @Override
    public String getUserRole() {
        return UserRoleEnum.CUSTOMER.getRole();
    }

    @Override
    public JSONObject createOrder(JSONObject jsonObject) {
        TicketOrder ticketOrder = jsonObject.toJavaObject(TicketOrder.class);
        log.info("userid={} 下单中....", ticketOrder.getUserId());
        AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(
            AppCodeEnum.valueOf(ticketOrder.getAppCode()));
        String orderNo = abstractUserAppService.createOrderNO();
        ticketOrder.setOrderNo(orderNo);
        /*if (ticketOrder.getOrderAmount() > 0) {
            ticketOrder.setOrderStatus(OrderStatusEnum.WAITPAY.getStatus());
        } else {
            ticketOrder.setOrderStatus(OrderStatusEnum.WAITROB.getStatus());
        }*/
        //优化流程下单--抢票中
        ticketOrder.setOrderStatus(OrderStatusEnum.ROBING.getStatus());
        ticketOrder.setModifyDate(new Date());
        ticketOrder.setCreateDate(new Date());
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        if (iDaoService.save(ticketOrder, SqlTypeEnum.DEAFULT) != 1) {
            log.info("下单失败 userid={} orderid={}", ticketOrder.getUserId(), ticketOrder.getId());
            throw new HNException(RespondMessageEnum.CREATORDERFAIL);
        }

        List<TicketDetails> list = ticketOrder.getTicketDetails();
        for (TicketDetails ticketDetails : list) {
            ticketDetails.setCreateDate(new Date());
            ticketDetails.setOrderNo(orderNo);
            ticketDetails.setUserId(ticketOrder.getUserId());
        }
        IDaoService iTicketDetailsDaoService = hnContext.getDaoService(TicketDetails.class.getSimpleName());
        if (iTicketDetailsDaoService.saveBatch(list, SqlTypeEnum.DEAFULT) != list.size()) {
            log.info("下单失败 userid={} orderid={}", ticketOrder.getUserId(), ticketOrder.getId());
            throw new HNException(RespondMessageEnum.CREATORDERDETAILSFAIL);
        }
        jsonObject.put("orderNo", orderNo);
        JSONObject result = new JSONObject();
       /*
        优化流程 -- 支付滞后
        if (ticketOrder.getOrderAmount() > 0) {
            result = abstractUserAppService.deposit(jsonObject);
        }*/
        result.put("id", ticketOrder.getId());
        return result;
    }

    @Override
    public JSONObject getOrderList(JSONObject jsonObject) {
        TicketOrder ticketOrder = jsonObject.toJavaObject(TicketOrder.class);
        log.info("userid={} 查询订单....", ticketOrder.getUserId());
        int pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        page.setOrderBy(" create_date DESC ");
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        List<TicketOrder> list = iDaoService.selectList(ticketOrder, SqlTypeEnum.DEAFULT);
        JSONObject reulstList = new JSONObject();
        reulstList.put("total", page.getTotal());
        reulstList.put("pageNum", pageNum);
        reulstList.put("datas", list);
        reulstList.put("pages", page.getPages());
        log.info("userid={} 查询订单成功....", ticketOrder.getUserId());
        return reulstList;
    }

    @Override
    public JSONObject getOrderDetails(JSONObject jsonObject) {
        TicketOrder ticketOrder = jsonObject.toJavaObject(TicketOrder.class);
        log.info("userid={} 查询订单详情....", ticketOrder.getUserId());
        IDaoService<TicketOrder> iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        TicketOrder ticket = iDaoService.selectObject(ticketOrder, SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        JSONObject json = (JSONObject)JSONObject.toJSON(ticket);
        log.info("userid={} 查询订单详情.... result={}", ticketOrder.getUserId(), json);
        return json;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean confirmeOrder(JSONObject jsonObject) {
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setId(jsonObject.getInteger("id"));
        ticketOrder.setOrderStatus(OrderStatusEnum.SUCCESS.getStatus());
        ticketOrder.setModifyDate(new Date());
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        if (iDaoService.updateObject(ticketOrder, SqlTypeEnum.DEAFULT) == 1) {
            TicketOrder queryTicketOrder = new TicketOrder();
            queryTicketOrder.setId(ticketOrder.getId());
            ticketOrder = (TicketOrder)iDaoService.selectObject(queryTicketOrder, SqlTypeEnum.DEAFULT);
            RobOrder robOrder = new RobOrder();
            robOrder.setRobStatus(OrderStatusEnum.USER_PAY.getStatus());
            robOrder.setOrderId(ticketOrder.getOrderNo());
            IDaoService iRobOrderDaoService = hnContext.getDaoService(RobOrder.class.getSimpleName());
            List<RobOrder> robOrders = iRobOrderDaoService.selectList(robOrder, SqlTypeEnum.DEAFULT);

            if (robOrders == null || robOrders.size() != 1) {
                ////todo 添加预警信息 -- 提示人工介入处理
                log.info(" orderId={} orderNo={}  订单确认失败查询失败原因可能是抢单信息有误!", ticketOrder.getId(),
                    ticketOrder.getOrderNo());
                throw new HNException(RespondMessageEnum.CONFIREMEORDERFAIL);
            }
            robOrder = robOrders.get(0);
            IDaoService iUserAccountDaoService = hnContext.getDaoService(UserAccount.class.getSimpleName());
            //划款处理
            //用户付款方---处理
            Integer fkUserId = ticketOrder.getUserId();
            //订单金额
            double orderAmount = ticketOrder.getOrderAmount();
            UserAccount fkUserAccount = new UserAccount();
            fkUserAccount.setUserId(fkUserId);
            fkUserAccount = (UserAccount)iUserAccountDaoService.selectObject(fkUserAccount,
                SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
            //付款方扣减金额处理
            UserAccount subAmount = new UserAccount();
            subAmount.setUserId(fkUserId);
            subAmount.setId(fkUserAccount.getId());
            subAmount.setAccountNo(fkUserAccount.getAccountNo());
            subAmount.setCoolAmount(-orderAmount);
            if (iUserAccountDaoService.updateObject(subAmount, SqlTypeEnum.UPDATEACCOUNTAMOUNT) != 1) {
                log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} 扣款失败!", ticketOrder.getId(),
                    ticketOrder.getOrderNo(),
                    fkUserAccount.getAccountNo(), fkUserAccount.getUserId(), orderAmount);
                throw new HNException(RespondMessageEnum.CONFIREMEORDERFAIL);
            }
            //商户收款方---处理
            Integer skUserId = robOrder.getUserId();
            //计算服务商分润
            TicketBusiness ticketBusiness = new TicketBusiness();
            ticketBusiness.setUserId(skUserId);
            IDaoService iTicketBusinessDaoService = hnContext.getDaoService(TicketBusiness.class.getSimpleName());
            ticketBusiness = (TicketBusiness)iTicketBusinessDaoService.selectObject(ticketBusiness,
                SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
            if (ticketBusiness == null) {
                log.info(" orderId={} orderNo={} userAcount={} 用户id={} amount={} 商户id={} 扣款失败--该商户不存在!",
                    ticketOrder.getId(), ticketOrder.getOrderNo(),
                    fkUserAccount.getAccountNo(), fkUserAccount.getUserId(), orderAmount, skUserId);
                throw new HNException(RespondMessageEnum.BUSINRESSNOEXIST);
            }
            //分润金额
            double skAmount = robOrder.getRobAmount();
            UserAccount skUserAccount = new UserAccount();
            skUserAccount.setUserId(skUserId);
            skUserAccount = (UserAccount)iUserAccountDaoService.selectObject(skUserAccount,
                SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
            if (skUserAccount == null) {
                //商户没有账户就创建一个账户
                //创建账户
                skUserAccount = new UserAccount();
                skUserAccount.setStatus(UserAccountStatusEnum.NORMAL.getStatus());
                skUserAccount.setAmountBalance(0.0);
                AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(
                    AppCodeEnum.valueOf(ticketOrder.getAppCode()));
                skUserAccount.setAccountNo(abstractUserAppService.createUserAcountNo());
                skUserAccount.setUserId(skUserId);
                skUserAccount.setCoolAmount(0.0);
                skUserAccount.setCreateDate(new Date());
                skUserAccount.setModifyDate(new Date());
                IDaoService userAccountDao = hnContext.getDaoService(UserAccount.class.getSimpleName());
                if (userAccountDao.save(skUserAccount, SqlTypeEnum.DEAFULT) != 1) {
                    log.info("userId={} userRole={}  appcode ={} 保存用户账户失败", skUserId, UserRoleEnum.BUSINESS,
                        ticketOrder.getAppCode());
                    throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
                }

            }
            //收款方增加金额处理
            UserAccount addAmount = new UserAccount();
            addAmount.setUserId(skUserId);
            addAmount.setId(skUserAccount.getId());
            addAmount.setAccountNo(skUserAccount.getAccountNo());
            addAmount.setAmountBalance(skAmount);
            if (iUserAccountDaoService.updateObject(addAmount, SqlTypeEnum.UPDATEACCOUNTAMOUNT) != 1) {
                log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} robOrderId={} 打款失败!",
                    ticketOrder.getId(), ticketOrder.getOrderNo(),
                    skUserAccount.getAccountNo(), skUserAccount.getUserId(), skAmount, robOrder.getId());
                throw new HNException(RespondMessageEnum.CONFIREMEORDERFAIL);
            }
            //增加交易记录
            UserTrade userTrade = new UserTrade();
            userTrade.setCreateDate(new Date());
            userTrade.setAmount(orderAmount);
            userTrade.setFactAmount(skAmount);
            userTrade.setFee(orderAmount - skAmount);
            userTrade.setFromAccount(fkUserAccount.getAccountNo());
            userTrade.setToAccount(skUserAccount.getAccountNo());
            userTrade.setOrderNo(ticketOrder.getOrderNo());
            userTrade.setModifyDate(new Date());
            AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(
                AppCodeEnum.valueOf(ticketOrder.getAppCode()));
            userTrade.setTradeNo(abstractUserAppService.createTradeNo());
            userTrade.setStatus(TradeStatusEnum.TRADE_AUDITING.getStatus());
            IDaoService iUserTradeDaoService = hnContext.getDaoService(UserTrade.class.getSimpleName());
            iUserTradeDaoService.save(userTrade, SqlTypeEnum.DEAFULT);
            log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} robOrderId={} 生成交易信息!",
                ticketOrder.getId(), ticketOrder.getOrderNo(),
                skUserAccount.getAccountNo(), skUserAccount.getUserId(), skAmount, robOrder.getId());

            //todo 通知商户 和 其他商户已出票
            try {
                RobOrder robOrderUpdate = new RobOrder();
                robOrderUpdate.setId(robOrder.getId());
                robOrderUpdate.setRobStatus(OrderStatusEnum.SUCCESS.getStatus());
                robOrderUpdate.setModifyDate(new Date());
                robOrderUpdate.setRemark("订单已完成用户已付款!");

                iRobOrderDaoService.updateObject(robOrderUpdate, SqlTypeEnum.DEAFULT);
                /*robOrder.setRobStatus(OrderStatusEnum.TICKET_SUCCESS.getStatus());
                robOrder.setRemark("该订单已经被其他商户完成！");
                iRobOrderDaoService.updateObject(robOrder,SqlTypeEnum.UPDATEBYORDERID);
                log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} robOrderId={} 生成通知信息!",ticketOrder
                .getId(),ticketOrder.getOrderNo(),
                        skUserAccount.getAccountNo(),skUserAccount.getUserId(),skAmount,robOrder.getId());*/
                //生成通知,通知收款人
               /* IDaoService iUserInfoDaoService = hnContext.getDaoService(UserInfo.class.getSimpleName());
                UserInfo userInfo = new UserInfo();
                userInfo.setId(skUserId);
                userInfo =(UserInfo) iUserInfoDaoService.selectObject(userInfo,SqlTypeEnum.DEAFULT);
                Notify notify = new Notify();
                notify.setToNo(userInfo.getUserPhoneno());
                notify.setCreateDate(new Date());
                notify.setFromNo("sys");
                notify.setTitle("手续费到账通知");
                notify.setContext("尊敬的商户，你好！你的抢单订单号为["+ticketOrder.getOrderNo()+"]订单手续费已尽被支付，请注意查收！");
                notify.setNotifyStatus(NotifyStatusEnum.WAIT_NOTIFY.getStatus());
                IDaoService iNotifyDaoService = hnContext.getDaoService(Notify.class.getSimpleName());
                iNotifyDaoService.save(notify,SqlTypeEnum.DEAFULT);*/
            } catch (Exception e) {
                //防止异常影响打款
                log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} robOrderId={} 生成通知失败!={}",
                    ticketOrder.getId(), ticketOrder.getOrderNo(),
                    skUserAccount.getAccountNo(), skUserAccount.getUserId(), skAmount, robOrder.getId(),
                    e.getMessage());
            }

        } else {
            log.info(" orderId={} userId={} 该订单不存在.....", ticketOrder.getId(), jsonObject.getString("userId")
            );
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean cancleOrder(JSONObject jsonObject) {
        //取消订单状态
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setId(jsonObject.getInteger("id"));
        ticketOrder.setUserId(jsonObject.getInteger("userId"));
        //ticketOrder.setOrderStatus(OrderStatusEnum.WAITROB.getStatus());
        TicketOrder ticketOrderSelect = (TicketOrder)iDaoService.selectObject(ticketOrder,
            SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        if (ticketOrderSelect == null) {
            log.info(" orderId={} orderNo={} 该订单暂不存在", ticketOrder.getId(), ticketOrder.getOrderNo());
            throw new HNException(RespondMessageEnum.CANCLEORDERNOEXSIT);
        }
        TicketOrder ticketOrder2 = new TicketOrder();
        ticketOrder2.setId(ticketOrder.getId());
        String orderStatus = ticketOrderSelect.getOrderStatus();
        if ((OrderStatusEnum.WAITROB.getStatus().equals(orderStatus) || OrderStatusEnum.ROBING.getStatus()
            .equals(orderStatus))){
            //未支付的---直接取消订单
            log.info(" orderId={} orderNo={} 该订单未支付直接取消", ticketOrder.getId(), ticketOrder.getOrderNo());
            ticketOrder2.setOrderStatus(OrderStatusEnum.CANCEL.getStatus());
        }/* else if ((OrderStatusEnum.WAITROB.getStatus().equals(orderStatus) || OrderStatusEnum.ROBING.getStatus()
            .equals(orderStatus))
            && ticketOrderSelect.getOrderAmount() > 0) {
            //查询支付的订单信息
            UserDeposit userDeposit = new UserDeposit();
            userDeposit.setUserId(ticketOrderSelect.getUserId());
            userDeposit.setOrderNo(ticketOrderSelect.getOrderNo());
            userDeposit.setAppCode(ticketOrderSelect.getAppCode());
            userDeposit.setStatus(WalletStatusEnum.SUCCESS.getStatus());
            IDaoService<UserDeposit> iUserDepositDaoService = hnContext.getDaoService(
                UserDeposit.class.getSimpleName());
            UserDeposit userDeposit2 = iUserDepositDaoService.selectObject(userDeposit,
                SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
            if (userDeposit2 == null) {
                log.info(" orderId={} orderNo={} 订单充值信息不存在", ticketOrder.getId(), ticketOrder.getOrderNo());
                throw new HNException(RespondMessageEnum.DEPOSITPAYREPEAT);
            }
            //已支付的退款
            ticketOrder2.setOrderStatus(OrderStatusEnum.RETURNING_AMOUNT.getStatus());
            ReturnOrder returnOrder = new ReturnOrder();
            returnOrder.setUserId(ticketOrderSelect.getUserId());
            returnOrder.setOrderNo(ticketOrderSelect.getOrderNo()); //付款订单号
            returnOrder.setDepositNo(userDeposit2.getDepositNo()); //付款订单号
            returnOrder.setReturnAmount(userDeposit2.getAmount());
            returnOrder.setOrderAmount(userDeposit2.getAmount());
            returnOrder.setCreateDate(new Date());
            returnOrder.setModifyDate(new Date());
            returnOrder.setReturnStatus(OrderStatusEnum.RETURNED_AUDIT.getStatus());
            AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(
                AppCodeEnum.valueOf(ticketOrderSelect.getAppCode()));
            String returnNO = abstractUserAppService.createReturnNO();
            returnOrder.setReturnNo(returnNO);
            returnOrder.setAppCode(jsonObject.getString("appCode"));
            IDaoService iReturnOrderDaoService = hnContext.getDaoService(ReturnOrder.class.getSimpleName());
            if (iReturnOrderDaoService.save(returnOrder, SqlTypeEnum.DEAFULT) != 1) {
                log.info(" orderId={} orderNo={} 创建退款订单失败", ticketOrder.getId(), ticketOrder.getOrderNo());
                throw new HNException(RespondMessageEnum.CANCLEORDERFAIL);
            }
        } else if (ticketOrderSelect.getOrderAmount() == 0) {
            log.info(" orderId={} orderNo={} 该订单金额为零直接取消", ticketOrder.getId(), ticketOrder.getOrderNo());
            ticketOrder2.setOrderStatus(OrderStatusEnum.CANCEL.getStatus());
        }*/
        else {
            log.info(" orderId={} orderNo={} 状态不是等待支付，等待抢票、抢票中的不允许取消", ticketOrder.getId(), ticketOrder.getOrderNo());
            throw new HNException(RespondMessageEnum.CANCLEORDERNOSUPPORT);
        }
        //修改订单状态
        if (iDaoService.updateObject(ticketOrder2, SqlTypeEnum.DEAFULT) != 1) {
            log.info(" orderId={} orderNo={} 修改订单状态....", ticketOrder.getId(), ticketOrder.getOrderNo());
            throw new HNException(RespondMessageEnum.CANCLEORDERFAIL);
        }

        //解冻金额
       /* IDaoService iUserAccountDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
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
            log.info(" orderId={} orderNo={} userAcount={} userId={} amount={} 解冻失败!",ticketOrder.getId(),ticketOrder
            .getOrderNo(),
                    userAccount.getAccountNo(),userAccount.getUserId(),userAccount.getCoolAmount());
            throw new HNException(RespondMessageEnum.UNFREEAMOUNTFAIL);
        }
        return true;*/

        return true;
    }

    @Override
    public boolean saveRoleInfo(UserInfoBody userInfoBody) {
        TicketCustomer ticketCustomer = new TicketCustomer();
        IDaoService iDaoService = hnContext.getDaoService(TicketCustomer.class.getSimpleName());
        if (StringUtils.isEmpty(userInfoBody.getUserIdentity())) {
            log.info("保存用户信息...userId{} ", userInfoBody.getUserId());
            ticketCustomer.setCustomerStatus(userInfoBody.getUserStatus());
            ticketCustomer.setUserId(Integer.parseInt(userInfoBody.getUserId()));
            ticketCustomer.setCustomerAccount(userInfoBody.getCustomerAccount());
            ticketCustomer.setCustomerPassword(userInfoBody.getCustomerAccount());
            ticketCustomer.setCreateDate(new Date());
            ticketCustomer.setModifyDate(new Date());
            if (iDaoService.save(ticketCustomer, SqlTypeEnum.DEAFULT) != 1) {
                log.info("保存用户信息失败userId{} 保存成功多条", userInfoBody.getUserId());
                throw new HNException(RespondMessageEnum.SAVEUSERINFOERROR);
            }
        } else {
            log.info("修改用户信息...userId{} ", userInfoBody.getUserId());
            ticketCustomer.setCustomerStatus(userInfoBody.getUserStatus());
            ticketCustomer.setUserId(Integer.parseInt(userInfoBody.getUserId()));
            ticketCustomer.setCustomerName(userInfoBody.getRealName());
            ticketCustomer.setIdentityImage(userInfoBody.getUserImage());
            ticketCustomer.setCustomerIdentity(userInfoBody.getUserIdentity());
            ticketCustomer.setModifyDate(new Date());
            ticketCustomer.setCustomerAccount(userInfoBody.getCustomerAccount());
            // ticketCustomer.setCustomerPassword(userInfoBody.getCustomerPassword());
            if (iDaoService.updateObject(ticketCustomer, SqlTypeEnum.UPDATEBYUSERID) != 1) {
                log.info("修改商户信息失败userId{} 修改成功多条", userInfoBody.getUserId());
                throw new HNException(RespondMessageEnum.UPDATEUSERINFOERROR);
            }
        }
        return true;
    }

    @Override
    protected boolean updateRoleInfo(JSONObject jsonObject) {
        TicketCustomer ticketCustomer = new TicketCustomer();
        BeanUtils.copyProperties(jsonObject, ticketCustomer);
        IDaoService iDaoService = hnContext.getDaoService(TicketCustomer.class.getSimpleName());
        if (iDaoService.updateObject(ticketCustomer, SqlTypeEnum.UPDATEBYUSERID) != 1) {
            return false;
        }
        return true;
    }

    @Override
    public UserInfoBody getRoleInfo(UserInfo userInfo) {
        log.info("获取用户实名信息 userID={} ", userInfo.getId());
        TicketCustomer ticketCustomer = new TicketCustomer();
        ticketCustomer.setUserId(userInfo.getId());
        ticketCustomer.setCustomerStatus(userInfo.getUserStatus());
        IDaoService iDaoService = hnContext.getDaoService(TicketCustomer.class.getSimpleName());
        ticketCustomer = (TicketCustomer)iDaoService.selectObject(ticketCustomer,
            SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        UserInfoBody userInfoBody = new UserInfoBody();
        userInfoBody.setRealName(ticketCustomer.getCustomerName());
        userInfoBody.setUserIdentity(ticketCustomer.getCustomerIdentity());
        userInfoBody.setCustomerAccount(ticketCustomer.getCustomerAccount());
        //userInfoBody.setCustomerPassword(ticketCustomer.getCustomerPassword());
        return userInfoBody;
    }

    @Override
    public boolean addContacts(JSONObject jsonObject) {
        log.info("添加联系人 jsonObject={}", jsonObject);
        IDaoService<UserLinkman> iDaoService = hnContext.getDaoService(UserLinkman.class.getSimpleName());
        UserLinkman userLinkman = jsonObject.toJavaObject(UserLinkman.class);
        userLinkman = iDaoService.selectObject(userLinkman, SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        if (userLinkman != null && userLinkman.getStatus().equals(UserLinkmanEnum.NORMAL.name())) {
            log.info(" addContacts jsonObject={} ", jsonObject);
            return true;
        }
       /*
        实名接口有问题直接干掉
        JSONObject json = otherPartyServiceImpl.realName(jsonObject);
        jsonObject.putAll(json);*/
        userLinkman = jsonObject.toJavaObject(UserLinkman.class);
        log.info("添加联系人开始userId={}  indentity ={} name={}  appCode={} .....", userLinkman.getUserId(),
            userLinkman.getIndentity(), userLinkman.getName(), userLinkman.getAppCode());
        userLinkman.setCreateDate(new Date());
        userLinkman.setModifyDate(new Date());
        userLinkman.setStatus(UserLinkmanEnum.NORMAL.getStatus());

        if (iDaoService.save(userLinkman, SqlTypeEnum.DEAFULT) != 1) {
            log.info("添加联系人 失败 userID {}", userLinkman.getUserId());
            throw new HNException(RespondMessageEnum.ADDCONTACTS_FAIL);
        }
        return true;
    }

    @Override
    public List<UserLinkman> selectContacts(JSONObject jsonObject) {
        UserLinkman userLinkman = jsonObject.toJavaObject(UserLinkman.class);
        IDaoService iDaoService = hnContext.getDaoService(UserLinkman.class.getSimpleName());
        return iDaoService.selectList(userLinkman, SqlTypeEnum.DEAFULT);
    }

    @Override
    public boolean returnOrder(JSONObject jsonObject) {
        return false;
    }

    @Override
    public void returnOrderHandle(JSONObject jsonObject) {
        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setReturnStatus(OrderStatusEnum.RETURNING_AMOUNT.getStatus());
        //每次处理1000条信息
        Page page = PageHelper.startPage(0, 1000);
        IDaoService<ReturnOrder> iDaoService = hnContext.getDaoService(ReturnOrder.class.getSimpleName());
        List<ReturnOrder> list = iDaoService.selectList(returnOrder, SqlTypeEnum.DEAFULT);
        log.info("退款开始--处理条数size={}.....", list == null ? 0 : list.size());
        /**
         *  处理退款信息
         */
        for (ReturnOrder ro : list) {
            log.info("userId={} orderNo={} orderAmount={} 退款开始......", ro.getUserId(), ro.getOrderNo(),
                ro.getOrderAmount());
            try {
                String appCode = ro.getAppCode();
                //退款类型
                //String  returnType = ro.getReturnType();
                AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(
                    AppCodeEnum.valueOf(appCode));
                JSONObject json = (JSONObject)JSONObject.toJSON(ro);

                JSONObject result = abstractUserAppService.returned(json);
                //修改退款表
                ReturnOrder re = new ReturnOrder();
                re.setId(ro.getId());
                re.setTradeChannelsReturnNo(result.getString("refund_id"));
                re.setReturnStatus(OrderStatusEnum.RETURNING_CHANNELS.getStatus());
                if (iDaoService.updateObject(re, SqlTypeEnum.DEAFULT) != 1) {
                    throw new HNException(RespondMessageEnum.REALNAME_FAIL);
                }
            } catch (HNException e) {
                log.error("userId={} orderNo={} orderAmount={} exception code={} msg={}", ro.getUserId(),
                    ro.getOrderNo(),
                    ro.getOrderAmount(), e.getCode(), e.getMsg());
                ReturnOrder re = new ReturnOrder();
                re.setId(ro.getId());
                re.setReturnStatus(OrderStatusEnum.RETURNED_AMOUNT_FAIL.getStatus());
                re.setRemark(e.getMsg());
                iDaoService.updateObject(re, SqlTypeEnum.DEAFULT);

            } catch (Exception e) {
                log.error("userId={} orderNo={} orderAmount={} exception={}", ro.getUserId(), ro.getOrderNo(),
                    ro.getOrderAmount(), e.getMessage());
                ReturnOrder re = new ReturnOrder();
                re.setId(ro.getId());
                re.setReturnStatus(OrderStatusEnum.RETURNED_AMOUNT_FAIL.getStatus());
                re.setRemark("服务器异常！");
                iDaoService.updateObject(re, SqlTypeEnum.DEAFULT);
            }
            log.info("userId={} orderNo={} orderAmount={} 退款结束......", ro.getUserId(), ro.getOrderNo(),
                ro.getOrderAmount());
        }

    }

    /**
     * 用户退款操作
     *
     * @param jsonObject
     * @return
     */
    @Override
    public boolean returnAmount(JSONObject jsonObject) {
        //取消订单状态
        IDaoService iDaoService = hnContext.getDaoService(TicketOrder.class.getSimpleName());
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setId(jsonObject.getInteger("id"));
        ticketOrder.setUserId(jsonObject.getInteger("userId"));
        //ticketOrder.setOrderStatus(OrderStatusEnum.WAITROB.getStatus());
        TicketOrder ticketOrderSelect = (TicketOrder)iDaoService.selectObject(ticketOrder,
            SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        if (ticketOrderSelect == null) {
            log.info(" orderId={} orderNo={} 该订单暂不存在", ticketOrder.getId(), ticketOrder.getOrderNo());
            throw new HNException(RespondMessageEnum.CANCLEORDERNOEXSIT);
        }
        TicketOrder ticketOrder2 = new TicketOrder();
        ticketOrder2.setId(ticketOrder.getId());
        //String orderStatus = ticketOrderSelect.getOrderStatus() ;
        //if (OrderStatusEnum.WAITROB.getStatus().equals(orderStatus) || OrderStatusEnum.ROBING.getStatus().equals
        // (orderStatus)){
        //查询支付的订单信息
        UserDeposit userDeposit = new UserDeposit();
        userDeposit.setUserId(ticketOrderSelect.getUserId());
        userDeposit.setOrderNo(ticketOrderSelect.getOrderNo());
        userDeposit.setAppCode(ticketOrderSelect.getAppCode());
        userDeposit.setStatus(WalletStatusEnum.SUCCESS.getStatus());
        IDaoService<UserDeposit> iUserDepositDaoService = hnContext.getDaoService(UserDeposit.class.getSimpleName());
        UserDeposit userDeposit2 = iUserDepositDaoService.selectObject(userDeposit,
            SqlTypeEnum.SELECTOBJECTBYSELECTIVE);
        if (userDeposit2 == null) {
            log.info(" orderId={} orderNo={} 订单充值信息不存在", ticketOrder.getId(), ticketOrder.getOrderNo());
            throw new HNException(RespondMessageEnum.DEPOSITPAYREPEAT);
        }
        //已支付的退款
        ticketOrder2.setOrderStatus(OrderStatusEnum.RETURNING_AMOUNT.getStatus());
        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setUserId(ticketOrderSelect.getUserId());
        returnOrder.setOrderNo(ticketOrderSelect.getOrderNo()); //付款订单号
        returnOrder.setDepositNo(userDeposit2.getDepositNo()); //充值订单号
        returnOrder.setReturnAmount(userDeposit2.getAmount());
        returnOrder.setOrderAmount(userDeposit2.getAmount());
        returnOrder.setCreateDate(new Date());
        returnOrder.setModifyDate(new Date());
        returnOrder.setReturnStatus(OrderStatusEnum.RETURNED_AUDIT.getStatus());
        AbstractUserAppService abstractUserAppService = hnContext.getAbstractUserAppService(
            AppCodeEnum.valueOf(ticketOrderSelect.getAppCode()));
        String returnNO = abstractUserAppService.createReturnNO();
        returnOrder.setReturnNo(returnNO);
        IDaoService iReturnOrderDaoService = hnContext.getDaoService(ReturnOrder.class.getSimpleName());
        if (iReturnOrderDaoService.save(returnOrder, SqlTypeEnum.DEAFULT) != 1) {
            log.info(" orderId={} orderNo={} 创建退款订单失败", ticketOrder.getId(), ticketOrder.getOrderNo());
            throw new HNException(RespondMessageEnum.CANCLEORDERFAIL);
        }
        // }
        //修改订单状态
        if (iDaoService.updateObject(ticketOrder2, SqlTypeEnum.DEAFULT) != 1) {
            log.info(" orderId={} orderNo={} 修改订单状态....", ticketOrder.getId(), ticketOrder.getOrderNo());
            throw new HNException(RespondMessageEnum.CANCLEORDERFAIL);
        }
        return true;
    }
}
