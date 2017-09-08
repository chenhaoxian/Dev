package oocl.ir4.shp.request.processor;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by chenhy on 8/15/2017.
 */
public class SuppToolUpdateProcessor implements Runnable{

//	private String bizKey = "";
	private static List<String> synDataList;
	private static Integer dataListIndex ;
	private static Properties configuration;
	private static PrintWriter resultWriter ;
	private int threadId ;
	private StringBuffer resultSB ;
	private Date date ;

	public void run() {
		try {
			initIndex();
			while (dataListIndex < synDataList.size()){
				System.out.println(dataListIndex + "---->" + synDataList.get(dataListIndex));
				String bizKey = synDataList.get(dataListIndex);
				String urlStr = constructUrlStr(bizKey);
				this.constructAndSendHttpRequest(urlStr,bizKey);
				this.addIndex();
			}
			this.writeResultToFile(resultSB.toString().toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("==========done thread" + threadId + "==============");
			System.out.print("total cast time:");
			System.out.println(new Date().getTime()/1000-date.getTime()/1000 + "s");
		}
	}

	private void constructAndSendHttpRequest(String urlStr, String bizKey) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setRequestMethod("PUT");
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setInstanceFollowRedirects(true);
		httpURLConnection.setRequestProperty("Content-Type", "application/xml");
		httpURLConnection.setRequestProperty("FWApplicationContext",
				"{\"userId\":\"chenhy\",\"token\":null,\"clientId\":\"chenhy-2-w7\",\"artContext\":\"23fe2e06-7231-4a19-bbac-218a016f7e46 0\"}");
		httpURLConnection.setRequestProperty("Accept", "application/xml");
		String xmlInfo = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><searchNumber><searchNumber>1</searchNumber></searchNumber>";
		PrintWriter out = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8"));
		out.write(new String(xmlInfo.getBytes("ISO-8859-1")));
		out.close();
		if(resultSB == null){
			resultSB = new StringBuffer();
		}
		if (httpURLConnection.getResponseCode() == 200) {
			resultSB.append(bizKey + "--->" + "success" + "\n");
//			this.writeResultToFile((bizKey + "--->" + "success" + "\n").toCharArray());
		} else {
//			this.writeResultToFile((bizKey + "--->" + "fail" + "\n").toCharArray());
			resultSB.append(bizKey + "--->" + "fail" + "\n");
		}
		httpURLConnection.disconnect();
	}

	private String constructUrlStr(String bizKey){
		if(StringUtils.isEmpty(bizKey)) return null;

		String urlStr = configuration.getProperty("UPDATE_URL")
				+ configuration.getProperty("OBJECT_TYPE")
				+ "-"
				+ bizKey.trim()
				+ ",xmlCacheType-"
				+ configuration.getProperty("XML_CACHE_TYPE");
		return urlStr;
	}

	private synchronized void addIndex() throws Exception {
		dataListIndex++;
	}

	private synchronized void initIndex() throws Exception {
		if(dataListIndex == null) {
			dataListIndex = 0;
		}else{
			addIndex();
		}
//		if(threadId > 1){
//			addIndex();
//		}
	}

	private synchronized void writeResultToFile(char[] charArray) throws Exception {
		if (resultWriter == null) {
			resultWriter = new PrintWriter(SuppToolUpdateProcessor.class.getClassLoader().getResource(configuration.getProperty("OUTPUT_FILE1")).getPath());
		}
		resultWriter.write(charArray);
		resultWriter.flush();
	}

	public SuppToolUpdateProcessor() {
		if (date == null) {
			date = new Date();
		}
	}

	public void setConfiguration(Properties configuration) {
		SuppToolUpdateProcessor.configuration = configuration;
	}

	public void setSynDataList(List<String> synDataList) {
		this.synDataList = synDataList;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
}
