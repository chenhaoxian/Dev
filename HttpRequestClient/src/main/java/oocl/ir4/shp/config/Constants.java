package oocl.ir4.shp.config;

import org.apache.http.message.BasicHeader;

/**
 * Created by CHENHY on 9/15/2017.
 */
public class Constants {
	public static final String DEFAULT_PWD = "";

	public static final BasicHeader[] LOGIN_REQUEST_HEADER = new BasicHeader[]{
			new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"),
			new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36"),
			new BasicHeader("Accept-Encoding", "gzip, deflate"),
			new BasicHeader("Accept-Accept-Language", "en,zh-CN;q=0.8,zh;q=0.6"),
	};
}
