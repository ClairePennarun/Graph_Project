package algo;

import graphe.calcul.GraphePartition;
import graphe.init.ListeAdjacence;
import graphe.calcul.Solution;

public class Exhaustif implements Algorithme, Runnable {
	private GraphePartition graphe;
	private int nbClasses;
	
	private Solution solutionOpt;
	private int evalOpt;

	public Exhaustif(ListeAdjacence l, int nbClasses){
		this.graphe = new GraphePartition(l, nbClasses);
		this.nbClasses = nbClasses;
	}
	
	public void run(){
		
		long startTime = System.currentTimeMillis();
		
		GraphePartition g = this.graphe;
		//g.calculerEvaluation();
		int nbSommets = g.getNbSommets();
		Solution sOpt = g.setSolutionExhaustif();
		System.out.println("La solution initiale est : " + sOpt + " avec l'evaluation : " + g.getEval());
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
				int evalG = g.getEval();
				Solution sol = g.getSolution();
				System.out.println("la solution : " + sol.toString() + " donne l'evaluation : " + evalG);				
				if ((evalG < evalOpt) && (evalG!=0)){
					sOpt = sol;
					evalOpt = sOpt.getEval();
				}
			}
			else //La precedente boucle a depasse le dernier sommet : on a tout teste
				boucle = false;
		}
		
		long endTime = System.currentTimeMillis();
		int tempsTotal = (int) (endTime-startTime);
		int min = (tempsTotal/1000)/60;
		int sec = (tempsTotal - min*1000*60)/1000;
		int ms = tempsTotal - sec*1000 - min*1000*60;
		System.out.println("Solution optimale est : " + sOpt + ", Evaluation : " + evalOpt + "\n" + 
		"Temps total d'execution : " + min + " minutes " + sec + " secondes " + ms + " millisecondes");
		
		this.solutionOpt = sOpt;
		this.evalOpt = evalOpt;
	}
	
	public Solution getBestSol(){
		return this.solutionOpt;
	}
	
	public int getBestEval(){
		return this.evalOpt;
	}
}