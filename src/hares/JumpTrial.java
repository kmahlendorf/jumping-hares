package hares;


import java.io.IOException;
import javax.imageio.ImageIO;


import java.awt.*;

/**
* 
* <h1>Jump Trial</h1>
* This Class loads ressources and provides utility.
*
* 
* @author Katrina Mahlendorf
* 
*/

public class JumpTrial {
	
	private static String bg_image = "/images/card_bg.jpg";
	private static String bg_female = "/images/card_bg_female.png";
	private static String bg_male = "/images/card_bg_male.png";
	
	private static String bg_dark = "/images/card_bg_dark.jpg";
		
	private static String fence_image = "/images/fence.png";
	private static String stream_image = "/images/stream.png";
	private static String stream_fence_image = "/images/stream_fence.png";
	
	private static String fence_dark = "/images/fence_dark.png";
	private static String stream_dark = "/images/stream_dark.png";
	private static String stream_fence_dark = "/images/stream_fence_dark.png";
	
	private static String x_image = "/images/card_x.png";
	private static String female_hare_image = "/images/hare_female.png";
	private static String male_hare_image = "/images/hare_male.png";
	
	private static String arrow_path = "/images/arrow.png";
	
	public static Color bg_color = new Color(205,186,150);
	
	// Color for Buttons
	public static Color bhl_color = new Color(255,231,186);
	public static Color b_color = new Color(238,216,174);
	
	
	private static int lvl = 0;
	
	public Image card_bg;
	public Image card_bg_female;
	public Image card_bg_male;
	public Image dark_card_bg;
	public Image fence;
	public Image dark_fence;
	public Image dark_stream;
	public Image stream;
	public Image stream_fence;
	public Image dark_stream_fence;
	public Image x;
	public Image female;
	public Image male;
	public Image arrow;
	
	/*
	 * Constructor for JumpTrial
	 * Loads ressources and generates starting deck
	 */
	public JumpTrial() {
		card_bg = loadImage(bg_image);
		card_bg_female = loadImage(bg_female);
		card_bg_male = loadImage(bg_male);
		dark_card_bg = loadImage(bg_dark);
		
		fence = loadImage(fence_image);
		dark_fence = loadImage(fence_dark);
		dark_stream = loadImage(stream_dark);
		stream = loadImage(stream_image);
		stream_fence = loadImage(stream_fence_image);
		dark_stream_fence = loadImage(stream_fence_dark);
		x = loadImage(x_image);
		female = loadImage(female_hare_image);
		male = loadImage(male_hare_image);
		arrow = loadImage(arrow_path);
	}
	
	public static Hare[] makeDeck() {
		Hare[] deck = new Hare[3];
		Gene[] genes = new Gene[2];
		genes[0] = new Gene(true, true, false);
		genes[1] = new Gene(false, false, true);
		deck[0] = new Hare(genes, 0);
		
		genes = new Gene[2];
		genes[0] = new Gene(false, true, false);
		genes[1] = new Gene(false, false, true);
		deck[1] = new Hare(genes, 1);
		
		genes = new Gene[2];
		genes[0] = new Gene(false, false, false);
		genes[1] = new Gene(true, false, true);
		deck[2] = new Hare(genes, 0);
		return deck;
	}
	

	private Image loadImage(String path) {
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource(path));
			
		} catch (IOException e) {
			System.out.println("Cannot find " + path);
			e.printStackTrace();
		}
		return img;
	}

	
	public static void newLvl(int level) {
		lvl = level;
		// TODO
	}
	
	public static Jump getJump() {
		if(lvl == 0) {
			return new Jump(0, 1);
		}
		if(lvl == 1) {
			return new Jump(1, 0);
		}
		return new Jump(1, 1);
	}
	
	public static boolean canWin(Hare[] hares) {
		boolean hGene1 = false;
		boolean hGene2 = false;
		boolean wGene = false;
		
		for(Hare h : hares) {
			if(h.getGenes()[0].hasOne()) {
				if(hGene1)
					hGene2 = true;
				else
					hGene1 = true;
			}
			if(h.getGenes()[1].hasOne()) 
				wGene = true;
			
			
			if(hGene1 && hGene2 && wGene)
				break;
		}
				
		return hGene1 && hGene2 && wGene;
		
	}
	
	
}
