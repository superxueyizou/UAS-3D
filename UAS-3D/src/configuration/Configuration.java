/**
 * 
 */
package configuration;

import java.util.HashMap;


/**
 * @author Xueyi
 *
 */
public class Configuration
{
	public GlobalConfig globalConfig;
	public OwnshipConfig ownshipConfig;
	public HashMap<String,IntruderConfig> intrudersConfig;
	
	private static Configuration config=null;
	
	public static Configuration getInstance()
	{
		if( config==null)
		{
			config=new Configuration();
		}
		return config;
	}


	private Configuration() 
	{
		globalConfig = new GlobalConfig();
		ownshipConfig = new OwnshipConfig();
		intrudersConfig = new HashMap<>();
	}
	
	public void addIntruderConfig(String alias, IntruderConfig intruderConfig)
	{
		intrudersConfig.put(alias,intruderConfig);
	}
		
}
