package oocl.ir4.shp.request.processor;


import com.sun.net.httpserver.spi.HttpServerProvider;
import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import oocl.ir4.shp.config.Constants;
import oocl.ir4.shp.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import sun.net.httpserver.DefaultHttpServerProvider;

import java.net.InetSocketAddress;

/**
 * Created by chenhy on 8/24/2017.
 */
public class CheckVersionProcessor implements Runnable {

	private static String cookie = null;

	public void run() {
		try {


			HttpPost httpGet = new HttpPost("http://ssoidppp.oocl.com/idp/startSSO.ping?PartnerSpId=OOCL:SP:SAML1PP&IdpAdapterId=CDIWA&TargetResource=http://hkgccf.oocl.com:3000/sso/login/opentoken/callback");
			httpGet.setHeaders(Constants.LOGIN_REQUEST_HEADER);
			httpGet.addHeader(getAuthorizationHeader());
			HttpResponse rs = HttpClients.createDefault().execute(httpGet);
			String body = EntityUtils.toString(rs.getEntity());
			if (rs.getStatusLine().getStatusCode() == 401) {
				throw new RuntimeException("Authorize failed of user: " + "chenhy");
			}
			HttpGet get1 = new HttpGet("http://ssoidppp.oocl.com/idp/CliOT/resumeSAML11/idp/startSSO.ping");
			get1.setHeaders(Constants.LOGIN_REQUEST_HEADER);
			get1.setHeaders(rs.getHeaders("Set-Cookie"));
			rs = HttpClients.createDefault().execute(get1);
			body = EntityUtils.toString(rs.getEntity());
			if (rs.getStatusLine().getStatusCode() == 401) {
				throw new RuntimeException("Authorize failed of user: " + "chenhy");
			}
			HttpPost post2 = new HttpPost("http://ssosppp.oocl.com/sp/acs.saml1");
			post2.setHeaders(Constants.LOGIN_REQUEST_HEADER);
			post2.setHeaders(rs.getHeaders("Set-Cookie"));
			rs = HttpClients.createDefault().execute(httpGet);
			body = EntityUtils.toString(rs.getEntity());
			if (rs.getStatusLine().getStatusCode() == 401) {
				throw new RuntimeException("Authorize failed of user: " + "chenhy");
			}

			if (rs.getStatusLine().getStatusCode() == 200) {
				this.setCookie(rs.getHeaders("Set-Cookie"));
			}
			HttpGet get2 = new HttpGet("http://hkgccf.oocl.com:3000/properties/prePrd/wls12c");
			get2.setHeaders(Constants.LOGIN_REQUEST_HEADER);
			get2.setHeaders(rs.getHeaders("Set-Cookie"));
			rs = HttpClients.createDefault().execute(get2);
			body = EntityUtils.toString(rs.getEntity());
			if (rs.getStatusLine().getStatusCode() == 401) {
				throw new RuntimeException("Authorize failed of user: " + "chenhy");
			}
			System.out.println(body);
			System.out.println(cookie);

//			response=httpClient.execute(httpGet);
//			String setCookie = response.getFirstHeader("Set-Cookie").getValue();;
//			System.out.println(setCookie);
//			HttpEntity entity=response.getEntity();
//			System.out.println(EntityUtils.toString(entity, "utf-8"));
//			response.close();
//			httpClient.close();


		}catch (Exception e){
			System.out.println("fail----->"+e.getMessage());
		}
	}

	public static BasicHeader getAuthorizationHeader(){
		Base64 token = new Base64();
		String userName = "CHENHY";
		StringUtil.assertNotNull(userName, "username");
		String authenticationEncoding = token.encodeAsString((userName + ":" + Constants.DEFAULT_PWD).getBytes());
		return new BasicHeader("Authorization", "Basic " + authenticationEncoding);
	}

	private static void setCookie(Header[] cookieHeaders) {
		if (cookieHeaders == null || cookieHeaders.length == 0){
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (Header header : cookieHeaders) {
			sb.append(header.getValue().split(";")[0]).append("; ");
		}
		cookie = sb.toString();
	}

}
