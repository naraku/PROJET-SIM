public class Cabine extends Constantes {

	public Etage etage; // Actuel.

	public boolean porteOuverte;

	private char status = '-'; // v : descendante 
	// ^ : montante
	// - : arret

	private Passager[] tableauPassager = new Passager[nombreDePlaceDansLaCabine];

	public Cabine(Etage e){
		etage = e;
		porteOuverte = false;
	}

	public void afficheLaSituation(){
		System.out.print("Contenu de la cabine: ");
		for(int i = 0; i < tableauPassager.length; i++) {
			Passager p = tableauPassager[i];
			if ( p != null ) {
				System.out.print(i+" ");
				p.afficher();
				System.out.print(", ");
			}
		}
		System.out.println();
		System.out.print("Priorite de la cabine: ");
		System.out.println(status);
	}

	public boolean ajouterPassager (Passager passager, boolean isModeParfait) {
		assert passager != null;
		assert ! transporte(passager);
		assert ( isModeParfait ? passager.prioPassager() == this.status() : true);
		int i = tableauPassager.length - 1;
		while ( i >= 0 ) {
			if (tableauPassager[i] == null) {
				tableauPassager[i] = passager;
				return true;
			}
			i--;
		}
		return false;
	}

	public boolean transporte (Passager passager) {
		// Pour savoir si le passager est dedans.
		for (int i = 0; i < tableauPassager.length; i ++) {
			if ( tableauPassager[i] == passager ) {
				return true;
			}
		}
		return false;
	}

	public char status () {
		return status;
	}

	public void set_status (char s) {
		assert ((s == '^') || (s == 'v') || (s == '-'));
		status = s;
	}

	public boolean porteOuverte () {
		return porteOuverte;
	}

	public boolean doitStopper(){
		int i = tableauPassager.length - 1;
		while(i >= 0){
			if(tableauPassager[i] != null){
				if(tableauPassager[i].etageDestination() == this.etage){
					return true;
				}
			}
			i--;
		}	
		return false;
	}

	public boolean isPlein(){
		for(int i = 0;i<tableauPassager.length;i++){
			if(tableauPassager[i] == null){
				return false;
			}
		}
		return true;
	}

	public boolean isVide(){
		for(int i = 0;i<tableauPassager.length;i++){
			if(tableauPassager[i] != null){
				return false;
			}
		}
		return true;
	}

	public long SortiePassager(Immeuble immeuble, long date) { // a refaire
		long temps = 0;
		for(int j=0;j<tableauPassager.length;j++){
			Passager p = tableauPassager[j];
			if((p!=null) && (p.etageDestination()==this.etage)){
				tableauPassager[j] = null;
				immeuble.cumulDesTempsDeTransport += (date-p.dateDepart());
				immeuble.nombreTotalDesPassagersSortis++;
				temps+= tempsPourEntrerOuSortirDeLaCabine;
			}
		}
		return temps;
	}

	//ToDo !!!
	public void recalcul_status(Immeuble immeuble){
		assert status != '-'; //fruit d'une reflexion (mais pas la notre)
		Etage etagecourant = immeuble.etage(immeuble.cabine.etage.numero());
		switch(status){
		case '^' : 
			if ( immeuble.etageLePlusHaut() != etagecourant && encore(status) ) break;
			status = 'v';
			break;
		case 'v' :
			if ( immeuble.etageLePlusBas() != etagecourant && encore(status) ) break;
			status = '^';
			break;
		default:
			assert false;
		}
		assert status=='^' || status=='v' || status=='-' ;
	}

	
	private boolean encore(char status) {
		int i = this.tableauPassager.length -1;
		
		while ( i >= 0)
		{
			Passager p = tableauPassager[i];
			if (p != null && p.prioPassager() == status ) return true;
			i--;
		}
		return false;
	}	

}
