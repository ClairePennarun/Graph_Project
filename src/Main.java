import graphe.calcul.Voisinage;
import graphe.calcul.VoisinagePickNDrop;
import graphe.calcul.VoisinageSwap;
import graphe.init.LecteurGraphes;
import graphe.init.ListeAdjacence;

import java.io.*;

import algo.Exhaustif;
import algo.Lanceur;

public class Main {

	/* Liste des algo :
	 * exhaustif
	 * descente de gradiant
	 * recuit simulé
	 * tabou

	 * Liste des parametres :
	 * => fichier : arg[0]
	 * => algo que l'on veut : arg[1]
	 * => nb de classes : arg[2]
	 * => voisinage : arg[3]
	 * => nombre de fois que l'on veut faire tourner l'algo : arg[4]
	 * => temperature initiale (pour le recuit) ou taille tableau tabou (pour le tabou): arg[5]

	 * Pour lancer le main avec des arguments depuis Eclipse :
	 * clic-droit sur le main -> run as -> run configuration
	 * onglet argument
	 * case program argument : écrire les arguments séparés par un espace
	 */


	public static void main (String args[]) throws IOException{

		File file = new File(args[0]);
		if (!file.exists()){
			System.out.println("Le fichier n'existe pas. Verifiez l'orthographe ou creez un nouveau fichier");

			return;
		}

		LecteurGraphes lg = new LecteurGraphes();
		ListeAdjacence list = lg.lectureFichier(file);
		int nbClasses = Integer.valueOf(args[2]);

		if (args.length < 3) {
			System.out.println("Les arguments du programme doivent être de cette forme " +
					": \n <fichier d'entrée> <algorithme désiré> <nombre de partitions> <voisinage(sauf pour exhaustif)>\n");
			return;
		}

		if (Integer.valueOf(args[2]) > list.getTaille()){
			System.out.println("Le nombre de partitions doit être inférieur au nombre de sommets du graphe.\n");
			return;

		}

		if (args[1].equals("ex")){
			System.out.println("Algorithme : Exhaustif");
			Exhaustif ex = new Exhaustif(list,nbClasses);
			ex.run();
			return;
		}

		if (args.length > 3){	//alors il y a un voisinage

			if ((args[3].equals("PnD")) || (args[3].equals("Swap"))){
				lancementParVoisinage(args, list);
			} else
				System.out.println("Les voisinages disponibles sont Pick and Drop : PnD et Swap : Swap");
		} else {
			if (args[1].equals("grad") ||args[1].equals("recuit") || args[1].equals("tabou"))
				System.out.println(" Pour cet algorithme vous devez choisir un voisinage entre : Pnd ou Swap.\n Recommencer le lancement.");

		}
	}

	private static void lancementParVoisinage (String[] args, ListeAdjacence list){
		Voisinage v;
		int nbClasses = Integer.valueOf(args[2]);

		if (args[3].equals("Swap"))
			v = new VoisinageSwap();
		else	
			v = new VoisinagePickNDrop();

		Lanceur l = new Lanceur (list, v, nbClasses);

		if (args[1].equals("gradient")){
			System.out.println("Algorithme : Gradient");
			l.run("grad", Integer.valueOf(args[4]));

		} else if (args[1].equals("recuit")){
			if (args.length < 6){
				System.out.println("Pour l'algorithme du recuit, " +
						"Vous devez entrer comme parametre suplémentaire la température initiale. \nRecommencer le lancement.");
				return;
			}
			if(Double.valueOf(args[5]) < 0.1){
				System.out.println("La température initiale de l'algorithme Recuit Simulé "+
						"doit être supérieure à 0.1");
				return ;
			}

			System.out.println("Algorithme : Recuit Simulé");
			l.run("recuit", Double.valueOf(args[5]) , Integer.valueOf(args[4]));

		} else if(args[1].equals("tabou")){
			if (args.length < 6){
				System.out.println("Pour l'algorithme Tabou, " +
						"vous devez entrer comme parametre suplémentaire " +
						"la taille de la liste des mouvements tabous. \nRecommencer le lancement.");
				return;
			}
			if (Integer.valueOf(args[4]) < 1){
				System.out.println("La taille du tableau des mouvements interdits doit être supérieure à 0");
				return;
			}
			System.out.println("Algorithme : Tabou");
			l.run("tabou", Double.valueOf(args[5]) , Integer.valueOf(args[4]));
		} else
			System.out.printf ("L'algorithme entré n'est pas valable. \n Les algorithmes implémentés sont : \n exhaustif : ex , \n descente de gradient : gradient , \n recuit simulé : recuit , \n tabou : tabou , \n ");
	}
}




