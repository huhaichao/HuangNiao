package com.sy.huangniao.common.Util;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.common.Util.ProxyUtil.ProxyInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by huchao on 2018/12/15.
 * 爬取12306车票信息
 */
public class TrainUtil {

    private static String getUrlApi() {
        StringBuilder buffer = new StringBuilder();
        try {
            buffer.append("https://kyfw.12306.cn/otn/");
            String trainDom = httpGet("https://kyfw.12306.cn/otn/leftTicket/init");
            String context = trainDom.substring(trainDom.indexOf("/*<![CDATA[*/"), trainDom.indexOf("/*]]>*/"));
            String ticketUrl = context.substring(context.indexOf(" var CLeftTicketUrl = "),
                context.indexOf(" var CLeftTicketUrl = ") + 43);
            ticketUrl = ticketUrl.replaceAll(" ", "");
            String aa[] = ticketUrl.split("=");
            buffer.append(aa[1].substring(aa[1].indexOf("'") + 1, aa[1].indexOf(";") - 1));
            buffer.append("?");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 通过出发日期-出发站点-到达站点查询车票列表
     *
     * @param departureDate
     * @param fromSite
     * @param toSite
     * @return
     */
    public static JSONObject getTrainList(String departureDate, String fromSite, String toSite) {
        StringBuilder stringBuilder = new StringBuilder();
        String trainUrl = getUrlApi();
        String newurl = stringBuilder.append(trainUrl).append("leftTicketDTO.train_date=").append(departureDate).append(
            "&leftTicketDTO.from_station=")
            .append(fromSite).append("&leftTicketDTO.to_station=").append(toSite).append("&purpose_codes=ADULT")
            .toString();
        //调用方法,获取两地之间的火车数组
        String result = httpGet(newurl);
        JSONObject jSONObject = JSONObject.parseObject(result);
        JSONObject listObject = jSONObject.getJSONObject("data");
        return listObject;
    }

    private static String httpGet(String url) {
        return httpGet(url, "utf-8", 30000, 30000);
    }

    /**
     * httpGet
     *
     * @param url
     * @param charset
     * @return
     */
    public static String httpGet(String url, String charset, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig;
        ThreadLocal<List<ProxyInfo>> threadLocal = ProxyUtil.getLocalProxyInfos();
        List<ProxyInfo> list = threadLocal.get();
        if (list !=null && list.size() > 0) {
            try {
                int i = ThreadLocalRandom.current().nextInt(0, list.size() - 1);
                ProxyInfo proxyInfo = list.get(i);
                requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(
                    socketTimeout).setProxy(new HttpHost(proxyInfo.getIp(), Integer.parseInt(proxyInfo.getPort())))
                    .build();
            } catch (Exception e) {
                e.printStackTrace();
                requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(
                    socketTimeout).build();
            }

        } else {
            requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(
                socketTimeout).build();
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response;
        String result = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, charset);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
