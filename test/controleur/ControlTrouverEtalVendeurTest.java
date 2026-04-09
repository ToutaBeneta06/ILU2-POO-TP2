package controleur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import villagegaulois.Etal;
import villagegaulois.Village;
import personnages.Chef;

/**
 * Tests unitaires pour ControlTrouverEtalVendeur.
 * Couvre : recherche d'étal, vendeurs, cas d'absence.
 */
@DisplayName("Tests du ControlTrouverEtalVendeur")
class ControlTrouverEtalVendeurTest {

	private Village village;
	private ControlTrouverEtalVendeur controlTrouverEtalVendeur;
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
		controlTrouverEtalVendeur = new ControlTrouverEtalVendeur(village);
		controlEmmenager = new ControlEmmenager(village);
	}

	// ---------------------------------------------------------------
	// Constructeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le constructeur retourne un objet non null")
	void testConstructor_retourneObjetNonNull() {
		assertNotNull(controlTrouverEtalVendeur);
	}

	// ---------------------------------------------------------------
	// trouverEtalVendeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un vendeur inexistant retourne null")
	void testTrouverEtalVendeur_vendeurInexistant() {
		Etal etal = controlTrouverEtalVendeur.trouverEtalVendeur("Inconnu");
		assertNull(etal);
	}

	@Test
	@DisplayName("Un vendeur qui n'a pas pris d'étal retourne null")
	void testTrouverEtalVendeur_vendeurSansEtal() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		Etal etal = controlTrouverEtalVendeur.trouverEtalVendeur("Asterix");
		assertNull(etal);
	}

	@Test
	@DisplayName("Un vendeur qui a pris un étal est trouvé")
	void testTrouverEtalVendeur_vendeurAvecEtal() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		Etal etal = controlTrouverEtalVendeur.trouverEtalVendeur("Asterix");
		assertNotNull(etal);
	}

	@Test
	@DisplayName("L'étal trouvé est occupé")
	void testTrouverEtalVendeur_etalOccupe() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		Etal etal = controlTrouverEtalVendeur.trouverEtalVendeur("Asterix");
		assertTrue(etal.isEtalOccupe());
	}

	@Test
	@DisplayName("Plusieurs vendeurs peuvent être trouvés individuellement")
	void testTrouverEtalVendeur_plusieurVendeurs() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		controlPrendreEtal.prendreEtal("Obelix", "fleurs", 3);

		Etal etalAsterix = controlTrouverEtalVendeur.trouverEtalVendeur("Asterix");
		Etal etalObelix = controlTrouverEtalVendeur.trouverEtalVendeur("Obelix");

		assertNotNull(etalAsterix);
		assertNotNull(etalObelix);
		assertNotSame(etalAsterix, etalObelix);
	}

	@Test
	@DisplayName("Le chef peut avoir un étal")
	void testTrouverEtalVendeur_chef() {
		controlPrendreEtal.prendreEtal("Abraracourcix", "menhirs", 10);
		Etal etal = controlTrouverEtalVendeur.trouverEtalVendeur("Abraracourcix");
		assertNotNull(etal);
	}

	@Test
	@DisplayName("Un druide peut avoir un étal")
	void testTrouverEtalVendeur_druide() {
		controlEmmenager.ajouterDruide("Panoramix", 10, 5, 7);
		controlPrendreEtal.prendreEtal("Panoramix", "potion", 1);
		Etal etal = controlTrouverEtalVendeur.trouverEtalVendeur("Panoramix");
		assertNotNull(etal);
	}

	@Test
	@DisplayName("La recherche est sensible à la casse")
	void testTrouverEtalVendeur_sensibleCasse() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);

		assertNotNull(controlTrouverEtalVendeur.trouverEtalVendeur("Asterix"));
		assertNull(controlTrouverEtalVendeur.trouverEtalVendeur("asterix"));
		assertNull(controlTrouverEtalVendeur.trouverEtalVendeur("ASTERIX"));
	}
}