package oocl.ir4.shp.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.List;

/**
 * Created by CHENHY on 9/28/2017.
 */
public class CheckVersionHelper {

	private static List<String> synDataList = null;
	private static String SHPFE_PORT = "8110" ;
	private static String SHPBE_PORT = "8111" ;//be
	private static String SHPBEEXP_PORT = "8111" ;
	private static String SHPTXL_PORT = "8184" ;//
	private static String SHPV_PORT = "8123" ;

	public void generateURLs(List<String> shpVersionUrls, String fileContents){
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
			if(destFolder.contains("/home/ir4prsbkg")){
				//8144
				JsonArray servers = json.get(i).getAsJsonObject().get("servers").getAsJsonArray();
				for(int j=0; j<servers.size(); j++){
//						System.out.println(json.get(i).getAsJsonObject().get("servers").getAsJsonArray().get(j).getAsJsonObject().get("name").toString().replaceAll("\"",""));
					String server = servers.get(j).getAsJsonObject().get("name").toString().replaceAll("\"","");
					String port = "8144";
					String wls = "wls_prs_bkg";
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
			// shv 8145
			if(destFolder.contains("/home/ir4prsshv")){
				JsonArray servers = json.get(i).getAsJsonObject().get("servers").getAsJsonArray();
				for(int j=0; j<servers.size(); j++){
//						System.out.println(json.get(i).getAsJsonObject().get("servers").getAsJsonArray().get(j).getAsJsonObject().get("name").toString().replaceAll("\"",""));
					String server = servers.get(j).getAsJsonObject().get("name").toString().replaceAll("\"","");
					String port = "8145";
					String wls = "wls_prs_shv";
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
	}

}
