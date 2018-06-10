package interfaces;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeInterface extends JFrame implements ActionListener{

	JButton btnStartServer = new JButton("Start the server");
	public JLabel lblServer = new JLabel("offline");
	
	public HomeInterface() {
		setPreferredSize(new Dimension(500,300));
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(lblServer, constraints);
		
		constraints.gridx = 1;
		panel.add(btnStartServer, constraints);
		
		btnStartServer.addActionListener(this);
		add(panel);
		pack();
	}
	
	public void actionPerformed(ActionEvent e) {	
		connexion.ServerConnexion server = new connexion.ServerConnexion();
		lblServer.setText("waiting for server client connection");
		server.connectServer();		
		lblServer.setText("online");
	}
}
