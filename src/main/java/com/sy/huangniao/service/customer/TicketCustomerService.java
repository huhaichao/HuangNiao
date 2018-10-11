package com.sy.huangniao.service.customer;

import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.pojo.ReturnOrder;
import com.sy.huangniao.pojo.UserLinkman;

import java.util.List;

/**
 * Created by huchao on 2018/9/14.
 *
 * 客户服务个性化接口
 */
public interface TicketCustomerService{

    /**
     * 接口待定义
     * 添加联系人接口
     * @return
     */
     public boolean  addContacts(JSONObject jsonObject);


    /**
     * 查询联系人信息
     * @param jsonObject
     * @return
     */
     public List<UserLinkman> selectContacts(JSONObject jsonObject);


    /**
     * 待定义接口
     * 退单接口
     * @return
     */
     public boolean  returnOrder(JSONObject jsonObject);


    /**
     *  退款订单处理接口
     * @param jsonObject
     * @return
     */
     public void  returnOrderHandle(JSONObject jsonObject);
}
