package modeling.encountergenerator;

import modeling.SAAModel;
import configuration.Configuration;
import configuration.IntruderConfig;

public class CrossingModel
{
	public static IntruderConfig newCrossing(Configuration config)
	{
		int numHeadOn=config.intrudersConfig.size()+1;
		int sign=(numHeadOn%2 ==0)?-1:+1;
		int scale=numHeadOn/2;
			
		IntruderConfig crossingConfig = new IntruderConfig();
		crossingConfig.intruderGs=-crossingConfig.intruderGs;	
//		crossingConfig.intruderOffsetX = config.globalConfig.alertTime*(config.ownshipConfig.ownshipVx-crossingConfig.intruderGs);
		crossingConfig.intruderOffsetY = sign*scale*200;
//		crossingConfig.intruderOffsetZ = 2*(SAAModel.getExistingInstance().random.nextDouble()-0.5)*5000;
		return crossingConfig;
	}

}
