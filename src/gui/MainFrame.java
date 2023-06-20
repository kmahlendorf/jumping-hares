package gui;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import hares.Hare;
import hares.Jump;
import hares.JumpTrial;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
 * TODO: make a class for the frame
 * cleanup
 * remove debug text
 */

public class MainFrame {

	public JFrame frame;
	private HarePanel harePanel;
	private HarePanel pairing;
	private JButton child;
	private TrialCard[] trials;
	private HarePanel chosen;
	private JButton jump;
	
	private JLabel message;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public MainFrame() throws IOException {
		initialize();
	}
	
	
	private void initialize() throws IOException {
		
		JumpTrial con = new JumpTrial();
		frame = new JFrame();
		frame.setBounds(30, 30, 1300, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JMenuBar menuBar = new JMenuBar();
		//a group of JMenuItems
		JMenuItem menuItem = new JMenuItem("Restart",
		                         KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "This doesn't really do anything");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                restart();
            }
        });
		menuBar.add(menuItem);
		frame.setJMenuBar(menuBar);
		
		
		JPanel contentPane = new JPanel();
		
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout());
		contentPane.setBackground(new Color(205,186,150));
		GridBagConstraints c = new GridBagConstraints();
		
		
		Hare[] deck = JumpTrial.makeDeck();
		
		//Font  font  = new Font(Font.SERIF, Font.PLAIN, 12);
		
		// row 1: deck
		
		harePanel = new HarePanel(6, 1, deck, "deck", con, this);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		//c.fill =  GridBagConstraints.HORIZONTAL;
		
		contentPane.add(harePanel, c);
		
		// row 2: pairing, button, tasks
		
		// a harePanel for two
		// + a button 
		// text
		pairing = new HarePanel(2, 1, null, "pair", con, this);
		
		c.gridy = 1;
		c.gridwidth = 2;
		c.insets = new Insets(50, 10, 10, 10);
		//c.fill =  GridBagConstraints.HORIZONTAL;
		contentPane.add(pairing, c);
		pairing.getCards()[1].setHareSex(0);
		pairing.getCards()[0].setHareSex(1);
		
		// pair button
		child = new KButton("Make child");
		child.addActionListener(new Lauscher());
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 1;
		c.gridx = 2;
		contentPane.add(child, c);
		
		// task panel
		JPanel tasks = new JPanel();
		GridBagLayout g = new GridBagLayout();
		tasks.setLayout(g);
		GridBagConstraints cs = new GridBagConstraints();
		
		JLabel instr1 = new JLabel("Please choose a hare for the jump trial.");
		JLabel instr2 = new JLabel("Two of your starting hares carry a recessive gene that increases jump height.");
		JLabel instr3 = new JLabel("One carries a dominant gene that increases jump width.");
		JLabel instr4 = new JLabel("Try to produce offspring that can jump over the fence and the water.");
		message = new JLabel();
		
		cs.anchor = GridBagConstraints.LINE_START;
		cs.gridy = 0;
		cs.insets = new Insets(10, 5, 10, 5);
		//instr1.setFont(font);
		tasks.add(instr1, cs);
		
		cs.gridy = 1;
		
		//instr2.setFont(font);
		tasks.add(instr2, cs);
		cs.gridy = 2;
		cs.insets = new Insets(0, 5, 10, 5);
		tasks.add(instr3, cs);
		cs.gridy = 3;
		
		cs.insets = new Insets(10, 5, 10, 5);
		tasks.add(instr4, cs);
		cs.gridy = 4;
		//cs.insets = new Insets(10, 10, 10, 10);
		tasks.add(message, cs);
		
		c.gridwidth = 3;
		c.gridy = 1;
		c.gridx = 3;
		contentPane.add(tasks, c);
		
		
		// Row 3
		
		// add jump trial
		// panel for Hare that should jump
		chosen = new HarePanel(1, 1, null, "trial", con, this);
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight = 2;
		contentPane.add(chosen, c);

		
		jump = new KButton("Jump!");
					
		jump.addActionListener(new TrialLauscher());
		
		c.insets = new Insets(100, 10, 0, 15);
		c.anchor = GridBagConstraints.PAGE_END;
		c.weighty = 2.0;
		
		c.gridheight = 1;
		c.gridx = 2;
		contentPane.add(jump, c);
		
		JLabel arrow = new JLabel(new ImageIcon(con.arrow));
		c.insets = new Insets(0, 80, 80, 60);
		c.anchor = GridBagConstraints.PAGE_START;
		c.weighty = 0.0;
		c.gridy = 3;

		contentPane.add(arrow, c);
		
		
		// level 1 obstacle: stream 
		// level 2 obstacle fence
		// level 3 obstacle stream with fence
		JPanel trialPanel = new JPanel();
		//trialPanel.setPreferredSize(new Dimension(100*3, 200));
		trials = new TrialCard[3];
		TrialCard trial = new TrialCard(0, con);
		trials[0] = trial;
		trial.addMouseListener( ml );
		TrialCard trial2 = new TrialCard(1, con);
		trials[1] = trial2;
		trial2.addMouseListener( ml );
		TrialCard trial3 = new TrialCard(2, con);
		trials[2] = trial3;
		trial3.addMouseListener( ml );
		
		
		trialPanel.setBackground(new Color(205,186,150));
		trialPanel.add(trial);
		trialPanel.add(trial2);
		trialPanel.add(trial3);
		
		c.gridheight = 2;
		c.weighty = 1.0;
		c.insets = new Insets(50, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 2;
		c.gridx = 4;
		c.gridwidth = 3;
		
		contentPane.add(trialPanel, c);

	}
	
	
	public void restart() {
		// delete all existing hares
		//1) in deck:
		harePanel.setEmpty();
		//2) in pairing
		pairing.setEmpty();
		//3) in trial
		chosen.setEmpty();
		
		// delete progress on jump trials
		for(TrialCard t: trials) {
			t.reset();
			t.goDark();
		}
		trials[0].goLight();
		
		
		// give new deck
		Hare[] hares = JumpTrial.makeDeck();
		for(Hare h: hares)
			harePanel.addcard(h);
		
		message.setText("");
	}
	
	public Hare[] getHares() {
		
		Hare[] hd = harePanel.getHares();
		Hare[] hp = pairing.getHares();
		Hare[] hc = chosen.getHares();
		
		Hare[] hares = new Hare[hd.length + hp.length + hc.length];
		
		int j = 0, i = 0;
		for(; j < hd.length; i++, j++) 
			hares[i] = hd[j];
		
		j = 0;
		for(; j < hp.length; i++, j++) 
			hares[i] = hp[j];
		
		j = 0;
		for(; j < hc.length; i++, j++) 
			hares[i] = hc[j];
		
		return hares;
	}

	
	class Lauscher implements ActionListener {
		
	    public void actionPerformed(ActionEvent e) {
	    	// Button pressed
	    	// make child of the two paired hares
	    	if(pairing.getCards()[0].isEmpty() || pairing.getCards()[1].isEmpty()){
	    		// add text
	    		message.setText("<html><font color='red'>Please choose two Parents.</font></html>");
	    		return;
	    	}

	    	// get mother and father
	    	Hare mother = pairing.getCards()[0].getHare();
	    	Hare father = pairing.getCards()[1].getHare();
	    	
	    	Hare child = new Hare(mother, father);
	    	
	    	// make new card in deck for child
	    	harePanel.addcard(child);
	    	message.setText("");
	    	System.out.println("Made Child: " + child);
	    }
	}
	
	class TrialLauscher implements ActionListener {
			
	    public void actionPerformed(ActionEvent e) {
	    	
	    	if(chosen.getCards()[0].isEmpty()) {
	    		
	    		message.setForeground(Color.RED);
	    		message.setText("You need to choose a jumper first.");
	    		return;
	    	}
	    	
	    	// check if hare can make the jump
	    	Hare hare = chosen.getCards()[0].getHare();
	    	
	    	Jump obstacle = JumpTrial.getJump();
	    	
	    	if(hare.jump(obstacle)) {
	    		
	    		message.setForeground(Color.GREEN);
	    		message.setText("Jump successful.");
	    		
	    		int i = 0;
	    		for(; i < trials.length; i++) {
	    			if(trials[i].isActive()) {
	    				trials[i].setComplete();
	    				trials[i].goDark();
	    				break;
	    			}
	    		}
	    		if(i < trials.length - 1) {
	    			trials[i+1].goLight();
	    			JumpTrial.newLvl(trials[i+1].level);
	    		}
	    		else {
	    			trials[0].goLight();
	    			JumpTrial.newLvl(trials[0].level);
	    		}
	    		
	    		if(i == 2) {
	    			//win if trial 3 was completed
	    			int win = WinWindow.win(frame);
		    		
					if(win == 0) 
						restart();

					else 
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	    		}
	    	}
	    	else {
	    		message.setForeground(Color.RED);
	    		message.setText("Jump unsuccessful.");
	    	}
	    }
	}
	
	
	MouseListener ml = new MouseAdapter()
	{
	    @Override
	    public void mousePressed(MouseEvent e)
	    {
	        TrialCard trial = (TrialCard)e.getSource();
	        
	        // TODO: put all trials in a list
	        // make all trials blackened
	        for(TrialCard t: trials) {
	        	t.goDark();
	        }
	        // highlight the chosen one
	        trial.goLight();
	        JumpTrial.newLvl(trial.level);
	        
	    }
	};

}



