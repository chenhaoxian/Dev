package oocl.ir4.shp.request.enums;

/**
 * Created by chenhy on 8/15/2017.
 */
public enum RequestType {
	SUPP_TOOL_GET_REQUEST("SuppToolGet"),
	SUPP_TOOL_UPDATE_REQUEST("SuppToolUpdate"),
	CHECK_VERSION("checkVersion"),
	CHECK_VERSION1("checkVersion1");

	private String value;

	private RequestType(String requestType){
		this.value = requestType;
	}

	public String getValue(){
		return this.value;
	}

	public static RequestType fromValue(String requestType){
		for(RequestType typeEnum : values()){
			if(typeEnum.value.equals(requestType)){
				return typeEnum;
			}
		}
		throw new IllegalArgumentException();
	}
}
