package graphe.calcul;

public class VoisinagePickNDrop implements Voisinage {

	public void bestSolVoisine(GraphePartition g) {
		int nbSommets = g.getNbSommets();
		int nbClasses = g.getNbClasses();
		// On cherche une solution voisine pour initaliser evalMin
		// On choisi alors de pickNdroper le sommet 0
		int sommetDeSolutionMin = 0;
		// On cherche sa classe pour �tre sur de le pickNdroper dans une nouvelle
		int classeDeSolutionMin = 1;
		int classeCourante = g.getClasse(sommetDeSolutionMin);
		int evalCourante;
		while (classeDeSolutionMin == classeCourante){
			classeDeSolutionMin++;
			assert(classeDeSolutionMin != nbClasses);
		}
		// Et on initialise evalMin
		int evalMax = g.evalPickNdrop(sommetDeSolutionMin, classeDeSolutionMin);
		
		// Test de tout les voisins possibles
		for(int i=0; i<nbSommets; i++){
			classeCourante = g.getClasse(i);
			for(int j=0; j<nbClasses; j++){
				if ((classeCourante != j) && ((evalCourante = g.evalPickNdrop(i, j)) < evalMax)){
					sommetDeSolutionMin = i;
					classeDeSolutionMin = j;
					evalMax = evalCourante;
				}
			}
		}	
		// Une fois le voisin optimal trouv�, on change le sommet en question de classe.
		// Ainsi, la solution courante DEVIENT le voisin le plus optimal
		g.pickNdrop(sommetDeSolutionMin, classeDeSolutionMin, evalMax);
	}

}
