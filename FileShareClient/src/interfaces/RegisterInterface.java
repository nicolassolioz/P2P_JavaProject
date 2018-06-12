package interfaces;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connexion.CheckAvailablePort;

public class RegisterInterface extends JFrame implements ActionListener{

	private JLabel lblUsername = new JLabel("Username : ");
	private JLabel lblPassword = new JLabel("Password : ");
	private JLabel lblFolder = new JLabel("Share folder : ");
	private JLabel lblError = new JLabel("");
	
	
	private JTextField txtUsername = new JTextField(20);
	private JTextField txtPassword = new JTextField(20);
	private JTextField txtFolder = new JTextField(20);
	
	private JButton btnRegister = new JButton("Register");
	private JButton btnBack = new JButton("Back");
	private JButton btnChooser = new JButton("Select folder");
	
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
		panel.add(lblFolder, constraints);
		
		
		constraints.gridx = 1;
		panel.add(txtFolder, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(btnRegister, constraints);
		
		constraints.gridx = 1;
		panel.add(btnBack, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		panel.add(btnChooser, constraints);
		
		constraints.gridy = 5;
		panel.add(lblError, constraints);
		
		//add event to button
		btnRegister.addActionListener(this);
		btnBack.addActionListener(this);
		btnChooser.addActionListener(this);
		
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
        	
        	if(txtUsername.getText() == "" || txtPassword.getText() == "" || txtFolder.getText() == "")
        	{
        		lblError.setText("please fill all fields !");
        	}
        	else
        	{
        		client.Client client = new client.Client(txtUsername.getText(), txtPassword.getText());
        		connexion.ClientConnexion connect = new connexion.ClientConnexion();
        		
        		connect.registerNewClient(client, txtFolder.getText());
        		
        		CheckAvailablePort portCheck = new CheckAvailablePort();
        		int clientPort = 0;
        		
        		for(int i = 50000; i<50100; i++)
        		{
        			if(portCheck.available(i))
        			{
        				clientPort = i;
        				System.out.println("port " + i + " is available!");
        				i = 50100;
        				
        			}
        		}
        		
        		String[][] result = connect.connectClient(client, clientPort);
        		
        		if (result != null && clientPort > 0) 
        		{     			
        			new interfaces.ConnectedInterface(client, result).setVisible(true);
            		this.setVisible(false);
        		}
        	}
        	    	
        }
	        if(e.getSource() == btnChooser)
	        {
	        	int result;

	        	 JFileChooser chooser = new JFileChooser(); 
	        	 chooser.setCurrentDirectory(new java.io.File("."));
	        	 chooser.setDialogTitle("Select folder");
	        	 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        	 //
	        	 // disable the "All files" option.
	        	 //
	        	 chooser.setAcceptAllFileFilterUsed(false);
	        	 //    
	        	 if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	        		 txtFolder.setText(chooser.getSelectedFile().toString());
	        	   }
	        	 else {
	        	   System.out.println("No Selection ");
	        	   
	        	  }
	        }
        }
}
