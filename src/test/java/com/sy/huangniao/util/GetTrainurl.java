package com.sy.huangniao.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GetTrainurl {

	public static String getUrlApi() {
		StringBuffer bufer=new StringBuffer();
		try {
			bufer.append("https://kyfw.12306.cn/otn/");
			String tx = getContentFromUrl("https://kyfw.12306.cn/otn/leftTicket/init", "UTF-8");
			String context=tx.substring(tx.indexOf("/*<![CDATA[*/"), tx.indexOf("/*]]>*/"));
			String tx2=context.substring(context.indexOf(" var CLeftTicketUrl = "), context.indexOf(" var CLeftTicketUrl = ")+43);
			String tx3=tx2.replaceAll(" ", "");
			String aa[]=tx3.split("=");
			bufer.append(aa[1].substring(aa[1].indexOf("'")+1, aa[1].indexOf(";")-1));
			bufer.append("?");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufer.toString();
	}

	/**
	 * 从网页上获取数据
	 *
	 * @param myUrl
	 * URL地址
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

}
