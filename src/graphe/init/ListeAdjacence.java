package graphe.init;


import java.util.ArrayList;


public class ListeAdjacence{
	
	ArrayList<SommetConstruction> list;
	int nbAretes;
	
	public ListeAdjacence(int n, int nbAretes){
		this.list = new ArrayList<SommetConstruction>(n);
		for (int i = 0; i < n; i++)	{
			list.add(new SommetConstruction());
		}
		this.nbAretes = nbAretes;
	}
	
	public void ajouterArete(int sommet1, int sommet2){
		list.get(sommet1-1).ajouterVoisin(sommet2-1);
		list.get(sommet2-1).ajouterVoisin(sommet1-1);
	}
	
	public ArrayList<SommetConstruction> getListe(){
		return this.list;
	}
	
	public int getNbArretes(){
		return this.nbAretes;
	}

	public int getTaille() {
		return this.list.size();
	}
	
}
