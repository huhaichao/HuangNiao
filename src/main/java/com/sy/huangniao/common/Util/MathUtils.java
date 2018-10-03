package com.sy.huangniao.common.Util;

import java.util.Random;

public class MathUtils {
	/**
     * 获取count个随机数
     * @param count 随机数个数
     * @return
     */
    public static String getRanomCode(int count){
        StringBuilder sb = new StringBuilder();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
        }
        return sb.toString();
    }
 
	public static void main(String[] args) {
        System.out.println(MathUtils.getRanomCode(6));
	}

}
