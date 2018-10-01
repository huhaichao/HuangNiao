
# 项目代号

  > HuangNiao  (黄鸟) : 言之命至，革故鼎新。


# 项目说明

* bin 启动脚本


* sql  存放数据库文件


* mysql +  mybatis + springboot + redis


* 项目关注点：
 
  > IDaoService : 顶级数据库处理接口
  
  > UserAppService: app 顶级业务处理接口

  > UserInfoService: 用户 顶级业务接口
  
     * TicketBusinessService : 商户定制化处理接口
     * TicketCustomerService : 客户定制化处理接口
     
  >  IPayChannelsService 支付顶级业务接口    
    
     * IWXPaychannelsService: 微信支付定制化接口
     
  > IRedisService : redis 服务接口    
     
     
# 代码生成工具

  > test 包下  CodeUtil.java
 







