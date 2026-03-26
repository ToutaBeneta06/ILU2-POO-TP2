package controleur;

import villagegaulois.Village;
import personnages.Personnage;

public class ControlVerifierIdentite {
	private Village village;

	public ControlVerifierIdentite(Village village) {
		this.village = village;
	}

	public boolean verifierIdentite(String nomVendeur) {
		Personnage personnage = village.trouverHabitant(nomVendeur);
		return personnage != null;
	}
}

