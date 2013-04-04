package algo;

import java.util.ArrayList;
import java.util.List;

import graphe.calcul.GraphePartition;
import graphe.calcul.Mouvement;
import graphe.calcul.Solution;
import graphe.calcul.Voisinage;
import graphe.init.ListeAdjacence;

public class Tabou implements Algorithme{

	private GraphePartition graphe;
	private Voisinage typeVoisinage;
	private List<Mouvement> tabTabou;
	private int nbTabou;
	
	public Tabou(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses, int nbTabou){
		this.graphe = new GraphePartition(l, nbClasses);
		this.typeVoisinage = typeVoisinage;
		this.tabTabou = new ArrayList<Mouvement>(nbTabou);
		this.nbTabou = nbTabou;
	}
	
	private static int puiss(int a, int k){
		int res = 1;
		for(int i=1; i <= k; i++){
			res = res * a;
		}
		return res;
	}
	
	public Solution run(){
		// on initialise sBest avec la solution courante
		graphe.calculerEvaluation();
		Solution sBest = this.graphe.getSolution();
		String sBestString = sBest.toString();
		System.out.println("Solution initiale : "+ sBestString + " avec l'evaluation : " + graphe.getEval());
		
		for(int j = 1; j < puiss(this.graphe.getNbSommets(),2); j++){

			this.typeVoisinage.bestSolVoisineTabou(this.graphe, this.tabTabou);
			// rajout du mouvement inverse dans le tabTabou dans bestSolVoisineTabou
			
			for(Mouvement m : this.tabTabou){ // gestion du tableau des mvts tabous
				if (m.getStamp() == nbTabou -1)
					this.tabTabou.remove(m);
				else m.incrStamp();
			}
			
			if(this.graphe.getEval() < sBest.getEval()){
				sBest = this.graphe.getSolution();
				sBestString = sBest.toString();
				System.out.println("On change pour la solution : "+ sBestString + " avec l'evaluation : " + sBest.getEval());
			}
		}
		System.out.println("Solution finale : "+ sBestString + " avec l'evaluation : " + sBest.getEval());
		return sBest;
	}
}
