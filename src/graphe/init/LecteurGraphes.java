package graphe.init;

import java.io.*;

public class LecteurGraphes {

	private GrapheMat graphe;
	private ListeAdjacence liste;
	private int nbAretes;

	// si on ne met rien dans le constructeur alors on ne met pas de constructeur
	
	public ListeAdjacence getList(){
		return liste;
	}
	
	public int getNbAretes(){
		return this.nbAretes;
	}
	
	private void readSommets(BufferedReader br) throws IOException{
		String ligne = br.readLine();
		while(isComment(ligne)){
			ligne = br.readLine();
		}
		String[] tab = ligne.split(" ");
		int nbSommets = Integer.parseInt(tab[0]);
		nbAretes = Integer.parseInt(tab[1]);
		this.graphe = new GrapheMat(nbSommets);
		this.liste = new ListeAdjacence(nbSommets);
	}
	
	private void readDegres(BufferedReader br) throws IOException{
		String ligne = br.readLine();
		while(isComment(ligne)){
			ligne = br.readLine();
		}
	}
	
	private void readAretes(BufferedReader br) throws IOException{
		String ligne = br.readLine();
		while(isComment(ligne)){
			ligne = br.readLine();
		}
		for (int i = 0; i < nbAretes; i++){
			String[] tab = ligne.split(" ");
			int sommet1 = Integer.parseInt(tab[0]);
			int sommet2 = Integer.parseInt(tab[1]);
			int poids = Integer.parseInt(tab[2]);
			graphe.ajouterAreteVal(sommet1, sommet2, poids);
			liste.ajouterArete(sommet1, sommet2);
		}
	}

	private boolean isComment(String ligne){
		return ligne.startsWith("#");
	}
	
	public GrapheMat lectureFichier(File fichier) throws IOException{
		InputStream is = new FileInputStream(fichier);
		InputStreamReader ipsr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(ipsr);
		
		readSommets(br);
		readDegres(br);
		readAretes(br);
		return graphe;
	}
	
}
