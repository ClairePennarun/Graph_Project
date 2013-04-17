package graphe.calcul;

import java.util.List;



public interface Voisinage {

	public void bestSolVoisine(GraphePartition g);
	public void bestSolVoisineTabou(GraphePartition g,
			List<Mouvement> tabTabou);
	public Solution getSolutionVoisineAleatoire(GraphePartition g);
}
