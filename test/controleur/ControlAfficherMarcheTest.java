package controleur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import villagegaulois.Village;
import personnages.Chef;

/**
 * Tests unitaires pour ControlAfficherMarche.
 * Couvre : affichage du marché, étals occupés, données retournées.
 */
@DisplayName("Tests du ControlAfficherMarche")
class ControlAfficherMarcheTest {

	private Village village;
	private ControlAfficherMarche controlAfficherMarche;
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
		controlAfficherMarche = new ControlAfficherMarche(village);
		controlEmmenager = new ControlEmmenager(village);
	}

	// ---------------------------------------------------------------
	// Constructeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le constructeur retourne un objet non null")
	void testConstructor_retourneObjetNonNull() {
		assertNotNull(controlAfficherMarche);
	}

	// ---------------------------------------------------------------
	// afficherMarche
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un marché vide retourne un tableau vide")
	void testAfficherMarche_marcheVide() {
		String[] donnees = controlAfficherMarche.afficherMarche();
		assertNotNull(donnees);
		assertEquals(0, donnees.length);
	}

	@Test
	@DisplayName("Un marché avec un vendeur retourne 3 données")
	void testAfficherMarche_unVendeur() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		String[] donnees = controlAfficherMarche.afficherMarche();
		assertNotNull(donnees);
		assertEquals(3, donnees.length); // nomVendeur, produit, quantite
	}

	@Test
	@DisplayName("Un marché avec deux vendeurs retourne 6 données")
	void testAfficherMarche_deuxVendeurs() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		controlPrendreEtal.prendreEtal("Obelix", "fleurs", 3);

		String[] donnees = controlAfficherMarche.afficherMarche();
		assertNotNull(donnees);
		assertEquals(6, donnees.length); // 2 vendeurs * 3 données
	}

	@Test
	@DisplayName("Les données du marché contiennent les noms des vendeurs")
	void testAfficherMarche_contientVendeurs() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		String[] donnees = controlAfficherMarche.afficherMarche();
		assertTrue(containsValue(donnees, "Asterix"));
	}

	@Test
	@DisplayName("Les données du marché contiennent les produits")
	void testAfficherMarche_contientProduits() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		String[] donnees = controlAfficherMarche.afficherMarche();
		assertTrue(containsValue(donnees, "pain"));
	}

	@Test
	@DisplayName("Les données du marché contiennent les quantités")
	void testAfficherMarche_contientQuantites() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		String[] donnees = controlAfficherMarche.afficherMarche();
		assertTrue(containsValue(donnees, "5"));
	}

	@Test
	@DisplayName("Plusieurs vendeurs avec différents produits")
	void testAfficherMarche_multipleVendeursMultipleProduits() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);
		controlEmmenager.ajouterGaulois("Vitalstatistix", 8);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		controlPrendreEtal.prendreEtal("Obelix", "fleurs", 3);
		controlPrendreEtal.prendreEtal("Vitalstatistix", "menhirs", 2);

		String[] donnees = controlAfficherMarche.afficherMarche();
		assertEquals(9, donnees.length); // 3 vendeurs * 3 données
		assertTrue(containsValue(donnees, "Asterix"));
		assertTrue(containsValue(donnees, "Obelix"));
		assertTrue(containsValue(donnees, "Vitalstatistix"));
	}

	@Test
	@DisplayName("Un vendeur qui libère son étal n'apparaît plus")
	void testAfficherMarche_vendeurLibere() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		String[] donnees1 = controlAfficherMarche.afficherMarche();
		assertEquals(3, donnees1.length);

		// Libérer l'étal
		ControlTrouverEtalVendeur controlTrouverEtalVendeur = new ControlTrouverEtalVendeur(village);
		ControlLibererEtal controlLibererEtal = new ControlLibererEtal(village, controlTrouverEtalVendeur);
		controlLibererEtal.libererEtal("Asterix");

		String[] donnees2 = controlAfficherMarche.afficherMarche();
		assertEquals(0, donnees2.length);
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