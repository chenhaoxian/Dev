package oocl.ir4.shp.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhy on 8/16/2017.
 */
public class LoadDataUtil {

	public static List<String> loadData(String dataFilePath) throws IOException {
		List<String> dataList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(dataFilePath));
		String line;
		long start = System.currentTimeMillis();
		System.out.println("Begin to load data from " + dataFilePath);
		while((line = br.readLine()) != null) {
			dataList.add(line);
		}
		System.out.println("Load data complete. Total " + dataList.size() + " records, cost: " + (System.currentTimeMillis() - start) + "ms");
		System.out.println("\n");
		return dataList;
	}

	public static String loadContents(String dataFilePath) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(dataFilePath));
		String line;
		long start = System.currentTimeMillis();
		System.out.println("Begin to load data from " + dataFilePath);
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
//		System.out.println("Load data complete. Total " + dataList.size() + " records, cost: " + (System.currentTimeMillis() - start) + "ms");
		System.out.println("\n");
		return sb.toString();
	}

}
