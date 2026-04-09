package controleur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import personnages.Chef;
import villagegaulois.Village;

/**
 * Tests unitaires pour ControlAcheterProduit.
 * Couvre : recherche de vendeurs, achat de produits, cas d'erreur.
 */
@DisplayName("Tests du ControlAcheterProduit")
class ControlAcheterProduitTest {

	private Village village;
	private ControlAcheterProduit controlAcheterProduit;
	private ControlVerifierIdentite controlVerifierIdentite;
	private ControlPrendreEtal controlPrendreEtal;
	private ControlEmmenager controlEmmenager;

	@BeforeEach
	public void initialiserSituation() {
		village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		controlVerifierIdentite = new ControlVerifierIdentite(village);
		controlPrendreEtal = new ControlPrendreEtal(village, controlVerifierIdentite);
		controlAcheterProduit = new ControlAcheterProduit(controlVerifierIdentite, village);
		controlEmmenager = new ControlEmmenager(village);
	}

	// ---------------------------------------------------------------
	// Constructeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le constructeur retourne un objet non null")
	void testConstructor_retourneObjetNonNull() {
		assertNotNull(controlAcheterProduit);
	}

	// ---------------------------------------------------------------
	// trouverVendeursParProduit
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Pas de vendeur pour un produit inexistant")
	void testTrouverVendeursParProduit_produitInexistant() {
		String[] vendeurs = controlAcheterProduit.trouverVendeursParProduit("pain");
		assertNotNull(vendeurs);
		assertEquals(0, vendeurs.length);
	}

