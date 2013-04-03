package graphe.calcul;

public class Mouvement {

	private int sommet1 = -1;
	private int sommet2 = -1;
	private int classe = -1;
	private int timestamp;
	
	public Mouvement(int sommet1, int sommet2, int classe){
		this.sommet1 = sommet1;
		this.sommet2 = sommet2;
		this.classe = classe;
		this.timestamp = 0;
	}
	
	public int getSommet1(){
		return this.sommet1;
	}
	
	public int getSommet2(){
		return this.sommet2;
	}
	
	public int getClasse(){
		return this.classe;
	}
	
	public int getStamp(){
		return timestamp;
	}
	
	public void incrStamp(){
		timestamp = timestamp +1;
	}
	
}
