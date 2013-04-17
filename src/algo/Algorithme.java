package algo;

import graphe.calcul.Solution;

public interface Algorithme {
	
	public void run();
	public Solution getBestSol();	
	public int getBestEval();

}
