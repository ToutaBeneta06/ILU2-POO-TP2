package villagegaulois;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import controleur.ControlVerifierIdentite;
import controleur.ControlPrendreEtal;
import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import personnages.Personnage;

/**
 * Tests unitaires pour Village avec 100% de coverage.
 * Couvre : création, habitants, étals, marché, et toutes les méthodes.
 */
@DisplayName("Tests du Village - 100% Coverage")
class VillageTest {

	private Village village;
	private Chef chef;
	private ControlVerifierIdentite controlVerifierIdentite;
	private ControlPrendreEtal controlPrendreEtal;

	@BeforeEach
	public void initialiserSituation() {
		village = new Village("le village des irréductibles", 10, 5);
		chef = new Chef("Abraracourcix", 10, village);
		village.setChef(chef);
		controlVerifierIdentite = new ControlVerifierIdentite(village);
		controlPrendreEtal = new ControlPrendreEtal(village, controlVerifierIdentite);
	}

	// ---------------------------------------------------------------
	// Constructeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un village est créé avec un nom")
	void testConstructor_avecNom() {
		assertNotNull(village);
		assertEquals("le village des irréductibles", village.getNom());
	}

	@Test
	@DisplayName("Un village a le bon nombre d'étals")
	void testConstructor_nbEtals() {
		assertEquals(5, village.donnerNbEtal());
	}

	// ---------------------------------------------------------------
	// getNom
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le nom du village est retourné correctement")
	void testGetNom() {
		assertEquals("le village des irréductibles", village.getNom());
	}

	// ---------------------------------------------------------------
	// setChef
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le chef est reconnu comme habitant")
	void testSetChef_estHabitant() {
		Personnage habitant = village.trouverHabitant("Abraracourcix");
		assertNotNull(habitant);
		assertEquals("Abraracourcix", habitant.getNom());
	}

