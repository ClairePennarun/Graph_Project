

import graphe.calcul.Voisinage;
import graphe.calcul.VoisinagePickNDrop;
import graphe.calcul.VoisinageSwap;
import graphe.init.LecteurGraphes;
import graphe.init.ListeAdjacence;

import java.io.*;

import algo.Exhaustif;
import algo.Gradient;
import algo.Tabou;
import algo.RecuitSimule;

public class Main { 

	/* Liste des algo : 
	 * exhaustif
	 * descente de gradiant
	 * recuit simulé
	 * tabou 

	 * Liste des parametres : 
	 * => fichier  : arg[0]
	 * => algo que l'on veut : arg[1]
	 * => nb de classes : arg[2]
	 * => voisinage : arg[3]
	 * => temperature initiale (pour le recuit) ou taille tableau tabou (pour le tabou): arg[4]

	 * Pour lancer le main avec des arguments depuis Eclipse :  
	 * clic-droit sur le main -> run as -> run configuration
	 * onglet argument 
	 * case program argument : écrire les arguments séparés par un espace
	 */


	public static void main (String args[]) throws IOException{

		File file = new File(args[0]);
		if (!file.exists())
			System.out.println("Le fichier n'existe pas. Verifiez l'orthographe ou creez un nouveau fichier");
		else {
			LecteurGraphes lg = new LecteurGraphes();
			ListeAdjacence list = lg.lectureFichier(file);
			int nbAretes = lg.getNbAretes();
			int nbClasses = Integer.valueOf(args[2]);
			Voisinage v; 		// instancie en fonction du parametre 4

			if (args.length < 3) {
				System.out.println("Les arguments du programme doivent être de cette forme " +
						": \n <fichier d'entrée> <algorithme désiré> <nombre de partitions> <voisinage(sauf pour exhaustif)>\n");
			} 
			else {
				if (Integer.valueOf(args[2]) > list.getTaille())
					System.out.println("Le nombre de partitions doit être inférieur au nombre de sommets du graphe.\n");

				else {
					if (args.length > 3){		//alors il y a un voisinage

						if (args[1].equals("ex")){
							System.out.println("Algorithme : Exhaustif");
							Exhaustif ex = new Exhaustif(list,nbClasses);
							ex.run(); 
						}
						else if (args[3].equals("PnD")){
							v = new VoisinagePickNDrop();

							if (args[1].equals("grad")){
								Gradient gr = new Gradient(list, v, nbClasses);
								System.out.println("Algorithme : Gradient, Voisinage : PnD");
								gr.run();

							} else if (args[1].equals("recuit")){
								if (args.length < 4){
									System.out.println("Pour l'algorithme du recuit, " +
											"vous devez entrer comme parametres suplémentaires la température initiale. " +
											"\nRecommencer le lancement.");
								} else {
									if(Double.valueOf(args[4]) < 0.1){
										System.out.println("La température initiale de l'algorithme Recuit Simulé "+ 
												"doit être supérieure à 0.1");
									}else {
										RecuitSimule gr = new RecuitSimule(list, v, Integer.valueOf(args[2]), Double.valueOf(args[4]));
										System.out.println("Algorithme : Gradient, Voisinage : PnD");
										gr.run();
									}
								}
							} else if(args[1].equals("tabou")){
								if (args.length < 4){
									System.out.println("Pour l'algorithme tabou, " +
											"vous devez entrer comme parametre suplémentaire la taille " +
											"de la liste des mouvements tabous. \nRecommencer le lancement.");
								}
								else{
									if (Integer.valueOf(args[4]) < 1)
										System.out.println("La taille du tableau des mouvements interdits doit être supérieure à 0");
									else {
										Tabou tabou = new Tabou(list, v, nbClasses, Integer.valueOf(args[4]) );
										tabou.run();
									}
								}
							}
						}
						else if (args[3].equals("Swap")){
							v = new VoisinageSwap();
							if (args[1].equals("grad")){
								System.out.println("Algorithme : Gradient");
								Gradient gr = new Gradient(list, v, 2);
								System.out.println("Algorithme : Gradient, Voisinage : Swap");
								gr.run();

							} else if (args[1].equals("recuit")){
								if (args.length < 4){
									System.out.println("Pour l'algorithme du recuit, " +
											"Vous devez entrez comme parametres suplémentaires la température initiale. \nRecommencer le lancement.");
								}else {
									if(Double.valueOf(args[4]) < 0.1)
										System.out.println("La température initiale de l'algorithme Recuit Simulé "+ 
												"doit être supérieure à 0.1");
									else {
										System.out.println("Algorithme : Recuit");
										RecuitSimule recuit = new RecuitSimule(list, v, nbClasses, Double.valueOf(args[4]));
										recuit.run();
									}
								}
							} else if(args[1].equals("tabou")){
								if (args.length < 4){
									System.out.println("Pour l'algorithme Tabou, " +
											"vous devez entrez comme parametre suplémentaire " +
											"la taille de la liste des mouvements tabous . \nRecommencer le lancement.");
								}else {
									if (Integer.valueOf(args[4]) < 1)
										System.out.println("La taille du tableau des mouvements interdits doit être supérieure à 0");
									else{
										System.out.println("Algorithme : Tabou");
										Tabou tabou = new Tabou(list, v, nbClasses, Integer.valueOf(args[4]));
										tabou.run();
									}
								}
							}
						}
						else
							System.out.println("Les voisinages disponibles sont Pick and Drop : PnD et Swap : Swap"); 
					}else {
						if (args[1].equals("ex")){
							System.out.println("Algorithme : Exhaustif");
							Exhaustif ex = new Exhaustif(list,nbClasses);
							ex.run();
						} else if (args[1].equals("grad") ||args[1].equals("recuit") || args[1].equals("tabou"))
							System.out.println(" Pour cet algorithme vous devez choisir un voisinage entre : Pnd ou Swap.\n Recommencer le lancement.");
						else {
							System.out.printf ("L'algorithme entré n'est pas valable. \n Les algorithmes implémentés sont : \n exhaustif : ex , \n descente de gradient : grad , \n recuit simulé : recuit , \n tabou : tabou , \n ");
							System.out.println("entree algo : -" + args[1] + "-");
						}
					}
				} 
			}
		}
	}
}


