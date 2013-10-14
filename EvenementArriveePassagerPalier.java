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


		if ( cabine.etage.numero() == etageDeDepart.numero() && cabine.porteOuverte() ) {
			if ( p.prioPassager() == cabine.status() || !immeuble.isModeParfait() )
			{
				long temps = 0;
				cabine.ajouterPassager(p);
				temps += Constantes.tempsPourEntrerOuSortirDeLaCabine;

				if ( temps != 0 )
				{
					echeancier.retarderFermeture(temps);
					date += etageDeDepart.arriveeSuivant ();
					echeancier.ajouter(this);
					return;
				}
			}

		} else {
			if( cabine.status() == '-' ) {
				if (cabine.etage.numero() > etageDeDepart.numero()) {
					cabine.set_status('v');
					echeancier.ajouter(new EvenementPassageCabinePalier (this.date, immeuble.etageInferieur(cabine.etage)));
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
