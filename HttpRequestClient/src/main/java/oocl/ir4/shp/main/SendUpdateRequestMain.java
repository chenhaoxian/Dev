package oocl.ir4.shp.main;

import oocl.ir4.shp.request.enums.RequestProcessors;
import oocl.ir4.shp.request.processor.SuppToolUpdateProcessor;
import oocl.ir4.shp.util.ConfigureUtil;
import oocl.ir4.shp.util.LoadDataUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhy on 8/15/2017.
 */
public class SendUpdateRequestMain extends CommonService{

	private static List<String> synDataList = null;

	public static void main(String[] args){
		try {
			synDataList = LoadDataUtil.loadData(SendUpdateRequestMain.class.getClassLoader().getResource("data/data_list.txt").getPath());
			SendUpdateRequestMain main = new SendUpdateRequestMain();
			main.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void requestJob() {
		int theadAmount = Integer.parseInt(configuration.getProperty("THREAD_AMOUNT"));
		for(int i=1; i<=theadAmount; i++){
			RequestProcessors processors = RequestProcessors.fromValue("SuppToolUpdate");
			SuppToolUpdateProcessor suppToolUpdateProcessor = (SuppToolUpdateProcessor) processors.createRequestProcessors();
			suppToolUpdateProcessor.setSynDataList(synDataList);
			suppToolUpdateProcessor.setThreadId(i);
			suppToolUpdateProcessor.setConfiguration(configuration);
			exe.execute(suppToolUpdateProcessor);
		}
	}
}
