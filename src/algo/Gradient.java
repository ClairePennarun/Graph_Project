package algo;

import graphe.calcul.GraphePartition;
import graphe.init.ListeAdjacence;
import graphe.calcul.Solution;
import graphe.calcul.Voisinage;

public class Gradient {

	private GraphePartition graphe;
	private Voisinage typeVoisinage;

	public Gradient(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses){
		this.graphe = new GraphePartition(l, nbClasses);
		this.typeVoisinage = typeVoisinage;
	}
	
	public Solution run(){
		// On initialise sBest (avec la solution courante)
		Solution sBest = this.graphe.getSolution();
		// On change la solution courante pour le meilleur voisin
		this.typeVoisinage.bestSolVoisine(this.graphe);
			// Tant que la solution ainsi trouv�e est meilleure, on r�it�re
		while (sBest.getEval() < this.graphe.getEval()){
			sBest = this.graphe.getSolution();
			this.typeVoisinage.bestSolVoisine(this.graphe);
		}
		return sBest;
	}
	
}