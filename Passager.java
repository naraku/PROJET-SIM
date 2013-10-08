public class Passager {

	private int numeroDeCreation;

	private long dateDepart;

	private Etage etageDepart;

	private Etage etageDestination;	

	public long dateDepart() {
		return this.dateDepart;
	}

	public Etage etageDepart() {
		return this.etageDepart;
	}

	public int numeroDepart() {
		return this.etageDepart.numero();
	}

	public Etage etageDestination() {
		return this.etageDestination;
	}

	public int numeroDestination() {
		return this.etageDestination.numero();
	}

	public Passager (long date, Etage etageDeDepart, Immeuble immeuble) {
		Etage niveauDuSol = immeuble.niveauDuSol();
		int nbEtages = immeuble.nbEtages();
		etageDepart = etageDeDepart;
		dateDepart = date;
		compteurGlobalDeCreationDesPassagers++;
		numeroDeCreation = compteurGlobalDeCreationDesPassagers;
		if ( etageDepart == niveauDuSol ) {
			etageDestination = niveauDuSol;
			while ( etageDestination == niveauDuSol ) {
				int auPif = randomGenerator.intSuivant(nbEtages);
				etageDestination = immeuble.etage(auPif + immeuble.etageLePlusBas().numero() - 1);
			}
		} else {
			etageDestination = niveauDuSol;
		}
	}

	public void afficher() {
		System.out.print('#');
		System.out.print(numeroDeCreation);
		System.out.print(':');
		int numeroDepart = etageDepart.numero();
		int numeroDestination = etageDestination.numero();
		System.out.print(numeroDepart);
		if (numeroDepart > numeroDestination) {
			System.out.print('v');
		}
		else {
			System.out.print('^');
		}
		System.out.print(numeroDestination);
		System.out.print(':');
		System.out.print(dateDepart);

	}

	private static int compteurGlobalDeCreationDesPassagers = 0;

	private static final PressRandomNumberGenerator 
	randomGenerator	= new PressRandomNumberGenerator(34);

	public char prioPassager(){
		// ?? CHOIX DE NOM ?? UTILISER DANS AFFICHER !
		int numeroDepart = etageDepart.numero();
		int numeroDestination = etageDestination.numero();
		if (numeroDepart > numeroDestination) {
			return('v');
		}
		else {
			return('^');
		}
	}

}
