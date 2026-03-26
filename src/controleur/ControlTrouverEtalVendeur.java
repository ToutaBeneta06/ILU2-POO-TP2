package controleur;

import villagegaulois.Etal;
import villagegaulois.Village;

public class ControlTrouverEtalVendeur {
    private Village village;

    public ControlTrouverEtalVendeur(Village village) {
        this.village = village;
    }

    public Etal trouverEtalVendeur(String nomVendeur) {
        return village.trouverEtalVendeur(nomVendeur);
    }
}