package controleur;

import villagegaulois.Etal;
import villagegaulois.Village;

public class ControlAfficherMarche {
    private Village village;

    public ControlAfficherMarche(Village village) {
        this.village = village;
    }

    public String[] afficherMarche() {
        Etal[] etals = village.getEtals();
        int nbEtalsOccupes = 0;
        
        // Compter le nombre d'étals occupés
        for (Etal etal : etals) {
            if (etal != null && etal.isEtalOccupe()) {
                nbEtalsOccupes++;
            }
        }
        
        // Créer le tableau : chaque étal occupe 3 cases [nomVendeur, produit, quantite]
        String[] donneesMarche = new String[nbEtalsOccupes * 3];
        int index = 0;
        
        for (Etal etal : etals) {
            if (etal != null && etal.isEtalOccupe()) {
                donneesMarche[index++] = etal.getVendeur().getNom();
                donneesMarche[index++] = etal.getProduit();
                donneesMarche[index++] = String.valueOf(etal.getQuantite());
            }
        }
        
        return donneesMarche;
    }
}