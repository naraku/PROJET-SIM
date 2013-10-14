public class EvenementFermeturePorteCabine extends Evenement {
    
    public EvenementFermeturePorteCabine(long l) {
    	super(l + tempsPourOuvrirOuFermerLesPortes);
    }
    
    public void afficheDetails(Immeuble immeuble) {
	System.out.print("FPC");
    }
    
    public void traiter(Immeuble immeuble, Echeancier echeancier) {
    	Cabine cabine = immeuble.cabine;
    	Etage etage= cabine.etage;
    	assert cabine.porteOuverte();
    	
    	cabine.porteOuverte = false;   	
    	
    	this.date += tempsPourBougerLaCabineDUnEtage;
    	
    	//REDONDANT AVEC EVENEMENT PASSAGER PALIER
    	if (cabine.status() == 'v')
		{
			etage = immeuble.etage( cabine.etage.numero() - 1 );
		} else {
			etage = immeuble.etage( cabine.etage.numero() + 1 );
		}
    	echeancier.ajouter(new EvenementPassageCabinePalier (date, etage));
    }
}
