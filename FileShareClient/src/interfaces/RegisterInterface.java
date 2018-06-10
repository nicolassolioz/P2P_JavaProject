package interfaces;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterInterface extends JFrame implements ActionListener{

	private JLabel lblUsername = new JLabel("Username : ");
	private JLabel lblPassword = new JLabel("Password : ");
	private JLabel lblError = new JLabel("");
	
	private JTextField txtUsername = new JTextField(20);
	private JTextField txtPassword = new JTextField(20);
	
	private JButton btnRegister = new JButton("Register");
	private JButton btnBack = new JButton("Back");
	
	public RegisterInterface() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(lblUsername, constraints);
		
		constraints.gridx = 1;
		panel.add(txtUsername, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(lblPassword, constraints);
		
		constraints.gridx = 1;
		panel.add(txtPassword, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(btnRegister, constraints);
		
		constraints.gridx = 1;
		panel.add(btnBack, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(lblError, constraints);
		
		//add event to button
		btnRegister.addActionListener(this);
		btnBack.addActionListener(this);
		
		add(panel);
		pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnBack)
        {
        	new interfaces.HomeInterface().setVisible(true);
        	this.setVisible(false);
        }
        
        if(e.getSource() == btnRegister) {
        	
        	client.Client client = new client.Client(txtUsername.getText(), txtPassword.getText());
    		connexion.ClientConnexion connect = new connexion.ClientConnexion();
    		
    		connect.registerNewClient(client);
    		
    		String[][] result = connect.connectClient(client);
    		
        	new interfaces.ConnectedInterface(client, result).setVisible(true);
    		this.setVisible(false);
        }
		
    } 
}
