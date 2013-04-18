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

		long endTime = System.currentTimeMillis();
		int tempsTotal = (int) (endTime-startTime);
		int min = (tempsTotal/1000)/60;
		int sec = (tempsTotal - min*1000*60)/1000;
		int ms = tempsTotal - sec*1000 - min*1000*60;
		System.out.println("Solution optimale est : " + sOpt + ", Evaluation : " + evalOpt + "\n" + 
		"Temps total d'execution : " + min + " minutes " + sec + " secondes " + ms + " millisecondes");
		
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