package controleur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import villagegaulois.Village;
import personnages.Chef;

/**
 * Tests unitaires pour ControlAfficherVillage.
 * Couvre : affichage du village, noms des villageois, informations du village.
 */
@DisplayName("Tests du ControlAfficherVillage")
class ControlAfficherVillageTest {

	private Village village;
	private ControlAfficherVillage controlAfficherVillage;
	private ControlEmmenager controlEmmenager;

	@BeforeEach
	public void initialiserSituation() {
		village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		controlAfficherVillage = new ControlAfficherVillage(village);
		controlEmmenager = new ControlEmmenager(village);
	}

	// ---------------------------------------------------------------
	// Constructeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le constructeur retourne un objet non null")
	void testConstructor_retourneObjetNonNull() {
		assertNotNull(controlAfficherVillage);
	}

	// ---------------------------------------------------------------
	// donnerNomsVillageois
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le village avec seulement le chef retourne son nom")
	void testDonnerNomsVillageois_chefSeul() {
		String[] noms = controlAfficherVillage.donnerNomsVillageois();
		assertNotNull(noms);
		assertEquals(1, noms.length);
		assertTrue(containsValue(noms, "Abraracourcix"));
	}

	@Test
	@DisplayName("Un gaulois ajouté apparaît dans la liste")
	void testDonnerNomsVillageois_avecGaulois() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		String[] noms = controlAfficherVillage.donnerNomsVillageois();
		assertEquals(2, noms.length);
		assertTrue(containsValue(noms, "Abraracourcix"));
		assertTrue(containsValue(noms, "Asterix"));
	}

	@Test
	@DisplayName("Plusieurs gaulois apparaissent dans la liste")
	void testDonnerNomsVillageois_plusieurGaulois() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterGaulois("Obelix", 12);
		controlEmmenager.ajouterGaulois("Vitalstatistix", 8);

		String[] noms = controlAfficherVillage.donnerNomsVillageois();
		assertEquals(4, noms.length); // Chef + 3 gaulois
		assertTrue(containsValue(noms, "Abraracourcix"));
		assertTrue(containsValue(noms, "Asterix"));
		assertTrue(containsValue(noms, "Obelix"));
		assertTrue(containsValue(noms, "Vitalstatistix"));
	}

	@Test
	@DisplayName("Un druide est marqué avec 'le druide'")
	void testDonnerNomsVillageois_druide() {
		controlEmmenager.ajouterDruide("Panoramix", 10, 5, 7);
		String[] noms = controlAfficherVillage.donnerNomsVillageois();
		assertEquals(2, noms.length); // Chef + Druide
		assertTrue(containsValue(noms, "le druide Panoramix"));
	}

	@Test
	@DisplayName("Gaulois et druides coexistent dans la liste")
	void testDonnerNomsVillageois_gauloisEtDruide() {
		controlEmmenager.ajouterGaulois("Asterix", 6);
		controlEmmenager.ajouterDruide("Panoramix", 10, 5, 7);

		String[] noms = controlAfficherVillage.donnerNomsVillageois();
		assertEquals(3, noms.length); // Chef + Gaulois + Druide
		assertTrue(containsValue(noms, "Abraracourcix"));
		assertTrue(containsValue(noms, "Asterix"));
		assertTrue(containsValue(noms, "le druide Panoramix"));
	}

	// ---------------------------------------------------------------
	// donnerNomVillage
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le nom du village est retourné correctement")
	void testDonnerNomVillage() {
		String nomVillage = controlAfficherVillage.donnerNomVillage();
		assertEquals("le village des irréductibles", nomVillage);
	}

	@Test
	@DisplayName("Le nom du village n'est pas null")
	void testDonnerNomVillage_nonNull() {
		String nomVillage = controlAfficherVillage.donnerNomVillage();
		assertNotNull(nomVillage);
	}

	// ---------------------------------------------------------------
	// donnerNbEtals
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le nombre d'étals du village est retourné correctement")
	void testDonnerNbEtals() {
		int nbEtals = controlAfficherVillage.donnerNbEtals();
		assertEquals(5, nbEtals);
	}

	@Test
	@DisplayName("Le nombre d'étals est positif")
	void testDonnerNbEtals_positif() {
		int nbEtals = controlAfficherVillage.donnerNbEtals();
		assertTrue(nbEtals > 0);
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