package oocl.ir4.shp.main;

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

	private static final Properties configuration = ConfigureUtil.getConfigureProperty("");
	private static List<String> synDataList = null;

	public static void main(String[] args){
//		System.out.println(configuration.getProperty("DATA_LIST_PATH"));
		try {
			List<String> dataList = LoadDataUtil.loadData(SendUpdateRequestMain.class.getClassLoader().getResource("data/data_list.txt").getPath());
			synDataList = Collections.synchronizedList(dataList);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void requestJob() {
//		ExecutorService exe = Executors.newFixedThreadPool(configuration.getProperty("THREAD_AMOUNT"));
	}
}
