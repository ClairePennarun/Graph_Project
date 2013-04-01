package algo;

import graphe.calcul.GraphePartition;
import graphe.init.ListeAdjacence;
import graphe.calcul.Solution;

public class Exhaustif {
	private GraphePartition graphe;
	private int nbClasses;

	public Exhaustif(ListeAdjacence l, int nbClasses){
		this.graphe = new GraphePartition(l, nbClasses);
		this.nbClasses = nbClasses;
	}
	
	public Solution run(){  
		GraphePartition g = this.graphe;
		g.calculerEvaluation();
		int nbSommets = g.getNbSommets();
		Solution sOpt = g.getSolution();
		String sOptString = sOpt.toString();
		System.out.println("La solution initiale est : " + sOptString + " avec l'evaluation : " + g.getEval());
		int evalOpt = g.getEval();
		
		boolean boucle = true;
		int sommetCourant;
		
		while (boucle){
			sommetCourant = 0;
			while (sommetCourant<nbSommets && // Tant que on est pas apres le dernier sommet
				   g.getClasse(sommetCourant) == this.nbClasses-1){ // et que la classe de ce sommet est la derniere classe
				g.pickNdrop(sommetCourant, 0); // On met le sommet dans la classe 0
				sommetCourant++; // Et on va au sommet suivant
			}
			if (sommetCourant<nbSommets){
				g.pickNdrop(sommetCourant, g.getClasse(sommetCourant)+1); // On met le sommet dans la classe suivante
				int eval = g.getEval();
				Solution sol = g.getSolution();
				System.out.println("la solution : " + sol.toString() + " donne l'evaluation : " + eval);
				if ((g.getEval() < evalOpt) && (g.getEval()!=0)){
					sOpt = sol;
					evalOpt = sOpt.getEval();
					sOptString = sOpt.toString();
				}
			}
			else //La precedente boucle a depasse le dernier sommet : on a tout teste
				boucle = false;
		}
		System.out.println("La solution optimale est : " + sOptString + " avec l'evaluation : " + evalOpt);
		return sOpt;
	}
}

// parcours en profondeur
