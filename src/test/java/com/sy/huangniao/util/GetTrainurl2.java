package com.sy.huangniao.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;

public class GetTrainurl2 {

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
		} catch (Exception e) {
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
		throws Exception {
		//URL u = new URL(myUrl);
		//int responsecode=0;
		//String str =IOUtils.toString(u,charset);
		/**
		 * 获取到可用代理IP	119.101.116.50	9999
		 获取到可用代理IP	119.101.113.133	9999
		 获取到可用代理IP	119.101.113.4	9999
		 获取到可用代理IP	111.177.167.156	9999
		 */

		System.getProperties().setProperty("proxySet", "true");
		System.getProperties().setProperty("http.proxyHost", "111.177.167.156");
		System.getProperties().setProperty("http.proxyPort", "9999");


		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		/*初始cookie*/
		String cookie = "current_captcha_type=Z; _jc_save_toDate=2019-01-06; _jc_save_fromStation=%u5317%u4EAC%2CBJP; _jc_save_fromDate=2019-01-06; _jc_save_toStation=%u4E0A%u6D77%2CSHH";
		DefaultHttpClient client = new DefaultHttpClient(getClientManager());
		HttpGet get = new HttpGet(myUrl);
		get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.addHeader("Connection", "Keep-Alive");
		get.addHeader("Cookie",cookie);
		get.addHeader("Host", "kyfw.12306.cn");
		get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
		HttpResponse response = client.execute(get);

		String responseText = EntityUtils.toString(response.getEntity(), charset);

		/*TrustManager[] trustAllCerts = new TrustManager[1];
		TrustManager tm = new SslUtil.miTM();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		//设置协议http和https对应的处理socket链接工厂的对象



		//创建自定义的httpclient对象
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
		HttpGet httpgett = new HttpGet(myUrl);
		CloseableHttpResponse Response;
		String result1 = null;
		try {
			Response = client.execute(httpgett);
			org.apache.http.HttpEntity entity = Response.getEntity();
			result1 = EntityUtils.toString(entity, "utf-8");
			//System.out.println("请求结果"+result1);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//Document document = Jsoup.connect(myUrl).timeout(3000).get();
		//生成一个URL对象
		//url=new URL("这里填你要访问的网址");
		//打开URL
		//urlConnection = (HttpURLConnection)u.openConnection();
		////伪造一个请求头 一般网页不用，有些网站会看你有没有请求头，比如 12306......
		//urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
		////urlConnection.setRequestProperty("Host","kyfw.12306.cn");
		//urlConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		//urlConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
		//urlConnection.setRequestProperty("Accept-Encoding","gzip, deflate, br");
		//urlConnection.setRequestProperty("Connection","keep-alive");
		//urlConnection.setRequestProperty("Upgrade-Insecure-Requests","1");
		//SslUtil.ignoreSsl();
		//获取服务器响应代码
		//responsecode=urlConnection.getResponseCode();

		//InputStream inputStream =urlConnection.getInputStream();
		//假如响应代码为200，就是代表成功
		//if(responsecode==200){
		//	reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
		//	while((line=reader.readLine())!=null){
		//		System.out.println(line);//在这里干你想干的事情
		//	}
		//}else{
		//	System.out.println("获取不到网页的源码，服务器响应代码为："+responsecode);
		//}
		/*InputStream in = u.openStream();
		StringBuilder sb = new StringBuilder();
		byte[] buff = new byte[1024];
		int len;
		while ((len = in.read(buff)) != -1) {
			// 此处使用UTF-8编码，如果遇到像新浪这样的网站编码不是UTF-8的，就会乱，
			// 此处我就不过细处理了
			sb.append(new String(buff, 0, len, charset));

		}
		in.close();*/
		return String.valueOf("");
	}


	public static void main(String[] args)  {

		try {
			GetTrainurl2.getContentFromUrl("https://kyfw.12306.cn/otn/leftTicket/init","UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static class MyX509TrustManager implements X509TrustManager {

		/* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
         */
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {

		}

		/* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
         */
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {

		}

		/* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
         */
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}

	public static ThreadSafeClientConnManager getClientManager() throws Exception {
		SSLContext ctx = SSLContext.getInstance("TLS");
		X509TrustManager tm = new MyX509TrustManager();

		ctx.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("https", 443, ssf));
		ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);

		return mgr;
	}

	public static  class MySecureProtocolSocketFactory implements
		SecureProtocolSocketFactory {

		private SSLContext sslContext = null;

		public MySecureProtocolSocketFactory() {
		}

		private  SSLContext createEasySSLContext() {
			try {
				SSLContext context = SSLContext.getInstance("SSL");
				context.init(null, new TrustManager[] {new MyX509TrustManager()}, null);
				return context;
			} catch (Exception e) {
				throw new HttpClientError(e.toString());
			}
		}

		/**
		 * @return
		 */
		private SSLContext getSSLContext() {
			if (this.sslContext == null) {
				this.sslContext = createEasySSLContext();
			}
			return this.sslContext;
		}

		public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
			throws IOException, UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
		}

		public Socket createSocket(final String host, final int port,
								   final InetAddress localAddress, final int localPort,
								   final HttpConnectionParams params) throws IOException,
			UnknownHostException, ConnectTimeoutException {
			if (params == null) {
				throw new IllegalArgumentException("Parameters may not be null");
			}
			int timeout = params.getConnectionTimeout();
			if (timeout == 0) {
				return createSocket(host, port, localAddress, localPort);
			} else {
				return ControllerThreadSocketFactory.createSocket(this, host, port, localAddress, localPort, timeout);
			}
		}

		public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(host, port);
		}

		public Socket createSocket(Socket socket, String host, int port,
								   boolean autoClose) throws IOException, UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
		}

	}
}
