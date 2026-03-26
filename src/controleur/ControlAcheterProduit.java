package controleur;

import villagegaulois.Etal;
import villagegaulois.Village;
import personnages.Personnage;

public class ControlAcheterProduit {
    private Village village;
    private ControlVerifierIdentite controlVerifierIdentite;

    public ControlAcheterProduit(Village village, ControlVerifierIdentite controlVerifierIdentite) {
        this.village = village;
        this.controlVerifierIdentite = controlVerifierIdentite;
    }

    public String[] trouverVendeursParProduit(String produit) {
        Etal[] etals = village.getEtals();
        int nbVendeurs = 0;
        
        // Compter le nombre de vendeurs du produit
        for (Etal etal : etals) {
            if (etal != null && etal.isEtalOccupe() && etal.getProduit().equals(produit)) {
                nbVendeurs++;
            }
        }
        
        // Créer le tableau : chaque vendeur occupe 2 cases [nomVendeur, produit]
        String[] vendeurs = new String[nbVendeurs * 2];
        int index = 0;
        
        for (Etal etal : etals) {
            if (etal != null && etal.isEtalOccupe() && etal.getProduit().equals(produit)) {
                vendeurs[index++] = etal.getVendeur().getNom();
                vendeurs[index++] = etal.getProduit();
            }
        }
        
        return vendeurs;
    }

    public int acheterProduit(String nomAcheteur, String nomVendeur, int quantite) {
        // Vérifier l'identité de l'acheteur
        if (!controlVerifierIdentite.verifierIdentite(nomAcheteur)) {
            return -2; // Acheteur non habitant
        }
        
        // Trouver l'étal du vendeur
        Etal etal = village.trouverEtalVendeur(nomVendeur);
        
        if (etal == null) {
            return -1; // Vendeur non trouvé
        }
        
        // Effectuer l'achat
        int quantiteAchetee = etal.acheterProduit(quantite);
        
        return quantiteAchetee;
    }
}