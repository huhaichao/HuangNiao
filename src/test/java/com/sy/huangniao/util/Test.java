package com.sy.huangniao.util;


public class Test {

    public static void main(String[] args) throws Exception {
        GetUtil getUtil = new GetUtil(); //两地之间的车次
        String trainurl=GetTrainurl.getUrlApi();
//        String trainurl = "https://kyfw.12306.cn/otn/leftTicket/query?";
        String train_date = "2018-12-16";
        String from_station = "BJP";
        String to_station = "CDW";
        String newurl = trainurl + "leftTicketDTO.train_date=" + train_date + "&leftTicketDTO.from_station="
                + from_station + "&leftTicketDTO.to_station=" + to_station + "&purpose_codes=ADULT";
        //调用方法,获取两地之间的火车数组
        String[] ss = getUtil.getList(newurl);
        for (String s : ss) {
            System.out.println("车次信息为：" + s);
        }
    }

}
