package controleur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import personnages.Chef;
import villagegaulois.Village;

/**
 * Tests unitaires pour ControlVerifierIdentite.
 * Couvre : vérification d'identité, identification du chef, gaulois, druides.
 */
@DisplayName("Tests du ControlVerifierIdentite")
class ControlVerifierIdentiteTest {

	private Village village;
	private ControlVerifierIdentite controlVerifierIdentite;
	private ControlEmmenager controlEmmenager;

	@BeforeEach
	public void initialiserSituation() {
		village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		controlVerifierIdentite = new ControlVerifierIdentite(village);
		controlEmmenager = new ControlEmmenager(village);
	}

	// ---------------------------------------------------------------
	// Constructeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le constructeur retourne un objet non null")
	void testConstructor_retourneObjetNonNull() {
		assertNotNull(controlVerifierIdentite);
	}

	// ---------------------------------------------------------------
	// verifierIdentite
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le chef est une identité vérifiée")
	void testVerifierIdentite_chefEstVerifie() {
		assertTrue(controlVerifierIdentite.verifierIdentite("Abraracourcix"));
	}

	@Test
	@DisplayName("Un habitant inexistant n'est pas vérifié")
	void testVerifierIdentite_habitantInexistant() {
		assertFalse(controlVerifierIdentite.verifierIdentite("Inconnu"));
	}

	@Test
	@DisplayName("Un gaulois ajouté est vérifié")
	void testVerifierIdentite_gauloisAjoute() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		assertTrue(controlVerifierIdentite.verifierIdentite("Asterix"));
	}

	@Test
	@DisplayName("Un druide ajouté est vérifié")
	void testVerifierIdentite_druideAjoute() {
		controlEmmenager.ajouterDruide("Panoramix", 10, 5, 7);
		assertTrue(controlVerifierIdentite.verifierIdentite("Panoramix"));
	}

	@Test
	@DisplayName("Une chaîne vide n'est pas vérifiée")
	void testVerifierIdentite_chaineVide() {
		assertFalse(controlVerifierIdentite.verifierIdentite(""));
	}

	@Test
	@DisplayName("La vérification est sensible à la casse")
	void testVerifierIdentite_sensibleCasse() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		assertTrue(controlVerifierIdentite.verifierIdentite("Asterix"));
		assertFalse(controlVerifierIdentite.verifierIdentite("asterix"));
		assertFalse(controlVerifierIdentite.verifierIdentite("ASTERIX"));
	}

	@Test
	@DisplayName("Plusieurs habitants peuvent être vérifiés")
	void testVerifierIdentite_multiplesHabitants() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);
		controlEmmenager.ajouterDruide("Panoramix", 10, 5, 7);

		assertTrue(controlVerifierIdentite.verifierIdentite("Abraracourcix"));
		assertTrue(controlVerifierIdentite.verifierIdentite("Asterix"));
		assertTrue(controlVerifierIdentite.verifierIdentite("Obelix"));
		assertTrue(controlVerifierIdentite.verifierIdentite("Panoramix"));
		assertFalse(controlVerifierIdentite.verifierIdentite("Inconnu"));
	}
}