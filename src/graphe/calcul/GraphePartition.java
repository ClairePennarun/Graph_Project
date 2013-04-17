package graphe.calcul;

import graphe.init.ListeAdjacence;
import graphe.init.SommetConstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class GraphePartition {

	private Sommet[] sommets;
	private int[] classes; // represente le numero de classe pour chaque sommet
	private int[] tailleClasses;
	private int nbClasses;
	private int evalSansPenaliteClasse;
	private int nbArretes;
	private int nbSommets;
	private double pourcentEcartMoyen; // On le conserve car il ne changera jamais
	// si le voisinage est le swap

	public GraphePartition(ListeAdjacence li, int nbClasses){
		this.nbClasses = nbClasses;

		ArrayList<SommetConstruction> l = li.getListe();
		this.nbSommets = l.size();
		this.nbArretes = li.getNbArretes();

		this.sommets = new Sommet[this.nbSommets];
		this.classes = new int[this.nbSommets];
		this.tailleClasses = new int[nbClasses];
		int i = 0;
		for (SommetConstruction s : l){
			this.sommets[i] = new Sommet(s, i);
			this.classes[i] = 0; // numero par defaut de la premiere classe
			i++;
		}
	}

	//----------------------- SOLUTION INITIALE TOUT à 0 (public) ---------------------------

	public Solution setSolutionExhaustif(){
		int nbSommet = this.nbSommets;
		for (int i = 0; i<nbSommet; i++){
			this.setSommetClasse(i, 0);
		}
		this.calculerEvaluation();
		return this.getSolution();
	}

	//----------------------- SOLUTION ALEATOIRE (public) ---------------------------

	public Solution setSolutionAleatoire(){
		int nbSom = this.nbSommets;
		int nbClasse = this.nbClasses;
		int randomClasse;
		for(int i = 0; i<nbSom; i++){
			randomClasse = (int) (Math.random()*nbClasse);
			this.setSommetClasse(i, randomClasse);
		}
		this.calculerEvaluation();
		return this.getSolution();
	}

	public Solution setSolutionAleatoire(int ecartMax){
		int nbSom = this.nbSommets;
		int nbClasse = this.nbClasses;
		int tailleMin = nbSom/nbClasse-ecartMax;
		int classeMin = tailleMin;
		int classeMax = tailleMin+ecartMax;
		int indiceClasse;
		int nouvelleVal;
		int tmp;

		List<Integer> classeExtensibles = new ArrayList<Integer>();
		List<Integer> classePleines = new ArrayList<Integer>();

		for(int i=0; i<nbClasses; i++){
			classeExtensibles.add(tailleMin);
			nbSom -= tailleMin;
		}
		while(nbSom > 0){
			indiceClasse = rand(0, classeExtensibles.size());
			nouvelleVal = classeExtensibles.get(indiceClasse) + 1;

			if (nouvelleVal == classeMax){
				classeExtensibles.remove(indiceClasse);
				classePleines.add(nouvelleVal);
			}
			else{
				classeExtensibles.set(indiceClasse, nouvelleVal);
			}
			nbSom--;

			Collections.sort(classeExtensibles);
			if (classeExtensibles.get(0) > classeMin){
				classeMin = classeExtensibles.get(0);
				classeMax = classeMin + ecartMax;
				tmp = classePleines.size();
				for(int i=0; i<tmp; i++){
					nouvelleVal = classePleines.get(i);
					classePleines.remove(i);
					classeExtensibles.add(nouvelleVal);
				}		
			}
		}

		nbSom = this.nbSommets;
		for(int i = 0; i<nbSom; i++){
			indiceClasse = rand(0, classeExtensibles.size());

			nouvelleVal = classeExtensibles.get(indiceClasse) - 1;
			if (nouvelleVal <= 0)
				classeExtensibles.remove(indiceClasse);
			else
				classeExtensibles.set(indiceClasse, nouvelleVal);

			this.setSommetClasse(i, indiceClasse);
		}
		this.calculerEvaluation();
		return this.getSolution();
	}

	// Renvoit un entier aleatoire dans [a, b[
	private int rand(int a, int b){
		return (int) (Math.random()*(b-a)+a);
	}

	//----------------------- SET SOLUTION ---------------------------
	
	public void setSolution(Solution s){
		this.classes = s.getClasses();
		this.tailleClasses = s.getTailleClasses();
		this.evalSansPenaliteClasse = s.getEvalSansPenalite();
	}


	//----------------------- CALCULS INITIAUX DE L'EVAL (private) ---------------------------

	// Pour calculer la premiere evaluation (a n'utiliser qu'une fois)
	private void calculerEvaluation(){
		int somme = 0;
		int classeCourante;
		for(int i=0; i<this.nbClasses; i++)
			this.tailleClasses[i] = 0;
		for (Sommet s : sommets){
			classeCourante = this.classes[s.getNum()];
			this.tailleClasses[classeCourante]++;
			for (int iVoisin : s.getVoisins())
				if (classeCourante != this.classes[iVoisin])
					somme = somme + 1; //1 etant le poids par defaut d'une arÃªte
		}
		this.evalSansPenaliteClasse = (somme/2); // On aura compte chaque arrete interclasse 2 fois
		this.pourcentEcartMoyen = this.pourcentageEcartMoyen();
	}

	// Donne l'écart moyen de la taille des classes, en pourcentage
	private double pourcentageEcartMoyen(){
		double ecartMoyen = 0;
		int n = 0;
		for(int i = 0; i<this.tailleClasses.length; i++)
			for(int j = i+1; j<this.tailleClasses.length; j++){
				ecartMoyen += Math.abs(this.tailleClasses[i]-this.tailleClasses[j]);
				n++;
			}
		return (ecartMoyen / n) / this.nbSommets;
	}

	// Donne une prédiction du prochain ecart moyen (après ce déplacement)
	private double predictionEcartMoyen(int iSommet, int classeDestination){ 
		int classeSrc = getClasse(iSommet);
		this.tailleClasses[classeSrc] --;
		this.tailleClasses[classeDestination] ++;
		double res = this.pourcentageEcartMoyen();
		this.tailleClasses[classeSrc] ++;
		this.tailleClasses[classeDestination] --;
		return res;
	}

	//----------------------- CALCULS FINAL DE L'EVAL (public) ---------------------------

	public int getEval(){
		double ecartMoyen = this.pourcentageEcartMoyen();
		return this.calculEval(this.evalSansPenaliteClasse, ecartMoyen);
	}

	private int calculEval(int evalSansPenalite, double ecartMoyen){
		return (int) ((100+ evalSansPenalite) * (1+ecartMoyen*2));
		// Si l'écart moyen est de 12%, on ajoutera alors 24% à l'évaluation;
	}

	//--------------------------------- SWAP (public) -------------------------------

	// swap mettant a jour l'evaluation
	public void swap(int iSommet1, int iSommet2){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		this.deplacerSommet(iSommet1, classe2);
		this.deplacerSommet(iSommet2, classe1);
	}

	// renvoit l'evaluation de la solution prevue si ce swap avait lieu
	public int evalSwap(int iSommet1, int iSommet2){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		int evalSol = this.evalDeplacerSommet(iSommet1, classe1, classe2, this.evalSansPenaliteClasse);
		evalSol = this.evalDeplacerSommet(iSommet2, classe2, classe1, evalSol);
		double ecartMoyen = this.pourcentEcartMoyen; // On présuppose que tout les voisinage
		// on été des swap et que cet écart
		// n'a jamais changé.
		return calculEval(evalSol, ecartMoyen);
	}
	
	public Solution getSolSwap(int iSommet1, int iSommet2){
		int classe1 = this.classes[iSommet1];
		int classe2 = this.classes[iSommet2];
		this.deplacerSommet(iSommet1, classe2);
		this.deplacerSommet(iSommet2, classe1);
		Solution s = this.getSolution();
		this.deplacerSommet(iSommet1, classe1);
		this.deplacerSommet(iSommet2, classe2);
		return s;
	}

	//--------------------------- PICKNDROP (public) ---------------------------

	// pickNdrop mettant a jour l'evaluation
	public void pickNdrop(int iSommet, int classeDestination){
		this.deplacerSommet(iSommet, classeDestination);
	}

	// renvoit l'evaluation de la solution prevue si ce pickNdrop avait lieu
	public int evalPickNdrop(int iSommet, int classeDestination){
		int classeDepart = getClasses()[iSommet];
		int evalSol = this.evalDeplacerSommet(iSommet, classeDepart, 
											  classeDestination, this.evalSansPenaliteClasse);
		double ecartMoyen = this.predictionEcartMoyen(iSommet, classeDestination);
		return calculEval(evalSol, ecartMoyen);
	}
	
	public Solution getSolPickNdrop(int iSommet, int classeDestination){
		int classeDepart = this.classes[iSommet];
		this.deplacerSommet(iSommet, classeDestination);
		Solution s = this.getSolution();
		this.deplacerSommet(iSommet, classeDepart);
		return s;
	}

	//------------------------- DEPLACER SOMMET (privé) ---------------------------

	// deplacerSommet mettant a jour l'evaluation
	private void deplacerSommet(int iSommet, int classeDestination){
		this.tailleClasses[this.classes[iSommet]]--;
		this.tailleClasses[classeDestination]++;
		int classeDepart = getClasse(iSommet);
		this.setSommetClasse(iSommet, classeDestination);
		this.evalSansPenaliteClasse = this.evalDeplacerSommet(iSommet, classeDepart, classeDestination, this.evalSansPenaliteClasse);
		}

	// renvoit l'evaluation de la solution prevue si ce deplacerSommet avait lieu
	private int evalDeplacerSommet(int iSommet, int classeDepart, int classeDestination, int evalCourante){
		int evalSol = evalCourante;
		int[] voisinage = this.sommets[iSommet].getVoisins();
		for (int iVoisin : voisinage){
			if (this.classes[iVoisin] == classeDestination) // l'arete n'est plus interclasse
				evalSol -= 1; // on enleve le poids par defaut
			else if(this.classes[iVoisin] == classeDepart) // l'arret devient interclasse
				evalSol += 1; // on ajoute le poids par defaut
		}
		return evalSol;
	}

	// Pour construire notre solution initiale
	private void setSommetClasse(int sommet, int classe){
		this.classes[sommet] = classe;
	}

	//--------------------------- ACCESSEURS (public)----------------------------------

	public Sommet[] getSommets(){
		return this.sommets;
	}

	public Solution getSolution() {
		return new Solution(this.classes.clone(), this.tailleClasses.clone(),
							this.nbClasses, this.evalSansPenaliteClasse, this.getEval());
	}

	public int getNbSommets() {
		return this.nbSommets;
	}

	public int[] getClasses(){
		return this.classes;
	}

	public int getNbArretes(){
		return this.nbArretes;
	}

	public int getClasse(int numeroSommet){
		return this.classes[numeroSommet];
	}

	public int getNbClasses(){
		return this.nbClasses;
	}

	public int getEvalSansPenalité(){
		return this.evalSansPenaliteClasse;
	}

	public String toString(){
		String res = new String();
		for (int i = 0; i < this.sommets.length; i++){
			res += "sommet : " + (i+1) + " est dans la classe : " + this.classes[i] + "\n" ;
		}
		return res;
	}
}