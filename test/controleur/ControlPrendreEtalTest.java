package controleur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import personnages.Chef;
import villagegaulois.Village;

/**
 * Tests unitaires pour ControlPrendreEtal.
 * Couvre : prise d'étal, vérification d'identité, capacité d'étals.
 */
@DisplayName("Tests du ControlPrendreEtal")
class ControlPrendreEtalTest {

	private Village village;
	private ControlPrendreEtal controlPrendreEtal;
	private ControlVerifierIdentite controlVerifierIdentite;
	private ControlEmmenager controlEmmenager;

	@BeforeEach
	public void initialiserSituation() {
		village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		controlVerifierIdentite = new ControlVerifierIdentite(village);
		controlPrendreEtal = new ControlPrendreEtal(village, controlVerifierIdentite);
		controlEmmenager = new ControlEmmenager(village);
	}

	// ---------------------------------------------------------------
	// Constructeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le constructeur retourne un objet non null")
	void testConstructor_retourneObjetNonNull() {
		assertNotNull(controlPrendreEtal);
	}

	// ---------------------------------------------------------------
	// prendreEtal
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un habitant inconnu ne peut pas prendre d'étal")
	void testPrendreEtal_habitantInconnu() {
		int numeroEtal = controlPrendreEtal.prendreEtal("Inconnu", "pain", 5);
		assertEquals(-1, numeroEtal);
	}

	@Test
	@DisplayName("Un habitant connu peut prendre un étal")
	void testPrendreEtal_habitantConnu() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		int numeroEtal = controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		assertNotEquals(-1, numeroEtal);
		assertTrue(numeroEtal >= 0);
	}

	@Test
	@DisplayName("Le premier étal pris est l'étal 0")
	void testPrendreEtal_premierEtal() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		int numeroEtal = controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		assertEquals(0, numeroEtal);
	}

	@Test
	@DisplayName("Plusieurs étals peuvent être pris par différents vendeurs")
	void testPrendreEtal_plusieurEtals() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);
		controlEmmenager.ajouterGaulois("Vitalstatistix", 8);

		int etal1 = controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		int etal2 = controlPrendreEtal.prendreEtal("Obelix", "fleurs", 3);
		int etal3 = controlPrendreEtal.prendreEtal("Vitalstatistix", "menhirs", 2);

		assertEquals(0, etal1);
		assertEquals(1, etal2);
		assertEquals(2, etal3);
	}

	@Test
	@DisplayName("Tous les étals disponibles peuvent être pris")
	void testPrendreEtal_tousEtalsDisponibles() {
		// Le village a 5 étals
		for (int i = 0; i < 5; i++) {
			controlEmmenager.ajouterGaulois("GAULOIS_" + i, 5);
		}

		for (int i = 0; i < 5; i++) {
			int numeroEtal = controlPrendreEtal.prendreEtal("GAULOIS_" + i, "produit" + i, 10);
			assertEquals(i, numeroEtal);
		}
	}

	@Test
	@DisplayName("Un étal ne peut pas être pris si aucun n'est disponible")
	void testPrendreEtal_aucunEtalDisponible() {
		// Remplir tous les étals (5 étals disponibles)
		for (int i = 0; i < 5; i++) {
			controlEmmenager.ajouterGaulois("GAULOIS_" + i, 5);
			controlPrendreEtal.prendreEtal("GAULOIS_" + i, "produit" + i, 10);
		}

		// Ajouter un 6ème gaulois et essayer de prendre un étal
		controlEmmenager.ajouterGaulois("GAULOIS_6", 5);
		int numeroEtal = controlPrendreEtal.prendreEtal("GAULOIS_6", "produit", 10);
		assertEquals(-1, numeroEtal);
	}

	@Test
	@DisplayName("Un vendeur peut prendre un étal avec un produit et une quantité")
	void testPrendreEtal_avecProduitEtQuantite() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		int numeroEtal = controlPrendreEtal.prendreEtal("Asterix", "poissons", 15);
		assertNotEquals(-1, numeroEtal);
	}

	@Test
	@DisplayName("Le chef peut également prendre un étal")
	void testPrendreEtal_chefPeut() {
		int numeroEtal = controlPrendreEtal.prendreEtal("Abraracourcix", "menhirs", 10);
		assertNotEquals(-1, numeroEtal);
	}
}