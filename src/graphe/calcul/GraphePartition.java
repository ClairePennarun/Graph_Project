package graphe.calcul;

import graphe.init.ListeAdjacence;
import graphe.init.Sommet;

import java.util.ArrayList;

public class GraphePartition {
	
	private SommetPartition[] sommets;
	private int[] classes; // represente le numero de classe pour chaque sommet
	private int nbClasses;
	private int evaluation;
	
	public GraphePartition(SommetPartition[] sommets, int[] classes, int nbClasses,  int eval){
		this.sommets = sommets;
		this.classes = classes;
		this.nbClasses = nbClasses;
		this.evaluation = eval;
	}
	
	public GraphePartition(ListeAdjacence li, int nbClasses){
		this.nbClasses = nbClasses;
		ArrayList<Sommet> l = li.getListe();
		this.sommets = new SommetPartition[l.size()];
		this.classes = new int[l.size()];
		int i = 0;
		for (Sommet s : l){
			this.sommets[i] = new SommetPartition(s, i);
			this.classes[i] = 0; // numero par defaut de la premiere classe
			i++;
		}
		this.classes[0] = 1;
	}
	
	// Pour construire notre solution initiale
	public void setSommetClasse(int sommet, int classe){
		this.classes[sommet] = classe;
	}
	
	// Pour calculer la premiere evaluation (a n'utiliser qu'une fois)
	public void calculerEvaluation(){
		int somme = 0;
		for (SommetPartition s : sommets){
			for (int iVoisin : s.getVoisins())
				if (this.classes[s.getNum()] != this.classes[iVoisin])
					somme = somme + 1; //1 etant le poids par defaut d'une arête
		}
		this.evaluation = somme/2; // on aura compte chaque arete interclasse 2 fois
	}
	
	// swap mettant a jour l'evaluation
	public void swap(int iSommet1, int iSommet2){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		this.deplacerSommet(iSommet1, classe2);
		this.deplacerSommet(iSommet2, classe1);
	}
	
	// swap plus rapide si on connait deja la nouvelle evaluation
	public void swap(int iSommet1, int iSommet2, int evalDejaCalculee){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		this.deplacerSommet(iSommet1, classe2, evalDejaCalculee);
		this.deplacerSommet(iSommet2, classe1, evalDejaCalculee);
	}
	
	// pickNdrop mettant a jour l'evaluation
	public void pickNdrop(int iSommet, int classe){
		this.deplacerSommet(iSommet, classe);
	}
	
	// pickNdrop plus rapide si on connait deja la nouvelle evaluation
	public void pickNdrop(int iSommet, int classe, int evalDejaCalculee){
		this.deplacerSommet(iSommet, classe, evalDejaCalculee);
	}
	
	// deplacerSommet mettant a jour l'evaluation
	private void deplacerSommet(int iSommet, int classeDestination){
		int classeDepart = this.classes[iSommet];
		int[] voisinage = this.sommets[iSommet].getVoisins();
		for (int iVoisin : voisinage){
			if (this.classes[iVoisin] == classeDestination) //l'arete n'est plus interclasse
				this.evaluation -= 1; // on enleve le poids par defaut
			else if(this.classes[iVoisin] == classeDepart) //l'arete devient interclasse
				this.evaluation += 1; // on ajoute le poids par defaut
		}
		
		this.classes[iSommet] = classeDestination; // on modifie la classe du sommet
	}
	
	// deplacerSommet plus rapide si on connait deja la nouvelle evaluation
	private void deplacerSommet(int iSommet, int classeDestination, int evalDejaCalculee){
		this.classes[iSommet] = classeDestination;
		this.evaluation = evalDejaCalculee;
	}

	// renvoit l'evaluation de la solution prevue si ce swap avait lieu
	public int evalSwap(int iSommet1, int iSommet2){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		int evalSol = this.evalDeplacerSommet(iSommet1, classe2, this.evaluation);
		return this.evalDeplacerSommet(iSommet2, classe1, evalSol);
	}
	
	// renvoit l'evaluation de la solution prevue si ce pickNdrop avait lieu
	public int evalPickNdrop(int iSommet, int classe){
		return this.evalDeplacerSommet(iSommet, classe, this.evaluation);
	}
	
	// renvoit l'evaluation de la solution prevue si ce deplacerSommet avait lieu
	private int evalDeplacerSommet(int iSommet, int classeDestination, int evalCourante){
		int evalSol = evalCourante;
		int classeDepart = this.classes[iSommet];
		int[] voisinage = this.sommets[iSommet].getVoisins();
		for (int iVoisin : voisinage){
			if (this.classes[iVoisin] == classeDestination) //l'arete n'est plus interclasse
				evalSol -= 1; // on enleve le poids par defaut
			else if(this.classes[iVoisin] == classeDepart) //l'arret devient interclasse
				evalSol += 1; // on ajoute le poids par defaut
		}
		return evalSol;
	}
	
	// Accesseurs
	public SommetPartition[] getSommets(){
		return this.sommets;
	}
	
	public Solution getSolution() {
		return new Solution(this.classes.clone(), this.nbClasses, this.evaluation);
	}
	
	public int getNbSommets() {
		return this.sommets.length;
	}
	
	public int[] getClasses(){
		return this.classes;
	}
	
	public int getClasse(int numeroSommet){
		return this.classes[numeroSommet];
	}
	
	public int getNbClasses(){
		return this.nbClasses;
	}
	
	public int getEval(){
		return this.evaluation;
	}
	
	public String toString(){
		String res = new String();
		for (int i = 0; i < this.sommets.length; i++){
			res += "sommet : " + (i+1) + " est dans la classe : " + this.classes[i] + "\n" ;
		}
		return res;
	}
}
