package frontiere;

import controleur.ControlPrendreEtal;

public class BoundaryPrendreEtal {
    private ControlPrendreEtal controlPrendreEtal;

    public BoundaryPrendreEtal(ControlPrendreEtal controlPrendreEtal) {
        this.controlPrendreEtal = controlPrendreEtal;
    }

    public int prendreEtal(String nomVendeur) {
        System.out.println("Bonjour " + nomVendeur + ", je vais regarder si je peux vous trouver un étal.");
        
        int numeroEtal = controlPrendreEtal.prendreEtal(nomVendeur, null, 0);
        
        if (numeroEtal == -1) {
            System.out.println("Je suis désolée " + nomVendeur + " mais il n'y a plus d'étal disponible.");
            return -1;
        }
        
        System.out.println("C'est parfait, il me reste un étal pour vous !");
        
        System.out.println("Quel produit souhaitez-vous vendre ?");
        String produit = Clavier.entrerChaine("");
        
        System.out.println("Combien souhaitez-vous en vendre ?");
        int quantite = Clavier.entrerEntier("");
        
        numeroEtal = controlPrendreEtal.prendreEtal(nomVendeur, produit, quantite);
        
        System.out.println("Le vendeur " + nomVendeur + " s'est installé à l'étal n°" + numeroEtal);
        
        return numeroEtal;
    }
}