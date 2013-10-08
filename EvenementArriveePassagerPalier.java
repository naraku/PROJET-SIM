public class EvenementArriveePassagerPalier extends Evenement {

	private Etage etageDeDepart;//etage de départ du guguss

	public EvenementArriveePassagerPalier(int d, Etage edd) {
		super(d);
		etageDeDepart = edd;
	}

	public void afficheDetails(Immeuble immeuble) {
		System.out.print("APP ");
		System.out.print(etageDeDepart.numero());
	}

	public void traiter(Immeuble immeuble, Echeancier echeancier) {	
		Cabine cabine = immeuble.cabine;
		assert etageDeDepart != null;
		Passager p = new Passager (this.date, etageDeDepart, immeuble);		
		assert etageDeDepart == p.etageDepart();	

		if (cabine.etage.numero() == etageDeDepart.numero() && cabine.porteOuverte()) {
			
			/*******************************/
			boolean rentre = false;
			
			boolean modeParfait = immeuble.isModeParfait();
			if (modeParfait)
			{
				 if ( p.prioPassager() == cabine.status() )
				 {
					 rentre = cabine.ajouterPassager(p, modeParfait);
				 }
			}
			else
			{
				rentre = cabine.ajouterPassager(p, modeParfait);
			}
		
			if ( rentre ) echeancier.retarderFermeture(tempsPourEntrerOuSortirDeLaCabine);
			
		
			/*******************************/
			
		} else {
			if( cabine.status() == '-' ) {
				if (cabine.etage.numero() > etageDeDepart.numero()) {
					cabine.set_status('v');
					echeancier.ajouter(new EvenementPassageCabinePalier (this.date + tempsPourBougerLaCabineDUnEtage, immeuble.etageInferieur(cabine.etage)));
				} else {
					aProgrammerPlusTard ();
				}
			}
			
		}
		
		etageDeDepart.ajouter(p);
		date += etageDeDepart.arriveeSuivant ();
		echeancier.ajouter(this);
	}
}
