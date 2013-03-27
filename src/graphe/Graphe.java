package graphe;

import java.util.ArrayList;


public class Graphe {
	
	private Sommet[] sommets;
	private int[] classes; // repr�sente le numero de classe pour chaque sommet
	private int nbClasses;
	private int evaluation;
	
	public Graphe(ListeAdjacence li, int nbClasses){
		this.nbClasses = nbClasses;
		ArrayList<Noeud> l = li.getListe();
		this.sommets = new Sommet[l.size()];
		this.classes = new int[l.size()];
		int i = 0;
		for (Noeud s : l){
			this.sommets[i] = new Sommet(s, i);
			this.classes[i] = 0; // num�ro par defaut de la premiere classe (donc 1)
			i++;
		}
	}
	
	// Pour construire notre solution initiale
	public void setSommetClasse(int sommet, int classe){
		this.classes[sommet] = classe;
	}
	
	// Pour calculer la premi�re �valuation (� n'utiliser qu'une fois)
	public void calculerEvaluation(){
		int somme = 0;
		for (Sommet s : sommets){
			for (int iVoisin : s.getVoisins())
				if (this.classes[s.getNum()] != this.classes[iVoisin])
					somme = somme + 1; //1 �tant le poid par d�faut d'une arrête
		}
		this.evaluation = somme/2; // on aura compt� chaque arr�te interclasse 2 fois
	}
	
	// swap mettant � jour l'�valuation
	public void swap(int iSommet1, int iSommet2){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		this.deplacerSommet(iSommet1, classe2);
		this.deplacerSommet(iSommet2, classe1);
	}
	
	// swap plus rapide si on connait d�j� la nouvelle �valuation
	public void swap(int iSommet1, int iSommet2, int evalDejaCalculee){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		this.deplacerSommet(iSommet1, classe2, evalDejaCalculee);
		this.deplacerSommet(iSommet2, classe1, evalDejaCalculee);
	}
	
	// pickNdrop mettant � jour l'�valuation
	public void pickNdrop(int iSommet, int classe){
		this.deplacerSommet(iSommet, classe);
	}
	
	// pickNdrop plus rapide si on connait d�j� la nouvelle �valuation
	public void pickNdrop(int iSommet, int classe, int evalDejaCalculee){
		this.deplacerSommet(iSommet, classe, evalDejaCalculee);
	}
	
	// deplacerSommet mettant � jour l'�valuation
	private void deplacerSommet(int iSommet, int classeDestination){
		int classeDepart = this.classes[iSommet];
		int[] voisinage = this.sommets[iSommet].getVoisins();
		for (int iVoisin : voisinage){
			if (this.classes[iVoisin] == classeDestination) //l'arrete n'est plus interclasse
				this.evaluation -= 1; // on enleve le poid par defaut
			else if(this.classes[iVoisin] == classeDepart) //l'arret devient interclasse
				this.evaluation += 1; // on ajoute le poid par defaut
		}
		this.classes[iSommet] = classeDestination; // on modifie la classe du sommet
	}
	
	// deplacerSommet plus rapide si on connait d�j� la nouvelle �valuation
	private void deplacerSommet(int iSommet, int classeDestination, int evalDejaCalculee){
		this.classes[iSommet] = classeDestination;
		this.evaluation = evalDejaCalculee;
	}

	// renvoit l'�valuation de la solution pr�vue si ce swap avait lieu
	public int evalSwap(int iSommet1, int iSommet2){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		int evalSol = this.evalDeplacerSommet(iSommet1, classe2, this.evaluation);
		return this.evalDeplacerSommet(iSommet2, classe1, evalSol);
	}
	
	// renvoit l'�valuation de la solution pr�vue si ce pickNdrop avait lieu
	public int evalPickNdrop(int iSommet, int classe){
		return this.evalDeplacerSommet(iSommet, classe, this.evaluation);
	}
	
	// renvoit l'�valuation de la solution pr�vue si ce deplacerSommet avait lieu
	private int evalDeplacerSommet(int iSommet, int classeDestination, int evalCourante){
		int evalSol = evalCourante;
		int classeDepart = this.classes[iSommet];
		int[] voisinage = this.sommets[iSommet].getVoisins();
		for (int iVoisin : voisinage){
			if (this.classes[iVoisin] == classeDestination) //l'arrete n'est plus interclasse
				evalSol -= 1; // on enleve le poid par defaut
			else if(this.classes[iVoisin] == classeDepart) //l'arret devient interclasse
				evalSol += 1; // on ajoute le poid par defaut
		}
		return evalSol;
	}
	
	// Accesseurs
	public Solution getSolution() {
		return new Solution(this.classes, this.nbClasses, this.evaluation);
	}
	
	public int getNbSommets() {
		return this.classes.length;
	}
	
	public int getClasse(int numeroSommet){
		return this.classes[numeroSommet];
	}
	
	public int getNbClasses(){
		return this.classes.length;
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
