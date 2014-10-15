package configuration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class IntruderConfigurator extends JDialog  
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private final ButtonGroup autoPilotAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup collisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup selfSeparationAlgorithmGroup = new ButtonGroup();

	public IntruderConfigurator(String intruderAlias) 
	{
		final IntruderConfig intruderConfig = Configuration.getInstance().intrudersConfig.get(intruderAlias);		
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));		
		contentPanel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Configuring "+intruderAlias);
		lblTitle.setBounds(10, 0, 240, 33);
		contentPanel.add(lblTitle);
		
		{
			JPanel positionPanel = new JPanel();
			positionPanel.setBorder(new TitledBorder(null, "Position", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			positionPanel.setBounds(10, 62, 292, 91);
			contentPanel.add(positionPanel);
			positionPanel.setLayout(null);
			
			JLabel lblOffsetY = new JLabel("OffsetY");
			lblOffsetY.setBounds(10, 21, 44, 15);
			positionPanel.add(lblOffsetY);
			
			final JLabel offsetYLabel = new JLabel(""+intruderConfig.intruderOffsetY);
			offsetYLabel.setBounds(234, 21, 48, 15);
			positionPanel.add(offsetYLabel);
			
			JSlider intruderOffsetYSlider = new JSlider();
			intruderOffsetYSlider.setBounds(64, 21, 160, 16);
			positionPanel.add(intruderOffsetYSlider);
			intruderOffsetYSlider.setSnapToTicks(true);
			intruderOffsetYSlider.setPaintLabels(true);		
			intruderOffsetYSlider.setMaximum(500);
			intruderOffsetYSlider.setMinimum(-500);
			intruderOffsetYSlider.setValue((int)(intruderConfig.intruderOffsetY));
			intruderOffsetYSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderOffsetY = source.getValue();
					offsetYLabel.setText(""+intruderConfig.intruderOffsetY);
				}
			});
			
			JLabel lblR = new JLabel("R");
			lblR.setBounds(10, 42, 44, 15);
			positionPanel.add(lblR);
			
			final JLabel rLabel = new JLabel(""+intruderConfig.intruderR);
			rLabel.setBounds(234, 42, 48, 15);
			positionPanel.add(rLabel);
			
			JSlider intruderRSlider = new JSlider();
			intruderRSlider.setBounds(64, 42, 160, 16);
			positionPanel.add(intruderRSlider);
			intruderRSlider.setSnapToTicks(true);
			intruderRSlider.setPaintLabels(true);		
			intruderRSlider.setMaximum(15000);
			intruderRSlider.setMinimum(6000);
			intruderRSlider.setValue((int)(intruderConfig.intruderR));
			intruderRSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderR = source.getValue();
					rLabel.setText(""+intruderConfig.intruderR);
				}
			});
			
			JLabel lblTheta = new JLabel("Theta");
			lblTheta.setBounds(10, 65, 44, 15);
			positionPanel.add(lblTheta);
			
			final JLabel thetaLabel = new JLabel(""+intruderConfig.intruderTheta);
			thetaLabel.setBounds(234, 65, 48, 15);
			positionPanel.add(thetaLabel);
			
			JSlider intruderThetaSlider = new JSlider();
			intruderThetaSlider.setBounds(64, 64, 160, 16);
			positionPanel.add(intruderThetaSlider);
			intruderThetaSlider.setSnapToTicks(true);
			intruderThetaSlider.setPaintLabels(true);		
			intruderThetaSlider.setMaximum(180);
			intruderThetaSlider.setMinimum(-180);
			intruderThetaSlider.setValue((int)(intruderConfig.intruderTheta));
			intruderThetaSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderTheta = source.getValue();
					thetaLabel.setText(""+intruderConfig.intruderTheta);
				}
			});
			
		}
		
			
		{
			JPanel sensorSelectionPanel = new JPanel();
			sensorSelectionPanel.setBorder(new TitledBorder(null, "Sensor Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			sensorSelectionPanel.setBounds(10, 164, 290, 47);
			contentPanel.add(sensorSelectionPanel);
			sensorSelectionPanel.setLayout(null);
			
			JCheckBox chckbxPerfectSensor = new JCheckBox("Perfect");
			chckbxPerfectSensor.setBounds(8, 20, 61, 23);
			sensorSelectionPanel.add(chckbxPerfectSensor);
			chckbxPerfectSensor.setSelected((intruderConfig.intruderSensorSelection&0B10000) == 0B10000);
			chckbxPerfectSensor.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						intruderConfig.intruderSensorSelection |= 0B10000;
					}
				}
			});
			
			
			JCheckBox chckbxAdsb = new JCheckBox("ADS-B");
			chckbxAdsb.setBounds(71, 20, 55, 23);
			sensorSelectionPanel.add(chckbxAdsb);
			chckbxAdsb.setSelected((intruderConfig.intruderSensorSelection&0B01000) == 0B01000);
			chckbxAdsb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						intruderConfig.intruderSensorSelection |= 0B01000;
					}
				}
			});
			
			JCheckBox chckbxTcas = new JCheckBox("TCAS");
			chckbxTcas.setBounds(127, 20, 55, 23);
			sensorSelectionPanel.add(chckbxTcas);
			chckbxTcas.setSelected((intruderConfig.intruderSensorSelection&0B00100) == 0B00100);
			chckbxTcas.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						intruderConfig.intruderSensorSelection |= 0B00100;
					}
				}
			});
			
			JCheckBox chckbxRadar = new JCheckBox("Radar");
			chckbxRadar.setBounds(178, 20, 55, 23);
			sensorSelectionPanel.add(chckbxRadar);
			chckbxRadar.setSelected((intruderConfig.intruderSensorSelection&0B00010) == 0B00010);
			chckbxRadar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						intruderConfig.intruderSensorSelection |= 0B00010;
					}
				}
			});
			
			JCheckBox chckbxEoir = new JCheckBox("EO/IR");
			chckbxEoir.setBounds(229, 20, 55, 23);
			sensorSelectionPanel.add(chckbxEoir);
			chckbxEoir.setSelected((intruderConfig.intruderSensorSelection&0B00001) == 0B00001);
			chckbxEoir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						intruderConfig.intruderSensorSelection |= 0B00001;
					}
				}
			});
		}
				
		
		{
			JPanel autoPilotAlgorithmSelectionPanel = new JPanel();
			
			autoPilotAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "Auto-Pilot", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			autoPilotAlgorithmSelectionPanel.setBounds(10, 210, 290, 47);
			contentPanel.add(autoPilotAlgorithmSelectionPanel);
			autoPilotAlgorithmSelectionPanel.setLayout(null);
			
			
			JRadioButton rdbtnWhitenoise = new JRadioButton("WhiteNoise");
			rdbtnWhitenoise.setSelected(intruderConfig.intruderAutoPilotAlgorithmSelection=="WhiteNoise");
			rdbtnWhitenoise.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						intruderConfig.intruderAutoPilotAlgorithmSelection="WhiteNoise";
					}
				}
			});
			rdbtnWhitenoise.setBounds(6, 19, 110, 23);
			autoPilotAlgorithmGroup.add(rdbtnWhitenoise);
			autoPilotAlgorithmSelectionPanel.add(rdbtnWhitenoise);
			
			
			JRadioButton rdbtnSpecific = new JRadioButton("Specific");
			rdbtnSpecific.setSelected(intruderConfig.intruderAutoPilotAlgorithmSelection=="Specific");
			rdbtnSpecific.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						intruderConfig.intruderAutoPilotAlgorithmSelection="Specific";
					}
				}
			});			
			rdbtnSpecific.setBounds(203, 19, 77, 23);
			autoPilotAlgorithmGroup.add(rdbtnSpecific);
			autoPilotAlgorithmSelectionPanel.add(rdbtnSpecific);
			

			
		}		
	
		
		
		{
			JPanel collisionAvoidanceAlgorithmSelectionPanel = new JPanel();
			collisionAvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			collisionAvoidanceAlgorithmSelectionPanel.setBounds(10, 256, 290, 47);
			contentPanel.add(collisionAvoidanceAlgorithmSelectionPanel);
			collisionAvoidanceAlgorithmSelectionPanel.setLayout(null);			
			
			JRadioButton rdbtnACASX2DAvoidanceAlgorithm = new JRadioButton("ACASX2D");
			rdbtnACASX2DAvoidanceAlgorithm.setBounds(6, 17, 94, 23);
			rdbtnACASX2DAvoidanceAlgorithm.setSelected(intruderConfig.intruderCollisionAvoidanceAlgorithmSelection == "ACASX2DAvoidanceAlgorithm");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnACASX2DAvoidanceAlgorithm);
			collisionAvoidanceAlgorithmGroup.add(rdbtnACASX2DAvoidanceAlgorithm);
			rdbtnACASX2DAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						intruderConfig.intruderCollisionAvoidanceAlgorithmSelection = "ACASX2DAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnACASX3DAvoidanceAlgorithm = new JRadioButton("ACASX3D");
			rdbtnACASX3DAvoidanceAlgorithm.setBounds(102, 17, 94, 23);
			rdbtnACASX3DAvoidanceAlgorithm.setSelected(intruderConfig.intruderCollisionAvoidanceAlgorithmSelection == "ACASX3DAvoidanceAlgorithm");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnACASX3DAvoidanceAlgorithm);
			collisionAvoidanceAlgorithmGroup.add(rdbtnACASX3DAvoidanceAlgorithm);
			rdbtnACASX3DAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						intruderConfig.intruderCollisionAvoidanceAlgorithmSelection = "ACASX3DAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(206, 17, 62, 23);
			rdbtnNone.setSelected(intruderConfig.intruderCollisionAvoidanceAlgorithmSelection == "None");
			collisionAvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			collisionAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						intruderConfig.intruderCollisionAvoidanceAlgorithmSelection = "None";
					}
				}
			});
		}
		
		
		{
			JPanel selfSeparationAlgorithmSelectionPanel = new JPanel();
			selfSeparationAlgorithmSelectionPanel.setBounds(10, 304, 290, 47);
			contentPanel.add(selfSeparationAlgorithmSelectionPanel);
			selfSeparationAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "SSA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			selfSeparationAlgorithmSelectionPanel.setLayout(null);			
			
			JRadioButton rdbtnNASAChorusAlgorithm = new JRadioButton("Chorus");
			rdbtnNASAChorusAlgorithm.setBounds(6, 17, 94, 23);
			rdbtnNASAChorusAlgorithm.setSelected(intruderConfig.intruderSelfSeparationAlgorithmSelection == "NASAChorusAlgorithm");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNASAChorusAlgorithm);
			selfSeparationAlgorithmGroup.add(rdbtnNASAChorusAlgorithm);
			rdbtnNASAChorusAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						intruderConfig.intruderSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm";
					}
				}
			});
			
				
			JRadioButton rdbtnNone_1 = new JRadioButton("None");
			rdbtnNone_1.setBounds(206, 17, 62, 23);
			rdbtnNone_1.setSelected(intruderConfig.intruderSelfSeparationAlgorithmSelection == "None");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNone_1);
			selfSeparationAlgorithmGroup.add(rdbtnNone_1);
			rdbtnNone_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						intruderConfig.intruderSelfSeparationAlgorithmSelection = "None";
					}
				}
			});
		}
	
		{
			JPanel otherPanel = new JPanel();
			otherPanel.setBackground(Color.LIGHT_GRAY);
			otherPanel.setBounds(12, 362, 290, 133);
			contentPanel.add(otherPanel);
			otherPanel.setLayout(null);
			
			JLabel lblVy = new JLabel("VY");
			lblVy.setBounds(10, 11, 37, 15);
			otherPanel.add(lblVy);
			
			final JLabel vyLabel = new JLabel(""+intruderConfig.intruderVy);
			vyLabel.setBounds(242, 11, 48, 15);
			otherPanel.add(vyLabel);
		
			JSlider intruderVySlider = new JSlider();
			intruderVySlider.setBounds(77, 11, 161, 16);
			otherPanel.add(intruderVySlider);
			intruderVySlider.setSnapToTicks(true);
			intruderVySlider.setPaintLabels(true);		
			intruderVySlider.setMaximum(58);
			intruderVySlider.setMinimum(-67);
			intruderVySlider.setValue((int)(intruderConfig.intruderVy));
			intruderVySlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderVy = source.getValue();
					vyLabel.setText(""+intruderConfig.intruderVy);

				}
			});
			
			JLabel lblGs = new JLabel("GS");
			lblGs.setBounds(10, 32, 27, 15);
			otherPanel.add(lblGs);
			
			final JLabel gsLabel = new JLabel(""+intruderConfig.intruderGs);
			gsLabel.setBounds(242, 32, 58, 15);
			otherPanel.add(gsLabel);
			
			JSlider intruderGsSlider = new JSlider();
			intruderGsSlider.setBounds(77, 32, 161, 16);			
			intruderGsSlider.setSnapToTicks(true);
			intruderGsSlider.setPaintLabels(true);		
			intruderGsSlider.setMaximum(304);
			intruderGsSlider.setMinimum(169);
			intruderGsSlider.setValue((int)(intruderConfig.intruderGs));
			intruderGsSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderGs = source.getValue();
					gsLabel.setText(""+intruderConfig.intruderGs);
				}
			});
			otherPanel.add(intruderGsSlider);
			
			JLabel lblBearing = new JLabel("Bearing");
			lblBearing.setBounds(10, 51, 57, 15);
			otherPanel.add(lblBearing);
			
			final JLabel bearingLabel = new JLabel(""+intruderConfig.intruderBearing);
			bearingLabel.setBounds(242, 51, 58, 15);
			otherPanel.add(bearingLabel);
		
			JSlider intruderBearingSlider = new JSlider();
			intruderBearingSlider.setBounds(77, 51, 161, 16);			
			intruderBearingSlider.setSnapToTicks(true);
			intruderBearingSlider.setPaintLabels(true);		
			intruderBearingSlider.setMaximum(180);
			intruderBearingSlider.setMinimum(-180);
			intruderBearingSlider.setValue((int)(intruderConfig.intruderBearing));
			intruderBearingSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderBearing = source.getValue();
					bearingLabel.setText(""+intruderConfig.intruderBearing);

				}
			});
			otherPanel.add(intruderBearingSlider);
			
			JLabel lblStdDevX = new JLabel("SDX");
			lblStdDevX.setBounds(10, 71, 37, 15);
			otherPanel.add(lblStdDevX);
			
			final JLabel stdDevXLabel = new JLabel(""+intruderConfig.intruderStdDevX);
			stdDevXLabel.setBounds(242, 71, 58, 15);
			otherPanel.add(stdDevXLabel);
			
			JSlider intruderStdDevXSlider = new JSlider();
			intruderStdDevXSlider.setBounds(77, 71, 161, 16);
			otherPanel.add(intruderStdDevXSlider);
			intruderStdDevXSlider.setSnapToTicks(true);
			intruderStdDevXSlider.setPaintLabels(true);		
			intruderStdDevXSlider.setMaximum(15);
			intruderStdDevXSlider.setMinimum(0);
			intruderStdDevXSlider.setValue((int)(intruderConfig.intruderStdDevX));
			intruderStdDevXSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderStdDevX = source.getValue();
					stdDevXLabel.setText(""+intruderConfig.intruderStdDevX);
				}
			});
			
			JLabel lblStdDevY = new JLabel("SDY");
			lblStdDevY.setBounds(10, 91, 37, 15);
			otherPanel.add(lblStdDevY);
			
			final JLabel stdDevYLabel = new JLabel(""+intruderConfig.intruderStdDevY);
			stdDevYLabel.setBounds(242, 91, 58, 15);
			otherPanel.add(stdDevYLabel);
			
			JSlider intruderStdDevYSlider = new JSlider();
			intruderStdDevYSlider.setBounds(77, 91, 161, 16);
			otherPanel.add(intruderStdDevYSlider);
			intruderStdDevYSlider.setSnapToTicks(true);
			intruderStdDevYSlider.setPaintLabels(true);		
			intruderStdDevYSlider.setMaximum(15);
			intruderStdDevYSlider.setMinimum(0);
			intruderStdDevYSlider.setValue((int)(intruderConfig.intruderStdDevY));
			intruderStdDevYSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderStdDevY = source.getValue();
					stdDevYLabel.setText(""+intruderConfig.intruderStdDevY);
				}
			});
		
			
			JLabel lblStdDevZ = new JLabel("SDZ");
			lblStdDevZ.setBounds(10, 111, 37, 15);
			otherPanel.add(lblStdDevZ);
			
			final JLabel stdDevZLabel = new JLabel(""+intruderConfig.intruderStdDevZ);
			stdDevZLabel.setBounds(242, 111, 58, 15);
			otherPanel.add(stdDevZLabel);
			
			JSlider intruderStdDevZSlider = new JSlider();
			intruderStdDevZSlider.setBounds(77, 111, 161, 16);
			otherPanel.add(intruderStdDevZSlider);
			intruderStdDevZSlider.setSnapToTicks(true);
			intruderStdDevZSlider.setPaintLabels(true);		
			intruderStdDevZSlider.setMaximum(15);
			intruderStdDevZSlider.setMinimum(0);
			intruderStdDevZSlider.setValue((int)(intruderConfig.intruderStdDevZ));
			intruderStdDevZSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					intruderConfig.intruderStdDevZ = source.getValue();
					stdDevZLabel.setText(""+intruderConfig.intruderStdDevZ);
				}
			});
			
		}
		
		
		{			
			JPanel performancePanel = new JPanel();
			performancePanel.setBackground(Color.LIGHT_GRAY);
			performancePanel.setBounds(10, 506, 290, 166);
			contentPanel.add(performancePanel);
			performancePanel.setLayout(null);
			JLabel lblMaxspeed = new JLabel("MaxSpeed");
			lblMaxspeed.setBounds(12, 14, 82, 15);
			performancePanel.add(lblMaxspeed);
			
			
			JTextField maxSpeedTextField_1 = new JTextField();
			maxSpeedTextField_1.setBounds(170, 14, 114, 19);
			performancePanel.add(maxSpeedTextField_1);
			maxSpeedTextField_1.setText(String.valueOf(intruderConfig.intruderMaxSpeed));
			maxSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					intruderConfig.intruderMaxSpeed = new Double(maxSpeedTextField.getText());
				}
			});
			maxSpeedTextField_1.setColumns(10);
			
			
			JLabel lblMinspeed = new JLabel("MinSpeed");
			lblMinspeed.setBounds(12, 43, 70, 19);
			performancePanel.add(lblMinspeed);
			
			
			JTextField minSpeedTextField_1 = new JTextField();
			minSpeedTextField_1.setBounds(170, 45, 114, 19);
			performancePanel.add(minSpeedTextField_1);
			minSpeedTextField_1.setText(String.valueOf(intruderConfig.intruderMinSpeed));
			minSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField minSpeedTextField = (JTextField) e.getSource();
					intruderConfig.intruderMinSpeed = new Double(minSpeedTextField.getText());
				}
			});
			minSpeedTextField_1.setColumns(10);
			
			JLabel lblMaxClimb = new JLabel("MaxClimb");
			lblMaxClimb.setBounds(11, 75, 70, 19);
			performancePanel.add(lblMaxClimb);
			
			
			JTextField maxClimbTextField_1 = new JTextField();
			maxClimbTextField_1.setBounds(169, 73, 114, 19);
			performancePanel.add(maxClimbTextField_1);
			maxClimbTextField_1.setText(String.valueOf(intruderConfig.intruderMaxClimb));
			maxClimbTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxClimbTextField = (JTextField) e.getSource();
					intruderConfig.intruderMaxClimb = new Double(maxClimbTextField.getText());
				}
			});
			maxClimbTextField_1.setColumns(10);
			
			JLabel lblMaxDescent = new JLabel("MaxDescent");
			lblMaxDescent.setBounds(11, 105, 101, 19);
			performancePanel.add(lblMaxDescent);
			
			
			JTextField maxDescentTextField_1 = new JTextField();
			maxDescentTextField_1.setBounds(169, 107, 114, 19);
			performancePanel.add(maxDescentTextField_1);
			maxDescentTextField_1.setText(String.valueOf(intruderConfig.intruderMaxDescent));
			maxDescentTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxDescentTextField = (JTextField) e.getSource();
					intruderConfig.intruderMaxDescent = new Double(maxDescentTextField.getText());
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
				maxTurningTextField_1.setText(String.valueOf(Math.round(Math.toDegrees(intruderConfig.intruderMaxTurning)*100)/100.0));
				maxTurningTextField_1.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField maxTurningTextField = (JTextField) e.getSource();
						intruderConfig.intruderMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
					}
				});
				maxTurningTextField_1.setColumns(10);
			}
		}
		
		setModal(true);
		setBounds(1240, 380, 340, 784); // for windows: setBounds(1160, 380, 347, 474); 
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
		});
		btnOk.setBounds(213, 700, 89, 23);
		contentPanel.add(btnOk);
		this.setVisible(true);
								
	}
}
