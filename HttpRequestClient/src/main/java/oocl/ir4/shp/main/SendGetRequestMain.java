package oocl.ir4.shp.main;

import oocl.ir4.shp.request.enums.RequestProcessors;
import oocl.ir4.shp.request.processor.SuppToolGetProcessor;
import oocl.ir4.shp.request.processor.SuppToolUpdateProcessor;
import oocl.ir4.shp.util.ConfigureUtil;
import oocl.ir4.shp.util.LoadDataUtil;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhy on 8/15/2017.
 */
public class SendGetRequestMain extends CommonService{

	public static void main(String[] args){
		try {
			SendGetRequestMain m = new SendGetRequestMain();
			m.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void requestJob() {
		int theadAmount = Integer.parseInt(configuration.getProperty("THREAD_AMOUNT"));
		for(int i=1; i<=theadAmount; i++){
			RequestProcessors processors = RequestProcessors.fromValue("SuppToolGet");
			SuppToolGetProcessor suppToolGetProcessor = (SuppToolGetProcessor) processors.createRequestProcessors();
			suppToolGetProcessor.setThreadId(i);
			suppToolGetProcessor.setConfiguration(configuration);
			exe.execute(suppToolGetProcessor);
		}
	}

}
