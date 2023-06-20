package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import hares.*;

import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;




/*
 * One card consists of a name, a discard button, an icon (hare image) and space for notes.
 * 
 */


@SuppressWarnings("serial")
public class CardPanel extends BackgroundPanel implements Transferable{
	
	private Hare hare;
	public static final DataFlavor HARE_DATA_FLAVOR = new DataFlavor(Hare.class, "Hare");

	
	private GridBagLayout layout;
	private JLabel name;
	private JLabel icon;
	private int hareGender = 1;
	private String inventory;
	private JTextField text;
	private JButton x;
	private JumpTrial con;
	
	// TODO: find a better solution
	private MainFrame m;
	

	public CardPanel(Hare hare, String inventory, JumpTrial con, MainFrame m){
		super(con.card_bg);
		this.hare = hare;
		this.con = con;
		this.m = m;
		
		initCard(inventory);
		initHare(hare);
		setVisible(true);
				
		addMouseListener(new java.awt.event.MouseAdapter() {
			
			@Override
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
				
				setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createRaisedBevelBorder(),
						BorderFactory.createLineBorder(new Color(0,0,0),2)));
		    	
		    }
			@Override
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    			setBorder(new LineBorder(new Color(0,0,0),2));
		    }
		});
		
	}
	
	/***
	 * Make a new empty card
	 */
	public CardPanel(String inventory, JumpTrial con, MainFrame m){
		super(con.card_bg);
		this.con = con;
		this.m = m;
		
		initCard(inventory);
		
		setVisible(true);
		//System.out.println("Empty card");
		repaint();
		revalidate();
	}
	
	public String getInventory() {
		return inventory;
	}
	
	private void initCard(String inventory) {
		setBorder(new LineBorder(new Color(0,0,0),2));
		this.inventory = inventory;
		layout = new GridBagLayout();
		setLayout(layout);
		
		
		@SuppressWarnings("unused")
		DragGestureRecognizer dgr = DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, new DragGestureHandler(this));
        @SuppressWarnings("unused")
		DropTarget dt = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetHandler(this), true);
		
	}
	
	private void initHare(Hare hare) {
		// label
		
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
		
		icon = (hare.getGender() == 1)?  new JLabel(new ImageIcon(con.male)) : new JLabel(new ImageIcon(con.female));

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
	
	public boolean isEmpty() {
		return (hare == null);
	}
	
	void setEmpty() {
		if(hare == null)
			return;
		
		this.hare = null;

		x.setVisible(false);
		name.setVisible(false);
		icon.setVisible(false);
		text.setVisible(false);
		
	}
	
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{HARE_DATA_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return HARE_DATA_FLAVOR.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		Object value = null;
        if (HARE_DATA_FLAVOR.equals(flavor)) {
            value = this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
        return value;
	}
	
	
	public int getHareGender() {
		return hareGender;
	}

	public void setHareGender(int hareGender) {
		this.hareGender = hareGender;
		
		if(inventory.equals("pair")) {

			if(hareGender == 1) {
				setImage(con.card_bg_male);
			}
			else {
				setImage(con.card_bg_female);
			}
					
			
		}
	}


	protected class DragGestureHandler implements DragGestureListener {

        private CardPanel panel;

        public DragGestureHandler(CardPanel panel) {
            this.panel = panel;
        }

        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
        	
        	
        	if(!isEmpty()) {
        		Transferable t = (Transferable) panel;
	        	panel.readText();
	            DragSource ds = dge.getDragSource();
	            ds.startDrag( dge, null, t, new DragSourceHandler(panel));
        	}
        	
            
        }
        
        protected class DragSourceHandler implements DragSourceListener {
        	
        	CardPanel source;
        	
        	public DragSourceHandler(CardPanel source) {
        		this.source = source;
        	}

            public void dragEnter(DragSourceDragEvent dsde) {}
            public void dragOver(DragSourceDragEvent dsde) {}
            public void dropActionChanged(DragSourceDragEvent dsde) {}
            public void dragExit(DragSourceEvent dse) {}

            public void dragDropEnd(DragSourceDropEvent dsde) {
                
            	if( dsde.getDropSuccess()) {
            		
            		//source.setEmpty();

            	}
            }
        }
    }
	
	protected class DropTargetHandler implements DropTargetListener {
		
        private CardPanel card;

        public DropTargetHandler(CardPanel card) {
        	this.card = card;
        }

        public void dragEnter(DropTargetDragEvent dtde) {
            if (dtde.getTransferable().isDataFlavorSupported(CardPanel.HARE_DATA_FLAVOR)) {
            	
                //System.out.println("Accept...");
                dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
            } else {
                //System.out.println("Reject...");
                dtde.rejectDrag();
            }
        }

        public void dragOver(DropTargetDragEvent dtde) {
        	if (dtde.getTransferable().isDataFlavorSupported(CardPanel.HARE_DATA_FLAVOR)) {
        		Transferable t = dtde.getTransferable();
                try {
                    Object transferData = t.getTransferData(CardPanel.HARE_DATA_FLAVOR);
                	
	        		if(transferData instanceof CardPanel) {
	                    	Hare hare = (Hare) ((CardPanel) transferData).getHare();
    
                		if((card.inventory.equals("pair") && card.getHareGender() == hare.getGender()) || card.inventory.equals("trial") || card.inventory.equals("deck")) {
	                			card.setBorder(BorderFactory.createCompoundBorder(
								BorderFactory.createRaisedBevelBorder(),
								BorderFactory.createLineBorder(new Color(0,0,0),2)));
	                		}
	        			}
	        		} catch (UnsupportedFlavorException | IOException ex) {
	        			ex.printStackTrace();
	        		}
        	}
        }

        public void dropActionChanged(DropTargetDragEvent dtde) {
        }

        public void dragExit(DropTargetEvent dte) {
        	card.setBorder(new LineBorder(new Color(0,0,0),2));
        	
        }

        public void drop(DropTargetDropEvent dtde) {
            
            if (dtde.getTransferable().isDataFlavorSupported(CardPanel.HARE_DATA_FLAVOR)) {
            	Transferable t = dtde.getTransferable();
                    try {
                        Object transferData = t.getTransferData(CardPanel.HARE_DATA_FLAVOR);
                        
                      //test
                    	if(transferData instanceof CardPanel) {
                    	
                        //if (transferData instanceof Hare) {
                        	Hare hare = (Hare) ((CardPanel) transferData).getHare();
                        	
                                                	
                        	// Hare must have the correct sex
                        	
                    		if((card.inventory.equals("pair") && card.getHareGender() == hare.getGender()) || card.inventory.equals("trial") || card.inventory.equals("deck")) {
                    			                    			
                                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                                
                                if(card.isEmpty()) {
                                	((CardPanel) transferData).setEmpty();
                                	card.addHare(hare);
                                }
                                else {
                                	((CardPanel) transferData).addHare(card.getHare());
                                	card.addHare(hare);
                                }
                                
                                
                                dtde.dropComplete(true);
                    		}
                    		
                    		else {
                    			dtde.rejectDrop();
                    		}

                        }
                    } catch (UnsupportedFlavorException ex) {
                        ex.printStackTrace();
                        dtde.rejectDrop();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        dtde.rejectDrop();
                    }
                } else {
                	
                    dtde.rejectDrop();
                }
            }
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
			Image img = (hare.getGender() == 1)? con.male : con.female;
			
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

}
