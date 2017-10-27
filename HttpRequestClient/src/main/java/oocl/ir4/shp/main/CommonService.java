package oocl.ir4.shp.main;

import oocl.ir4.shp.util.ConfigureUtil;
import oocl.ir4.shp.util.LoadDataUtil;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhy on 8/21/2017.
 */
public abstract class CommonService {

	private static List<String> synDataList = null;
	protected static Properties configuration ;
	protected static ExecutorService exe;

	protected abstract void requestJob();

	public final void execute() throws Exception {
		exe = Executors.newFixedThreadPool(50);
		configuration = ConfigureUtil.getConfigureProperty("");

		this.preExecute();
		this.requestJob();
		this.afterExecute();
		this.finalAction(exe);
	}

	private List<String> getDataList() throws IOException {
		return LoadDataUtil.loadData(SendUpdateRequestMain.class.getClassLoader().getResource("data/data_list.txt").getPath());
	}

	protected void preExecute() throws Exception{}

	protected void afterExecute(){
	}

	private void finalAction(ExecutorService exe){
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
