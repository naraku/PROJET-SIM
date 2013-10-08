public class EvenementPassageCabinePalier extends Evenement {
    
    private Etage etage;
	
    public EvenementPassageCabinePalier(long l, Etage e) {
	super(l);
	etage = e;
    }
    
    public void afficheDetails(Immeuble immeuble) {
	System.out.print("PCP ");
	System.out.print(etage.numero());
    }
    
    public void traiter(Immeuble immeuble, Echeancier echeancier) {
    	Cabine cabine = immeuble.cabine;
    	assert !cabine.porteOuverte();
    	
    	cabine.etage = etage;
    	
    	if ( cabine.doitStopper() || cabine.etage.doitStopper()) {
    		echeancier.ajouter(new EvenementOuverturePorteCabine (this.date + tempsPourOuvrirOuFermerLesPortes));
    	} else {
    		if (cabine.status() == 'v')
    		{
				etage = immeuble.etage(cabine.etage.numero() - 1);
    		} else {
    			etage = immeuble.etage(cabine.etage.numero() + 1);
    		}
    		this.date += tempsPourBougerLaCabineDUnEtage;
    		echeancier.ajouter(this);
    	}

    }
}