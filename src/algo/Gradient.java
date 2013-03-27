package algo;

import graphe.Graphe;
import graphe.ListeAdjacence;
import graphe.Solution;
import graphe.Voisinage;

public class Gradient {

	private Graphe graphe;
	private Voisinage typeVoisinage;

	public Gradient(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses){
		this.graphe = new Graphe(l, nbClasses);
		this.typeVoisinage = typeVoisinage;
	}
	
	public Solution run(){
		// On initialise sBest (avec la solution courante)
		Solution sBest = this.graphe.getSolution();
		// On change la solution courante pour le meilleur voisin
		this.typeVoisinage.bestSolVoisine(this.graphe);
			// Tant que la solution ainsi trouvée est meilleure, on réitère
		while (sBest.getEval() < this.graphe.getEval()){
			sBest = this.graphe.getSolution();
			this.typeVoisinage.bestSolVoisine(this.graphe);
		}
		return sBest;
	}
	
}