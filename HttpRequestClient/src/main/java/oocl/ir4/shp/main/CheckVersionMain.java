package oocl.ir4.shp.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import oocl.ir4.shp.helper.CheckVersionHelper;
import oocl.ir4.shp.request.enums.RequestProcessors;
import oocl.ir4.shp.request.enums.RequestType;
import oocl.ir4.shp.request.processor.CheckVersionProcessorV1;
import oocl.ir4.shp.util.LoadDataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhy on 8/24/2017.
 */
public class CheckVersionMain extends CommonService{

	private static List<String> shpVersionUrls ;

	public static void main(String[] arg0){

		try {
			shpVersionUrls = new ArrayList<String>();
			String fileContents = LoadDataUtil.loadContents(CheckVersionMain.class.getClassLoader().getResource("data/version.txt").getPath());
			CheckVersionHelper checkVersionHelper = new CheckVersionHelper();
			checkVersionHelper.generateURLs(shpVersionUrls,fileContents);
			CheckVersionMain main = new CheckVersionMain();
			main.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void requestJob() {
		for(int i=0; i<shpVersionUrls.size(); i++){
			RequestProcessors processors = RequestProcessors.fromValue("checkVersion1");
			CheckVersionProcessorV1 checkVersionProcessorV1 = (CheckVersionProcessorV1) processors.createRequestProcessors();
			checkVersionProcessorV1.setUrl(shpVersionUrls.get(i).split("#")[0]);
			checkVersionProcessorV1.setBizKey(shpVersionUrls.get(i).split("#")[1]);
			exe.execute(checkVersionProcessorV1);
		}

	}
}
