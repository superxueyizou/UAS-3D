package dominant;

import javax.swing.JFrame;

import configuration.SAAConfigurator;

import modeling.SAAModelWithUI;
import sim.display.Console;

/**
 * @author xueyi
 *Simulation with UI but without the use of GA
 */
public class SimulationWithUI 
{
	/**
	 * @param args
	 */
	
    public static void main(String[] args)
    {
    	SAAModelWithUI saaModelWithUI = new SAAModelWithUI(1580, 1164);
    	    	    	
    	Console c = new Console(saaModelWithUI); 
    	c.setBounds(1580, 0, 340, 380); 
		c.setVisible(true);		
		c.setWhenShouldEnd(50);
		
		SAAConfigurator configurator = new SAAConfigurator(saaModelWithUI.state, saaModelWithUI);
		configurator.setBounds(1580, 380, 340,784); 
		configurator.setVisible(true);
		configurator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				
    }
}
