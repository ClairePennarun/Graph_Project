package algo;

import graphe.Graphe;
import graphe.ListeAdjacence;
import graphe.Solution;

public class Exhaustif {
	private Graphe graphe;
	private int nbClasses;

	public Exhaustif(ListeAdjacence l, int nbClasses){
		this.graphe = new Graphe(l, nbClasses);
		this.nbClasses = nbClasses;
	}
	
	public Solution run(){
		Graphe g = this.graphe;
		g.calculerEvaluation();
		int nbSommets = g.getNbSommets();
		
		Solution sOpt = g.getSolution();
		int evalOpt = g.getEval();
		boolean boucle = true;
		int sommetCourant;
		while (boucle){
			sommetCourant = 0;
			while (sommetCourant<nbSommets && // Tant que on est pas apres le dernier sommet
				   g.getClasse(sommetCourant) ==this.nbClasses-1){ // et que la classe de ce sommet est la derniere classe
				g.pickNdrop(sommetCourant, 0); // On met le sommet à la classe 0
				sommetCourant++; // Et on va au sommet suivant
			}
			if (sommetCourant<nbSommets){
				g.pickNdrop(sommetCourant, g.getClasse(sommetCourant)+1); // On met le sommet à la classe suivante
				if (g.getEval() > evalOpt){
					sOpt = g.getSolution();
					evalOpt = g.getEval();
				}
			}
			else //La precedente boucle a dépassé le dernier sommet : on a tout testé
				boucle = false;
		}
		return sOpt;
	}
}
