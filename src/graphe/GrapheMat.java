package graphe;

public class GrapheMat {

	int[][] matrice;

	public GrapheMat(int nb){
		matrice = new int[nb][nb];
	}

	public void ajouterArete(int sommet1, int sommet2){
		this.ajouterAreteVal(sommet1, sommet2, 1);
	}

	public void ajouterAreteVal(int sommet1, int sommet2, int poids){
		matrice[sommet1][sommet2] = poids;
		matrice[sommet2][sommet1] = poids;
	}

	public int poidsArete(int sommet1, int sommet2){
		return matrice[sommet1][sommet2];
	}

	public void supprimerArete(int sommet1, int sommet2){
		matrice[sommet1][sommet2] = 0;
		matrice[sommet2][sommet1] = 0;
	}

	public boolean existeArete(int sommet1, int sommet2){
		return (matrice[sommet1][sommet2]!=0);
	}

}