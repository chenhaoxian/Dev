package oocl.ir4.shp.request.processor;

import oocl.ir4.shp.util.ConfigureUtil;
import oocl.ir4.shp.util.GenerateDataHelper;

import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * Created by chenhy on 8/15/2017.
 */
public class SuppToolGetProcessor implements Runnable{

	private Properties configuration;
	private FileInputStream propertiesFileIn;

	private static BufferedReader bufReader;
	private static int i = 1;
	private static PrintWriter printWriter;
	private static PrintWriter printWriter_output2;

	private Date date;
	private int threadId;

	public void run() {
		StringBuffer allStrBuf = new StringBuffer();
		StringBuffer irlStrBuf = new StringBuffer();
		int j = i;
		try {
			for (String line = null; (line = myReadLine(bufReader)) != null; line = null) {
				j++;
				System.out.println("line" + (i++) + "£º" + line);
				String url = configuration.getProperty("URL") + line.trim() + configuration.getProperty("LW_VERSION");
				String document = GenerateDataHelper.generateDataFromURL(url);
				if (document != null) {
					handleData(document, irlStrBuf, line, allStrBuf);
				} else {
					allStrBuf.append(line + "--->" + "No record;" + "\n");
				}
			}
			myWriteLine(printWriter, allStrBuf.toString().toCharArray());
			myWriteLine(printWriter_output2, irlStrBuf.toString().toCharArray());

			printWriter.flush();
			printWriter_output2.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			System.out.println("==========done thread" + threadId + "==============");
			System.out.print("total cast time:");
			System.out.println(new Date().getTime() / 1000 - date.getTime() / 1000 + "s");
		}
	}

	private synchronized static String myReadLine(BufferedReader reader) throws Exception {
		return reader.readLine();
	}

	private synchronized static void myWriteLine(PrintWriter writer, char[] charArray) throws Exception {
		writer.write(charArray);
	}

	public void handleData(String document, StringBuffer irlStrBuf, String line, StringBuffer allStrBuf) {
		String key1 = "<" + configuration.getProperty("KEY_WORD") + ">";
		String key2 = "</" + configuration.getProperty("KEY_WORD") + ">";
		String subRegionNodeStr = "";
		try {
			subRegionNodeStr = key1 + document.split(key1)[1].split(key2)[0] + key2;
		} catch (Exception e) {
			subRegionNodeStr = "!!!!!!!! not exist !!!!!!";
		}
		if (subRegionNodeStr.contains("<code>IRL</code>")) {
			irlStrBuf.append(line + "\n");
		}
		allStrBuf.append(line + "--->" + subRegionNodeStr + ";" + "\n");
	}

	public SuppToolGetProcessor() {
//		this.threadId = id;
		try {
			if(configuration == null || configuration.isEmpty()){
				configuration = ConfigureUtil.getConfigureProperty("");
			}
			if (bufReader == null) {
				bufReader = new BufferedReader(new FileReader(SuppToolUpdateProcessor.class.getClassLoader().getResource("data/data_list.txt").getPath()));
			}
			if (printWriter == null) {
				printWriter = new PrintWriter(SuppToolUpdateProcessor.class.getClassLoader().getResource(configuration.getProperty("OUTPUT_FILE1")).getPath());
			}
			if (printWriter_output2 == null) {
				printWriter_output2 = new PrintWriter(SuppToolUpdateProcessor.class.getClassLoader().getResource(configuration.getProperty("OUTPUT_FILE2")).getPath());
			}
			if (date == null) {
				date = new Date();
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setConfiguration(Properties configuration) {
		this.configuration = configuration;
	}

	public void closeAll() {
		try {
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		printWriter.flush();
		printWriter.close();
		printWriter_output2.flush();
		printWriter_output2.close();
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

}
