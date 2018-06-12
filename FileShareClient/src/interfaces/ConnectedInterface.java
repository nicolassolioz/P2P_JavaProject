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

import client.Client;

public class ConnectedInterface extends JFrame implements ActionListener{

	private JLabel lblFiles = new JLabel("Available files : ");	
	private JLabel lblError = new JLabel("");
	private JLabel lblUser = new JLabel("");
	
	private JButton btnDisconnect = new JButton("Disconnect");
	private JButton btnDownload = new JButton("Download");
	
	private JList listFiles;
	
	private String[][] availableFiles;
	client.Client client;

	connexion.ClientConnexion connect = new connexion.ClientConnexion();
	
	public ConnectedInterface(client.Client client, String[][] availableFiles) {
		this.client = client;
		this.availableFiles = availableFiles;
		
		// fill list with available files based on current connected clients
		DefaultListModel listData = new DefaultListModel();
		if(availableFiles == null)
		{
			listData.addElement("empty");
		}
		else
		{
			
			for(int i = 0; i<availableFiles.length; i++)
			{
				System.out.println(availableFiles[i][0]);
				listData.addElement(availableFiles[i][0]);
			}	
			
		}
	
		listFiles = new JList(listData);

		JPanel panel = new JPanel(new GridBagLayout());
		
		// place elements
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

			// get info of selected file
        	String file = availableFiles[listFiles.getSelectedIndex()][0];
        	String IP = availableFiles[listFiles.getSelectedIndex()][1];
        	int port = Integer.valueOf(availableFiles[listFiles.getSelectedIndex()][2]);
        	String shareFolder = availableFiles[listFiles.getSelectedIndex()][4];
        	
        	// here I indicate the name of the file, in which folder it is, and which socket and port to use
        	// unfortunately, firewalls restrictions do not allow me to use the ip, so I connect to the server
        	// using the localHost address (loopback)
        	System.out.println(IP);
        	connect.getFile(file, IP, port, shareFolder);
        }
        
        if(e.getSource() == btnDisconnect) {

        	// does not effectively disconnect
        	// hides panel
        	this.setVisible(false);
        }
    }

}
