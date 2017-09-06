package oocl.ir4.shp.request.processor;


import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by chenhy on 8/24/2017.
 */
public class CheckVersionProcessor implements Runnable {

	public void run() {
		try {
//			String urlStr = "http://hkgccf.oocl.com:3000/properties/prePrd/wls12c";
//			String cookieVal = "";
//			String key = "";
//			URL url = new URL(urlStr);
//			HttpURLConnection conn  = (HttpURLConnection)url.openConnection();
//			conn.setRequestProperty("FWApplicationContext",
//					" {\"userId\":\"chenhy\",\"token\":null,\"clientId\":\"chenhy-2-w7\",\"artContext\":\"C7836486-9FB0-2B39-70DB9102E97B9C0D 0\"}");
//
//			if(conn.getResponseCode() == 200){
//				StringBuffer document = new StringBuffer();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//				String line1 = null;
//				while((line1 = reader.readLine()) != null){
//					document.append(line1 + " ");
//				}
//				System.out.println(document.toString());
//				reader.close();
//			}else{
//				System.out.println("FAIL!");
//			}



			CloseableHttpClient httpClient= HttpClients.createDefault(); // 创建httpClient实例
			HttpGet httpGet=new HttpGet("http://hkgccf.oocl.com:3000/sso/login/fragmentRedirect/client.html"); // 创建httpget实例
			httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
			CloseableHttpResponse response=httpClient.execute(httpGet); // 执行http get请求
//			response=httpClient.execute(httpGet);
//			response=httpClient.execute(httpGet);
			String setCookie = response.getFirstHeader("Set-Cookie").getValue();;
			System.out.println(setCookie);
			HttpGet httpGet2=new HttpGet("http://hkgccf.oocl.com:3000/properties/prePrd/wls12c");
			CloseableHttpResponse response2=httpClient.execute(httpGet2);
			HttpEntity entity=response2.getEntity(); // 获取返回实体
			System.out.println("网页内容："+ EntityUtils.toString(entity, "utf-8")); // 获取网页内容
			response.close(); // response关闭
			httpClient.close(); // httpClient关闭
			response2.close(); // response关闭
//			httpClient2.close(); // httpClient关闭



		}catch (Exception e){
			System.out.println("fail----->"+e.getMessage());
		}
	}
}
