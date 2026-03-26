package frontiere;

import controleur.ControlAfficherMarche;

public class BoundaryAfficherMarche {
    private ControlAfficherMarche controlAfficherMarche;

    public BoundaryAfficherMarche(ControlAfficherMarche controlAfficherMarche) {
        this.controlAfficherMarche = controlAfficherMarche;
    }

    public void afficherMarche(String nomClient) {
        String[] donneesMarche = controlAfficherMarche.afficherMarche();
        
        System.out.println(nomClient + ", vous trouverez au marché :");
        
        if (donneesMarche.length == 0) {
            System.out.println("Aucun étal n'est actuellement occupé.");
        } else {
            for (int i = 0; i < donneesMarche.length; i += 3) {
                String nomVendeur = donneesMarche[i];
                String produit = donneesMarche[i + 1];
                String quantite = donneesMarche[i + 2];
                System.out.println("- " + nomVendeur + " qui vend " + quantite + " " + produit);
            }
        }
    }
}