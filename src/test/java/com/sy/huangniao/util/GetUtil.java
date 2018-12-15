package com.sy.huangniao.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class GetUtil {


    public String[] getList(String url) {
        CloseableHttpClient httPclient = HttpClients.createDefault();
        HttpGet httpgett = new HttpGet(url);
        CloseableHttpResponse Response;
        String result1 = null;
        try {
            Response = httPclient.execute(httpgett);
            org.apache.http.HttpEntity entity = Response.getEntity();
            result1 = EntityUtils.toString(entity, "utf-8");
            //System.out.println("请求结果"+result1);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject jSONObject = JSONObject.parseObject(result1);
        Object listObject = jSONObject.get("data");
        jSONObject = JSONObject.parseObject(listObject.toString());
        JSONArray json = jSONObject.getJSONArray("result");
        //存放火车列次的数组
        String[] strs = new String[json.size()];
        for (int i = 0; i < json.size(); i++) {
            String str = json.getString(i);
            String[] arr = str.split("[|]");
            strs[i] = arr[3];
        }
        return strs;

    }
}
