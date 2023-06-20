package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JPanel;

import hares.Hare;
import hares.JumpTrial;

/*
 * Holds all the Hare Cards
 */


@SuppressWarnings("serial")
public class HarePanel extends JPanel {


	
	private GridLayout layout;
	private String name;
	private CardPanel[] cards;
	private final int HEIGHT = 210;
	private final int CARD_WIDTH = 150;
	

	/**
     * A panel that holds all hare cards
     * @param size (max number of cards)
     * @param height
	 * @throws IOException 
     */
	
	public HarePanel(int size, int height, Hare[] startingHares, String inventory, JumpTrial con, MainFrame m){
		
		int width;
		if (size <= 8 || name.equals("Offspring")) {
			width = size;
		}
		
		else {
			width = 8;
			height += 1;
		}
		
		cards = new CardPanel[size];

		this.layout = new GridLayout(height, width, 2, 1);
		
		setLayout(layout);
		
		this.setPreferredSize(new Dimension(size*CARD_WIDTH+ size*5, HEIGHT));
		setBackground(new Color(205,186,150));
		initCards(startingHares, size, inventory, con, m);
	}
	

	private void initCards(Hare[] startingHares, int size, String inventory, JumpTrial con, MainFrame m){
		// initialize with a set of starting cards
		// 1) make cards
		
		cards = new CardPanel[size];
		
		if(startingHares == null) {
			for(int i = 0 ;i < size; i++) {
				// add invisuble empty cards
				getCards()[i] = new CardPanel(inventory, con, m);
				add(getCards()[i]);
			}
		}
		else {
		
			int i = 0;
			for (; i < startingHares.length; i++){
				getCards()[i] = new CardPanel(startingHares[i], inventory, con, m);
				add(getCards()[i]);
				getCards()[i].setVisible(true);
			}
			for(;i < size; i++) {
				// add invisuble empty cards
				getCards()[i] = new CardPanel(inventory, con, m);
				add(getCards()[i]);
			}
		}
	}
	/***
	 * Add a new card to the deck
	 * @param hare
	 */
	public void addcard(Hare hare) {
		// fill one of the empty spaces with a new card
		// get first empty card
		
		for(CardPanel card : cards) {
			if(card.isEmpty()) {
				card.addHare(hare);
				return;
			}
		}
	}
	
	public void setEmpty() {
		for(CardPanel card : cards) {
			card.setEmpty();
		}
	}

	public CardPanel[] getCards() {
		return cards;
	}
	
	public Hare[] getHares() {
		
		Hare[] hares = new Hare[0];
		
		for(CardPanel card: cards) {
			if(card.isEmpty())
				continue;
			
			Hare[] hares1 = new Hare[hares.length+1];
			
			for(int i = 0; i < hares1.length-1; i++) {
				hares1[i] = hares[i];
			}
			hares1[hares1.length-1] = card.getHare();
			hares = hares1;
		}
			
		return hares;
	}
	

}
