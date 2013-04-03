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
	private List<Mouvement> tabou;
	private int nbTabou;
	
	public Tabou(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses, int nbTabou){
		this.graphe = new GraphePartition(l, nbClasses);
		this.typeVoisinage = typeVoisinage;
		this.tabou = new ArrayList<Mouvement>(nbTabou);
		this.nbTabou = nbTabou;
	}
	
	public Solution run(){
		// on initialise sBest avec la solution courante
		graphe.calculerEvaluation();
		Solution sBest = this.graphe.getSolution();
		String sBestString = sBest.toString();
		System.out.println("Solution initiale : "+ sBestString + " avec l'evaluation : " + graphe.getEval());
		
		for(int j = 1; j < Math.pow(this.graphe.getNbSommets(),2); j++){
			
			Solution sj = null;
			
			for(Mouvement m : this.tabou){ // gestion du tableau des mvts tabous
				if (m.getStamp() == nbTabou -1)
					this.tabou.remove(m);
				else m.incrStamp();
			}
			
			if(sj.getEval() < sBest.getEval())
				sBest = sj;
		}
		
		return null;
	}
}
