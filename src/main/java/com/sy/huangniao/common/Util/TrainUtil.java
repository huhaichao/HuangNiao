package com.sy.huangniao.common.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.client.ClientProtocolException;
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
            String trainDom = getContentFromUrl("https://kyfw.12306.cn/otn/leftTicket/init", "UTF-8");
            String context = trainDom.substring(trainDom.indexOf("/*<![CDATA[*/"), trainDom.indexOf("/*]]>*/"));
            String ticketUrl = context.substring(context.indexOf(" var CLeftTicketUrl = "),
                context.indexOf(" var CLeftTicketUrl = ") + 43);
            ticketUrl = ticketUrl.replaceAll(" ", "");
            String aa[] = ticketUrl.split("=");
            buffer.append(aa[1].substring(aa[1].indexOf("'") + 1, aa[1].indexOf(";") - 1));
            buffer.append("?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 从网页上获取数据
     *
     * @param myUrl URL地址
     * @return 网页上的数据，string类型
     * @throws IOException
     */
    public static String getContentFromUrl(String myUrl, String charset)
        throws IOException {
        URL u = new URL(myUrl);
        InputStream in = u.openStream();
        StringBuilder sb = new StringBuilder();
        byte[] buff = new byte[1024];
        int len;
        while ((len = in.read(buff)) != -1) {
            // 此处使用UTF-8编码，如果遇到像新浪这样的网站编码不是UTF-8的，就会乱，
            // 此处我就不过细处理了
            sb.append(new String(buff, 0, len, charset));

        }
        in.close();
        return String.valueOf(sb);
    }

    /**
     * 通过出发日期-出发站点-到达站点查询车票列表
     *
     * @param departureDate
     * @param fromSite
     * @param toSite
     * @return
     */
    public static JSONArray getTrainList(String departureDate, String fromSite, String toSite) {
        StringBuilder stringBuilder = new StringBuilder();
        String trainUrl = getUrlApi();
        String newurl = stringBuilder.append(trainUrl).append("leftTicketDTO.train_date=").append(departureDate).append(
            "&leftTicketDTO.from_station=")
            .append(fromSite).append("&leftTicketDTO.to_station=").append(toSite).append("&purpose_codes=ADULT")
            .toString();
        //调用方法,获取两地之间的火车数组
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGett = new HttpGet(newurl);
        CloseableHttpResponse Response;
        String result = null;
        try {
            Response = httpClient.execute(httpGett);
            org.apache.http.HttpEntity entity = Response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jSONObject = JSONObject.parseObject(result);
        Object listObject = jSONObject.get("data");
        jSONObject = JSONObject.parseObject(listObject.toString());
        JSONArray json = jSONObject.getJSONArray("result");
        return json;
    }
}
