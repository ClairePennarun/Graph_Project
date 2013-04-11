package graphe.calcul;

import java.util.List;

public class VoisinagePickNDrop implements Voisinage {

	public void bestSolVoisine(GraphePartition g) {
		int nbSommets = g.getNbSommets();
		int nbClasses = g.getNbClasses();
		// On cherche une solution voisine pour initaliser evalMin
		// On choisit alors de pickNdroper le sommet 0
		int sommetDeSolutionMin = 0;
		// On cherche sa classe pour etre sur de le pickNdroper dans une nouvelle
		int classeCourante = g.getClasse(sommetDeSolutionMin);
		int classeDeSolutionMin = 0;		
		int evalCourante;
		if (classeDeSolutionMin == classeCourante){
			classeDeSolutionMin++;
		}
		// Et on initialise evalMin
		int evalMax = g.evalPickNdrop(sommetDeSolutionMin, classeDeSolutionMin);

		// Test de tous les voisins possibles
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
		// Une fois le voisin optimal trouve, on change le sommet en question de classe.
		// Ainsi, la solution courante DEVIENT le voisin le plus optimal
		g.pickNdrop(sommetDeSolutionMin, classeDeSolutionMin, evalMax);
	}
	public void bestSolVoisineTabou(GraphePartition g, List<Mouvement> tabTabou) {
		int nbSommets = g.getNbSommets();
		int nbClasses = g.getNbClasses();
		// On cherche une solution voisine pour initaliser evalMin
		// On choisit alors de pickNdroper le sommet 0
		int sommetDeSolutionMin = 0;
		// On cherche sa classe pour etre sur de le pickNdroper dans une nouvelle
		int classeDeSolutionMin = 1;
		int classeCourante = g.getClasse(sommetDeSolutionMin);
		int evalCourante;
		while (classeDeSolutionMin == classeCourante){
			classeDeSolutionMin++;
			assert(classeDeSolutionMin != nbClasses);
		}
		// Et on initialise evalMin
		int evalMin = g.evalPickNdrop(sommetDeSolutionMin, classeDeSolutionMin);

		// Test de tous les voisins possibles
		for(int i=0; i<nbSommets; i++){
			classeCourante = g.getClasse(i);
			for(int j=0; j<nbClasses; j++){
				if ((!tabTabou.contains(new Mouvement(i,-1,j))) &&
						(classeCourante != j) &&
						((evalCourante = g.evalPickNdrop(i, j)) < evalMin)){
					sommetDeSolutionMin = i;
					classeDeSolutionMin = j;
					evalMin = evalCourante;
				}
			}
		}
		// Une fois le voisin optimal trouve, on change le sommet en question de classe.
		// Ainsi, la solution courante DEVIENT le voisin le plus optimal
		g.pickNdrop(sommetDeSolutionMin, classeDeSolutionMin, evalMin);
		tabTabou.add(new Mouvement(sommetDeSolutionMin, -1, classeDeSolutionMin));
	}
}
