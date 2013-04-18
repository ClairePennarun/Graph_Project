package algo;

import java.util.ArrayList;
import java.util.List;

import graphe.calcul.Solution;
import graphe.calcul.Voisinage;
import graphe.init.ListeAdjacence;

public class Lanceur {

	private ListeAdjacence liste;
	private Voisinage typeVoisinage;
	private int nbClasses;

	public Lanceur(ListeAdjacence l, Voisinage typeVoisinage, int nbClasses){
		this.liste = l;
		this.typeVoisinage = typeVoisinage;
		this.nbClasses = nbClasses;
	}
	
	public void run(String algo, int nbTours){
		this.run(algo, -1, nbTours);
	}
	
	public void run(String nomAlgo, double param, int nbTours){
		Solution sOpt;
		Solution sCourante;
		int evalOpt;
		int evalCourante;
		double evalMoyenne = 0;
		double tempsMoyen = 0;
		List<Algorithme> algoList = new ArrayList<Algorithme>();
		List<Thread> threadList = new ArrayList<Thread>();
		Algorithme algo;

		System.out.println("Début des " + nbTours + " lancements de l'algorithme " + nomAlgo + " :");
		System.out.println("-----------------------------------------------------------------------");

		long startTime = System.currentTimeMillis();
		for(int i=0; i<nbTours; i++){
			algo = this.nouvelAlgo(nomAlgo, param);
			algoList.add(algo);
			Thread t = new Thread((Runnable) algo);
			threadList.add(t);
		
			t.start();
		}
		for(Thread t : threadList)
			while(t.isAlive())
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}
		long endTime = System.currentTimeMillis();
		
		algo = algoList.get(0);
		sOpt = algo.getBestSol();
		evalOpt = algo.getBestEval();
		sCourante = sOpt;
		evalCourante = evalOpt;
		evalMoyenne += evalCourante;

		// Lecture des résultats
		for(int i=1; i<nbTours; i++){
			algo = algoList.get(i);
			sCourante = algo.getBestSol();
			evalCourante = algo.getBestEval();
			evalMoyenne += evalCourante;
			tempsMoyen += algo.getTemps();
			if (evalCourante < evalOpt){
				sOpt = sCourante;
				evalOpt = evalCourante;
			}
		}
		
		evalMoyenne = evalMoyenne / nbTours;
		tempsMoyen = tempsMoyen / nbTours;
		
		System.out.println("-----------------------------------------------------------------------");
		int tempsTotal = (int) (endTime-startTime);
		System.out.println("Temps total d'execution : " + algo.convertirTemps(tempsTotal));
		System.out.println("Temps moyen d'execution : " + algo.convertirTemps((int) tempsMoyen));
		System.out.println("Evaluation moyenne " + evalMoyenne);
		System.out.println("Meilleure solution finale : " + sOpt);
		System.out.println("Meilleure evaluation finale : " + evalOpt);
		
		
	}
	
	private Algorithme nouvelAlgo(String algo, double param){
		ListeAdjacence l = this.liste;
		Voisinage v = this.typeVoisinage;
		int nbClasses = this.nbClasses;
		if (algo.equals("recuit")){
			return new RecuitSimule(l, v, nbClasses, param);
		}
		else if (algo.equals("tabou")){
			return new Tabou(l, v, nbClasses, (int) param);
		}
		else{
			return new Gradient(l, v, nbClasses);		
		}
	}
	
}
