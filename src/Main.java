

import graphe.calcul.Voisinage;
import graphe.calcul.VoisinagePickNDrop;
import graphe.calcul.VoisinageSwap;
import graphe.init.GrapheMat;
import graphe.init.LecteurGraphes;
import graphe.init.ListeAdjacence;

import java.io.*;

import algo.Exhaustif;
import algo.Gradient;
import algo.RecuitSimule;

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

		File file = new File(args[0]); // TODO : verifier que le fichier existe
		LecteurGraphes lg = new LecteurGraphes();
		GrapheMat graph = lg.lectureFichier(file);
		ListeAdjacence list = lg.getList();
		Voisinage v; 		// instancie en fonction du parametre 4


		if (args.length < 3) {
			System.out.println("Les arguments du programme doivent être de cette forme : \n <fichier d'entrée> <algorithme désiré> <nombre de partitions> <voisinage(sauf pour exhaustif)>\n");
		} else {
			if (Integer.valueOf(args[2]) > graph.getTaille())
				System.out.println("Le nombre de partitions doit être inférieur au nombre de sommets du graphe.\n");

			else {
				if (args.length > 3){		//alors il y a un voisinage

					int nbClasses = Integer.valueOf(args[2]);
					if (args[1].equals("ex")){
						Exhaustif ex = new Exhaustif(list,nbClasses);
						ex.run(); 
					}
					else if (args[3].equals("PnD")){
						v = new VoisinagePickNDrop();
						if (args[1].equals("grad")){
							Gradient gr = new Gradient(list, v, nbClasses);
							System.out.println("etree grad, PnD");
							gr.run();
						} else if (args[1].equals("recuit")){
							if (args.length > 5){
								System.out.println("Pour l'algorithme du recuit, " +
										"vous devez entrez comme parametres suplémentaires la température initiale " +
										"ainsi que la taille du problème. \nRecommencer le lancement.");
							}else {
								RecuitSimule recuit = new RecuitSimule(list, v, nbClasses, Double.valueOf(args[4]), Integer.valueOf(args[5]));
								recuit.run();
							}
						} /*else if(args[1].equals("tabou")){
				Tabou tabou = new Tabou(???);
				tabou.run();
				}*/
					}
					else if (args[3].equals("Swap")){
						v = new VoisinageSwap();
						if (args[1].equals("grad")){
							Gradient gr = new Gradient(list, v, 2);
							System.out.println("entree grad, Swap");
							gr.run();
						} /*else if (args[1].equals("recuit")){
					Recuit recuit = new Recuit(list, v, 2);
					recuit.run();
				} else if(args[1].equals("tabou")){
				Tabou tabou = new Tabou(???);
				tabou.run();
				}*/	
					}
					else
						System.out.println("Les voisinages disponibles sont Pick and Drop : PnD et Swap : Swap"); 
				}else {
					if (args[1].equals("ex")){
						Exhaustif ex = new Exhaustif(list,2);
						ex.run();
					} else if (args[1].equals("grad") ||args[1].equals("recuit") || args[1].equals("tabou"))
						System.out.println(" Pour cet algorithme vous devez choisir en voisinage entre : Pnd ou Swap.\n Recommencer le lancement.");

					else {
						System.out.printf ("L'algorithme entré n'est pas valable. \n Les algorithmes implémentés sont : \n exhaustif : ex , \n descente de gradient : grad , \n recuit simulé : recuit , \n tabou : tabou , \n ");
						System.out.println("entree algo : -" + args[1] + "-");
					}
				}
			} 
		}
	}
}





/* pour lancer le main avec des arguments : 
 * clic-droit sur le main => run as => run configuration
 * onglet argument 
 * case program argument : écrires les arguments séparés par un espace
 * */

