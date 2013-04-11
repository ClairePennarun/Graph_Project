package graphe.calcul;

import java.util.List;


public class VoisinageSwap implements Voisinage {

	public void bestSolVoisine(GraphePartition g) {
		int nbSommets = g.getNbSommets();
		// On cherche une solution voisine pour initaliser evalMin
		// On choisit alors de changer le sommet 0 et le sommet 1 a swaper
		int sommet1DeSolutionMin = 0;
		int sommet2DeSolutionMin = 1;
		while(g.getClasse(sommet2DeSolutionMin)==g.getClasse(sommet1DeSolutionMin)){
			sommet2DeSolutionMin ++;
		}
		// Puis on cherche de quelle classe il est deja, pour en choisir une autre
		int evalCourante;
		// Et on initialise evalMin
		int evalMax = g.evalSwap(sommet1DeSolutionMin, sommet2DeSolutionMin);

		// Test de tous les couples de voisins possibles
		for(int i=0; i<nbSommets; i++)
			for(int j = i+1; j<nbSommets; j++){
				if (g.getClasse(i) != g.getClasse(j)){
					if (((evalCourante = g.evalSwap(i, j)) > evalMax)){
						sommet1DeSolutionMin = i;
						sommet2DeSolutionMin = j;
						evalMax = evalCourante;
					}
				}
			}

		// Une fois le voisin optimal trouve, on swap le couple de sommets
		// Ainsi, la solution courante DEVIENT le voisin le plus optimal
		g.swap(sommet1DeSolutionMin, sommet2DeSolutionMin, evalMax);
	}
	
	public void bestSolVoisineTabou(GraphePartition g, List<Mouvement> tabTabou) {
		int nbSommets = g.getNbSommets();
		// On cherche une solution voisine pour initaliser evalMin
		// On choisit alors de changer le sommet 0 et le sommet 1 a swaper
		int sommet1DeSolutionMin = 0;
		int sommet2DeSolutionMin = 1;
		// Puis on cherche de quelle classe il est deja, pour en choisir une autre
		int evalCourante;
		// Et on initialise evalMin
		int evalMax = g.evalSwap(sommet1DeSolutionMin, sommet2DeSolutionMin);
		
		// Test de tous les couples de voisins possibles
		for(int i=0; i<nbSommets; i++)
			for(int j = i+1; j<nbSommets; j++){
				if (((evalCourante = g.evalSwap(i, j)) > evalMax) && (!tabTabou.contains(new Mouvement(i,-1,j)))){
						sommet1DeSolutionMin = i;
						sommet2DeSolutionMin = j;
						evalMax = evalCourante;
				}
		}
		
		// Une fois le voisin optimal trouve, on swap le couple de sommets
		// Ainsi, la solution courante DEVIENT le voisin le plus optimal
		g.swap(sommet1DeSolutionMin, sommet2DeSolutionMin, evalMax);
		tabTabou.add(new Mouvement(sommet1DeSolutionMin, sommet2DeSolutionMin, -1));
	}

}
