package controleur;

import villagegaulois.Etal;
import villagegaulois.Village;

public class ControlLibererEtal {
    private Village village;
    private ControlTrouverEtalVendeur controlTrouverEtalVendeur;

    public ControlLibererEtal(Village village, ControlTrouverEtalVendeur controlTrouverEtalVendeur) {
        this.village = village;
        this.controlTrouverEtalVendeur = controlTrouverEtalVendeur;
    }

    public String[] libererEtal(String nomVendeur) {
        Etal etal = controlTrouverEtalVendeur.trouverEtalVendeur(nomVendeur);
        
        if (etal == null) {
            return null;
        }
        
        String[] donneesEtal = etal.libererEtal();
        return donneesEtal;
    }
}