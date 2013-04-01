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
		graphe.calculerEvaluation();
		Solution sBest = this.graphe.getSolution();
		String sBestString = sBest.toString();
		System.out.println("Solution initiale : "+ sBestString + " avec l'evaluation : " + graphe.getEval());
		
		// On change la solution courante pour le meilleur voisin
		this.typeVoisinage.bestSolVoisine(this.graphe);
			// Tant que la solution ainsi trouvee est meilleure, on reitere
		while (sBest.getEval() < this.graphe.getEval()){
			sBest = this.graphe.getSolution();
			sBestString = sBest.toString();
			System.out.println("On change pour la solution : "+ sBestString + " avec l'evaluation : " + sBest.getEval());
			this.typeVoisinage.bestSolVoisine(this.graphe);
		}
		System.out.println("Solution finale : "+ sBestString + " avec l'evaluation : " + sBest.getEval());
		return sBest;
	}
	
}