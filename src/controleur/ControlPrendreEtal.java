package controleur;

import villagegaulois.Etal;
import villagegaulois.Village;

public class ControlPrendreEtal {
    private Village village;
    private ControlVerifierIdentite controlVerifierIdentite;

    public ControlPrendreEtal(Village village, ControlVerifierIdentite controlVerifierIdentite) {
        this.village = village;
        this.controlVerifierIdentite = controlVerifierIdentite;
    }

    public int prendreEtal(String nomVendeur, String produit, int nbProduit) {
        if (!controlVerifierIdentite.verifierIdentite(nomVendeur)) {
            return -1;
        }
        
        int numeroEtal = village.trouverEtalLibre();
        if (numeroEtal == -1) {
            return -1;
        }
        
        village.occuperEtal(numeroEtal, nomVendeur, produit, nbProduit);
        return numeroEtal;
    }
}