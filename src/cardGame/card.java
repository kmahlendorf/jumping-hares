package cardGame;

import java.awt.Color;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;

import hares.JumpTrial;

/*
 * Transferable JPanel with background image
 */


@SuppressWarnings("serial")
public abstract class Card extends KPanel implements Transferable{
	
	/**
	 * 
	 */

	private String inventory;
	//private Object entity;

	public Card(Image bgImage, String inventory) {
		super(bgImage);
		
		this.inventory = inventory;
		setBorder(new LineBorder(new Color(0,0,0),2));
		mouseOver();
		
		@SuppressWarnings("unused")
		DragGestureRecognizer dgr = DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, new DragGestureHandler(this));
        @SuppressWarnings("unused")
		DropTarget dt = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetHandler(this), true);
	}
		
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		Object value = null;
        if (isDataFlavorSupported(flavor)) {
            value = this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
        return value;
	}
	
	protected class DragGestureHandler implements DragGestureListener {

        private Card panel;

        public DragGestureHandler(Card panel) {
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
        	
        	Card source;
        	
        	public DragSourceHandler(Card source) {
        		this.source = source;
        	}

            public void dragEnter(DragSourceDragEvent dsde) {}
            public void dragOver(DragSourceDragEvent dsde) {}
            public void dropActionChanged(DragSourceDragEvent dsde) {}
            public void dragExit(DragSourceEvent dse) {}
            public void dragDropEnd(DragSourceDropEvent dsde) {}
            
        }
    }
	public String getInventory() {
		return inventory;
	}
	
	public abstract boolean dropPossible(Object card);
	
	public abstract void setEmpty();
	
	public abstract void readText();
	
	public abstract boolean isEmpty();
	
	public abstract Object drop(Object card);
	
	public abstract void setValue(Object entity);
	
	private void mouseOver() {
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
	
	protected class DropTargetHandler implements DropTargetListener {
		
        private Card card;

        public DropTargetHandler(Card card) {
        	this.card = card;
        }

        public void dragEnter(DropTargetDragEvent dtde) {
            if (dtde.getTransferable().isDataFlavorSupported(JumpTrial.HARE_DATA_FLAVOR)) {
            	
                //System.out.println("Accept...");
                dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
            } else {
                //System.out.println("Reject...");
                dtde.rejectDrag();
            }
        }

        public void dragOver(DropTargetDragEvent dtde) {
        	if (dtde.getTransferable().isDataFlavorSupported(JumpTrial.HARE_DATA_FLAVOR)) {

        		Transferable t = dtde.getTransferable();
                try {
                    Object transferData = t.getTransferData(JumpTrial.HARE_DATA_FLAVOR);
                	
	        		if(transferData instanceof Card ){

	                    if(dropPossible(transferData)) {
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
            
            if (dtde.getTransferable().isDataFlavorSupported(JumpTrial.HARE_DATA_FLAVOR)) {
            	Transferable t = dtde.getTransferable();
                    try {
                        Object transferData = t.getTransferData(JumpTrial.HARE_DATA_FLAVOR);
                        
                      
                    	if(transferData instanceof Card) {
                    		
                        	// card -> receiver
                        	
                        	
                        	if(dropPossible(transferData)) {
                    			                    			
                                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);


                                if(card.isEmpty()) {
                                	card.drop(transferData);
                                	((Card) transferData).setEmpty();
                                }
                                else {
                                	Object oHare = card.drop(transferData);
                                	((Card) transferData).setValue(oHare);
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
	

	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{JumpTrial.HARE_DATA_FLAVOR};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return JumpTrial.HARE_DATA_FLAVOR.equals(flavor);
	}

	

	
}
