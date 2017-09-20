package oocl.ir4.shp.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

	private static List<String> synDataList = null;
	private static String SHPFE_PORT = "8110" ;
	private static String SHPBE_PORT = "8111" ;//be
	private static String SHPBEEXP_PORT = "8111" ;
	private static String SHPTXL_PORT = "8184" ;//
	private static String SHPV_PORT = "8123" ;
	private static List<String> shpVersionUrls ;

	public static void main(String[] arg0){

		try {
			shpVersionUrls = new ArrayList<String>();
			String fileContents = LoadDataUtil.loadContents(CheckVersionMain.class.getClassLoader().getResource("data/version.txt").getPath());
			JsonParser parse =new JsonParser();
			JsonArray json = (JsonArray) parse.parse(fileContents);
			for(int i=0; i<json.size(); i++){
//				System.out.println(json.get(i).getAsJsonObject().get("destFolder"));
				String destFolder = json.get(i).getAsJsonObject().get("destFolder").toString().replaceAll("\"","");
				if(destFolder.contains("/home/ir4domshp")){
					JsonArray servers = json.get(i).getAsJsonObject().get("servers").getAsJsonArray();
					for(int j=0; j<servers.size(); j++){
//						System.out.println(json.get(i).getAsJsonObject().get("servers").getAsJsonArray().get(j).getAsJsonObject().get("name").toString().replaceAll("\"",""));
						String server = servers.get(j).getAsJsonObject().get("name").toString().replaceAll("\"","");
						String port = "8109";
						String wls = "wls_dom_shp";
						if(destFolder.contains("shpfe")){
							port = SHPFE_PORT;
						}else if(destFolder.contains("shpbe")){
							port = SHPBE_PORT;
						}else if(destFolder.contains("shptxl")){
							port = SHPTXL_PORT;
							wls = wls + "txl";
						}else if(destFolder.contains("shpv")){
							port = SHPV_PORT;
						}

						String url = "http://"
								+ server
								+ ":"
								+ port
								+ "/"
								+ wls
								+ "/verification/version.jsp";
						shpVersionUrls.add(url+"#"+destFolder);
					}
				}

			}
			CheckVersionMain main = new CheckVersionMain();
			main.requestJob();

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void requestJob() {

		ExecutorService exe = Executors.newFixedThreadPool(50);
		for(int i=0; i<shpVersionUrls.size(); i++){
			RequestProcessors processors = RequestProcessors.fromValue("checkVersion1");
			CheckVersionProcessorV1 checkVersionProcessorV1 = (CheckVersionProcessorV1) processors.createRequestProcessors();
			checkVersionProcessorV1.setUrl(shpVersionUrls.get(i).split("#")[0]);
			checkVersionProcessorV1.setBizKey(shpVersionUrls.get(i).split("#")[1]);
			exe.execute(checkVersionProcessorV1);
		}
		exe.shutdown();
		while (true) {
			if (exe.isTerminated()) {
				System.out.println("End");
				break;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
