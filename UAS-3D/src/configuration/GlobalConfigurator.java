package configuration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.FileDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import sim.display.GUIState;
import sim.engine.SimState;
import tools.UTILS;
import java.awt.Color;
import javax.swing.JTextField;

public class GlobalConfigurator extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Configuration config= Configuration.getInstance();
	private final SimState state;
	private final GUIState stateWithUI;
	private String file;
	private BufferedReader br;
	
	private JButton btnNext;
	private JButton btnFinish;
	private JButton btnOpen;
	private JButton btnLoad;
	private JTextField alertTimeTextField;

	public GlobalConfigurator(SimState simState, GUIState guiState) 
	{
		this.state=simState;
		this.stateWithUI=guiState;

		setLayout(null);		
		
		JRadioButton rdbtnCAEnable = new JRadioButton("CA Enable?");
		rdbtnCAEnable.setBounds(12, 19, 102, 15);
		this.add(rdbtnCAEnable);
		rdbtnCAEnable.setSelected(config.globalConfig.collisionAvoidanceEnabler);
		rdbtnCAEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.collisionAvoidanceEnabler = true;
				} else {
					
					config.globalConfig.collisionAvoidanceEnabler = false;
				}
			}
		});
		
		JRadioButton rdbtnSSEnable = new JRadioButton("SS Enable?");
		rdbtnSSEnable.setBounds(186, 19, 102, 15);
		this.add(rdbtnSSEnable);
		rdbtnSSEnable.setSelected(config.globalConfig.selfSeparationEnabler);
		rdbtnSSEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.selfSeparationEnabler = true;
				} else {
					
					config.globalConfig.selfSeparationEnabler = false;
				}
			}
		});
		
		JRadioButton rdbtnAccidentDetectorEnable = new JRadioButton("AccidentDetector Enable?");
		rdbtnAccidentDetectorEnable.setBounds(12, 57, 229, 15);
		this.add(rdbtnAccidentDetectorEnable);
		rdbtnAccidentDetectorEnable.setSelected(config.globalConfig.accidentDetectorEnabler);
		rdbtnAccidentDetectorEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.accidentDetectorEnabler = true;
				} else {
					
					config.globalConfig.accidentDetectorEnabler = false;
				}
			}
		});
		
		JRadioButton rdbtnSensorNoiseEnable = new JRadioButton("Sensor noise enable?");
		rdbtnSensorNoiseEnable.setBounds(12, 75, 254, 23);
		add(rdbtnSensorNoiseEnable);
		rdbtnSensorNoiseEnable.setSelected(config.globalConfig.sensorNoiseEnabler);
		rdbtnSensorNoiseEnable.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.sensorNoiseEnabler = true;
				} else {
					
					config.globalConfig.sensorNoiseEnabler = false;
				}
			}
		});
		
		
		JRadioButton rdbtnSensorValueUncertainty = new JRadioButton("Sensor value uncertainty?");
		rdbtnSensorValueUncertainty.setBounds(12, 101, 229, 15);
		this.add(rdbtnSensorValueUncertainty);
		rdbtnSensorValueUncertainty.setSelected(config.globalConfig.sensorValueUncertainty);
		rdbtnSensorValueUncertainty.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.sensorValueUncertainty = true;
				} else {
					
					config.globalConfig.sensorValueUncertainty = false;
				}
			}
		});
		
		JRadioButton rdbtnEncounterGenerator = new JRadioButton("encounter generator enable?");
		rdbtnEncounterGenerator.setBounds(12, 125, 229, 15);
		this.add(rdbtnEncounterGenerator);
		rdbtnEncounterGenerator.setSelected(config.globalConfig.encounterGeneratorEnabler);
		rdbtnEncounterGenerator.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.encounterGeneratorEnabler = true;
				} else {
					
					config.globalConfig.encounterGeneratorEnabler = false;
				}
			}
		});
		
		JLabel lblAlertTime = new JLabel("Alert time");
		lblAlertTime.setBounds(12, 156, 63, 23);
		add(lblAlertTime);
		
		alertTimeTextField = new JTextField();
		alertTimeTextField.setText(""+config.globalConfig.alertTime);
		alertTimeTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.globalConfig.alertTime= Integer.parseInt(alertTimeTextField.getText());
//				System.out.println(config.globalConfig.alertTime);
			}
		});
		alertTimeTextField.setBounds(85, 157, 86, 20);
		add(alertTimeTextField);
		alertTimeTextField.setColumns(10);
		
	
