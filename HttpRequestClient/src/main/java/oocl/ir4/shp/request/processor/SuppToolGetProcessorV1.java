package oocl.ir4.shp.request.processor;

import oocl.ir4.shp.util.GenerateDataHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by chenhy on 8/15/2017.
 */
public class SuppToolGetProcessorV1 implements Runnable{

	private static List<String> synDataList;
	private static Integer dataListIndex ;
	private static Properties configuration;
	private static PrintWriter resultWriter ;
	private int threadId ;
	private StringBuffer resultSB ;
	private Date date ;
	private static PrintWriter printWriter;

	public void run() {
		try {
			StringBuffer allStrBuf = new StringBuffer();
			initIndex();
			while (dataListIndex < synDataList.size()){
				System.out.println(dataListIndex + "---->" + synDataList.get(dataListIndex));
				String bizKey = synDataList.get(dataListIndex);
				String urlStr = constructUrlStr(bizKey);
				String document = GenerateDataHelper.generateDataFromURL(urlStr);
				if (document != null) {
					handleData(document, bizKey, allStrBuf);
				} else {
					allStrBuf.append(bizKey + "--->" + "No record;" + "\n");
				}
				this.addIndex();
			}
			writeResultToFile(allStrBuf.toString().toCharArray());
//			myWriteLine(resultWriter, allStrBuf.toString().toCharArray());
//			resultWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("==========done thread" + threadId + "==============");
			System.out.print("total cast time:");
			System.out.println(new Date().getTime()/1000-date.getTime()/1000 + "s");
		}
	}

	private synchronized static void myWriteLine(PrintWriter writer, char[] charArray) throws Exception {
		writer.write(charArray);
	}

	public void handleData(String document, String line, StringBuffer allStrBuf) {
		String key1 = "<" + configuration.getProperty("KEY_WORD") + ">";
		String key2 = "</" + configuration.getProperty("KEY_WORD") + ">";
		String subRegionNodeStr = "";
		try {
			subRegionNodeStr = key1 + document.split(key1)[1].split(key2)[0] + key2;
		} catch (Exception e) {
			subRegionNodeStr = "!!!!!!!! not exist !!!!!!";
		}
		allStrBuf.append(line + "--->" + subRegionNodeStr + ";" + "\n");
	}

	private String constructUrlStr(String bizKey){
		if(StringUtils.isEmpty(bizKey)) return null;

		String urlStr = configuration.getProperty("URL")
				+ bizKey.trim()
				+ configuration.getProperty("LW_VERSION");
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

	public SuppToolGetProcessorV1() {
		if (date == null) {
			date = new Date();
		}
//		if (printWriter == null) {
//			try {
//				printWriter = new PrintWriter(configuration.getProperty("OUTPUT_FILE1"));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public void setConfiguration(Properties configuration) {
		SuppToolGetProcessorV1.configuration = configuration;
	}

	public void setSynDataList(List<String> synDataList) {
		this.synDataList = Collections.synchronizedList(synDataList);
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

}
