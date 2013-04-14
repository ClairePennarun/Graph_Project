package algo;

import graphe.calcul.GraphePartition;
import graphe.init.ListeAdjacence;
import graphe.calcul.Solution;
import graphe.calcul.Voisinage;

public class Gradient implements Algorithme {

	private GraphePartition graphe;
	private Voisinage typeVoisinage;

	public Gradient(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses){
		this.graphe = new GraphePartition(l, nbClasses);
		this.typeVoisinage = typeVoisinage;
	}
	
	public Solution run(){
		
		long startTime = System.currentTimeMillis();
		// On initialise sBest (avec la solution courante)
		Solution sBest = this.graphe.setSolutionAleatoire();
		System.out.println("Solution initiale : "+ sBest + " avec l'evaluation : " + graphe.getEval());
		
		// On change la solution courante pour le meilleur voisin
		this.typeVoisinage.bestSolVoisine(this.graphe);
		System.out.println("best sol voisine : " + graphe.getEval());
		
		int tour = 0;
		// Tant que la solution ainsi trouvee est meilleure, on reitere
		while (this.graphe.getEval() < sBest.getEval()){
			System.out.println("tour " + tour + " grapheCourantEval : " + this.graphe.getEval() + " sBestEval : " + sBest.getEval());
			sBest = this.graphe.getSolution();
			System.out.println("On change pour la solution : "+ sBest+ " avec l'evaluation : " + sBest.getEval());
			this.typeVoisinage.bestSolVoisine(this.graphe);
			System.out.println("sBest : " + sBest.getEval() + " BestVoisinage : " + this.graphe.getEval());
			tour++;
			}
		
		System.out.println("Solution finale : "+ sBest + " avec l'evaluation : " + sBest.getEval());
		
		long endTime = System.currentTimeMillis();
		System.out.println("Temps d'exécution de l'algo de descente de gradient : " + (endTime-startTime) + " ms");
		return sBest;
	}
	
}