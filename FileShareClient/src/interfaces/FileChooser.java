package interfaces;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FileChooser extends JPanel implements ActionListener {
	
// CODE FROM http://www.rgagnon.com/javadetails/java-0370.html
// ALL RIGHTS RESERVED
	
JButton btnGo;
JFileChooser chooser;
String choosertitle;

	public FileChooser() {
	 JButton btnGo = new JButton("OK");
	 btnGo.addActionListener(this);
	 add(btnGo);
	}

public void actionPerformed(ActionEvent e) {
 int result;
 
     
 chooser = new JFileChooser(); 
 chooser.setCurrentDirectory(new java.io.File("."));
 chooser.setDialogTitle(choosertitle);
 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 //
 // disable the "All files" option.
 //
 chooser.setAcceptAllFileFilterUsed(false);
 //    
 if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
   System.out.println("getCurrentDirectory(): " 
      +  chooser.getCurrentDirectory());
   System.out.println("getSelectedFile() : " 
      +  chooser.getSelectedFile());
   }
 else {
   System.out.println("No Selection ");
   }
  }

public Dimension getPreferredSize(){
 return new Dimension(200, 200);
 }
}