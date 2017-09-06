package oocl.ir4.shp.request.processor;

import java.util.Date;
import java.util.List;

/**
 * Created by chenhy on 8/15/2017.
 */
public class SuppToolUpdateProcessor implements Runnable{

	private String bizKey = "";
	private static List<String> synDataList;
	private static int dataListIndex = 0;
	private int threadId ;
	private Date date ;

	public void run() {
		try {
			while (dataListIndex < synDataList.size()){
				System.out.println(dataListIndex + "---->" + synDataList.get(dataListIndex));
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				addIndex();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("==========done thread" + threadId + "==============");
			System.out.print("total cast time:");
			System.out.println(new Date().getTime()/1000-date.getTime()/1000 + "s");
		}
	}

	private synchronized static void addIndex() throws Exception {
		dataListIndex++;
	}

	public SuppToolUpdateProcessor() {
		if (date == null) {
			date = new Date();
		}
	}

	public void setSynDataList(List<String> synDataList) {
		this.synDataList = synDataList;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
}
