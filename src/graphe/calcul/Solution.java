package graphe.calcul;

import java.util.Arrays;

public class Solution {

	private int[] classes;
	private int nbClasses;
	private int evaluation;

	public Solution(int[] classes, int nbClasses, int evaluation) {
		this.classes = classes;
		this.nbClasses = nbClasses;
		this.evaluation = evaluation;
	}
	
	public int getEval(){
		return this.evaluation;
	}
	
	public int[] getSol(){
		return this.classes.clone();
	}
	
	public int getNbClasses(){
		return this.nbClasses;
	}
	
	public String toString(){
		return Arrays.toString(classes);
	}
	
}
