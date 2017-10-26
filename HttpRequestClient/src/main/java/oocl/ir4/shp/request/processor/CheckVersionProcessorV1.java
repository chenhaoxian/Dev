package oocl.ir4.shp.request.processor;


import oocl.ir4.shp.config.Constants;
import oocl.ir4.shp.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chenhy on 8/24/2017.
 */
public class CheckVersionProcessorV1 implements Runnable {

	private static String cookie = null;

	private String urlStr ;
	private String bizKey ;

	public void run() {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			String line = null;
			if(conn.getResponseCode() == 200){
				StringBuffer document = new StringBuffer();
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				while((line = reader.readLine()) != null){
					document.append(line + " ");
				}
				System.out.println(urlStr+" ==>"+bizKey+":"+document.toString());
				reader.close();
			}else{
				System.out.println(urlStr+"-->"+bizKey+": FAIL!");
			}
		}catch (Exception e){
			System.out.println(urlStr+"-->"+bizKey+"---->"+"fail----->"+e.getMessage());
		}
	}

	public void setUrl(String urlStr) {
		this.urlStr = urlStr;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
}
