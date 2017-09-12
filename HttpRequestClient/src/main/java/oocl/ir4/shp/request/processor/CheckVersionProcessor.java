package oocl.ir4.shp.request.processor;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenhy on 8/24/2017.
 */
public class CheckVersionProcessor implements Runnable {

	public void run() {
		try {

			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("http://hkgccf.oocl.com:3000/user")
					.get()
					.addHeader("FWApplicationContext", "{\"userId\":\"CHENHY\",\"clientId\":\"\",\"artContext\":\"C7836486-9FB0-2B39-70DB9102E97B9C0D 0\",\"token\":null}")
					.addHeader("cache-control", "no-cache")
					.addHeader("postman-token", "1140785e-d531-b90e-8bbe-3cccb72bd383")
					.build();

			Response response = client.newCall(request).execute();

			System.out.println(response.body().string());
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
}
