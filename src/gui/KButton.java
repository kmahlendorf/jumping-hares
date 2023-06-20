package gui;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

import hares.JumpTrial;

/*
 *@author Katrina Mahlendorf
 */

@SuppressWarnings("serial")
public class KButton extends JButton{
	
	
	public KButton(String text) {
		super(text);
		setBackground(JumpTrial.b_color);
		
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		
		addMouseListener(new java.awt.event.MouseAdapter() {
			
			@Override
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	setBackground(JumpTrial.bhl_color);
		    	setBorder(BorderFactory.createCompoundBorder(
		    			BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		    }
			@Override
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	setBackground(JumpTrial.b_color);
		    	setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createRaisedBevelBorder(),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		    }
			@Override
		    public void mousePressed(java.awt.event.MouseEvent e) {
				setBackground(JumpTrial.bhl_color);
				setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLoweredBevelBorder(),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
			}
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				setBackground(JumpTrial.b_color);
				setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createRaisedBevelBorder(),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
			}
			
	});
		
		
	}
	
}
