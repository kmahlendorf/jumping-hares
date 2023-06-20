package cardGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;
import javax.swing.JPanel;

/*
 * Panel with background image
 */

@SuppressWarnings("serial")
public class KPanel extends JPanel{
	
	private Image image;
	
	public KPanel(Image image) {
		setLayout(new BorderLayout());
		setImage(image);
	}
	
	public void setImage(Image image) {
		this.image = image;
		repaint();
	}

	/*
	 * PreferredSize returns image size
	 */
	@Override
	public Dimension getPreferredSize() {
		if (image == null)
			return super.getPreferredSize();
		else
			return new Dimension(image.getWidth(null), image.getHeight(null));
	}
	
	/*
	 * Added components are transparent.
	 */
	public void add(JComponent component) {
		add(component, null);
	}
	
	public void add(JComponent component, Object constraints) {
		makeComponentTransparent(component);
		super.add(component, constraints);
	}
	
	
	private void makeComponentTransparent(JComponent component)	{
		component.setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (image == null ) return;
		
		drawImage(g);
	}
	
	private void drawImage(Graphics g)
	{
		Dimension d = getSize();
		g.drawImage(image, 0, 0, d.width, d.height, null);
	}
}
