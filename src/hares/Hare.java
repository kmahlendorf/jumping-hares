package hares;

import java.util.Collections;
import java.util.Random;

/**
*
* @author Katrina Mahlendorf
* 
* 
*/

public class Hare {
	/*
	 * A hare has genetics, a name and an age. 
	 */
	private String name;
	private int sex; // 0 = male, 1 = female
	

	private String notes = "";
	
	
	// genetics
	private Gene[] genes;
	
	public Hare(Gene[] genes, int gender) {
		this.genes = genes;
		this.sex = gender;
		initialize();
	}
	
	public Hare(Hare mother, Hare father) {

		inheritance(mother, father);
		
		Random rand = new Random();
		sex = rand.nextInt(2);
		
		initialize();
	}
	
	private void initialize(){
		
		// generate Name
		if(getSex() == 0) {
			if(JumpTrial.fName.size() == 0)
				name = "Hare";
			else {
				Collections.shuffle(JumpTrial.fName);
				name = JumpTrial.fName.get(0);
				JumpTrial.fName.remove(0);
			}
		}
		else {
			if(JumpTrial.mName.size() == 0)
				name = "Hare";
			else {
				Collections.shuffle(JumpTrial.mName);
				name = JumpTrial.mName.get(0);
				JumpTrial.mName.remove(0);
			}
		}
	}
	
	public Gene[] getGenes() {
		return genes;
	}
	
	
	private void inheritance(Hare mother, Hare father) {
		// make  Genes of new Hare
		genes = new Gene[mother.getGenes().length];
		
		genes[0] = new Gene(mother.getGenes()[0], father.getGenes()[0], false);
		genes[1] = new Gene(mother.getGenes()[1], father.getGenes()[1], true);
		
	}
	
	
	 /**
     * A jump is successful if the hare can reach both height and width.
     * @param width
     * @param height
     * @return true if the hare can make the jump
     */
	public boolean jump(Jump j) {
		System.out.println("Jump: " + j.height + " " + j.width + " Hare: " + jumpHeight() + " " + jumpWidth());
		return jumpHeight() >= j.height && jumpWidth() >= j.width;
	}
	
	private int jumpHeight() {
		// TODO: adjust according to added Genes
		// assuming genes[0] i for height
		return (genes[0].active()) ? 1 : 0;
	}
	
	private int jumpWidth() {
		if(genes.length == 2) {
			return (genes[1].active()) ? 1 : 0;
		}
		return 0;
	}
	
	
	/**
     * toString method
     * @return String
     */
	public String toString() {
		
		String str = getName();
		if( getSex() == 1)
			str += " m";
		else
			str += " f";
		
		for(Gene g : genes) {
			str += " " + g;
		}
		return str;
	}

	public String getName() {
		return name;
	}

	public int getSex() {
		return sex;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	

	
}
