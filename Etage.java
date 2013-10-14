import java.util.ArrayList;

public class Etage {

	private int numero; // de l'Etage

	private Immeuble immeuble;

	private LoiDePoisson poissonFrequenceArrivee;

	private ArrayList<Passager> listePassagersEtage = new ArrayList<Passager>();

	public Etage (int n, int fa, Immeuble im) {
		numero = n;
		immeuble = im;
		int germe = 0;
		germe = n << 2;
		if (germe <= 0) {
			germe = -germe + 1;
		}
		poissonFrequenceArrivee = new LoiDePoisson(germe,fa);
	}

	public void afficheLaSituation() {
		if(numero() >= 0) {
			System.out.print(" ");
		}
		System.out.print(numero());	
		if (this == immeuble.cabine.etage) {
			System.out.print(" C ");
			if (immeuble.cabine.porteOuverte()) {
				System.out.print("[  ]: ");
			} else {
				System.out.print(" [] : ");
			}
		} else  {
			System.out.print("   ");
			System.out.print(" [] : ");
		}
		int i = 0;
		boolean stop = listePassagersEtage.size() == 0;
		while ( ! stop ) {
			if ( i >= listePassagersEtage.size() ) {
				stop = true;
			}
			else if ( i > 6 ) {
				stop = true;
				System.out.print("...(");
				System.out.print(listePassagersEtage.size());
				System.out.print(')');
			}
			else {
				listePassagersEtage.get(i).afficher();
				i++;
				if (i < listePassagersEtage.size()) {
					System.out.print(", ");
				}
			}
		}
		System.out.print('\n');
	}

	public int numero () {
		// En Eiffel il est possible de mettre celle qui suit:
		// assert immeuble.recalcule_numero(this) == this.numero;
		return this.numero;
	}

	public void ajouter (Passager passager) {
		listePassagersEtage.add(passager);
	}

	public boolean isVide () {
		return (listePassagersEtage.size() == 0);
	}

	public int arriveeSuivant () {
		return poissonFrequenceArrivee.suivant();
	}

	public boolean doitStopper(){
		for (Passager p : listePassagersEtage){
			if (p.etageDepart() == this){
				return true;
			}
		}
		return false;
	}

	public long EntreePassager(){
		long temps = 0;
		Cabine cabine = immeuble.cabine;
		char prio = immeuble.cabine.status();
		int i = listePassagersEtage.size() - 1;
		
		boolean isParfait = immeuble.isModeParfait();
		boolean rentre = true;
		
		while( rentre && i >= 0 ){
			Passager p = listePassagersEtage.get(i);
			if ( p.prioPassager() == cabine.status() || !isParfait )
			{
				rentre = cabine.ajouterPassager(p);
				temps += Constantes.tempsPourEntrerOuSortirDeLaCabine;
				listePassagersEtage.remove(i);
			}
			i--;
		}
		return temps;
	}

	public Passager getPremier()
	{
		return listePassagersEtage.get(0);
	}

}
