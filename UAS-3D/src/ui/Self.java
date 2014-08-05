package ui;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tools.CONFIGURATION;
import javax.swing.JCheckBox;
import java.awt.Color;

public class Self extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup selfAutoPilotAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup selfCollisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup selfSelfSeparationAlgorithmGroup = new ButtonGroup();

	public Self() 
	{
		setLayout(null);
		
		{
			JPanel sensorSelectionPanel = new JPanel();
			sensorSelectionPanel.setBorder(new TitledBorder(null, "Sensor Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			sensorSelectionPanel.setBounds(10, 11, 290, 47);
			add(sensorSelectionPanel);
			sensorSelectionPanel.setLayout(null);
			
			JCheckBox chckbxPerfectSensor = new JCheckBox("Perfect");
			chckbxPerfectSensor.setBounds(8, 20, 61, 23);
			sensorSelectionPanel.add(chckbxPerfectSensor);
			chckbxPerfectSensor.setSelected((CONFIGURATION.selfSensorSelection&0B10000) == 0B10000);
			chckbxPerfectSensor.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B10000;
					}
				}
			});
			
			
			JCheckBox chckbxAdsb = new JCheckBox("ADS-B");
			chckbxAdsb.setBounds(71, 20, 55, 23);
			sensorSelectionPanel.add(chckbxAdsb);
			chckbxAdsb.setSelected((CONFIGURATION.selfSensorSelection&0B01000) == 0B01000);
			chckbxAdsb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B01000;
					}
				}
			});
			
			JCheckBox chckbxTcas = new JCheckBox("TCAS");
			chckbxTcas.setBounds(127, 20, 55, 23);
			sensorSelectionPanel.add(chckbxTcas);
			chckbxTcas.setSelected((CONFIGURATION.selfSensorSelection&0B00100) == 0B00100);
			chckbxTcas.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B00100;
					}
				}
			});
			
			JCheckBox chckbxRadar = new JCheckBox("Radar");
			chckbxRadar.setBounds(178, 20, 55, 23);
			sensorSelectionPanel.add(chckbxRadar);
			chckbxRadar.setSelected((CONFIGURATION.selfSensorSelection&0B00010) == 0B00010);
			chckbxRadar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B00010;
					}
				}
			});
			
			JCheckBox chckbxEoir = new JCheckBox("EO/IR");
			chckbxEoir.setBounds(229, 20, 55, 23);
			sensorSelectionPanel.add(chckbxEoir);
			chckbxEoir.setSelected((CONFIGURATION.selfSensorSelection&0B00001) == 0B00001);
			chckbxEoir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B00001;
					}
				}
			});
		}
				
		
		{
			JPanel autoPilotAlgorithmSelectionPanel = new JPanel();
			
			autoPilotAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "Auto-Pilot", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			autoPilotAlgorithmSelectionPanel.setBounds(10, 57, 290, 47);
			add(autoPilotAlgorithmSelectionPanel);
			autoPilotAlgorithmSelectionPanel.setLayout(null);
			
			
			JRadioButton rdbtnWhitenoise = new JRadioButton("WhiteNoise");
			rdbtnWhitenoise.setSelected(CONFIGURATION.selfAutoPilotAlgorithmSelection=="WhiteNoise");
			rdbtnWhitenoise.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfAutoPilotAlgorithmSelection="WhiteNoise";
					}
				}
			});
			rdbtnWhitenoise.setBounds(6, 19, 110, 23);
			selfAutoPilotAlgorithmGroup.add(rdbtnWhitenoise);
			autoPilotAlgorithmSelectionPanel.add(rdbtnWhitenoise);
			
			
			JRadioButton rdbtnSpecific = new JRadioButton("Specific");
			rdbtnSpecific.setSelected(CONFIGURATION.selfAutoPilotAlgorithmSelection=="Specific");
			rdbtnSpecific.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfAutoPilotAlgorithmSelection="Specific";
					}
				}
			});			
			rdbtnSpecific.setBounds(203, 19, 77, 23);
			selfAutoPilotAlgorithmGroup.add(rdbtnSpecific);
			autoPilotAlgorithmSelectionPanel.add(rdbtnSpecific);
			

			
		}		
	
		
		
		{
			JPanel collisionAvoidanceAlgorithmSelectionPanel = new JPanel();
			collisionAvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			collisionAvoidanceAlgorithmSelectionPanel.setBounds(10, 103, 290, 47);
			this.add(collisionAvoidanceAlgorithmSelectionPanel);
			collisionAvoidanceAlgorithmSelectionPanel.setLayout(null);			
			
			JRadioButton rdbtnACASXAvoidanceAlgorithm = new JRadioButton("ACASX");
			rdbtnACASXAvoidanceAlgorithm.setBounds(6, 17, 94, 23);
			rdbtnACASXAvoidanceAlgorithm.setSelected(CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection == "ACASXAvoidanceAlgorithm");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnACASXAvoidanceAlgorithm);
			selfCollisionAvoidanceAlgorithmGroup.add(rdbtnACASXAvoidanceAlgorithm);
			rdbtnACASXAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection = "ACASXAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnRandomAvoidanceAlgorithm = new JRadioButton("Random");
			rdbtnRandomAvoidanceAlgorithm.setEnabled(false);
			rdbtnRandomAvoidanceAlgorithm.setBounds(119, 17, 74, 23);
			rdbtnRandomAvoidanceAlgorithm.setSelected(CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection == "RandomAvoidanceAlgorithm");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnRandomAvoidanceAlgorithm);
			selfCollisionAvoidanceAlgorithmGroup.add(rdbtnRandomAvoidanceAlgorithm);
			rdbtnRandomAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection = "RandomAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(206, 17, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection == "None");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			selfCollisionAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection = "None";
					}
				}
			});
		}
	
		{
			JPanel otherPanel = new JPanel();
			otherPanel.setBackground(Color.LIGHT_GRAY);
			otherPanel.setBounds(10, 209, 290, 139);
			add(otherPanel);
			otherPanel.setLayout(null);
			
			JLabel lblVx = new JLabel("VX");
			lblVx.setBounds(10, 11, 37, 15);
			otherPanel.add(lblVx);
			
			final JLabel vxLabel = new JLabel(""+CONFIGURATION.selfVx);
			vxLabel.setBounds(223, 11, 58, 15);
			otherPanel.add(vxLabel);
			
			JSlider selfVxSlider = new JSlider();
			selfVxSlider.setBounds(55, 11, 161, 16);
			otherPanel.add(selfVxSlider);
			selfVxSlider.setSnapToTicks(true);
			selfVxSlider.setPaintLabels(true);		
			selfVxSlider.setMaximum(304);
			selfVxSlider.setMinimum(169);
			selfVxSlider.setValue((int)(CONFIGURATION.selfVx));
			selfVxSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.selfVx = source.getValue();
					vxLabel.setText(""+CONFIGURATION.selfVx);
				}
			});
			
			JLabel lblVy = new JLabel("VY");
			lblVy.setBounds(10, 31, 37, 15);
			otherPanel.add(lblVy);
			
			final JLabel vyLabel = new JLabel(""+CONFIGURATION.selfVy);
			vyLabel.setBounds(223, 31, 58, 15);
			otherPanel.add(vyLabel);
		
			JSlider selfVySlider = new JSlider();
			selfVySlider.setBounds(55, 31, 161, 16);
			otherPanel.add(selfVySlider);
			selfVySlider.setSnapToTicks(true);
			selfVySlider.setPaintLabels(true);		
			selfVySlider.setMaximum(58);
			selfVySlider.setMinimum(-67);
			selfVySlider.setValue((int)(CONFIGURATION.selfVy));
			selfVySlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.selfVy = source.getValue();
					vyLabel.setText(""+CONFIGURATION.selfVy);

				}
			});
			
			JLabel lblVz = new JLabel("VZ");
			lblVz.setBounds(10, 51, 37, 15);
			otherPanel.add(lblVz);
			
			final JLabel vzLabel = new JLabel(""+CONFIGURATION.selfVz);
			vzLabel.setBounds(223, 51, 58, 15);
			otherPanel.add(vzLabel);
		
			JSlider selfVzSlider = new JSlider();
			selfVzSlider.setBounds(55, 51, 161, 16);
			otherPanel.add(selfVzSlider);
			selfVzSlider.setSnapToTicks(true);
			selfVzSlider.setPaintLabels(true);		
			selfVzSlider.setMaximum(58);
			selfVzSlider.setMinimum(-67);
			selfVzSlider.setValue((int)(CONFIGURATION.selfVz));
			selfVzSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.selfVz = source.getValue();
					vzLabel.setText(""+CONFIGURATION.selfVz);

				}
			});
			
			JLabel lblStdDevX = new JLabel("SDX");
			lblStdDevX.setBounds(10, 71, 37, 15);
			otherPanel.add(lblStdDevX);
			
			final JLabel stdDevXLabel = new JLabel(""+CONFIGURATION.selfStdDevX);
			stdDevXLabel.setBounds(223, 71, 58, 15);
			otherPanel.add(stdDevXLabel);
			
			JSlider selfStdDevXSlider = new JSlider();
			selfStdDevXSlider.setBounds(55, 71, 161, 16);
			otherPanel.add(selfStdDevXSlider);
			selfStdDevXSlider.setSnapToTicks(true);
			selfStdDevXSlider.setPaintLabels(true);		
			selfStdDevXSlider.setMaximum(15);
			selfStdDevXSlider.setMinimum(0);
			selfStdDevXSlider.setValue((int)(CONFIGURATION.selfStdDevX));
			selfStdDevXSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.selfStdDevX = source.getValue();
					stdDevXLabel.setText(""+CONFIGURATION.selfStdDevX);
				}
			});
			
			JLabel lblStdDevY = new JLabel("SDY");
			lblStdDevY.setBounds(10, 91, 37, 15);
			otherPanel.add(lblStdDevY);
			
			final JLabel stdDevYLabel = new JLabel(""+CONFIGURATION.selfStdDevY);
			stdDevYLabel.setBounds(223, 91, 58, 15);
			otherPanel.add(stdDevYLabel);
			
			JSlider selfStdDevYSlider = new JSlider();
			selfStdDevYSlider.setBounds(55, 91, 161, 16);
			otherPanel.add(selfStdDevYSlider);
			selfStdDevYSlider.setSnapToTicks(true);
			selfStdDevYSlider.setPaintLabels(true);		
			selfStdDevYSlider.setMaximum(15);
			selfStdDevYSlider.setMinimum(0);
			selfStdDevYSlider.setValue((int)(CONFIGURATION.selfStdDevY));
			selfStdDevYSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.selfStdDevY = source.getValue();
					stdDevYLabel.setText(""+CONFIGURATION.selfStdDevY);
				}
			});
		
			
			JLabel lblStdDevZ = new JLabel("SDZ");
			lblStdDevZ.setBounds(10, 111, 37, 15);
			otherPanel.add(lblStdDevZ);
			
			final JLabel stdDevZLabel = new JLabel(""+CONFIGURATION.selfStdDevZ);
			stdDevZLabel.setBounds(223, 111, 58, 15);
			otherPanel.add(stdDevZLabel);
			
			JSlider selfStdDevZSlider = new JSlider();
			selfStdDevZSlider.setBounds(55, 111, 161, 16);
			otherPanel.add(selfStdDevZSlider);
			selfStdDevZSlider.setSnapToTicks(true);
			selfStdDevZSlider.setPaintLabels(true);		
			selfStdDevZSlider.setMaximum(15);
			selfStdDevZSlider.setMinimum(0);
			selfStdDevZSlider.setValue((int)(CONFIGURATION.selfStdDevZ));
			selfStdDevZSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.selfStdDevZ = source.getValue();
					stdDevZLabel.setText(""+CONFIGURATION.selfStdDevZ);
				}
			});
		}
		
		
		{
			JPanel selfSeparationAlgorithmSelectionPanel = new JPanel();
			selfSeparationAlgorithmSelectionPanel.setBounds(10, 151, 290, 47);
			add(selfSeparationAlgorithmSelectionPanel);
			selfSeparationAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "SSA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			selfSeparationAlgorithmSelectionPanel.setLayout(null);			
			
			JRadioButton rdbtnNASAChorusAlgorithm = new JRadioButton("Chorus");
			rdbtnNASAChorusAlgorithm.setBounds(6, 17, 94, 23);
			rdbtnNASAChorusAlgorithm.setSelected(CONFIGURATION.selfSelfSeparationAlgorithmSelection == "NASAChorusAlgorithm");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNASAChorusAlgorithm);
			selfSelfSeparationAlgorithmGroup.add(rdbtnNASAChorusAlgorithm);
			rdbtnNASAChorusAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm";
					}
				}
			});
			
				
			JRadioButton rdbtnNone_1 = new JRadioButton("None");
			rdbtnNone_1.setBounds(206, 17, 62, 23);
			rdbtnNone_1.setSelected(CONFIGURATION.selfSelfSeparationAlgorithmSelection == "None");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNone_1);
			selfSelfSeparationAlgorithmGroup.add(rdbtnNone_1);
			rdbtnNone_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSelfSeparationAlgorithmSelection = "None";
					}
				}
			});
		}
		
		
		{			
			JPanel performancePanel = new JPanel();
			performancePanel.setBackground(Color.LIGHT_GRAY);
			performancePanel.setBounds(10, 359, 290, 188);
			add(performancePanel);
			performancePanel.setLayout(null);
			JLabel lblMaxspeed = new JLabel("MaxSpeed");
			lblMaxspeed.setBounds(12, 14, 82, 15);
			performancePanel.add(lblMaxspeed);
			
			
			JTextField maxSpeedTextField_1 = new JTextField();
			maxSpeedTextField_1.setBounds(170, 14, 114, 19);
			performancePanel.add(maxSpeedTextField_1);
			maxSpeedTextField_1.setText(String.valueOf(CONFIGURATION.selfMaxSpeed));
			maxSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.selfMaxSpeed = new Double(maxSpeedTextField.getText());
				}
			});
			maxSpeedTextField_1.setColumns(10);
			
			
			JLabel lblMinspeed = new JLabel("MinSpeed");
			lblMinspeed.setBounds(12, 43, 70, 19);
			performancePanel.add(lblMinspeed);
			
			
			JTextField minSpeedTextField_1 = new JTextField();
			minSpeedTextField_1.setBounds(170, 45, 114, 19);
			performancePanel.add(minSpeedTextField_1);
			minSpeedTextField_1.setText(String.valueOf(CONFIGURATION.selfMinSpeed));
			minSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField minSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.selfMinSpeed = new Double(minSpeedTextField.getText());
				}
			});
			minSpeedTextField_1.setColumns(10);
			
			JLabel lblPrefSpeed = new JLabel("PrefSpeed");
			lblPrefSpeed.setBounds(12, 74, 105, 15);
			performancePanel.add(lblPrefSpeed);
			
			JTextField prefSpeedTextField = new JTextField();
			prefSpeedTextField.setBounds(171, 72, 114, 19);
			performancePanel.add(prefSpeedTextField);
			prefSpeedTextField.setText(String.valueOf(CONFIGURATION.selfPrefSpeed));
			prefSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField speedTextField = (JTextField) e.getSource();
					CONFIGURATION.selfPrefSpeed = new Double(speedTextField.getText());
				}
			});
			prefSpeedTextField.setColumns(10);
			
			
			
			
			JLabel lblMaxClimb = new JLabel("MaxClimb");
			lblMaxClimb.setBounds(12, 101, 70, 19);
			performancePanel.add(lblMaxClimb);
			
			
			JTextField maxClimbTextField_1 = new JTextField();
			maxClimbTextField_1.setBounds(170, 99, 114, 19);
			performancePanel.add(maxClimbTextField_1);
			maxClimbTextField_1.setText(String.valueOf(CONFIGURATION.selfMaxClimb));
			maxClimbTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxClimbTextField = (JTextField) e.getSource();
					CONFIGURATION.selfMaxClimb = new Double(maxClimbTextField.getText());
				}
			});
			maxClimbTextField_1.setColumns(10);
			
			JLabel lblMaxDescent = new JLabel("MaxDescent");
			lblMaxDescent.setBounds(12, 131, 101, 19);
			performancePanel.add(lblMaxDescent);
			
			
			JTextField maxDescentTextField_1 = new JTextField();
			maxDescentTextField_1.setBounds(170, 133, 114, 19);
			performancePanel.add(maxDescentTextField_1);
			maxDescentTextField_1.setText(String.valueOf(CONFIGURATION.selfMaxDescent));
			maxDescentTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxDescentTextField = (JTextField) e.getSource();
					CONFIGURATION.selfMaxDescent = new Double(maxDescentTextField.getText());
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
				maxTurningTextField_1.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.selfMaxTurning)*100)/100.0));
				maxTurningTextField_1.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField maxTurningTextField = (JTextField) e.getSource();
						CONFIGURATION.selfMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
					}
				});
				maxTurningTextField_1.setColumns(10);
			}
		}
					
	}

	public Self(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Self(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Self(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
}
