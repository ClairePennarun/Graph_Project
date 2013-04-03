package algo;

import graphe.calcul.GraphePartition;
import graphe.calcul.Solution;
import graphe.calcul.Voisinage;
import graphe.calcul.VoisinagePickNDrop;
import graphe.init.ListeAdjacence;

public class RecuitSimule {

	private GraphePartition graphe;
	private double temperatureInit;
	private Voisinage typeVoisinage;
	private int profondeur;

	public RecuitSimule(ListeAdjacence l, Voisinage v, int nbClasses, double temp, int profondeur){
		this.graphe = new GraphePartition(l, nbClasses);
		this.temperatureInit = temp;
		this.typeVoisinage = v;
		this.profondeur = profondeur;
	}

	public Solution solAleatoire(GraphePartition graphe){ 
		int nbSommet = graphe.getNbSommets();
		int sommet1 = (int) (Math.random() * nbSommet);
		int sommet2 = (int) (Math.random() * nbSommet);
		int classe1 = graphe.getClasse(sommet1);
		int classe2 = graphe.getClasse(sommet2);
				
		
		while( classe1 == classe2){
			sommet2 = (int) (Math.random() * nbSommet);
			classe2 = graphe.getClasse(sommet2);
		}
		if (typeVoisinage instanceof VoisinagePickNDrop)
			graphe.pickNdrop(sommet1, classe2);
		else
			graphe.swap(sommet1, sommet2);
		
		return graphe.getSolution();
	}


	public Solution run(){ 
		GraphePartition g = this.graphe;
		g.calculerEvaluation();

		Solution sCourante = g.getSolution();
		int evalCourante = g.getEval();
		double tempCourante = this.temperatureInit;
		double raison = (Math.random() *(0.9-0.7)) + 0.7 ; // doit etre compris entre 0.7 et 0.9
		double tempMin = 0.01;
		Solution sOpt = g.getSolution();
		int evalOpt = g.getEval();

		boolean changement = true; // indique si sOpt à changé
		int k = 0; // nombre de fois que la température descent sans que sOpt change
		int solSup = 0; // nombre de fois ou l'on a tiré une solution plus couteuse
		int solSupAcc = 0; // nombre de fois ou l'on a acepté une solution plus couteuse
		int tourWhile2 = 0; // nombre de parcours de la boucle while 2
		
		while((k >= 5) || (tempCourante <= tempMin)){ // || (nbTour > nbTourMax))
			
			while((solSup < 100 * profondeur) || (solSupAcc < 10 * profondeur) || (tourWhile2 < profondeur * profondeur)){ 
				
				GraphePartition voisinAleatoire = new GraphePartition (this.graphe.getSommets(), this.graphe.getClasses(), this.graphe.getNbClasses(), this.graphe.getEval());
				Solution solAleatoire = solAleatoire(voisinAleatoire);
				voisinAleatoire.calculerEvaluation();
				/* Il faut creer un nouveau graphePartition avec la nouvelle solution. 
				Si c'est bon, il remplace l'ancien */
				
				int evalAleatoire = solAleatoire.getEval();
				if (evalCourante > evalAleatoire){
					sCourante = solAleatoire;
					evalCourante = sCourante.getEval();
					if (evalOpt > evalCourante){
						evalOpt = evalCourante;
						sOpt = sCourante;
						changement = true;
					}
					else{
						changement =false;
						solSup++;
					}
				}
				else{
					// Metropolis
					double p = Math.random();
					double deltaF = Math.abs(evalCourante - evalAleatoire);
					double q = Math.exp(-deltaF/ tempCourante);
					if (q <= p){
						sCourante = solAleatoire;
						solSupAcc++;
					}	
				} 
				tourWhile2 ++;
			}
			
			//modification tempCourante
			tempCourante = tempCourante * raison ;
			if (changement == false)
				k+= 1;
			else
				k = 0;
			
		}
		return sOpt;
	}
	
}
