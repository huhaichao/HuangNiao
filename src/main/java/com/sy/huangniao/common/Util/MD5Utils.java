package com.sy.huangniao.common.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * MD5校验工具类
 */
public class MD5Utils {

    /**
     * MD5加密字符串
     *
     * @param str 目标字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5String(String str) {
        return EncryptUtils.md5(str);
    }

    /**
     * 校验密码与其MD5是否一致
     *
     * @param pwd 密码字符串
     * @param md5 基准MD5值
     * @return 检验结果
     */

    public static boolean checkPassword(String pwd, String md5) {
        return EncryptUtils.md5(pwd).equalsIgnoreCase(md5);
    }

    /**
     * 校验密码与其MD5是否一致
     *
     * @param pwd 以字符数组表示的密码
     * @param md5 基准MD5值
     * @return 检验结果
     */

    public static boolean checkPassword(char[] pwd, String md5) {
        return checkPassword(new String(pwd), md5);

    }

    /**
     * 检验文件的MD5值
     *
     * @param file 目标文件
     * @param md5  基准MD5值
     * @return 检验结果
     */

    public static boolean checkFileMD5(File file, String md5) {
        return EncryptUtils.md5(file).equalsIgnoreCase(md5);

    }

    /**
     * 检验文件的MD5值
     *
     * @param fileName 目标文件的完整名称
     * @param md5      基准MD5值
     * @return 检验结果
     */

    public static boolean checkFileMD5(String fileName, String md5) {
        return checkFileMD5(new File(fileName), md5);
    }

    /**
     * 按照加密方法
     * 1、para按照key进行排序，然后组成字符串
     * 2、字符串儿的末尾加上key
     * 3、md5
     * 4、小写转大写
     */
    public static String encryption(Map<String,String> para, String key){
        StringBuffer retMsgB = new StringBuffer();
        TreeMap<String,String> tm = new TreeMap<String,String>();
        tm.putAll(para);
        for (Map.Entry<String, String> entry : tm.entrySet()) {
            String mkey = entry.getKey();
            String mvalue = entry.getValue();

            if(!StringUtils.isBlank(mkey)&&!StringUtils.isBlank(mvalue)){
                String temValue = new String(mvalue);
                //如果 mvalue 包含转移 \ 去除
                if(temValue.contains("\\")){
                    temValue = temValue.replace("\\", "");
                }

                retMsgB.append(mkey+"="+temValue+"&");
            }

        }
        String retMsg = retMsgB.toString();
        if(retMsg.charAt(retMsg.length()-1)=='&'){
            retMsg = retMsg.substring(0,retMsg.length()-1);
        }
        retMsg = retMsg+key;
        System.out.println("验签字符串为： " + retMsg);
        /** 加密 、转大写 */
        retMsg = getMD5String(retMsg).toUpperCase();
        System.out.println("加密验签字符串为： " + retMsg);
        return retMsg.toString();
    }

    public static JSONObject encryption(JSONObject jsonObject, String key){
        Map<String,String> maps = (Map)JSON.parse(jsonObject.toJSONString());
        jsonObject.put("sign",encryption(maps,key));
        return jsonObject;
    }

    /**
     * 验证签名
     * @param json
     * @param key
     * @return
     */
    public static boolean checkEncryption(JSONObject json, String key, String sign){
        Map<String,String> maps = (Map)JSON.parse(json.toJSONString());
        if(encryption(maps,key).equals(sign)){
            return true;
        }else {
            return false;
        }
    }
    /**
     * 验证签名
     * @param maps
     * @param key
     * @return
             */
    public static boolean checkEncryption(Map<String,String> maps, String key, String sign){
        if(encryption(maps,key).equals(sign)){
            return true;
        }else {
            return false;
        }
    }

}
