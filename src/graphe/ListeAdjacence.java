package graphe;

import java.util.ArrayList;


public class ListeAdjacence{
	
	ArrayList<Noeud> list;
	
	public ListeAdjacence(int n){
		this.list = new ArrayList<Noeud>(n);
		for (int i = 0; i < n; i++)
		{
			list.add(new Noeud());
		}
	}
	
	public void ajouterArete(int sommet1, int sommet2){
		list.get(sommet1-1).ajouterVoisin(sommet2-1);
		list.get(sommet2-1).ajouterVoisin(sommet1-1);
	}
	
	public ArrayList<Noeud> getListe(){
		return this.list;
	}
}
