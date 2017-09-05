package oocl.ir4.shp.main;

import oocl.ir4.shp.request.enums.RequestProcessors;
import oocl.ir4.shp.request.enums.RequestType;

/**
 * Created by chenhy on 8/24/2017.
 */
public class CheckVersionMain extends CommonService{

	public static void main(String[] arg0){

		CheckVersionMain main = new CheckVersionMain();
		main.requestJob();

	}

	public void requestJob() {
		RequestProcessors processors = RequestProcessors.fromValue("checkVersion");
		processors.createRequestProcessors().run();

	}
}
