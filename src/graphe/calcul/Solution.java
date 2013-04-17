package graphe.calcul;

import java.util.Arrays;

public class Solution {

	private int[] classes;
	private int[] tailleClasses;
	private int nbClasses;
	private int evaluation;
	private int evalSansPenalite;

	public Solution(int[] classes, int[] tailleClasses, int nbClasses, int evalSansPenalite, int evaluation) {
		this.classes = classes;
		this.nbClasses = nbClasses;
		this.tailleClasses = tailleClasses;
		this.evalSansPenalite = evalSansPenalite;
		this.evaluation = evaluation;
	}

	public int getEval(){
		return this.evaluation;
	}

	public int getEvalSansPenalite(){
		return this.evalSansPenalite;
	}
	
	public int[] getClasses(){
		return this.classes;
	}

	public int[] getTailleClasses(){
		return this.tailleClasses;
	}
	
	public int getNbClasses(){
		return this.nbClasses;
	}

	public String toString(){
		return Arrays.toString(classes);
	}

}
