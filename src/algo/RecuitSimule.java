package algo;

import graphe.calcul.GraphePartition;
import graphe.calcul.Solution;
import graphe.calcul.Voisinage;
import graphe.init.ListeAdjacence;

public class RecuitSimule extends Algorithme implements Runnable {

	private GraphePartition graphe;
	private double temperatureInit;
	private Voisinage typeVoisinage;
	private int profondeur;

	public RecuitSimule(ListeAdjacence l, Voisinage v, int nbClasses, double temp){
		this.graphe = new GraphePartition(l, nbClasses);
		this.temperatureInit = temp;
		this.typeVoisinage = v;
		this.profondeur = this.graphe.getNbArretes();
	}

	public void run(){ 
		long startTime = System.currentTimeMillis();
		GraphePartition g = this.graphe;
		Solution sOpt = this.graphe.setSolutionAleatoire();
		int evalOpt = g.getEval();
		
		Solution sInitiale = sOpt;
		Solution sCourante = sOpt;
		
		int evalCourante = g.getEval();
		double tempCourante = this.temperatureInit;
		double raison = (Math.random() *(0.9-0.7)) + 0.7 ; // doit etre compris entre 0.7 et 0.9
		double tempMin = 0.01;
		int nbSolSup;
		int nbSolSupAcc;
		int nbTourWhile2;
		Solution solAleatoire;
		int evalAleatoire;

		boolean changement = true; // indique si sOpt à changé
		int k = 0; // nombre de fois où la température descend sans que sOpt change

		// on arrête lorsque :
		// on a descendu de températre 5 fois sans que la solution optimale change 
		// ou quand la tempéature devient trop basse
		while((k <= 5) && (tempCourante >= tempMin)){ // || (nbTour > nbTourMax))

			nbSolSup = 0; // nombre de fois ou l'on a tiré une solution plus couteuse
			nbSolSupAcc = 0; // nombre de fois ou l'on a accepté une solution plus couteuse
			nbTourWhile2 = 0; // nombre de parcours de la boucle while 2
			changement = false;

			// on arrête lorsque :
			// on a trouvé 100*n solutions moins bonne que l'actuelle
			// on a accepté 10*n solution de moins bonne qualité 
			// on a fait n² tours
			while((nbSolSup < 100 * profondeur) && (nbSolSupAcc < 10 * profondeur) && (nbTourWhile2 < profondeur * profondeur)){

				solAleatoire =	this.typeVoisinage.getSolutionVoisineAleatoire(this.graphe);
				evalAleatoire = solAleatoire.getEval();
				
				if (evalCourante > evalAleatoire){
					sCourante = solAleatoire;
					evalCourante = evalAleatoire;
					this.graphe.setSolution(sCourante);
					if (evalOpt > evalCourante){
						sOpt = sCourante;
						evalOpt = evalCourante;
						changement = true;
					}
				}
				else{
					if (evalCourante < evalAleatoire){
						nbSolSup++;
					}
					// Metropolis
					double p = Math.random();
					double deltaF = Math.abs(evalCourante - evalAleatoire);
					double q = Math.exp(-deltaF/ tempCourante);
					if (q <= p){
						sCourante = solAleatoire;
						evalCourante = evalAleatoire;
						this.graphe.setSolution(sCourante);
						nbSolSupAcc++;
					}
				}
				
				nbTourWhile2++;
			}

			// modification tempCourante
			tempCourante = tempCourante * raison ;
			if (changement)
				k = 0;
			else
				k++;

		}
		long endTime = System.currentTimeMillis();

		this.temps = (int) (endTime-startTime);
		super.afficherResultat(sInitiale, sOpt, endTime-startTime);
		
		this.solutionOpt = sOpt;
		this.evalOpt = evalOpt;
	}
}