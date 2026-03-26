package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private String vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;
	private Object etals;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public String getVendeur() {
		return vendeur;
	}

	public int getQuantite() {
		return quantite;
	}

	public String getProduit() {
		return produit;
	}

	public void occuperEtal(String nomVendeur, String produit, int quantite) {
		this.vendeur = nomVendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}

	public int acheterProduit(int quantiteAcheter) {
		if (quantite == 0) {
			quantiteAcheter = 0;
		}
		if (quantiteAcheter > quantite) {
			quantiteAcheter = quantite;
		}
		if (etalOccupe) {
			quantite -= quantiteAcheter;
		}
		return quantiteAcheter;
	}

	public String[] libererEtal() {
		etalOccupe = false;
		return null;
	}

	/**
	 * 
	 * @return donneesVente est un tableau de chaine contenant [0] : un boolean
	 *         indiquant si l'étal est occupé [1] : nom du vendeur [2] : produit
	 *         vendu [2] : quantité de produit à vendre au début du marché [4] :
	 *         quantité de produit vendu
	 */
	public String[] etatEtal() {
		String[] donneesVente = new String[5];
		donneesVente[0] = String.valueOf(etalOccupe);
		if (etalOccupe) {
			donneesVente[1] = vendeur;  // vendeur est déjà un String
			donneesVente[2] = produit;
			donneesVente[3] = String.valueOf(quantiteDebutMarche);
			donneesVente[4] = String.valueOf(quantiteDebutMarche - quantite);
		}
		return donneesVente;
	}



}
