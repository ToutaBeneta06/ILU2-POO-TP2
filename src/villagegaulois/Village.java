package villagegaulois;

import personnages.Chef;
import personnages.Personnage;
import personnages.Druide;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}
	
	public void ajouterHabitant(Personnage personnage) {
		if (personnage instanceof Gaulois) {
			ajouterHabitant((Gaulois) personnage);
		}
	}

	public Personnage trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois habitant = villageois[i];
			if (habitant.getNom().equals(nomGaulois)) {
				return habitant;
			}
		}
		return null;
	}

	public String[] donnerVillageois() {
		String[] donnees = new String[nbVillageois + 1];
		donnees[0] = chef.getNom();
		for (int i = 0; i < nbVillageois; i++) {
			if (villageois[i] instanceof Druide) {
				donnees[i + 1] = "le druide " + villageois[i].getNom();
			} else {
				donnees[i + 1] = villageois[i].getNom();
			}
		}
		return donnees;
	}

	// ========== MÉTHODES POUR LES CONTROLEURS ==========
	public int trouverEtalLibre() {
		return marche.trouverEtalLibre();
	}
	
	public void occuperEtal(int numeroEtal, String nomVendeur, String produit, int nbProduit) {
		Personnage personnage = trouverHabitant(nomVendeur);
		if (personnage instanceof Gaulois) {
			marche.utiliserEtal(numeroEtal, (Gaulois) personnage, produit, nbProduit);
		} else {
			// Si le personnage n'est pas trouvé ou n'est pas un Gaulois, on utilise la version String
			marche.utiliserEtal(numeroEtal, nomVendeur, produit, nbProduit);
		}
	}
	
	public Etal[] getEtals() {
		return marche.getEtals();
	}
	
	public Etal trouverEtalVendeur(String nomVendeur) {
		for (Etal etal : marche.getEtals()) {
			if (etal != null && etal.isEtalOccupe()) {
				Gaulois vendeur = etal.getVendeur();
				if (vendeur != null && vendeur.getNom().equals(nomVendeur)) {
					return etal;
				}
			}
		}
		return null;
	}
	// =================================================

	public int donnerNbEtal() {
		return marche.getNbEtal();
	}

	public int installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indiceEtal = marche.trouverEtalLibre();
		if (indiceEtal >= 0) {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
		}
		return indiceEtal;
	}

	public void partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal != null) {
			etal.libererEtal();
		}
	}

	public boolean rechercherEtalVide() {
		return marche.trouverEtalLibre() != -1;
	}

	public Gaulois[] rechercherVendeursProduit(String produit) {
		Gaulois[] vendeurs = null;
		Etal[] etalsProduit = marche.trouverEtals(produit);
		if (etalsProduit != null) {
			vendeurs = new Gaulois[etalsProduit.length];
			for (int i = 0; i < etalsProduit.length; i++) {
				vendeurs[i] = etalsProduit[i].getVendeur();
			}
		}
		return vendeurs;
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String[] donnerEtatMarche() {
		return marche.donnerEtat();
	}

	////////////////////// Classe Interne ///////////////////////
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		private Etal[] getEtals() {
			return etals;
		}

		// UtiliserEtal avec Gaulois
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
		
		// UtiliserEtal avec String
		private void utiliserEtal(int indiceEtal, String nomVendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(nomVendeur, produit, nbProduit);
			}
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nbEtal++;
				}
			}
			if (nbEtal == 0) {
				return null;
			}
			Etal[] etalsProduitsRecherche = new Etal[nbEtal];
			int nbEtalTrouve = 0;
			for (int i = 0; i < etals.length && nbEtalTrouve < nbEtal; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsProduitsRecherche[nbEtalTrouve] = etals[i];
					nbEtalTrouve++;
				}
			}
			return etalsProduitsRecherche;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if (etal.isEtalOccupe()) {
					Gaulois vendeur = etal.getVendeur();
					if (vendeur != null && vendeur.getNom().equals(gaulois.getNom())) {
						return etal;
					}
				}
			}
			return null;
		}

		private int getNbEtal() {
			return etals.length;
		}

		private int getNbEtalsOccupe() {
			int nbEtal = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe()) {
					nbEtal++;
				}
			}
			return nbEtal;
		}

		private String[] donnerEtat() {
			int tailleTableau = getNbEtalsOccupe() * 3;
			String[] donnees = new String[tailleTableau];
			int j = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe()) {
					Gaulois vendeur = etal.getVendeur();
					donnees[j++] = vendeur.getNom();
					donnees[j++] = String.valueOf(etal.getQuantite());
					donnees[j++] = etal.getProduit();
				}
			}
			return donnees;
		}
	}
}