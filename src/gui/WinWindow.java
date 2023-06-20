package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class WinWindow {
	public static int win(JFrame parent){
		 
		 int name = JOptionPane.showConfirmDialog(parent,
				 "Restart?", "YOU WIN", JOptionPane.YES_NO_OPTION);
	     return name;
	    }
}
