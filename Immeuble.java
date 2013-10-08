public class Immeuble extends Constantes {

	private Etage[] tableauDesEtages;

	public Cabine cabine; // de l'ascenseur.

	private int niveauDuSol; // le niveau 0 en général.

	private boolean mode_parfait;

	public int cumulDesTempsDeTransport = 0;

	public int nombreTotalDesPassagersSortis = 0;

	public Etage etageLePlusBas() {
		assert tableauDesEtages[0] != null;
		return tableauDesEtages[0];
	}

	public Etage etageLePlusHaut() {
		assert tableauDesEtages[tableauDesEtages.length - 1] != null;
		return tableauDesEtages[tableauDesEtages.length - 1];
	}

	public Etage niveauDuSol() {
		Etage res = etage(niveauDuSol);
		assert etageLePlusHaut().numero() >= res.numero();
		assert etageLePlusBas().numero() <= res.numero();
		return res;
	}

	public Immeuble (Echeancier echeancier, boolean mode) {
		mode_parfait = mode;

		int etageMin = -1;
		niveauDuSol = 0;
		tableauDesEtages = new Etage[8];

		int n = etageMin;
		int i = 0;
		while (	i < tableauDesEtages.length ) {
			// Une personnne toutes les 3 secondes:
			int fa = 30;
			if ( n != niveauDuSol ) {
				fa = fa * (tableauDesEtages.length - 1);
			}
			tableauDesEtages[i] = new Etage(n, fa, this);
			i++;
			n++;
		}
		cabine = new Cabine(etageLePlusHaut());
		// Initialisation des premiers EvenementArrivee pour chaque Etage:
		for (i=0; i < tableauDesEtages.length; i++) {
			Etage etage = tableauDesEtages[i];
			assert recalcule_numero(etage) == etage.numero();
			int date = etage.arriveeSuivant();
			echeancier.ajouter(new EvenementArriveePassagerPalier(date, etage));
		}
	}

	public int recalcule_numero(Etage etage) {
		// Juste pour le debug... vraiment lent.
		assert etage != null;
		int i = 0;
		while ( tableauDesEtages[i] != etage ) {
			i++;
		}
		return tableauDesEtages[i].numero();
	}

	public void afficheLaSituation () {
		System.out.print("Immeuble (mode ");
		if ( mode_parfait ) {
			System.out.print("parfait");
		}
		else {
			System.out.print("infernal");
		}
		System.out.println("):");
		int i = etageLePlusHaut().numero();
		while ( i >= etageLePlusBas().numero() ) {
			etage(i).afficheLaSituation();
			i--;
		}
		cabine.afficheLaSituation();
		System.out.print("Cumul des temps de transport: ");
		System.out.println(cumulDesTempsDeTransport);
		System.out.print("Nombre total des passagers sortis: ");
		System.out.println(nombreTotalDesPassagersSortis);

	}

	public Etage etage (int i) {
		// Retrouve par calcul (assez lent) un Etage avec son numero.
		assert etageLePlusBas().numero() <= i : "ERREUR trop haut";
		assert etageLePlusHaut().numero() >= i : "ERREUR trop bas";
		Etage res = tableauDesEtages[ i - etageLePlusBas().numero() ];
		assert res.numero() == i;
		return res;
	}

	public boolean isModeParfait() {
		return mode_parfait;
	}

	public int nbEtages() {
		int res = tableauDesEtages.length;
		assert res == (etageLePlusHaut().numero() - etageLePlusBas().numero() + 1);
		return res;
	}

	public Etage etageInferieur(Etage etagecourant){
		return  tableauDesEtages[etagecourant.numero()];
	}

	public Etage etageSuperieur(Etage etagecourant) {
		return  tableauDesEtages[etagecourant.numero()+2];
	}

	public void chercherPassager (Evenement e) {
		aProgrammerPlusTard();
	}
}
