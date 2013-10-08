public abstract class Evenement extends Constantes {

	protected long date; // en dixieme de secondes

	public Evenement (long l) {
		date = l;
	}

	public abstract void traiter (Immeuble immeuble, Echeancier echeancier);

	public void affiche (Immeuble immeuble) {
		System.out.print("["+ date +",");
		this.afficheDetails(immeuble);
		System.out.print("]");
	}

	public abstract void afficheDetails (Immeuble immeuble);

}
