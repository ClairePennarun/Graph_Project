package algo;

import graphe.calcul.GraphePartition;
import graphe.init.ListeAdjacence;
import graphe.calcul.Solution;
import graphe.calcul.Voisinage;

public class Gradient extends Algorithme implements Runnable {

	private GraphePartition graphe;
	private Voisinage typeVoisinage;

	public Gradient(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses){
		this.graphe = new GraphePartition(l, nbClasses);
		this.typeVoisinage = typeVoisinage;
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		// On initialise sBest (avec la solution courante)
		Solution sOpt = this.graphe.setSolutionAleatoire();
		int evalOpt = sOpt.getEval();
		
		Solution sInitiale = sOpt;
		
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
		this.temps = (int) (endTime-startTime);
		super.afficherResultat(sInitiale, sOpt, endTime-startTime);
		
		this.solutionOpt = sOpt;
		this.evalOpt = evalOpt;
	}
}