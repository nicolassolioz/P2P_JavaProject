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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectedInterface extends JFrame implements ActionListener{

	private JLabel lblFiles = new JLabel("Available files : ");	
	private JLabel lblError = new JLabel("");
	private JLabel lblUser = new JLabel("");
	
	private JButton btnDisconnect = new JButton("Disconnect");
	private JButton btnDownload = new JButton("Download");
	
	private JList listFiles;
	
	private String[][] availableFiles;
	client.Client client;

	
	public ConnectedInterface(client.Client client, String[][] availableFiles) {
		this.client = client;
		this.availableFiles = availableFiles;
		
		DefaultListModel listData = new DefaultListModel();
		int length = 0;
		if(availableFiles == null)
		{
			listData.addElement("empty");
			length = 1;
		}
		else
		{
			length = availableFiles[0].length * availableFiles.length;
			
			for(int i = 0; i<availableFiles[0].length; i++)
			{
				listData.addElement(availableFiles[i][0]);
			}	
			
		}
		
		
		listFiles = new JList(listData);


		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(lblFiles, constraints);
		
		constraints.gridx = 1;
		panel.add(listFiles, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(btnDownload, constraints);
		
		constraints.gridx = 1;
		panel.add(btnDisconnect, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(lblError, constraints);
		
		constraints.gridy = 3;
		lblUser.setText("Hi " + client.getUsername() + " !");
		panel.add(lblUser, constraints);
		
		//add events
		btnDownload.addActionListener(this);
		btnDisconnect.addActionListener(this);
		
		add(panel);
		pack();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnDownload)
        {	
        	lblError.setText("Download " + availableFiles[listFiles.getSelectedIndex()][0]);
        }
        
        if(e.getSource() == btnDisconnect) {
        	connexion.ClientConnexion connect = new connexion.ClientConnexion();
        	connect.writeDBConnect(this.client, 0, 50000);
        	this.setVisible(false);
        }
    }	
}
