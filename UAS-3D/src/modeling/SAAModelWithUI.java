package modeling;

import java.awt.Color;
import java.io.FileNotFoundException;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.swing.JFrame;
import javax.vecmath.Color3f;

import modeling.env.Waypoint;
import modeling.uas.UAS;
import sim.display.Controller;
import sim.display.GUIState;
import sim.display3d.Display3D;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal3d.SimplePortrayal3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.simple.BranchGroupPortrayal3D;
import sim.portrayal3d.simple.SpherePortrayal3D;
import sim.portrayal3d.simple.WireFrameBoxPortrayal3D;
import tools.CONFIGURATION;

/**
 * A class for running a simulation with a UI, run to see a simulation with a UI
 * showing it running.
 * 
 * @author Xueyi Zou
 */
public class SAAModelWithUI extends GUIState
{	
	protected SimInitializer simInitializer; 
	
	public Display3D display3D;
	public JFrame display3DFrame;	
	
	private int displayFrameX;
	private int displayFrameY;	

	ContinuousPortrayal3D environment3DPortrayal = new ContinuousPortrayal3D();
	WireFrameBoxPortrayal3D wireFrameP;
   
    public SAAModelWithUI(int UIWidth, int UIHight) 
    {   
        super(new SAAModel(785945568, CONFIGURATION.worldX, CONFIGURATION.worldY, CONFIGURATION.worldZ, true)); 	
        this.displayFrameX=UIWidth;
        this.displayFrameY=UIHight;
    	simInitializer = new SimInitializer((SAAModel) state);
    	 // build the box
        wireFrameP = new WireFrameBoxPortrayal3D(-0.5*CONFIGURATION.worldX,-0.5*CONFIGURATION.worldY,-0.5*CONFIGURATION.worldZ,0.5*CONFIGURATION.worldX,0.5*CONFIGURATION.worldY,0.5*CONFIGURATION.worldZ);

    }
  
    
    public void init(Controller c)
    {
        super.init(c);
          
        // make the display
        display3D = new Display3D(CONFIGURATION.worldX,CONFIGURATION.worldY,this);           
        display3D.scale(1.0 / CONFIGURATION.worldX);

        display3DFrame = display3D.createFrame();
        display3DFrame.setTitle("SAA Simulation");
        c.registerFrame(display3DFrame);   // register the frame so it appears in the "Display" list
        display3DFrame.setVisible(true);
        display3DFrame.setBounds(0, 0, displayFrameX, displayFrameY); //(new Dimension(1580, 1164))
        
		//adding the different layers to the display2D
        display3D.attach(environment3DPortrayal, "Environment3D" );
        display3D.attach(wireFrameP, "WireFrame");
//        display3D.rotateX(90);

    }


	public void start()
	{
		((SAAModel)state).reset();
		simInitializer.generateSimulation();		
		super.start();
		setupPortrayals();	
		
	}


	public void load(SimState state)
	{
		((SAAModel)state).reset();
		simInitializer.generateSimulation();
		super.load(state);
		setupPortrayals();
	}

	
	
	/**
	 * A method which sets up the portrayals of the different layers in the UI,
	 * this is where details of the simulation are coloured and set to different
	 * parts of the UI
	 */
	public void setupPortrayals()
	{		
		SAAModel simulation = (SAAModel) state;		
		
		// tell the portrayals what to portray and how to portray them
		environment3DPortrayal.setField( simulation.environment3D );

		javax.media.j3d.BranchGroup bg=null;
		try {
			bg= BranchGroupPortrayal3D.getBranchGroupForResource(BranchGroupPortrayal3D.class, "shapes/MQ-27.obj");
		} catch (IllegalArgumentException | FileNotFoundException e) {
			e.printStackTrace();
		}

		BranchGroupPortrayal3D bp=new BranchGroupPortrayal3D(bg,1);
		
		environment3DPortrayal.setPortrayalForClass(UAS.class, bp);
		
		environment3DPortrayal.setPortrayalForClass(Waypoint.class, new SpherePortrayal3D(new Color(0, 255, 0), 40, 10)
		{
			private static final long serialVersionUID = 1L;
			
			public javax.media.j3d.TransformGroup getModel(java.lang.Object obj, javax.media.j3d.TransformGroup j3dModel)
			{
				Color3f col=new Color3f(0, 0, 0);
				double scale=40;
				
				int actionCode = ((Waypoint) obj).getAction();
				if(actionCode==31||actionCode==33 ||actionCode==35)
				{//climb					
					switch(actionCode)
					{
					case 31:
						col = new Color3f(255,0,0);//CL25
						scale =40;
						break;
					case 33:
						col = new Color3f(255, 0, 0);//SCL25
						scale =80;
						break;
					case 35:
						col = new Color3f(255, 0, 0);//SCL42	
						scale =120;
					}
				}
				else if(actionCode==32||actionCode==34 ||actionCode==36)
				{//descend	
					double k=1;
					switch(actionCode)
					{
					case 32:
						col = new Color3f(0,255,0);//DES25
						scale =40*k;
						break;
					case 34:
						col = new Color3f(0, 255, 0);//SDES25
						scale =80*k;
						break;
					case 36:
						col = new Color3f(0, 255, 0);//SDES42	
						scale =120*k;
					}
				}
				else if(actionCode==30)
				{//COC
					col = new Color3f(0, 0, 255);
					scale = 40;
				}
				else
				{
					//white for normal
					col = new Color3f(255, 255, 255);
					scale = 40;
				}

				//Create the coloring attributes
				ColoringAttributes ca = new ColoringAttributes(col, ColoringAttributes.NICEST);
				//Add the attributes to the appearance
				Appearance ap = new Appearance();
				ap.setColoringAttributes(ca);			
				//setTransform(j3dModel,javax.media.j3d.Transform3D transform);
				setAppearance(j3dModel, ap);
				
				setScale(j3dModel,scale);
				return  super.getModel(obj, j3dModel);
			}
		});				

		display3D.createSceneGraph();
		// reschedule the displayer
		display3D.reset();
		
	}
	
	

    public void quit()
    {
        super.quit();        
        if (display3DFrame!=null) display3DFrame.dispose();
        display3DFrame = null;
        display3D = null;
    }
    
    
    
    public static String getName() { return "UAS-SAA-Sim"; }

	public Object getSimulationInspectedObject(){return state;}
    
    public Inspector getInspector()
    {
    	Inspector i = super.getInspector();
    	i.setVolatile(true);
    	return i;
    }   

}
