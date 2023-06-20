package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import hares.JumpTrial;

/*
 * One card consists of an icon (obstacle).
 * 
 * Layout:
 * 
 * Name
 * Icon
 * Functionality: Choose a hare to jump over the fence --> defeat level or fail
 */

@SuppressWarnings("serial")
public class TrialCard extends BackgroundPanel{

	
	
	
	
	private final int HEIGHT = 210;
	private final int CARD_WIDTH = 150;
	private JLabel icon;
	
	public int level;
	private boolean dark = true;
	private boolean complete = false;
	JumpTrial con;
	
	
	
	public TrialCard(int level, JumpTrial con) throws IOException {
		super(con.dark_card_bg);
		this.con = con;
		this.level = level;
		//TODO: Options for higher levels
		initFence(level, con);
		setPreferredSize(new Dimension(CARD_WIDTH, HEIGHT));
		setBorder(new LineBorder(new Color(	
				139,26,26),2));
	}
	
	
	
	/*
	 * A fence obstacle has a height of 1 and a width of 0.
	 */
	private void initFence(int level, JumpTrial con) {
		
		if(level == 0) {
			icon = new JLabel(new ImageIcon(con.stream));
			dark = false;

			setImage(con.card_bg);

		}
		else if(level == 1)
			icon = new JLabel(new ImageIcon(con.dark_fence));
		else
			icon = new JLabel(new ImageIcon(con.dark_stream_fence));
		
		this.add(icon);
	}
	
	public void goDark() {
		if(dark)
			return;
		if(level == 0) 
			icon.setIcon(new ImageIcon(con.dark_stream));
		else if(level == 1)
			icon.setIcon(new ImageIcon(con.dark_fence));
		else
			icon.setIcon(new ImageIcon(con.dark_stream_fence));
		dark = true;

		setImage(con.dark_card_bg);

	}
	
	public void goLight() {
		if(!dark)
			return;
		if(level == 0) 
			icon.setIcon(new ImageIcon(con.stream));
		else if(level == 1)
			icon.setIcon(new ImageIcon(con.fence));
		else
			icon.setIcon(new ImageIcon(con.stream_fence));
		dark = false;

		setImage(con.card_bg);

	}
	
	public void setComplete() {
		if(complete)
			return;
		
		complete = true;
		setBorder(new LineBorder(new Color(	
				0,100,0),2));
	}
	
	public boolean getComplete() {
		return complete;
	}
	
	public void reset() {
		if(!complete)
			return;
		
		complete = false;
		setBorder(new LineBorder(new Color(	
				139,26,26),2));
	}
	
	public boolean isActive() {
		return !dark;
	}
	
	
	
}
