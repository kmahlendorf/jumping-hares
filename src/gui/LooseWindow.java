package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LooseWindow {
	public static int death(JFrame parent){
		 
		 int name = JOptionPane.showConfirmDialog(parent,
				 "Try again?", "YOU LOST", JOptionPane.YES_NO_OPTION);
	     return name;       
	    }
}
