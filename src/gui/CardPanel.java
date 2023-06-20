package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import cardGame.Card;
import hares.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


/*
 * One card consists of a name, a discard button, an icon (hare image) and space for notes.
 * 
 */



@SuppressWarnings("serial")
public class CardPanel extends Card{
	
	private Hare hare;
		
	private JLabel name;
	private JLabel icon;
	private int hareSex = 1;

	private JTextField text;
	private JButton x;
	private JumpTrial con;
	
	// TODO: find a better solution
	private MainFrame m;
	

	public CardPanel(Hare hare, String inventory, JumpTrial con, MainFrame m){
		super(con.card_bg, inventory);
		
		this.hare = hare;
		this.con = con;
		this.m = m;
		
		setLayout( new GridBagLayout() );
		
		initHare(hare);
	}
	
	/***
	 * Make a new empty card
	 */
	public CardPanel(String inventory, JumpTrial con, MainFrame m){
		super(con.card_bg, inventory);
		
		this.con = con;
		this.m = m;
		
		setLayout( new GridBagLayout() );
	}

	
	private void initHare(Hare hare) {
		// all the design stuff
		
		this.hare = hare;
		name = new JLabel(hare.getName());
		
		
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		
		
		x = new JButton();
		 try {
		    x.setIcon(new ImageIcon(con.x));
		} catch (Exception ex) {
		    System.out.println(ex);
		}
		
		x.setMargin(new Insets(0, 0, 0, 0));
		x.setBorder(null);
		x.addActionListener(new xLauscher());
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		this.add(x, c);
		c.gridy = 1;
		
				
		c.anchor = GridBagConstraints.PAGE_START; 		
		c.insets = new Insets(2, 2, 2, 2);
		this.add(name, c);
		
		c.gridheight = 3;
		c.gridy += 1;
		
		icon = (hare.getSex() == 1)?  new JLabel(new ImageIcon(con.male)) : new JLabel(new ImageIcon(con.female));

		this.add(icon, c);
		
		
		c.gridheight = 2;
		c.gridy += 3;
		text = new JTextField(hare.getNotes());
		text.setColumns(8);
		text.setBorder(new LineBorder(new Color(139,90,43),1));
		this.add(text, c);
	}
	
	public Hare getHare() {
		return hare;
	}
	
	@Override
	public boolean isEmpty() {
		return (hare == null);
	}
	
	@Override
	public void setEmpty() {
		if(hare == null)
			return;
		
		this.hare = null;

		x.setVisible(false);
		name.setVisible(false);
		icon.setVisible(false);
		text.setVisible(false);
	}

	public int getHareSex() {
		return hareSex;
	}

	public void setHareSex(int hareSex) {
		this.hareSex = hareSex;
		
		if(getInventory().equals("pair")) {

			if(hareSex == 1) {
				setImage(con.card_bg_male);
			}
			else {
				setImage(con.card_bg_female);
			}
					
			
		}
	}
	
	public boolean dropPossible(Object transferCard) {
		if(!(transferCard instanceof CardPanel))
			return false;
		
		CardPanel nCard = (CardPanel) transferCard;

		Hare nHare = nCard.getHare();
		
		if((getInventory().equals("pair") && nHare.getSex() == getHareSex() ) || getInventory().equals("trial") || getInventory().equals("deck")) 
			return true;
		
		return false;
	}
	
	
	public Object drop(Object card) {

		CardPanel nCard = (CardPanel) card;

		Hare nHare = nCard.getHare();
		Hare oHare = this.hare;
		
		addHare(nHare);
		return oHare;
	}
	
	public void readText() {
		hare.setNotes(text.getText());
	}
	
	public void addHare(Hare hare) {
		if(isEmpty()) {
			initHare(hare);
		}
		else {
			this.hare = hare;
			name.setText(hare.getName());
			Image img = (hare.getSex() == 1)? con.male : con.female;
			
			icon.setIcon(new ImageIcon(img));
			text.setText(hare.getNotes());
		}
		
		revalidate();
		repaint();
		x.setIcon(new ImageIcon(con.x));
	}
	
	class xLauscher implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	    	setEmpty();
	    	
	    	// check if game can still be completed
	    	boolean canWin = JumpTrial.canWin(m.getHares());
	    	
	    	if(!canWin) {
	    		int death = LooseWindow.death(m.frame);
	    		
				if(death == 0) 
					m.restart();

				else 
					m.frame.dispatchEvent(new WindowEvent(m.frame, WindowEvent.WINDOW_CLOSING));

	    	}
	    }
	}

	@Override
	public void setValue(Object entity) {
		if(entity instanceof Hare) {
			Hare nHare = (Hare) entity;
			addHare(nHare);
		}
	}
	
	
	
}
