package controleur;

import villagegaulois.Etal;
import villagegaulois.Village;
import personnages.Personnage;

public class ControlAcheterProduit {
    private Village village;
    private ControlVerifierIdentite controlVerifierIdentite;

    public ControlAcheterProduit(ControlVerifierIdentite controlVerifierIdentite, Village village) {
        this.controlVerifierIdentite = controlVerifierIdentite;
        this.village = village;
    }

    public String[] trouverVendeursParProduit(String produit) {
        Etal[] etals = village.getEtals();
        int nbVendeurs = 0;
        
   
        for (Etal etal : etals) {
            if (etal != null && etal.isEtalOccupe() && etal.getProduit().equals(produit)) {
                nbVendeurs++;
            }
        }
        
      
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

    public int acheterProduit(String nomAcheteur, String nomVendeur) {
    
        Etal etal = village.trouverEtalVendeur(nomVendeur);
        
        if (etal == null) {
            return -1; 
        }
        
       
        return etal.acheterProduit(1); 
    }
}