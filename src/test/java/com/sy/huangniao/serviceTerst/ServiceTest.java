package com.sy.huangniao.serviceTerst;

import com.sy.huangniao.HNApplication;
import com.sy.huangniao.service.customer.TicketCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huchao on 2018/10/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HNApplication.class)
@Slf4j
public class ServiceTest {

    @Autowired
    TicketCustomerService ticketCustomerServiceImpl;


    @Test
    public  void  returnTest(){
        log.info("......退款处理启动......");
        ticketCustomerServiceImpl.returnOrderHandle(null);
        log.info("......退款处理结束......");
    }


}
