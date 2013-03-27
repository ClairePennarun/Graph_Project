package graphe;

import java.util.ArrayList;

public class Noeud {

	public ArrayList<Integer> voisins;

	public Noeud(){
		this.voisins = new ArrayList<Integer>();
	}

	public void ajouterVoisin(int sommet){
		int i = 0;
		while (voisins.get(i) < sommet){
			i++;
		}
		voisins.add(sommet, i);
	}

	public ArrayList<Integer> getVoisins(){
		return this.voisins;
	}
	
	public int nbVoisins(){
		return voisins.size();
	}

	public boolean isVoisin(int sommet){ // pas utilisé (recodé dans Sommet)
		return voisins.contains(sommet);
	}


}