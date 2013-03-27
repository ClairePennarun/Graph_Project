

import graphe.GrapheMat;
import graphe.LecteurGraphes;
import graphe.ListeAdjacence;
import java.io.*;

import algo.Exhaustif;

public class Main { 

/* prendre en entrée 
	=> fichier  : arg[0]
	=> algo que l'on veut : arg[1]
	=> nb de classes : arg[2]

algo : 
exhaustif
descente de gradiant
recuit simulé
tabou / agent / Hopfield / génétique
*/

	public static void main (String args[]) throws IOException{

		File file = new File("data/input.txt");
		LecteurGraphes lg = new LecteurGraphes();
		GrapheMat graph = lg.lectureFichier(file);
		ListeAdjacence list = lg.getList();
		Exhaustif ex = new Exhaustif(list,2);
		ex.run();
	}
		
	
		/*if (args.length != 3) {
			System.out.printf ("La commande de lancement doit être de cette forme : \n ./graphe <fichier d'entrée> <algorithme désiré> <nombre de partitions>\n");
		*/

		/*if ((args[2] == (0 || 1)) 
			System.out.println("Le nombre de partitions doit être supérieur à 1.\n");
		
		if (args[2] > length.graphe.sommets())
			System.out.println("Le nombre de partitions doit être inférieur au nombre de sommets du graphe.\n");
		
		if (arg[1] == "ex"){
			Exhaustif.run(graphe, args[2]);  
		}

		/* autres algorithmes
		*/


		/*else {
			System.out.printf ("L'algorithme entré n'est pas valable. \n Les algorithmes implémentés sont : \n exhaustif : ex , \n descente de gradient : des , \n recuit simulé : recuit , \n tabou : tabou , \n ");
		}*/
	//}	

}