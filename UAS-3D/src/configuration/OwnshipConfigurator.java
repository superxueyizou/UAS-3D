package configuration;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OwnshipConfigurator extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static Configuration config= Configuration.getInstance();
	private final ButtonGroup ownshipAutoPilotAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup ownshipCollisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup ownshipSelfSeparationAlgorithmGroup = new ButtonGroup();

	public OwnshipConfigurator() 
	{	
		setLayout(null);		
		
		{
			JPanel sensorSelectionPanel = new JPanel();
			sensorSelectionPanel.setBorder(new TitledBorder(null, "Sensor Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			sensorSelectionPanel.setBounds(10, 85, 290, 47);
			add(sensorSelectionPanel);
			sensorSelectionPanel.setLayout(null);
			
			JCheckBox chckbxPerfectSensor = new JCheckBox("Perfect");
			chckbxPerfectSensor.setBounds(8, 20, 61, 23);
			sensorSelectionPanel.add(chckbxPerfectSensor);
			chckbxPerfectSensor.setSelected((config.ownshipConfig.ownshipSensorSelection&0B10000) == 0B10000);
			chckbxPerfectSensor.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipSensorSelection |= 0B10000;
					}
				}
			});
			
			
			JCheckBox chckbxAdsb = new JCheckBox("ADS-B");
			chckbxAdsb.setBounds(71, 20, 55, 23);
			sensorSelectionPanel.add(chckbxAdsb);
			chckbxAdsb.setSelected((config.ownshipConfig.ownshipSensorSelection&0B01000) == 0B01000);
			chckbxAdsb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipSensorSelection |= 0B01000;
					}
				}
			});
			
			JCheckBox chckbxTcas = new JCheckBox("TCAS");
			chckbxTcas.setBounds(127, 20, 55, 23);
			sensorSelectionPanel.add(chckbxTcas);
			chckbxTcas.setSelected((config.ownshipConfig.ownshipSensorSelection&0B00100) == 0B00100);
			chckbxTcas.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipSensorSelection |= 0B00100;
					}
				}
			});
			
			JCheckBox chckbxRadar = new JCheckBox("Radar");
			chckbxRadar.setBounds(178, 20, 55, 23);
			sensorSelectionPanel.add(chckbxRadar);
			chckbxRadar.setSelected((config.ownshipConfig.ownshipSensorSelection&0B00010) == 0B00010);
			chckbxRadar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipSensorSelection |= 0B00010;
					}
				}
			});
			
			JCheckBox chckbxEoir = new JCheckBox("EO/IR");
			chckbxEoir.setBounds(229, 20, 55, 23);
			sensorSelectionPanel.add(chckbxEoir);
			chckbxEoir.setSelected((config.ownshipConfig.ownshipSensorSelection&0B00001) == 0B00001);
			chckbxEoir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipSensorSelection |= 0B00001;
					}
				}
			});
		}
				
		
		{
			JPanel autoPilotAlgorithmSelectionPanel = new JPanel();
			
			autoPilotAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "Auto-Pilot", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			autoPilotAlgorithmSelectionPanel.setBounds(10, 131, 290, 47);
			add(autoPilotAlgorithmSelectionPanel);
			autoPilotAlgorithmSelectionPanel.setLayout(null);
			
			
			JRadioButton rdbtnWhitenoise = new JRadioButton("WhiteNoise");
			rdbtnWhitenoise.setSelected(config.ownshipConfig.ownshipAutoPilotAlgorithmSelection=="WhiteNoise");
			rdbtnWhitenoise.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipAutoPilotAlgorithmSelection="WhiteNoise";
					}
				}
			});
			rdbtnWhitenoise.setBounds(6, 19, 110, 23);
			ownshipAutoPilotAlgorithmGroup.add(rdbtnWhitenoise);
			autoPilotAlgorithmSelectionPanel.add(rdbtnWhitenoise);
			
			
			JRadioButton rdbtnSpecific = new JRadioButton("Specific");
			rdbtnSpecific.setSelected(config.ownshipConfig.ownshipAutoPilotAlgorithmSelection=="Specific");
			rdbtnSpecific.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipAutoPilotAlgorithmSelection="Specific";
					}
				}
			});			
			rdbtnSpecific.setBounds(203, 19, 77, 23);
			ownshipAutoPilotAlgorithmGroup.add(rdbtnSpecific);
			autoPilotAlgorithmSelectionPanel.add(rdbtnSpecific);
			

			
		}		
	
		
		
		{
			JPanel collisionAvoidanceAlgorithmSelectionPanel = new JPanel();
			collisionAvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			collisionAvoidanceAlgorithmSelectionPanel.setBounds(10, 177, 290, 47);
			this.add(collisionAvoidanceAlgorithmSelectionPanel);
			collisionAvoidanceAlgorithmSelectionPanel.setLayout(null);			
			
			JRadioButton rdbtnACASX2DAvoidanceAlgorithm = new JRadioButton("ACASX2D");
			rdbtnACASX2DAvoidanceAlgorithm.setBounds(6, 17, 94, 23);
			rdbtnACASX2DAvoidanceAlgorithm.setSelected(config.ownshipConfig.ownshipCollisionAvoidanceAlgorithmSelection == "ACASX2DAvoidanceAlgorithm");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnACASX2DAvoidanceAlgorithm);
			ownshipCollisionAvoidanceAlgorithmGroup.add(rdbtnACASX2DAvoidanceAlgorithm);
			rdbtnACASX2DAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipCollisionAvoidanceAlgorithmSelection = "ACASX2DAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnACASX3DAvoidanceAlgorithm = new JRadioButton("ACASX3D");
			rdbtnACASX3DAvoidanceAlgorithm.setBounds(102, 17, 87, 23);
			rdbtnACASX3DAvoidanceAlgorithm.setSelected(config.ownshipConfig.ownshipCollisionAvoidanceAlgorithmSelection == "ACASX3DAvoidanceAlgorithm");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnACASX3DAvoidanceAlgorithm);
			ownshipCollisionAvoidanceAlgorithmGroup.add(rdbtnACASX3DAvoidanceAlgorithm);
			rdbtnACASX3DAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipCollisionAvoidanceAlgorithmSelection = "ACASX3DAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(206, 17, 62, 23);
			rdbtnNone.setSelected(config.ownshipConfig.ownshipCollisionAvoidanceAlgorithmSelection == "None");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			ownshipCollisionAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipCollisionAvoidanceAlgorithmSelection = "None";
					}
				}
			});
		}
	
		{
			JPanel otherPanel = new JPanel();
			otherPanel.setBackground(Color.LIGHT_GRAY);
			otherPanel.setBounds(10, 283, 290, 139);
			add(otherPanel);
			otherPanel.setLayout(null);
			
			JLabel lblVy = new JLabel("VY");
			lblVy.setBounds(10, 11, 37, 15);
			otherPanel.add(lblVy);
				
			final JLabel vyLabel = new JLabel(""+config.ownshipConfig.ownshipVy);
			vyLabel.setBounds(226, 11, 58, 15);
			otherPanel.add(vyLabel);
		
			JSlider ownshipVySlider = new JSlider();
			ownshipVySlider.setBounds(57, 11, 161, 16);
			otherPanel.add(ownshipVySlider);
			ownshipVySlider.setSnapToTicks(true);
			ownshipVySlider.setPaintLabels(true);		
			ownshipVySlider.setMaximum(58);
			ownshipVySlider.setMinimum(-67);
			ownshipVySlider.setValue((int)(config.ownshipConfig.ownshipVy));
			ownshipVySlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.ownshipConfig.ownshipVy = source.getValue();
					vyLabel.setText(""+config.ownshipConfig.ownshipVy);

				}
			});
			
			JLabel lblGs = new JLabel("GS");
			lblGs.setBounds(10, 32, 37, 15);
			otherPanel.add(lblGs);
			
			final JLabel gsLabel = new JLabel(""+config.ownshipConfig.ownshipGs);
			gsLabel.setBounds(226, 32, 58, 15);
			otherPanel.add(gsLabel);
			
			JSlider ownshipGsSlider = new JSlider();
			ownshipGsSlider.setBounds(57, 32, 161, 16);
			otherPanel.add(ownshipGsSlider);
			ownshipGsSlider.setSnapToTicks(true);
			ownshipGsSlider.setPaintLabels(true);		
			ownshipGsSlider.setMaximum(304);
			ownshipGsSlider.setMinimum(169);
			ownshipGsSlider.setValue((int)(config.ownshipConfig.ownshipGs));
			ownshipGsSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.ownshipConfig.ownshipGs = source.getValue();
					gsLabel.setText(""+config.ownshipConfig.ownshipGs);
				}
			});
			
			JLabel lblBearing = new JLabel("Bearing");
			lblBearing.setBounds(10, 51, 45, 15);
			otherPanel.add(lblBearing);
			
			final JLabel bearingLabel = new JLabel(""+config.ownshipConfig.ownshipBearing);
			bearingLabel.setBounds(226, 51, 58, 15);
			otherPanel.add(bearingLabel);
		
			JSlider ownshipBearingSlider = new JSlider();
			ownshipBearingSlider.setBounds(57, 51, 161, 16);
			otherPanel.add(ownshipBearingSlider);
			ownshipBearingSlider.setSnapToTicks(true);
			ownshipBearingSlider.setPaintLabels(true);		
			ownshipBearingSlider.setMaximum(180);
			ownshipBearingSlider.setMinimum(-180);
			ownshipBearingSlider.setValue((int)(config.ownshipConfig.ownshipBearing));
			ownshipBearingSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.ownshipConfig.ownshipBearing = source.getValue();
					bearingLabel.setText(""+config.ownshipConfig.ownshipBearing);

				}
			});
			
			JLabel lblStdDevX = new JLabel("SDX");
			lblStdDevX.setBounds(10, 71, 37, 15);
			otherPanel.add(lblStdDevX);
			
			final JLabel stdDevXLabel = new JLabel(""+config.ownshipConfig.ownshipStdDevX);
			stdDevXLabel.setBounds(226, 71, 58, 15);
			otherPanel.add(stdDevXLabel);
			
			JSlider ownshipStdDevXSlider = new JSlider();
			ownshipStdDevXSlider.setBounds(57, 71, 161, 16);
			otherPanel.add(ownshipStdDevXSlider);
			ownshipStdDevXSlider.setSnapToTicks(true);
			ownshipStdDevXSlider.setPaintLabels(true);		
			ownshipStdDevXSlider.setMaximum(15);
			ownshipStdDevXSlider.setMinimum(0);
			ownshipStdDevXSlider.setValue((int)(config.ownshipConfig.ownshipStdDevX));
			ownshipStdDevXSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.ownshipConfig.ownshipStdDevX = source.getValue();
					stdDevXLabel.setText(""+config.ownshipConfig.ownshipStdDevX);
				}
			});
			
			JLabel lblStdDevY = new JLabel("SDY");
			lblStdDevY.setBounds(10, 91, 37, 15);
			otherPanel.add(lblStdDevY);
			
			final JLabel stdDevYLabel = new JLabel(""+config.ownshipConfig.ownshipStdDevY);
			stdDevYLabel.setBounds(226, 91, 58, 15);
			otherPanel.add(stdDevYLabel);
			
			JSlider ownshipStdDevYSlider = new JSlider();
			ownshipStdDevYSlider.setBounds(57, 91, 161, 16);
			otherPanel.add(ownshipStdDevYSlider);
			ownshipStdDevYSlider.setSnapToTicks(true);
			ownshipStdDevYSlider.setPaintLabels(true);		
			ownshipStdDevYSlider.setMaximum(15);
			ownshipStdDevYSlider.setMinimum(0);
			ownshipStdDevYSlider.setValue((int)(config.ownshipConfig.ownshipStdDevY));
			ownshipStdDevYSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.ownshipConfig.ownshipStdDevY = source.getValue();
					stdDevYLabel.setText(""+config.ownshipConfig.ownshipStdDevY);
				}
			});
		
			
			JLabel lblStdDevZ = new JLabel("SDZ");
			lblStdDevZ.setBounds(10, 111, 37, 15);
			otherPanel.add(lblStdDevZ);
			
			final JLabel stdDevZLabel = new JLabel(""+config.ownshipConfig.ownshipStdDevZ);
			stdDevZLabel.setBounds(226, 111, 58, 15);
			otherPanel.add(stdDevZLabel);
			
			JSlider ownshipStdDevZSlider = new JSlider();
			ownshipStdDevZSlider.setBounds(57, 111, 161, 16);
			otherPanel.add(ownshipStdDevZSlider);
			ownshipStdDevZSlider.setSnapToTicks(true);
			ownshipStdDevZSlider.setPaintLabels(true);		
			ownshipStdDevZSlider.setMaximum(15);
			ownshipStdDevZSlider.setMinimum(0);
			ownshipStdDevZSlider.setValue((int)(config.ownshipConfig.ownshipStdDevZ));
			ownshipStdDevZSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.ownshipConfig.ownshipStdDevZ = source.getValue();
					stdDevZLabel.setText(""+config.ownshipConfig.ownshipStdDevZ);
				}
			});
		}
		
		
		{
			JPanel selfSeparationAlgorithmSelectionPanel = new JPanel();
			selfSeparationAlgorithmSelectionPanel.setBounds(10, 225, 290, 47);
			add(selfSeparationAlgorithmSelectionPanel);
			selfSeparationAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "SSA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			selfSeparationAlgorithmSelectionPanel.setLayout(null);			
			
			JRadioButton rdbtnNASAChorusAlgorithm = new JRadioButton("Chorus");
			rdbtnNASAChorusAlgorithm.setBounds(6, 17, 94, 23);
			rdbtnNASAChorusAlgorithm.setSelected(config.ownshipConfig.ownshipSelfSeparationAlgorithmSelection == "NASAChorusAlgorithm");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNASAChorusAlgorithm);
			ownshipSelfSeparationAlgorithmGroup.add(rdbtnNASAChorusAlgorithm);
			rdbtnNASAChorusAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm";
					}
				}
			});
			
				
			JRadioButton rdbtnNone_1 = new JRadioButton("None");
			rdbtnNone_1.setBounds(206, 17, 62, 23);
			rdbtnNone_1.setSelected(config.ownshipConfig.ownshipSelfSeparationAlgorithmSelection == "None");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNone_1);
			ownshipSelfSeparationAlgorithmGroup.add(rdbtnNone_1);
			rdbtnNone_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						config.ownshipConfig.ownshipSelfSeparationAlgorithmSelection = "None";
					}
				}
			});
		}
		
		
		{			
			JPanel performancePanel = new JPanel();
			performancePanel.setBackground(Color.LIGHT_GRAY);
			performancePanel.setBounds(10, 433, 290, 170);
			add(performancePanel);
			performancePanel.setLayout(null);
			JLabel lblMaxspeed = new JLabel("MaxSpeed");
			lblMaxspeed.setBounds(12, 14, 82, 15);
			performancePanel.add(lblMaxspeed);
			
			
			JTextField maxSpeedTextField_1 = new JTextField();
			maxSpeedTextField_1.setBounds(170, 14, 114, 19);
			performancePanel.add(maxSpeedTextField_1);
			maxSpeedTextField_1.setText(String.valueOf(config.ownshipConfig.ownshipMaxSpeed));
			maxSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					config.ownshipConfig.ownshipMaxSpeed = new Double(maxSpeedTextField.getText());
				}
			});
			maxSpeedTextField_1.setColumns(10);
			
			
			JLabel lblMinspeed = new JLabel("MinSpeed");
			lblMinspeed.setBounds(12, 43, 70, 19);
			performancePanel.add(lblMinspeed);
			
			
			JTextField minSpeedTextField_1 = new JTextField();
			minSpeedTextField_1.setBounds(170, 45, 114, 19);
			performancePanel.add(minSpeedTextField_1);
			minSpeedTextField_1.setText(String.valueOf(config.ownshipConfig.ownshipMinSpeed));
			minSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField minSpeedTextField = (JTextField) e.getSource();
					config.ownshipConfig.ownshipMinSpeed = new Double(minSpeedTextField.getText());
				}
			});
			minSpeedTextField_1.setColumns(10);
					
			JLabel lblMaxClimb = new JLabel("MaxClimb");
			lblMaxClimb.setBounds(11, 75, 70, 19);
			performancePanel.add(lblMaxClimb);
			
			
			JTextField maxClimbTextField_1 = new JTextField();
			maxClimbTextField_1.setBounds(169, 73, 114, 19);
			performancePanel.add(maxClimbTextField_1);
			maxClimbTextField_1.setText(String.valueOf(config.ownshipConfig.ownshipMaxClimb));
			maxClimbTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxClimbTextField = (JTextField) e.getSource();
					config.ownshipConfig.ownshipMaxClimb = new Double(maxClimbTextField.getText());
				}
			});
			maxClimbTextField_1.setColumns(10);
			
			JLabel lblMaxDescent = new JLabel("MaxDescent");
			lblMaxDescent.setBounds(11, 105, 101, 19);
			performancePanel.add(lblMaxDescent);
			
			
			JTextField maxDescentTextField_1 = new JTextField();
			maxDescentTextField_1.setBounds(169, 107, 114, 19);
			performancePanel.add(maxDescentTextField_1);
			maxDescentTextField_1.setText(String.valueOf(config.ownshipConfig.ownshipMaxDescent));
			maxDescentTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxDescentTextField = (JTextField) e.getSource();
					config.ownshipConfig.ownshipMaxDescent = new Double(maxDescentTextField.getText());
				}
			});
			maxDescentTextField_1.setColumns(10);
			
			JLabel lblMaxturning = new JLabel("MaxTurning");
			lblMaxturning.setBounds(11, 136, 82, 15);
			performancePanel.add(lblMaxturning);
			
			{
				
				JTextField maxTurningTextField_1 = new JTextField();
				maxTurningTextField_1.setBounds(170, 138, 114, 19);
				performancePanel.add(maxTurningTextField_1);
				maxTurningTextField_1.setText(String.valueOf(Math.round(Math.toDegrees(config.ownshipConfig.ownshipMaxTurning)*100)/100.0));
				maxTurningTextField_1.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField maxTurningTextField = (JTextField) e.getSource();
						config.ownshipConfig.ownshipMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
					}
				});
				maxTurningTextField_1.setColumns(10);
			}
		}
					
	}

}
