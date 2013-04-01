package graphe.calcul;

import graphe.init.Sommet;

import java.util.ArrayList;

public class SommetPartition {

	private int[] voisinage;
	private int numero;
	
	public SommetPartition(Sommet s, int numero) {
		this.numero = numero;
		this.voisinage = new int[s.nbVoisins()];
		ArrayList<Integer> voisinageAdj = s.getVoisins();
		int i = 0;
		for (int v : voisinageAdj){
			this.voisinage[i] = v;
			i++;
		}
	}
	
	public int[] getVoisins(){
		return this.voisinage;
	}
	
	public int nbVoisins(){
		return this.voisinage.length;
	}
	
	public boolean estVoisin(SommetPartition s){
		return estVoisinRecursif(this.voisinage, 0, this.voisinage.length-1, s.getNum());
	}
	
	public int getNum() {
		return this.numero;
	}

	private boolean estVoisinRecursif(int[] tab, int debut, int fin, int indice){
		if (fin < debut)
			return false;
		int milieu = (int) ((debut+fin)/2);
		if (indice == tab[milieu])
			return true;
		else if (indice < tab[milieu])
			return estVoisinRecursif(tab, debut, milieu, indice);
		else
			return estVoisinRecursif(tab, milieu, fin, indice);
	}

}