	@Test
	@DisplayName("Un vendeur pour un produit peut être trouvé")
	void testTrouverVendeursParProduit_unVendeur() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		String[] vendeurs = controlAcheterProduit.trouverVendeursParProduit("pain");
		assertNotNull(vendeurs);
		assertEquals(2, vendeurs.length);
		assertEquals("Asterix", vendeurs[0]);
		assertEquals("pain", vendeurs[1]);
	}

	@Test
	@DisplayName("Plusieurs vendeurs du même produit sont trouvés")
	void testTrouverVendeursParProduit_plusieursVendeurs() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		controlPrendreEtal.prendreEtal("Obelix", "pain", 3);

		String[] vendeurs = controlAcheterProduit.trouverVendeursParProduit("pain");
		assertNotNull(vendeurs);
		assertEquals(4, vendeurs.length);
		assertTrue(containsValue(vendeurs, "Asterix"));
		assertTrue(containsValue(vendeurs, "Obelix"));
	}

	@Test
	@DisplayName("Les vendeurs de différents produits ne sont pas mélangés")
	void testTrouverVendeursParProduit_produitsDifferents() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		controlPrendreEtal.prendreEtal("Obelix", "fleurs", 3);

		String[] vendeursPain = controlAcheterProduit.trouverVendeursParProduit("pain");
		String[] vendeursFleurs = controlAcheterProduit.trouverVendeursParProduit("fleurs");

		assertEquals(2, vendeursPain.length);
		assertEquals(2, vendeursFleurs.length);
		assertEquals("Asterix", vendeursPain[0]);
		assertEquals("Obelix", vendeursFleurs[0]);
	}

	@Test
	@DisplayName("Un étal vide ne peut pas vendre")
	void testTrouverVendeursParProduit_etalVide() {
		String[] vendeurs = controlAcheterProduit.trouverVendeursParProduit("pain");
		assertEquals(0, vendeurs.length);
	}

	@Test
	@DisplayName("La recherche est sensible à la casse")
	void testTrouverVendeursParProduit_sensibleCasse() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		String[] vendeurs1 = controlAcheterProduit.trouverVendeursParProduit("pain");
		String[] vendeurs2 = controlAcheterProduit.trouverVendeursParProduit("Pain");
		String[] vendeurs3 = controlAcheterProduit.trouverVendeursParProduit("PAIN");

		assertEquals(2, vendeurs1.length);
		assertEquals(0, vendeurs2.length);
		assertEquals(0, vendeurs3.length);
	}

	// ---------------------------------------------------------------
	// acheterProduit
	// ---------------------------------------------------------------

	@Test
	@DisplayName("L'acheteur doit être un habitant du village")
	void testAcheterProduit_acheteurInconnu() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		int quantite = controlAcheterProduit.acheterProduit("Inconnu", "Asterix");
		assertEquals(-2, quantite);
	}

	@Test
	@DisplayName("Le vendeur doit exister et avoir un étal")
	void testAcheterProduit_vendeurInexistant() {
		controlEmmenager.ajouterGaulois("Asterix", 6);

		int quantite = controlAcheterProduit.acheterProduit("Asterix", "Inconnu");
		assertEquals(-1, quantite);
	}

	@Test
	@DisplayName("Un achat valide retourne la quantité achetée (1)")
	void testAcheterProduit_achatValide() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		int quantite = controlAcheterProduit.acheterProduit("Obelix", "Asterix");
		assertEquals(1, quantite);
	}

	@Test
	@DisplayName("On ne peut pas acheter si le stock est vide")
	void testAcheterProduit_stockVide() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 1);

		int achat1 = controlAcheterProduit.acheterProduit("Obelix", "Asterix");
		int achat2 = controlAcheterProduit.acheterProduit("Obelix", "Asterix");

		assertEquals(1, achat1);
		assertEquals(0, achat2);
	}

	@Test
	@DisplayName("Le chef peut acheter")
	void testAcheterProduit_chefAcheteur() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		int quantite = controlAcheterProduit.acheterProduit("Abraracourcix", "Asterix");
		assertEquals(1, quantite);
	}

	@Test
	@DisplayName("Le chef peut vendre")
	void testAcheterProduit_chefVendeur() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Abraracourcix", "menhirs", 10);

		int quantite = controlAcheterProduit.acheterProduit("Asterix", "Abraracourcix");
		assertEquals(1, quantite);
	}

	@Test
	@DisplayName("Plusieurs achats consécutifs (1 par 1)")
	void testAcheterProduit_achatsMultiples() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 3);

		int achat1 = controlAcheterProduit.acheterProduit("Obelix", "Asterix");
		int achat2 = controlAcheterProduit.acheterProduit("Obelix", "Asterix");
		int achat3 = controlAcheterProduit.acheterProduit("Obelix", "Asterix");
		int achat4 = controlAcheterProduit.acheterProduit("Obelix", "Asterix");

		assertEquals(1, achat1);
		assertEquals(1, achat2);
		assertEquals(1, achat3);
		assertEquals(0, achat4);
	}

	@Test
	@DisplayName("Acheteur qui n'existe pas retourne -2")
	void testAcheterProduit_acheteurNonHabitant() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		int quantite = controlAcheterProduit.acheterProduit("Fantôme", "Asterix");
		assertEquals(-2, quantite);
	}

	@Test
	@DisplayName("Vendeur sans étal retourne -1")
	void testAcheterProduit_vendeurSansEtal() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		int quantite = controlAcheterProduit.acheterProduit("Obelix", "Asterix");
		assertEquals(-1, quantite);
	}

	@Test
	@DisplayName("La recherche du vendeur est sensible à la casse")
	void testAcheterProduit_vendeurSensibleCasse() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		int quantite1 = controlAcheterProduit.acheterProduit("Obelix", "Asterix");
		int quantite2 = controlAcheterProduit.acheterProduit("Obelix", "asterix");

		assertEquals(1, quantite1);
		assertEquals(-1, quantite2);
	}

	// ---------------------------------------------------------------
	// Méthode utilitaire
	// ---------------------------------------------------------------

	private boolean containsValue(String[] array, String value) {
		for (String element : array) {
			if (element != null && element.equals(value)) {
				return true;
			}
		}
		return false;
	}
}