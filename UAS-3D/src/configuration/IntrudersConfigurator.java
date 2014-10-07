package configuration;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import modeling.encountergenerator.CrossingModel;
import modeling.encountergenerator.HeadOnModel;
import modeling.encountergenerator.OvertakenModel;
import modeling.encountergenerator.OvertakingModel;



public class IntrudersConfigurator extends JPanel 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Configuration config= Configuration.getInstance();
	private int counter=0;
	
	public IntrudersConfigurator() 
	{
		setLayout(null);						
		
		JLabel lblSelectAnIntruder = new JLabel("Select an intruder type, then \"add\"");
		lblSelectAnIntruder.setBounds(10, 11, 205, 14);
		add(lblSelectAnIntruder);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"HeadOn", "Crossing", "Overtaking", "Overtaken"}));
		comboBox.setBounds(10, 44, 144, 24);
		String intruderType;
		add(comboBox);		
		
		final DefaultListModel<String> listModel= new DefaultListModel<>();
		final JList list = new JList(listModel);
		list.setBorder(new TitledBorder(null, "List of Intruders", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		list.setBackground(Color.ORANGE);
		list.setBounds(10, 102, 280, 160);
		add(list);
		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String intruderType=comboBox.getSelectedItem().toString();	
				IntruderConfig intruderConfig = new IntruderConfig();
				if(config.globalConfig.encounterGeneratorEnabler)
				{
					switch(intruderType)
					{
					case "HeadOn":
						intruderConfig=HeadOnModel.newHeadOn(config);
						break;
					case "Crossing":
						intruderConfig=CrossingModel.newCrossing(config);
						break;
					case "Overtaking":
						intruderConfig=OvertakingModel.newOvertaking(config);
						break;
					case "Overtaken":
						intruderConfig=OvertakenModel.newOvertaken(config);
						break;
					default:
						intruderConfig=HeadOnModel.newHeadOn(config);
					
					}
						
				}
				String alias = intruderType+ (++counter);
				config.intrudersConfig.put(alias,intruderConfig);
				listModel.addElement(alias);
				
			}
		});
		btnAdd.setBounds(201, 45, 89, 23);
		add(btnAdd);
		
		JButton btnConfigureSelected = new JButton("Configure Selected");
		btnConfigureSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex()>=0)
				{
					new IntruderConfigurator(list.getSelectedValue().toString());
				}
				
			}
		});
		btnConfigureSelected.setBounds(10, 273, 129, 23);
		add(btnConfigureSelected);
		
		JButton btnRemoveSelected = new JButton("Remove Selected");
		btnRemoveSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex()>=0)
				{
					System.out.println(list.getSelectedValue().toString());
					config.intrudersConfig.remove(list.getSelectedValue().toString());	
					listModel.removeElementAt(list.getSelectedIndex());				
				}
			}
		});
		btnRemoveSelected.setBounds(175, 273, 115, 23);
		add(btnRemoveSelected);
	}
	
}
