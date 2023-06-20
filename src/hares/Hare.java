package hares;

import java.util.ArrayList;
import java.util.Arrays;
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
	 * hare has attributes and genetics
	 * also a name and an age
	 */
	// Hare counter

	
	// name List
	private static ArrayList<String> fName = new ArrayList<String>(Arrays.asList(
			"Angel",
			"Annie",
			"Arabella",
			"Aster",
			"Ava",
			"Bella",
			"Betsy",
			"Binky",
			"Bluebell",
			"Bonnie",
			"Bubbles",
			"Calla",
			"Carly",
			"Cherry",
			"Chrissy",
			"Cookie",
			"Cutie",
			"Daffodil",
			"Daisy",
			"Darcy",
			"Delilah",
			"Ellie",
			"Evie",
			"Florence",
			"Fluffy",
			"Fressia",
			"Freya",
			"Fuzzy",
			"Gem",
			"Georgie",
			"Gracie",
			"Harper",
			"Harriet",
			"Hazel",
			"Heather",
			"Holly",
			"Hope",
			"Iris",
			"Isla",
			"Ivy",
			"Jasmine",
			"Jessie",
			"Joy",
			"Juniper",
			"Karla",
			"Lavender",
			"Layla",
			"Lilac",
			"Lottie",
			"Luna",
			"Maple",
			"Marigold",
			"Milly",
			"Minnie",
			"Moon",
			"Moss",
			"Nina",
			"Opal",
			"Orchid",
			"Pansy",
			"Peaches",
			"Petal",
			"Pip",
			"Popcorn",
			"Poppet",
			"Poppy",
			"Queenie",
			"Quinn",
			"Rae",
			"Rain",
			"Raspberry",
			"River",
			"Rosie",
			"Sage",
			"Sally",
			"Salsa",
			"Saturn",
			"Sparkle",
			"Stella",
			"Star",
			"Strawberry",
			"Sugar",
			"Summer",
			"Sunflower",
			"Sweetie",
			"Tessie",
			"Tilly",
			"Toffee",
			"Treacle",
			"Trinket",
			"Tulip",
			"Twinkle",
			"Venus",
			"Violet",
			"Wanda",
			"Willow",
			"Winnie",
			"Zara",
			"Zinnia"
		));

	private static ArrayList<String> mName = new ArrayList<String>(Arrays.asList("Arlo",
			"Angus",
			"Arkwright",
			"Alonso",
			"Bernard",
			"Benji",
			"Boris",
			"Buster",
			"Brutus",
			"Barker",
			"Caper",
			"Chuck",
			"Crocus",
			"Carrot Cake",
			"Columbo",
			"Corbett",
			"Digger",
			"Duster",
			"Dexter",
			"Denzil",
			"Dinky",
			"Erasmus",
			"Eveready",
			"Eric",
			"Ernest",
			"Fred",
			"Fuzz",
			"Fighter",
			"Flop",
			"Fungus",
			"Giant",
			"George",
			"Gus",
			"Gorilla",
			"Horatio",
			"Hoppity",
			"Happy",
			"Hurley",
			"Ivan",
			"Inky",
			"Imp",
			"Iain",
			"Joe",
			"Jumpy",
			"Jack",
			"Jelly",
			"Kanga",
			"Kristoff",
			"Killer",
			"Lennon",
			"Lollop",
			"Loverboy",
			"Lex",
			"Monty",
			"Moose",
			"Merlin",
			"Maverick",
			"Morecambe",
			"Nelson",
			"Napoleon",
			"Noodle",
			"Neville",
			"Oscar",
			"Orville",
			"Ollie",
			"Otto",
			"Orson",
			"Oberon",
			"Percy",
			"Pinky",
			"Pando",
			"Peter",
			"Rolf",
			"Rankin",
			"Roger",
			"Roly",
			"Romer",
			"Rusty",
			"Rex",
			"Smudge",
			"Smoke",
			"Silver",
			"Sage",
			"Softy",
			"Toby",
			"Tufty",
			"Thumper",
			"Titch",
			"Tubby",
			"T-Rex",
			"Thomas",
			"Turnip",
			"Victor",
			"Vernon",
			"Wesley",
			"Wise",
			"Whisper",
			"Ziggy",
			"Zayn",
			"Zanzibar"
		));
	
	
	private String name;
	private int gender; // 0 = male, 1 = female
	

	private String notes = "";
	
	
	// genetics
	private Gene[] genes;
	
	public Hare(Gene[] genes, int gender) {
		this.genes = genes;
		this.gender = gender;
		initialize();
	}
	
	public Hare(Hare mother, Hare father) {

		inheritance(mother, father);
		
		Random rand = new Random();
		gender = rand.nextInt(2);
		
		initialize();
	}
	
	private void initialize(){
		
		
		// generate Name
		if(getGender() == 0) {
			System.out.println("Female Hare");
			Collections.shuffle(fName);
			name = fName.get(0);
			fName.remove(0);
		}
		else {
			Collections.shuffle(mName);
			System.out.println("Male Hare");
			name = mName.get(0);
			mName.remove(0);
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
		if( getGender() == 0)
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

	public int getGender() {
		return gender;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	

	
}
