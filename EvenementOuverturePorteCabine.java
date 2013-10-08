public class EvenementOuverturePorteCabine extends Evenement {
	
    public EvenementOuverturePorteCabine(long l) {
	super(l);
    }
    
    public void afficheDetails(Immeuble immeuble) {
	System.out.print("OPC");
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
    	Cabine cabine = immeuble.cabine;
    	assert !cabine.porteOuverte();
    	
    	//CALCUL DE LA PRIO VIENS ICI
    	cabine.recalcul_status(immeuble);
    	
    	cabine.porteOuverte = true;  	  	
    	long temps = cabine.SortiePassager(immeuble, date);	
    	
    	temps += immeuble.etage(cabine.etage.numero()).EntreePassager();
    	
    	echeancier.ajouter( new EvenementFermeturePorteCabine (this.date + temps) );
    }
    
}
