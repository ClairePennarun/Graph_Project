package graphe.calcul;

import java.util.List;



public interface Voisinage {

	public void bestSolVoisine(GraphePartition g);
	public void bestSolVoisineTabou(GraphePartition graphe,
			List<Mouvement> tabTabou);
}
