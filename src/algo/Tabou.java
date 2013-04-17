package algo;

import java.util.ArrayList;
import java.util.Iterator;
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
		long startTime = System.currentTimeMillis();
		
		// on initialise sBest avec la solution courante
		Solution sBest = this.graphe.setSolutionAleatoire();
		int bestEval = sBest.getEval();
		System.out.println("Solution initiale : "+ sBest + " avec l'evaluation : " + graphe.getEval());

		for(int j = 1; j < puiss(this.graphe.getNbSommets(),2); j++){
			bestEval = sBest.getEval();
			this.typeVoisinage.bestSolVoisineTabou(this.graphe, this.tabTabou);
			// rajout du mouvement inverse dans le tabTabou dans bestSolVoisineTabou

			Iterator<Mouvement> it = tabTabou.iterator(); // on gère le tableau
			while(it.hasNext()){
				Mouvement m = it.next();
				if (m.getStamp() == nbTabou -1)
					it.remove();
				else m.incrStamp();
			}


			int eval = this.graphe.getEval(); // on regarde si on change de meilleure solution ou non
			if(eval!=0 && eval < bestEval){
				sBest = this.graphe.getSolution();
				System.out.println("On change pour la solution : "+ sBest + " avec l'evaluation : " + eval);
			}
		}
		System.out.println("Solution finale : "+ sBest + " avec l'evaluation : " + bestEval);
		long endTime = System.currentTimeMillis();
		System.out.println("Temps d'exécution de l'algo Tabou : " + (endTime-startTime) + "ms = " + 
		(endTime-startTime)/1000 + "s = " + ((endTime-startTime)/1000)/60 + "min");
		return sBest;
	}
}
