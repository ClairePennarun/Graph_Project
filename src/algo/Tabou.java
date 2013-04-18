package algo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import graphe.calcul.GraphePartition;
import graphe.calcul.Mouvement;
import graphe.calcul.Solution;
import graphe.calcul.Voisinage;
import graphe.init.ListeAdjacence;

public class Tabou implements Algorithme, Runnable {

	private GraphePartition graphe;
	private Voisinage typeVoisinage;
	private List<Mouvement> tabTabou;
	private int nbTabou;
	
	private Solution solutionOpt;
	private int evalOpt;

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

	public void run(){		
		long startTime = System.currentTimeMillis();
		
		// on initialise sBest avec la solution courante
		Solution sOpt = this.graphe.setSolutionAleatoire();
		int evalOpt = sOpt.getEval();
		System.out.println("Solution initiale : "+ sOpt + " avec l'evaluation : " + graphe.getEval());

		for(int j = 1; j < puiss(this.graphe.getNbSommets(),2); j++){
			evalOpt = sOpt.getEval();
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
			if(eval!=0 && eval < evalOpt){
				sOpt = this.graphe.getSolution();
			}
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
