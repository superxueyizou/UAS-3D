package configuration;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import sim.display.GUIState;
import sim.engine.SimState;


public class SAAConfigurator extends JFrame
{	
	private static final long serialVersionUID = 1L;
	public JTabbedPane tabbedPane=null;
	public JPanel modelBuilderConfigPanel=null;
	public JPanel ownshipConfigPanel=null;
	public JPanel intrudersPanel=null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SAAConfigurator frame = new SAAConfigurator(null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SAAConfigurator(SimState state, GUIState stateWithUI) 
	{
		super("SAA Configurator");
		this.setBounds(1500+80, 404, 340,742);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 12, 326, 818);
				
		modelBuilderConfigPanel = new GlobalConfigurator(state, stateWithUI);		
		modelBuilderConfigPanel.setLayout(null);
		tabbedPane.addTab("ModelBuilder", null, modelBuilderConfigPanel, null);
		
		ownshipConfigPanel = new OwnshipConfigurator();
		ownshipConfigPanel.setLayout(null);
		tabbedPane.addTab("Own-ship", null, ownshipConfigPanel, null);
		
					
		intrudersPanel = new IntrudersConfigurator();
		intrudersPanel.setLayout(null);
		tabbedPane.addTab("Intruders", null, intrudersPanel, null);
	
		this.getContentPane().add(tabbedPane);
		

	}
	
	public void refresh()
	{
		JTabbedPane tabbedPane= (JTabbedPane)(this.getContentPane().getComponent(0));
		tabbedPane.remove(1);
		tabbedPane.remove(1);
		
		ownshipConfigPanel = new OwnshipConfigurator();
		tabbedPane.addTab("Own-ship", null, ownshipConfigPanel, null);
					
		intrudersPanel = new IntrudersConfigurator();
		tabbedPane.addTab("Intruders", null, intrudersPanel, null);
	}

}