//		JLabel lblModelbuilderSetting = new JLabel("ModelBuilder Setting");
//		lblModelbuilderSetting.setBounds(12, 151, 165, 15);
//		this.add(lblModelbuilderSetting);
//			
//		btnLoad = new JButton("Load");
//		btnLoad.addActionListener(new ActionListener() 
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//				String result = JOptionPane.showInputDialog(null, "copy and paste:", "Genome",JOptionPane.PLAIN_MESSAGE);
//
//				if(result!=null && !result.isEmpty())
//				{
//					result = result.trim();
//					String[] pArr= result.split("\\s+");
////						System.out.println(pArr[1]);
//													
//
//					config.globalConfig.headOnSelected= Double.parseDouble(pArr[2]);
//					config.globalConfig.headOnOffsetY=Double.parseDouble(pArr[3]);
//					config.globalConfig.headOnPrefSpeed=Double.parseDouble(pArr[5]);
//					   		
//				}
//			
//				SAAConfigurator newFrame = new SAAConfigurator(state, stateWithUI);
//				newFrame.setBounds(1500+80, 404, 340,786); // for windows: frame.setBounds(1500+40, 380, 380,700);
//				newFrame.setVisible(true);
//				newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
//				if(br!=null)
//				{
//					System.err.println("you forgoet to close the file");
//				}
//				
//				((SAAConfigurator)((JButton)e.getSource()).getRootPane().getParent()).dispose();				
//				
////					System.out.println(config.globalConfig.selfDestDist);
//			}
//		});
//		btnLoad.setBounds(12, 178, 77, 25);
//		this.add(btnLoad);
//		
//		JButton btnSave = new JButton("Save");
//		btnSave.addActionListener(new ActionListener() 
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//			       
//				String result = JOptionPane.showInputDialog(null, "please add comments:", "Comments",JOptionPane.PLAIN_MESSAGE);
//			
//				String comment = "";
//				if(result!=null && !result.isEmpty())
//				{
//					comment = result.trim();
//				}
//				
//	        	StringBuilder dataItem = new StringBuilder();
//	        	dataItem.append(comment+",");
//	        	dataItem.append(config.globalConfig.selfPrefSpeed+",");
//	        	dataItem.append(config.globalConfig.headOnSelected+",");
//	        	dataItem.append(config.globalConfig.headOnOffsetY+",");
//	        	dataItem.append(config.globalConfig.headOnPrefSpeed);	        	
//	        	UTILS.writeDataItem2CSV("./src/tools/ChallengingDB.csv", dataItem.toString(), true);
//        		
//				}					
//		});
//		btnSave.setBounds(211, 178, 77, 25);
//		this.add(btnSave);
//		
//		JPanel panel = new JPanel();
//		panel.setBackground(Color.YELLOW);
//		panel.setForeground(Color.YELLOW);
//		panel.setBounds(12, 215, 276, 87);
//		add(panel);
//		panel.setLayout(null);
//		
//		
//		final JLabel lblFile = new JLabel("filename");
//		lblFile.setBounds(88, 12, 176, 26);
//		panel.add(lblFile);
//		
//		btnOpen = new JButton("open");
//		btnOpen.setBounds(12, 13, 70, 25);
//		panel.add(btnOpen);
//		btnOpen.addActionListener(new ActionListener() 
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//				
//				FileDialog fd = new FileDialog((SAAConfigurator)((JButton)e.getSource()).getRootPane().getParent(), "select a file", FileDialog.LOAD);
//				fd.setDirectory("/home/viki/GitLocal/Framework/UAS/");
//				fd.setFile("*.csv");
//				fd.setVisible(true);
//				String filename = fd.getFile();
//				file= fd.getDirectory()+fd.getFile();
//				lblFile.setText(filename);
//				System.out.println(file);
//				try {
//					br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//					br.readLine();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				btnNext.setEnabled(true);
//				btnFinish.setEnabled(true);
//				btnOpen.setEnabled(false);
//				btnLoad.setEnabled(false);
//				
//			}
//		});
//		
//		btnNext = new JButton("Next");// won't refresh the panel
//		btnNext.setBounds(12, 50, 66, 25);
//		panel.add(btnNext);
//		btnNext.setEnabled(false);
//		btnNext.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//				try 
//				{					
//					String line = br.readLine();
////					System.out.println(line);
//					if(line!=null && !line.isEmpty())
//					{
//						line = line.trim();
//						String[] pa= line.split(";");
//						String[] pArr = new String[14];
////						System.out.println(pa.length);
//						for (int i=1; i<15; i++)
//						{
//							pArr[i-1]=pa[i];
//						}
////							System.out.println(pArr[1]);
//														
//						config.globalConfig.selfPrefSpeed=Double.parseDouble(pArr[1]);
//						
//						config.globalConfig.headOnSelected= Double.parseDouble(pArr[2]);
//						config.globalConfig.headOnOffsetY=Double.parseDouble(pArr[3]);
//						config.globalConfig.headOnPrefSpeed=Double.parseDouble(pArr[5]);
//						 		
//					}
//				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}			
//			
////				SAAConfigurator newFrame = new SAAConfigurator(state, stateWithUI);
////				newFrame.setBounds(1500+80, 404, 340,786); // for windows: frame.setBounds(1500+40, 380, 380,700);
////				newFrame.setVisible(true);
////				newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
////				
////				((SAAConfigurator)((JButton)e.getSource()).getRootPane().getParent()).dispose();			
//			}
//		});
//		
//		btnFinish = new JButton("Finish");
//		btnFinish.setBounds(120, 50, 76, 25);
//		panel.add(btnFinish);
//		btnFinish.setEnabled(false);
//		btnFinish.addActionListener(new ActionListener() 
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//				if(br!=null)
//				{
//					// Done with the file
//					try {
//						br.close();
//						br = null;
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//				btnNext.setEnabled(false);
//				btnFinish.setEnabled(false);
//				btnOpen.setEnabled(true);
//				btnLoad.setEnabled(true);
//			}
//		});		
				
		
	}
}
