package ui;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tools.CONFIGURATION;
import java.awt.Color;


public class HeadOn extends JPanel 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup headOnAutoPilotAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup headOnCollisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup headOnSelfSeparationAlgorithmGroup = new ButtonGroup();
	private JTextField txtHeadontimes;

	public HeadOn() 
	{
		setLayout(null);
		
		
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(12, 12, 290, 31);
			
			JCheckBox chckbxHeadonencounter_1 = new JCheckBox("HeadOn");
			chckbxHeadonencounter_1.setSelected(CONFIGURATION.headOnSelected==1);
			chckbxHeadonencounter_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox chckbxHeadonencounter = (JCheckBox)e.getSource();
					CONFIGURATION.headOnSelected=chckbxHeadonencounter.isSelected()?1:0;
				}
			});
			splitPane.setLeftComponent(chckbxHeadonencounter_1);
			
			JSplitPane splitPane_1 = new JSplitPane();
			splitPane_1.setResizeWeight(0.6);
			splitPane.setRightComponent(splitPane_1);
			
			JLabel lblX = new JLabel("X");
			splitPane_1.setLeftComponent(lblX);
			
			JSplitPane splitPane_2 = new JSplitPane();
			splitPane_2.setResizeWeight(0.8);
			splitPane_1.setRightComponent(splitPane_2);
			
			txtHeadontimes = new JTextField();
			txtHeadontimes.setEnabled(false);
			txtHeadontimes.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField txtHeadontimes=(JTextField) e.getSource();
					CONFIGURATION.headOnIntruders=Integer.parseInt(txtHeadontimes.getText());
				}
			});
			txtHeadontimes.setText("1");
			splitPane_2.setLeftComponent(txtHeadontimes);
			txtHeadontimes.setColumns(5);
			
			JButton btnConfig = new JButton("Config");			
			btnConfig.setEnabled(false);
			btnConfig.setHorizontalAlignment(SwingConstants.RIGHT);
			btnConfig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try 
					{
						    //System.out.println(e.getActionCommand());
							IntruderConfig dialog = new IntruderConfig();
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setName("HeadonEncounter--IntruderConfig");
							dialog.textFieldInit("HeadOnEncounter--IntruderConfig");
							dialog.setModal(true);
							//dialog.setBounds(x, y, width, height)
							dialog.setVisible(true);
					}
						
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
					
				}
			});
			splitPane_2.setRightComponent(btnConfig);
			this.add(splitPane);
			
		}
		
		{
			JPanel sensorSelectionPanel = new JPanel();
			sensorSelectionPanel.setBorder(new TitledBorder(null, "Sensor Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			sensorSelectionPanel.setBounds(12, 55, 290, 47);
			add(sensorSelectionPanel);
			sensorSelectionPanel.setLayout(null);
			
			JCheckBox chckbxPerfectSensor = new JCheckBox("Perfect");
			chckbxPerfectSensor.setBounds(8, 20, 61, 23);
			sensorSelectionPanel.add(chckbxPerfectSensor);
			chckbxPerfectSensor.setSelected((CONFIGURATION.headOnSensorSelection&0B10000) == 0B10000);
			chckbxPerfectSensor.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B10000;
					}
				}
			});
			
			
			JCheckBox chckbxAdsb = new JCheckBox("ADS-B");
			chckbxAdsb.setBounds(71, 20, 55, 23);
			sensorSelectionPanel.add(chckbxAdsb);
			chckbxAdsb.setSelected((CONFIGURATION.headOnSensorSelection&0B01000) == 0B01000);
			chckbxAdsb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B01000;
					}
				}
			});
			
			JCheckBox chckbxTcas = new JCheckBox("TCAS");
			chckbxTcas.setBounds(127, 20, 55, 23);
			sensorSelectionPanel.add(chckbxTcas);
			chckbxTcas.setSelected((CONFIGURATION.headOnSensorSelection&0B00100) == 0B00100);
			chckbxTcas.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B00100;
					}
				}
			});
			
			JCheckBox chckbxRadar = new JCheckBox("Radar");
			chckbxRadar.setBounds(178, 20, 55, 23);
			sensorSelectionPanel.add(chckbxRadar);
			chckbxRadar.setSelected((CONFIGURATION.headOnSensorSelection&0B00010) == 0B00010);
			chckbxRadar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B00010;
					}
				}
			});
			
			JCheckBox chckbxEoir = new JCheckBox("EO/IR");
			chckbxEoir.setBounds(229, 20, 55, 23);
			sensorSelectionPanel.add(chckbxEoir);
			chckbxEoir.setSelected((CONFIGURATION.headOnSensorSelection&0B00001) == 0B00001);
			chckbxEoir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B00001;
					}
				}
			});
		}
				
		
		{
			JPanel autoPilotAlgorithmSelectionPanel = new JPanel();
			
			autoPilotAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "Auto-Pilot", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			autoPilotAlgorithmSelectionPanel.setBounds(12, 101, 290, 47);
			add(autoPilotAlgorithmSelectionPanel);
			autoPilotAlgorithmSelectionPanel.setLayout(null);
			
			
			JRadioButton rdbtnWhitenoise = new JRadioButton("WhiteNoise");
			rdbtnWhitenoise.setSelected(CONFIGURATION.headOnAutoPilotAlgorithmSelection=="WhiteNoise");
			rdbtnWhitenoise.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnAutoPilotAlgorithmSelection="WhiteNoise";
					}
				}
			});
			rdbtnWhitenoise.setBounds(6, 19, 110, 23);
			headOnAutoPilotAlgorithmGroup.add(rdbtnWhitenoise);
			autoPilotAlgorithmSelectionPanel.add(rdbtnWhitenoise);
			
			
			JRadioButton rdbtnSpecific = new JRadioButton("Specific");
			rdbtnSpecific.setSelected(CONFIGURATION.headOnAutoPilotAlgorithmSelection=="Specific");
			rdbtnSpecific.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnAutoPilotAlgorithmSelection="Specific";
					}
				}
			});			
			rdbtnSpecific.setBounds(203, 19, 77, 23);
			headOnAutoPilotAlgorithmGroup.add(rdbtnSpecific);
			autoPilotAlgorithmSelectionPanel.add(rdbtnSpecific);
			

			
		}		
	
		
		
		{
			JPanel collisionAvoidanceAlgorithmSelectionPanel = new JPanel();
			collisionAvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			collisionAvoidanceAlgorithmSelectionPanel.setBounds(12, 147, 290, 47);
			this.add(collisionAvoidanceAlgorithmSelectionPanel);
			collisionAvoidanceAlgorithmSelectionPanel.setLayout(null);			
			
			JRadioButton rdbtnACASXAvoidanceAlgorithm = new JRadioButton("ACASX");
			rdbtnACASXAvoidanceAlgorithm.setBounds(6, 17, 94, 23);
			rdbtnACASXAvoidanceAlgorithm.setSelected(CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection == "ACASXAvoidanceAlgorithm");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnACASXAvoidanceAlgorithm);
			headOnCollisionAvoidanceAlgorithmGroup.add(rdbtnACASXAvoidanceAlgorithm);
			rdbtnACASXAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection = "ACASXAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnRandomAvoidanceAlgorithm = new JRadioButton("Random");
			rdbtnRandomAvoidanceAlgorithm.setEnabled(false);
			rdbtnRandomAvoidanceAlgorithm.setBounds(119, 17, 74, 23);
			rdbtnRandomAvoidanceAlgorithm.setSelected(CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection == "RandomAvoidanceAlgorithm");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnRandomAvoidanceAlgorithm);
			headOnCollisionAvoidanceAlgorithmGroup.add(rdbtnRandomAvoidanceAlgorithm);
			rdbtnRandomAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection = "RandomAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(206, 17, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection == "None");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			headOnCollisionAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection = "None";
					}
				}
			});
		}
	
		{
			JPanel otherPanel = new JPanel();
			otherPanel.setBackground(Color.LIGHT_GRAY);
			otherPanel.setBounds(12, 253, 290, 176);
			add(otherPanel);
			otherPanel.setLayout(null);
			
			JLabel lblOffsetY = new JLabel("OffsetY");
			lblOffsetY.setBounds(10, 11, 44, 15);
			otherPanel.add(lblOffsetY);
			
			final JLabel offsetYLabel = new JLabel(""+CONFIGURATION.headOnOffsetY);
			offsetYLabel.setBounds(223, 11, 48, 15);
			otherPanel.add(offsetYLabel);
			
			JSlider selfOffsetYSlider = new JSlider();
			selfOffsetYSlider.setBounds(55, 11, 160, 16);
			otherPanel.add(selfOffsetYSlider);
			selfOffsetYSlider.setSnapToTicks(true);
			selfOffsetYSlider.setPaintLabels(true);		
			selfOffsetYSlider.setMaximum(500);
			selfOffsetYSlider.setMinimum(-500);
			selfOffsetYSlider.setValue((int)(CONFIGURATION.headOnOffsetY));
			selfOffsetYSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.headOnOffsetY = source.getValue();
					offsetYLabel.setText(""+CONFIGURATION.headOnOffsetY);
				}
			});
			
			JLabel lblOffsetZ = new JLabel("OffsetZ");
			lblOffsetZ.setBounds(10, 31, 44, 15);
			otherPanel.add(lblOffsetZ);
			
			final JLabel offsetZLabel = new JLabel(""+CONFIGURATION.headOnOffsetZ);
			offsetZLabel.setBounds(223, 31, 48, 15);
			otherPanel.add(offsetZLabel);
			
			JSlider selfOffsetZSlider = new JSlider();
			selfOffsetZSlider.setBounds(55, 31, 160, 16);
			otherPanel.add(selfOffsetZSlider);
			selfOffsetZSlider.setSnapToTicks(true);
			selfOffsetZSlider.setPaintLabels(true);		
			selfOffsetZSlider.setMaximum(500);
			selfOffsetZSlider.setMinimum(-500);
			selfOffsetZSlider.setValue((int)(CONFIGURATION.headOnOffsetZ));
			selfOffsetZSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.headOnOffsetZ = source.getValue();
					offsetZLabel.setText(""+CONFIGURATION.headOnOffsetZ);
				}
			});
			
			JLabel lblVx = new JLabel("VX");
			lblVx.setBounds(10, 51, 37, 15);
			otherPanel.add(lblVx);
			
			final JLabel vxLabel = new JLabel(""+CONFIGURATION.headOnVx);
			vxLabel.setBounds(223, 51, 58, 15);
			otherPanel.add(vxLabel);
			
			JSlider selfVxSlider = new JSlider();
			selfVxSlider.setBounds(55, 51, 161, 16);
			otherPanel.add(selfVxSlider);
			selfVxSlider.setSnapToTicks(true);
			selfVxSlider.setPaintLabels(true);		
			selfVxSlider.setMaximum(304);
			selfVxSlider.setMinimum(169);
			selfVxSlider.setValue((int)(CONFIGURATION.headOnVx));
			selfVxSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.headOnVx = source.getValue();
					vxLabel.setText(""+CONFIGURATION.headOnVx);
				}
			});
			
			JLabel lblVy = new JLabel("VY");
			lblVy.setBounds(10, 71, 37, 15);
			otherPanel.add(lblVy);
			
			final JLabel vyLabel = new JLabel(""+CONFIGURATION.headOnVy);
			vyLabel.setBounds(223, 71, 58, 15);
			otherPanel.add(vyLabel);
		
			JSlider selfVySlider = new JSlider();
			selfVySlider.setBounds(55, 71, 161, 16);
			otherPanel.add(selfVySlider);
			selfVySlider.setSnapToTicks(true);
			selfVySlider.setPaintLabels(true);		
			selfVySlider.setMaximum(58);
			selfVySlider.setMinimum(-67);
			selfVySlider.setValue((int)(CONFIGURATION.headOnVy));
			selfVySlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.headOnVy = source.getValue();
					vyLabel.setText(""+CONFIGURATION.headOnVy);

				}
			});
			
			JLabel lblVz = new JLabel("VZ");
			lblVz.setBounds(10, 91, 37, 15);
			otherPanel.add(lblVz);
			
			final JLabel vzLabel = new JLabel(""+CONFIGURATION.headOnVz);
			vzLabel.setBounds(223, 91, 58, 15);
			otherPanel.add(vzLabel);
		
			JSlider selfVzSlider = new JSlider();
			selfVzSlider.setBounds(55, 91, 161, 16);
			otherPanel.add(selfVzSlider);
			selfVzSlider.setSnapToTicks(true);
			selfVzSlider.setPaintLabels(true);		
			selfVzSlider.setMaximum(58);
			selfVzSlider.setMinimum(-67);
			selfVzSlider.setValue((int)(CONFIGURATION.headOnVz));
			selfVzSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.headOnVz = source.getValue();
					vzLabel.setText(""+CONFIGURATION.headOnVz);

				}
			});
			
			JLabel lblStdDevX = new JLabel("SDX");
			lblStdDevX.setBounds(10, 111, 37, 15);
			otherPanel.add(lblStdDevX);
			
			final JLabel stdDevXLabel = new JLabel(""+CONFIGURATION.headOnStdDevX);
			stdDevXLabel.setBounds(223, 111, 58, 15);
			otherPanel.add(stdDevXLabel);
			
			JSlider headOnStdDevXSlider = new JSlider();
			headOnStdDevXSlider.setBounds(55, 111, 161, 16);
			otherPanel.add(headOnStdDevXSlider);
			headOnStdDevXSlider.setSnapToTicks(true);
			headOnStdDevXSlider.setPaintLabels(true);		
			headOnStdDevXSlider.setMaximum(15);
			headOnStdDevXSlider.setMinimum(0);
			headOnStdDevXSlider.setValue((int)(CONFIGURATION.headOnStdDevX));
			headOnStdDevXSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.headOnStdDevX = source.getValue();
					stdDevXLabel.setText(""+CONFIGURATION.headOnStdDevX);
				}
			});
			
			JLabel lblStdDevY = new JLabel("SDY");
			lblStdDevY.setBounds(10, 131, 37, 15);
			otherPanel.add(lblStdDevY);
			
			final JLabel stdDevYLabel = new JLabel(""+CONFIGURATION.headOnStdDevY);
			stdDevYLabel.setBounds(223, 131, 58, 15);
			otherPanel.add(stdDevYLabel);
			
			JSlider headOnStdDevYSlider = new JSlider();
			headOnStdDevYSlider.setBounds(55, 131, 161, 16);
			otherPanel.add(headOnStdDevYSlider);
			headOnStdDevYSlider.setSnapToTicks(true);
			headOnStdDevYSlider.setPaintLabels(true);		
			headOnStdDevYSlider.setMaximum(15);
			headOnStdDevYSlider.setMinimum(0);
			headOnStdDevYSlider.setValue((int)(CONFIGURATION.headOnStdDevY));
			headOnStdDevYSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.headOnStdDevY = source.getValue();
					stdDevYLabel.setText(""+CONFIGURATION.headOnStdDevY);
				}
			});
		
			
			JLabel lblStdDevZ = new JLabel("SDZ");
			lblStdDevZ.setBounds(10, 151, 37, 15);
			otherPanel.add(lblStdDevZ);
			
			final JLabel stdDevZLabel = new JLabel(""+CONFIGURATION.headOnStdDevZ);
			stdDevZLabel.setBounds(223, 151, 58, 15);
			otherPanel.add(stdDevZLabel);
			
			JSlider headOnStdDevZSlider = new JSlider();
			headOnStdDevZSlider.setBounds(55, 151, 161, 16);
			otherPanel.add(headOnStdDevZSlider);
			headOnStdDevZSlider.setSnapToTicks(true);
			headOnStdDevZSlider.setPaintLabels(true);		
			headOnStdDevZSlider.setMaximum(15);
			headOnStdDevZSlider.setMinimum(0);
			headOnStdDevZSlider.setValue((int)(CONFIGURATION.headOnStdDevZ));
			headOnStdDevZSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.headOnStdDevZ = source.getValue();
					stdDevZLabel.setText(""+CONFIGURATION.headOnStdDevZ);
				}
			});
		}
		
		
		{
			JPanel selfSeparationAlgorithmSelectionPanel = new JPanel();
			selfSeparationAlgorithmSelectionPanel.setBounds(12, 195, 290, 47);
			add(selfSeparationAlgorithmSelectionPanel);
			selfSeparationAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "SSA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			selfSeparationAlgorithmSelectionPanel.setLayout(null);			
			
			JRadioButton rdbtnNASAChorusAlgorithm = new JRadioButton("Chorus");
			rdbtnNASAChorusAlgorithm.setBounds(6, 17, 94, 23);
			rdbtnNASAChorusAlgorithm.setSelected(CONFIGURATION.headOnSelfSeparationAlgorithmSelection == "NASAChorusAlgorithm");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNASAChorusAlgorithm);
			headOnSelfSeparationAlgorithmGroup.add(rdbtnNASAChorusAlgorithm);
			rdbtnNASAChorusAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm";
					}
				}
			});
			
				
			JRadioButton rdbtnNone_1 = new JRadioButton("None");
			rdbtnNone_1.setBounds(206, 17, 62, 23);
			rdbtnNone_1.setSelected(CONFIGURATION.headOnSelfSeparationAlgorithmSelection == "None");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNone_1);
			headOnSelfSeparationAlgorithmGroup.add(rdbtnNone_1);
			rdbtnNone_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSelfSeparationAlgorithmSelection = "None";
					}
				}
			});
		}
		
		
		{			
			JPanel performancePanel = new JPanel();
			performancePanel.setBackground(Color.LIGHT_GRAY);
			performancePanel.setBounds(12, 440, 290, 188);
			add(performancePanel);
			performancePanel.setLayout(null);
			JLabel lblMaxspeed = new JLabel("MaxSpeed");
			lblMaxspeed.setBounds(12, 14, 82, 15);
			performancePanel.add(lblMaxspeed);
			
			
			JTextField maxSpeedTextField_1 = new JTextField();
			maxSpeedTextField_1.setBounds(170, 14, 114, 19);
			performancePanel.add(maxSpeedTextField_1);
			maxSpeedTextField_1.setText(String.valueOf(CONFIGURATION.headOnMaxSpeed));
			maxSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.headOnMaxSpeed = new Double(maxSpeedTextField.getText());
				}
			});
			maxSpeedTextField_1.setColumns(10);
			
			
			JLabel lblMinspeed = new JLabel("MinSpeed");
			lblMinspeed.setBounds(12, 43, 70, 19);
			performancePanel.add(lblMinspeed);
			
			
			JTextField minSpeedTextField_1 = new JTextField();
			minSpeedTextField_1.setBounds(170, 45, 114, 19);
			performancePanel.add(minSpeedTextField_1);
			minSpeedTextField_1.setText(String.valueOf(CONFIGURATION.headOnMinSpeed));
			minSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField minSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.headOnMinSpeed = new Double(minSpeedTextField.getText());
				}
			});
			minSpeedTextField_1.setColumns(10);
			
			JLabel lblPrefSpeed = new JLabel("PrefSpeed");
			lblPrefSpeed.setBounds(12, 74, 105, 15);
			performancePanel.add(lblPrefSpeed);
			
			JTextField prefSpeedTextField = new JTextField();
			prefSpeedTextField.setBounds(171, 72, 114, 19);
			performancePanel.add(prefSpeedTextField);
			prefSpeedTextField.setText(String.valueOf(CONFIGURATION.headOnPrefSpeed));
			prefSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField speedTextField = (JTextField) e.getSource();
					CONFIGURATION.headOnPrefSpeed = new Double(speedTextField.getText());
				}
			});
			prefSpeedTextField.setColumns(10);
			
			
			
			
			JLabel lblMaxClimb = new JLabel("MaxClimb");
			lblMaxClimb.setBounds(12, 101, 70, 19);
			performancePanel.add(lblMaxClimb);
			
			
			JTextField maxClimbTextField_1 = new JTextField();
			maxClimbTextField_1.setBounds(170, 99, 114, 19);
			performancePanel.add(maxClimbTextField_1);
			maxClimbTextField_1.setText(String.valueOf(CONFIGURATION.headOnMaxClimb));
			maxClimbTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxClimbTextField = (JTextField) e.getSource();
					CONFIGURATION.headOnMaxClimb = new Double(maxClimbTextField.getText());
				}
			});
			maxClimbTextField_1.setColumns(10);
			
			JLabel lblMaxDescent = new JLabel("MaxDescent");
			lblMaxDescent.setBounds(12, 131, 101, 19);
			performancePanel.add(lblMaxDescent);
			
			
			JTextField maxDescentTextField_1 = new JTextField();
			maxDescentTextField_1.setBounds(170, 133, 114, 19);
			performancePanel.add(maxDescentTextField_1);
			maxDescentTextField_1.setText(String.valueOf(CONFIGURATION.headOnMaxDescent));
			maxDescentTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxDescentTextField = (JTextField) e.getSource();
					CONFIGURATION.headOnMaxDescent = new Double(maxDescentTextField.getText());
				}
			});
			maxDescentTextField_1.setColumns(10);
			
			JLabel lblMaxturning = new JLabel("MaxTurning");
			lblMaxturning.setBounds(12, 162, 82, 15);
			performancePanel.add(lblMaxturning);
			
			{
				
				JTextField maxTurningTextField_1 = new JTextField();
				maxTurningTextField_1.setBounds(171, 164, 114, 19);
				performancePanel.add(maxTurningTextField_1);
				maxTurningTextField_1.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.headOnMaxTurning)*100)/100.0));
				maxTurningTextField_1.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField maxTurningTextField = (JTextField) e.getSource();
						CONFIGURATION.headOnMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
					}
				});
				maxTurningTextField_1.setColumns(10);
			}
		}
		
						
	}

	public HeadOn(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public HeadOn(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public HeadOn(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
}