	// ---------------------------------------------------------------
	// ajouterHabitant (Gaulois)
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un gaulois peut être ajouté au village")
	void testAjouterHabitant_gaulois() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		assertNotNull(village.trouverHabitant("Asterix"));
	}

	@Test
	@DisplayName("Plusieurs gaulois peuvent être ajoutés")
	void testAjouterHabitant_plusieurGaulois() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		Gaulois obelix = new Gaulois("Obelix", 12);
		village.ajouterHabitant(asterix);
		village.ajouterHabitant(obelix);

		assertNotNull(village.trouverHabitant("Asterix"));
		assertNotNull(village.trouverHabitant("Obelix"));
	}

	@Test
	@DisplayName("La limite de villageois est respectée (capacité 10)")
	void testAjouterHabitant_limiteRespectee() {
		for (int i = 0; i < 10; i++) {
			Gaulois gaulois = new Gaulois("GAULOIS_" + i, 5 + i);
			village.ajouterHabitant(gaulois);
		}

		for (int i = 0; i < 10; i++) {
			assertNotNull(village.trouverHabitant("GAULOIS_" + i));
		}
	}

	@Test
	@DisplayName("Un gaulois au-delà de la capacité n'est pas ajouté")
	void testAjouterHabitant_depassementIgnore() {
		for (int i = 0; i < 10; i++) {
			Gaulois gaulois = new Gaulois("GAULOIS_" + i, 5);
			village.ajouterHabitant(gaulois);
		}

		Gaulois surplus = new Gaulois("SURPLUS", 5);
		village.ajouterHabitant(surplus);

		assertNull(village.trouverHabitant("SURPLUS"));
	}

	// ---------------------------------------------------------------
	// ajouterHabitant (Personnage)
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un personnage Gaulois peut être ajouté via Personnage")
	void testAjouterHabitant_personnageGaulois() {
		Personnage asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		assertNotNull(village.trouverHabitant("Asterix"));
	}

	@Test
	@DisplayName("Un druide peut être ajouté au village")
	void testAjouterHabitant_druide() {
		Druide panoramix = new Druide("Panoramix", 10, 5, 7);
		village.ajouterHabitant((Personnage) panoramix);
		assertNotNull(village.trouverHabitant("Panoramix"));
	}

	@Test
	@DisplayName("Un personnage non-Gaulois n'est pas ajouté")
	void testAjouterHabitant_personnageNonGaulois() {
		Personnage chef = new Chef("Vercingétorix", 15, village);
		village.ajouterHabitant(chef); // Ne devrait pas être ajouté
		// Le chef est différent d'Abraracourcix
		assertNull(village.trouverHabitant("Vercingétorix"));
	}

	// ---------------------------------------------------------------
	// trouverHabitant
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le chef est trouvé par son nom")
	void testTrouverHabitant_chef() {
		Personnage habitant = village.trouverHabitant("Abraracourcix");
		assertNotNull(habitant);
	}

	@Test
	@DisplayName("Un habitant inexistant retourne null")
	void testTrouverHabitant_inexistant() {
		Personnage habitant = village.trouverHabitant("Inconnu");
		assertNull(habitant);
	}

	@Test
	@DisplayName("Un gaulois ajouté est trouvé")
	void testTrouverHabitant_gauloisAjoute() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		Personnage habitant = village.trouverHabitant("Asterix");
		assertNotNull(habitant);
	}

	@Test
	@DisplayName("La recherche est sensible à la casse")
	void testTrouverHabitant_sensibleCasse() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		
		assertNotNull(village.trouverHabitant("Asterix"));
		assertNull(village.trouverHabitant("asterix"));
	}

	// ---------------------------------------------------------------
	// donnerVillageois
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un village avec chef retourne son nom")
	void testDonnerVillageois_chefSeul() {
		String[] villageois = village.donnerVillageois();
		assertEquals(1, villageois.length);
		assertEquals("Abraracourcix", villageois[0]);
	}

	@Test
	@DisplayName("Les gaulois apparaissent dans la liste")
	void testDonnerVillageois_contientGaulois() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		
		String[] villageois = village.donnerVillageois();
		assertTrue(containsValue(villageois, "Asterix"));
	}

	@Test
	@DisplayName("Un druide est marqué avec 'le druide'")
	void testDonnerVillageois_druideMarque() {
		Druide panoramix = new Druide("Panoramix", 10, 5, 7);
		village.ajouterHabitant((Personnage) panoramix);
		
		String[] villageois = village.donnerVillageois();
		assertTrue(containsValue(villageois, "le druide Panoramix"));
	}

	@Test
	@DisplayName("Gaulois et druides sont dans la liste")
	void testDonnerVillageois_gauloisEtDruide() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		Druide panoramix = new Druide("Panoramix", 10, 5, 7);
		village.ajouterHabitant(asterix);
		village.ajouterHabitant((Personnage) panoramix);
		
		String[] villageois = village.donnerVillageois();
		assertEquals(3, villageois.length); // Chef + Gaulois + Druide
		assertTrue(containsValue(villageois, "Asterix"));
		assertTrue(containsValue(villageois, "le druide Panoramix"));
	}

	// ---------------------------------------------------------------
	// donnerNbEtal
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le nombre d'étals est correct")
	void testDonnerNbEtal() {
		assertEquals(5, village.donnerNbEtal());
	}

	// ---------------------------------------------------------------
	// trouverEtalLibre
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un village vide a un étal libre à l'indice 0")
	void testTrouverEtalLibre_premierEtal() {
		int numeroEtal = village.trouverEtalLibre();
		assertEquals(0, numeroEtal);
	}

	@Test
	@DisplayName("Après occupation, le prochain étal libre est différent")
	void testTrouverEtalLibre_apresOccupation() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		
		int numeroEtal = village.trouverEtalLibre();
		assertEquals(1, numeroEtal);
	}

	// ---------------------------------------------------------------
	// getEtals
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Le village retourne ses étals")
	void testGetEtals_nonNull() {
		Etal[] etals = village.getEtals();
		assertNotNull(etals);
	}

	@Test
	@DisplayName("Le nombre d'étals retournés est correct")
	void testGetEtals_nombre() {
		Etal[] etals = village.getEtals();
		assertEquals(5, etals.length);
	}

	// ---------------------------------------------------------------
	// occuperEtal
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un étal peut être occupé")
	void testOccuperEtal_avecGaulois() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		village.occuperEtal(0, "Asterix", "pain", 5);
		
		Etal[] etals = village.getEtals();
		assertTrue(etals[0].isEtalOccupe());
	}

	@Test
	@DisplayName("Un étal peut être occupé avec un nom String non trouvé")
	void testOccuperEtal_avecStringNonTrouve() {
		village.occuperEtal(0, "Inconnu", "pain", 5);
		
		Etal[] etals = village.getEtals();
		assertTrue(etals[0].isEtalOccupe());
	}

	// ---------------------------------------------------------------
	// trouverEtalVendeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un étal de vendeur peut être trouvé")
	void testTrouverEtalVendeur_vendeurExistant() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		
		Etal etal = village.trouverEtalVendeur("Asterix");
		assertNotNull(etal);
	}

	@Test
	@DisplayName("Un vendeur inexistant retourne null")
	void testTrouverEtalVendeur_vendeurInexistant() {
		Etal etal = village.trouverEtalVendeur("Inconnu");
		assertNull(etal);
	}

	// ---------------------------------------------------------------
	// rechercherEtalVide
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un village vide a un étal vide")
	void testRechercheretalVide_existant() {
		boolean etalVide = village.rechercherEtalVide();
		assertTrue(etalVide);
	}

	@Test
	@DisplayName("Un village plein n'a pas d'étal vide")
	void testRechercherEtalVide_nonExistant() {
		// Remplir tous les étals
		for (int i = 0; i < 5; i++) {
			Gaulois gaulois = new Gaulois("GAULOIS_" + i, 5);
			village.ajouterHabitant(gaulois);
			controlPrendreEtal.prendreEtal("GAULOIS_" + i, "produit" + i, 10);
		}
		
		boolean etalVide = village.rechercherEtalVide();
		assertFalse(etalVide);
	}

	// ---------------------------------------------------------------
	// rechercherVendeursProduit
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Les vendeurs d'un produit peuvent être trouvés")
	void testRechercherVendeursProduit_vendeurExistant() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		Gaulois obelix = new Gaulois("Obelix", 12);
		village.ajouterHabitant(asterix);
		village.ajouterHabitant(obelix);
		
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		controlPrendreEtal.prendreEtal("Obelix", "pain", 3);
		
		Gaulois[] vendeurs = village.rechercherVendeursProduit("pain");
		assertNotNull(vendeurs);
		assertEquals(2, vendeurs.length);
	}

	@Test
	@DisplayName("Un produit absent retourne null")
	void testRechercherVendeursProduit_produitAbsent() {
		Gaulois[] vendeurs = village.rechercherVendeursProduit("pain");
		assertNull(vendeurs);
	}

	// ---------------------------------------------------------------
	// rechercherEtal
	// ---------------------------------------------------------------

	@Test
	@DisplayName("L'étal d'un vendeur peut être recherché")
	void testRechercherEtal_vendeurExistant() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		
		Etal etal = village.rechercherEtal(asterix);
		assertNotNull(etal);
	}

	@Test
	@DisplayName("Un vendeur sans étal retourne null")
	void testRechercherEtal_vendeurSansEtal() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		
		Etal etal = village.rechercherEtal(asterix);
		assertNull(etal);
	}

	// ---------------------------------------------------------------
	// partirVendeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un vendeur peut partir et libérer son étal")
	void testPartirVendeur_vendeurExistant() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		
		Etal etalAvant = village.rechercherEtal(asterix);
		assertTrue(etalAvant.isEtalOccupe());
		
		village.partirVendeur(asterix);
		
		Etal etalApres = village.rechercherEtal(asterix);
		assertNull(etalApres);
	}

	@Test
	@DisplayName("Un vendeur sans étal ne change rien")
	void testPartirVendeur_vendeurSansEtal() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		
		village.partirVendeur(asterix); // Ne doit pas lever d'exception
		assertNull(village.rechercherEtal(asterix));
	}

	// ---------------------------------------------------------------
	// donnerEtatMarche
	// ---------------------------------------------------------------

	@Test
	@DisplayName("L'état du marché vide retourne un tableau vide")
	void testDonnerEtatMarche_marcheVide() {
		String[] etat = village.donnerEtatMarche();
		assertNotNull(etat);
		assertEquals(0, etat.length);
	}

	@Test
	@DisplayName("L'état du marché retourne les infos des vendeurs")
	void testDonnerEtatMarche_avecVendeur() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		controlPrendreEtal.prendreEtal("Asterix", "pain", 5);
		
		String[] etat = village.donnerEtatMarche();
		assertNotNull(etat);
		assertEquals(3, etat.length); // nomVendeur, quantite, produit
	}

	// ---------------------------------------------------------------
	// installerVendeur
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un vendeur peut être installé sur un étal")
	void testInstallerVendeur_succes() {
		Gaulois asterix = new Gaulois("Asterix", 6);
		village.ajouterHabitant(asterix);
		
		int numeroEtal = village.installerVendeur(asterix, "pain", 5);
		assertTrue(numeroEtal >= 0);
	}

	@Test
	@DisplayName("L'installation retourne -1 si pas d'étal libre")
	void testInstallerVendeur_pasEtal() {
		// Remplir tous les étals
		for (int i = 0; i < 5; i++) {
			Gaulois gaulois = new Gaulois("GAULOIS_" + i, 5);
			village.ajouterHabitant(gaulois);
			village.installerVendeur(gaulois, "produit" + i, 10);
		}
		
		Gaulois surplus = new Gaulois("SURPLUS", 5);
		village.ajouterHabitant(surplus);
		
		int numeroEtal = village.installerVendeur(surplus, "extra", 5);
		assertEquals(-1, numeroEtal);
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