package algo;

import graphe.calcul.GraphePartition;
import graphe.init.ListeAdjacence;
import graphe.calcul.Solution;
import graphe.calcul.Voisinage;

public class Gradient implements Algorithme, Runnable {

	private GraphePartition graphe;
	private Voisinage typeVoisinage;
	
	private Solution solutionOpt;
	private int evalOpt;

	public Gradient(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses){
		this.graphe = new GraphePartition(l, nbClasses);
		this.typeVoisinage = typeVoisinage;
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		// On initialise sBest (avec la solution courante)
		Solution sOpt = this.graphe.setSolutionAleatoire();
		int evalOpt = sOpt.getEval();
		System.out.println("Solution initiale : "+ sOpt + ", Evaluation : " + graphe.getEval());
		
		// On change la solution courante pour le meilleur voisin
		this.typeVoisinage.bestSolVoisine(this.graphe);
		int evalCourante = this.graphe.getEval();
		
		// Tant que la solution ainsi trouvee est meilleure, on reitere
		while (evalCourante < evalOpt){
			sOpt = this.graphe.getSolution();
			evalOpt = evalCourante;
			this.typeVoisinage.bestSolVoisine(this.graphe);
			evalCourante = this.graphe.getEval();
		}

		System.out.println("Solution optimale : "+ sOpt + ", Evaluation : " + sOpt.getEval());
		long endTime = System.currentTimeMillis();
		System.out.println("Temps d'exécution : " + (endTime-startTime) + " ms.");
		
		this.solutionOpt = sOpt;
		this.evalOpt = evalOpt;
	}
	
	public Solution getBestSol(){
		return this.solutionOpt;
	}
	
	public int getBestEval(){
		return this.evalOpt;
	}
	
}