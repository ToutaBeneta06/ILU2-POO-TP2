package controleur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import villagegaulois.Village;
import personnages.Chef;

/**
 * Tests unitaires pour ControlLibererEtal.
 * Couvre : libération d'étal, vendeurs absent, données retournées.
 */
@DisplayName("Tests du ControlLibererEtal")
class ControlLibererEtalTest {

	private Village village;
	private ControlLibererEtal controlLibererEtal;
	private ControlVerifierIdentite controlVerifierIdentite;
	private ControlPrendreEtal controlPrendreEtal;
	private ControlTrouverEtalVendeur controlTrouverEtalVendeur;
	private ControlEmmenager controlEmmenager;

	@BeforeEach
	public void initialiserSituation() {
		village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		controlVerifierIdentite = new ControlVerifierIdentite(village);
		controlPrendreEtal = new ControlPrendreEtal(village, controlVerifierIdentite);
		controlTrouverEtalVendeur = new ControlTrouverEtalVendeur(village);
		controlLibererEtal = new ControlLibererEtal(village, controlTrouverEtalVendeur);
		controlEmmenager = new ControlEmmenager(village);
	}

	// ---------------------------------------------------------------
	// Constructeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le constructeur retourne un objet non null")
	void testConstructor_retourneObjetNonNull() {
		assertNotNull(controlLibererEtal);
	}

	// ---------------------------------------------------------------
	// libererEtal
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un vendeur sans étal retourne null")
	void testLibererEtal_vendeurSansEtal() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		String[] donnees = controlLibererEtal.libererEtal("Asterix");
		assertNull(donnees);
	}

	@Test
	@DisplayName("Un vendeur inexistant retourne null")
	void testLibererEtal_vendeurInexistant() {
		String[] donnees = controlLibererEtal.libererEtal("Inconnu");
		assertNull(donnees);
	}

	@Test
	@DisplayName("Un vendeur peut libérer son étal")
	void testLibererEtal_vendeurAvecEtal() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		String[] donnees = controlLibererEtal.libererEtal("Asterix");
		assertNotNull(donnees);
	}

	@Test
	@DisplayName("Les données retournées après libération ne sont pas null")
	void testLibererEtal_donneesNonNull() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		String[] donnees = controlLibererEtal.libererEtal("Asterix");
		assertNotNull(donnees);
		assertTrue(donnees.length >= 0);
	}

	@Test
	@DisplayName("Plusieurs vendeurs peuvent libérer leurs étals")
	void testLibererEtal_plusieurVendeurs() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		controlPrendreEtal.prendreEtal("Obelix", "fleurs", 3);

		String[] donneesAsterix = controlLibererEtal.libererEtal("Asterix");
		String[] donneesObelix = controlLibererEtal.libererEtal("Obelix");

		assertNotNull(donneesAsterix);
		assertNotNull(donneesObelix);
	}

	@Test
	@DisplayName("Un étal libéré peut être réutilisé")
	void testLibererEtal_etalReutilisable() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);

		// Asterix prend l'étal 0
		int etalAsterix = controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		assertEquals(0, etalAsterix);

		// Asterix libère son étal
		controlLibererEtal.libererEtal("Asterix");

		// Obelix devrait pouvoir prendre l'étal 0
		int etalObelix = controlPrendreEtal.prendreEtal("Obelix", "fleurs", 3);
		assertEquals(0, etalObelix);
	}

	@Test
	@DisplayName("Le chef peut libérer un étal")
	void testLibererEtal_chef() {
		controlPrendreEtal.prendreEtal("Abraracourcix", "menhirs", 10);
		String[] donnees = controlLibererEtal.libererEtal("Abraracourcix");
		assertNotNull(donnees);
	}
}