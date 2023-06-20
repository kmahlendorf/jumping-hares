package hares;

import java.util.Random;

/**
*
* @author Katrina Mahlendorf
*/

public class Gene {
	private boolean a;
	private boolean b;
	private boolean dom;
	
	public Gene(boolean a, boolean b, boolean dom) {
		this.dom = dom;
		this.a = a;
		this.b = b;
	}
	
	public Gene(Gene m, Gene f, boolean dom) {
		// one of the two genes from parents
		this.dom = dom;
		a = m.passOne();
		b = f.passOne();
		
		// TODO: add mutations
	}
	
	public boolean hasOne(){
		return a || b;
	}
	
	public boolean active() {
		if(dom)
			return a || b;
		return a && b;
	}
	
	public String toString() {
		String ga = (a)? "x" : "o";
		String gb = (b)? "x" : "o";
		
		return ga + gb;
	}
	
	
	private boolean passOne() {
		// randomly choose a or b
		
		Random rand = new Random();
		return (rand.nextBoolean())? a : b;

	}
}
