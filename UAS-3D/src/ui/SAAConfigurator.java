package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import sim.display.GUIState;
import sim.engine.SimState;


public class SAAConfigurator extends JFrame implements ActionListener
{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);	
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 12, 326, 818);
				
		JPanel modelBuilderConfigPanel = new ModelBuilder(state, stateWithUI);
		tabbedPane.addTab("ModelBuilder", null, modelBuilderConfigPanel, null);
		modelBuilderConfigPanel.setLayout(null);
		
		JPanel selfConfigPanel = new Self();
		tabbedPane.addTab("Self", null, selfConfigPanel, null);
		selfConfigPanel.setLayout(null);
					
		JPanel headOnPanel = new HeadOn();
		tabbedPane.addTab("HeadOn", null, headOnPanel, null);
		headOnPanel.setLayout(null);
		
//		JPanel crossingPanel = new Crossing();
//		tabbedPane.addTab("Crossing", null, crossingPanel, null);
//		crossingPanel.setLayout(null);
//		
//		JPanel tailApproachPanel = new TailApproach();
//		tabbedPane.addTab("TailApproach", null, tailApproachPanel, null);
//		tailApproachPanel.setLayout(null);
		
		getContentPane().add(tabbedPane);
		contentPane.add(tabbedPane);

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="Refresh")
		{
			this.dispose();
		}
		
	}
}

