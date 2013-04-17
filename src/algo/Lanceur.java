package algo;

import graphe.calcul.Solution;
import graphe.calcul.Voisinage;
import graphe.init.ListeAdjacence;

public class Lanceur {

	private String algorithme;
	private ListeAdjacence liste;
	private Voisinage typeVoisinage;
	private int nbClasses;

	public Lanceur(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses){
		this.liste = l;
		this.typeVoisinage = typeVoisinage;
		this.nbClasses = nbClasses;
	}
	
	public void run(String algo, int nbTours){
		this.run(algo, nbTours, -1);
	}
	
	public void run(String algo, int nbTours, double param){
		Solution sOpt;
		Solution sCourante;
		int evalOpt;
		int evalCourante;
		int moyenneEval = 0;

		System.out.println("Début du lancement :");
		
		// Premier tour pour initialiser les solutions
		this.algorithme.run();
		sOpt = this.algorithme.getBestSol();
		evalOpt = this.algorithme.getBestEval();
		sCourante = sOpt;
		evalCourante = evalOpt;
		moyenneEval += evalCourante;

		// Début des lancements
		for(int i=1; i<nbTours; i++){
			this.algorithme.run();
			sCourante = this.algorithme.getBestSol();
			evalCourante = this.algorithme.getBestEval();
			moyenneEval += evalCourante;		
			if (evalCourante < evalOpt){
				sOpt = sCourante;
				evalOpt = evalCourante;
			}
			
		}
	}
	
	private Algorithme nouvelAlgo(String algo, double param){
		ListeAdjacence l = this.liste;
		Voisinage v = this.typeVoisinage;
		int nbClasses = this.nbClasses;
		if (algo.equals("RecuitSimule")){
			return new RecuitSimule(l, v, nbClasses, param);
		}
		else if (algo.equals("Tabou")){
			return new Tabou(l, v, nbClasses, (int) param);
		}
		else{
			return new Gradient(l, v, nbClasses);		
		}
	}
	
}
