package graphe;

import java.util.ArrayList;


public class ListeAdjacence{
	
	ArrayList<Noeud> list;
	
	public ListeAdjacence(int n){
		this.list = new ArrayList<Noeud>();
	}
	
	public void ajouterArete(int sommet1, int sommet2){
		list.get(sommet1).ajouterVoisin(sommet2);
		list.get(sommet2).ajouterVoisin(sommet1);
	}
	
	public ArrayList<Noeud> getListe(){
		return this.list;
	}
}
