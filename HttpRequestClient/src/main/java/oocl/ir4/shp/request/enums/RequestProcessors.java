package oocl.ir4.shp.request.enums;

import oocl.ir4.shp.request.processor.CheckVersionProcessor;
import oocl.ir4.shp.request.processor.CheckVersionProcessorV1;
import oocl.ir4.shp.request.processor.SuppToolGetProcessor;
import oocl.ir4.shp.request.processor.SuppToolUpdateProcessor;

import java.util.List;

/**
 * Created by chenhy on 8/15/2017.
 */
public enum RequestProcessors {
  SUPP_TOOL_GET_REQUEST(RequestType.SUPP_TOOL_GET_REQUEST, SuppToolGetProcessor.class),
  SUPP_TOOL_UPDATE_REQUEST(RequestType.SUPP_TOOL_UPDATE_REQUEST, SuppToolUpdateProcessor.class),
  CHECK_VERSION(RequestType.CHECK_VERSION, CheckVersionProcessor.class) ,
  CHECK_VERSION1(RequestType.CHECK_VERSION1, CheckVersionProcessorV1.class) ;

  private RequestType requestType;
  private Class<? extends Runnable> requestProcessors;

  private RequestProcessors(RequestType requestType, Class<? extends Runnable> requestProcessors){
    this.requestType = requestType;
    this.requestProcessors = requestProcessors;
  }

  public Runnable createRequestProcessors(){
    if(requestProcessors != null){
      try {
        return requestProcessors.getConstructor().newInstance();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public RequestType getRequestType() {
    return requestType;
  }

  public static RequestProcessors fromValue(String requestType){
    for(RequestProcessors requestMap : values()){
      if(requestMap.requestType.getValue().equals(requestType)){
        return requestMap;
      }
    }
    throw new IllegalArgumentException();
  }

}
