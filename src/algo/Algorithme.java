package algo;

import graphe.calcul.Solution;

public abstract class Algorithme{
	protected int temps;
	protected Solution solutionOpt;
	protected int evalOpt;
	
	public abstract void run();
	
	public String convertirTemps(int temps){
		int min = (temps/1000)/60;
		int sec = (temps - min*1000*60)/1000;
		int ms = temps - min*1000*60 - sec*1000;
		return min + " minutes " + sec + " secondes " + ms + " millisecondes";		
	}

	public void afficherResultat(Solution init, Solution finale, double temps){
		StringBuffer res = new StringBuffer();
		res.append("Solution initiale : "+ init + "\nEvaluation initiale : " + init.getEval() + "\n");
		res.append("Solution optimale est : " + finale + "\nEvaluation optimale: " + finale.getEval() + "\n" + 
		"Temps total d'execution : " + this.convertirTemps((int) temps) + "\n-------");
		System.out.println(res);
	}
	
	public Solution getBestSol(){
		return this.solutionOpt;
	}
	
	public int getBestEval(){
		return this.evalOpt;
	}
	
	public int getTemps() {
		return this.temps;
	}
	
}
