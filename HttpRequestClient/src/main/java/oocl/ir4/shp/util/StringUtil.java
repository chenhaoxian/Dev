package oocl.ir4.shp.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CHENHY on 9/15/2017.
 */
public class StringUtil {

	private static final Pattern urlPattern = Pattern.compile(".*action=\"(.*)\"");
	private static final Pattern paramPattern = Pattern.compile(".*name=\"(.*)\".*value=\"(.*)\"");

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String nvlString(String value, String defaultVal){
		return value == null ? defaultVal : value;
	}

	public static void validateParameter(String paramName, String value) {
		if (isEmpty(value)) {
			throw new RuntimeException("Parameter [" + paramName + "] is empty, please configure it");
		}
	}

	public static String getExceptionStackTrace(Exception e){
		StringBuilder stack = new StringBuilder();
		StackTraceElement[] trace = e.getStackTrace();
		stack.append(e.getMessage());
		for (StackTraceElement traceElement : trace)
			stack.append("\n").append("         at ").append(traceElement);
		return stack.toString();
	}

	public static String listToString(List v, String delimiter) {
		if(CollectionUtils.isEmpty(v)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object obj : v) {
			if(sb.length() > 0) {
				sb.append(delimiter);
			}
			sb.append(obj);
		}
		return sb.toString();
	}

	public static String getActionUrl(String html){
		Matcher m = urlPattern.matcher(html);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	public static String getFormattedHTMLParameters(String html){
		return formatHTMLParameter(getParameters(html));
	}

	public static Map<String, String> getParameters(String html){
		Map<String, String> result = new HashMap<String, String>();
		Matcher m = paramPattern.matcher(html);
		while (m.find()) {
			result.put(m.group(1), m.group(2));
		}
		return result;
	}

	public static List<NameValuePair> getNameValuePairs(Map<String, String> params){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String key:params.keySet()){
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		return nvps;
	}

	public static String formatHTMLParameter(Map<String, String> params){
		StringBuilder sb = new StringBuilder();
		for (String key:params.keySet()){
			if (sb.length()> 0) {
				sb.append("&");
			}
			sb.append(key).append("=").append(params.get(key));
		}
		return sb.toString();
	}

	public static List<NameValuePair> getPOSTRequestParameters(String body) {
		return getNameValuePairs(getParameters(body));
	}

	public static void assertNotNull(String value, String name) {
		if (isEmpty(value))
			throw new RuntimeException(name + " is empty.");
	}

	public static String getCargoNature(String param){
		if (param.contains("\"AD\""))
			return "AD";
		if (param.contains("\"AW\""))
			return "AW";
		if (param.contains("\"RD\""))
			return "RD";
		if (param.contains("\"RF\""))
			return "RF";
		if (param.contains("\"DG\""))
			return "DG";
		return "GC";
	}
}
