package oocl.ir4.shp.util;

import org.apache.commons.lang3.StringUtils;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Created by chenhy on 8/15/2017.
 */
public class ConfigureUtil {

	private static final String DEFAULT_CONFIG_PATH = "config/requestParam.properties";

	private static Properties prop ;

	public static Properties getConfigureProperty(String configPath)  {
		try {
			if(prop == null || prop.isEmpty()){
				prop = new Properties();
				FileInputStream propertiesFileIn = null;
				String filePath ;
				if(StringUtils.isEmpty(configPath)){
					filePath = ConfigureUtil.class.getClassLoader().getResource(DEFAULT_CONFIG_PATH).getPath();
					propertiesFileIn = new FileInputStream(filePath);
				}else{
					filePath = ConfigureUtil.class.getClassLoader().getResource(configPath).getPath();
					propertiesFileIn = new FileInputStream(filePath);
				}
				prop.load(propertiesFileIn);
			}
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return prop;
	}
}
