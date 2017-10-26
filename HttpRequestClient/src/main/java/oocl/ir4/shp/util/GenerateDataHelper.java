package oocl.ir4.shp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CHENHY on 1/10/2016.
 */
public class GenerateDataHelper {
	
	public static String generateDataFromURL(String urlStr){
		StringBuffer document = new StringBuffer();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if(conn.getResponseCode() == 200){
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;
				while((line = reader.readLine()) != null){
					document.append(line + " ");
				}
				reader.close();
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (!"".equals(document.toString()) && document.toString() != null)?document.toString():null;
	}

}
