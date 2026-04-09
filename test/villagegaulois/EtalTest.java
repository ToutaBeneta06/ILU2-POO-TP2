package villagegaulois;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import personnages.Gaulois;

/**
 * Tests unitaires pour Etal.
 * Couvre : occupation, produits, achats, libération d'étal.
 */
@DisplayName("Tests de l'Etal")
class EtalTest {

	private Etal etal;
	private Gaulois vendeur;

	@BeforeEach
	public void initialiserSituation() {
		etal = new Etal();
		vendeur = new Gaulois("Asterix", 6);
	}

	// ---------------------------------------------------------------
	// Constructeur et état initial
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un étal est créé libre")
	void testConstructor_etalLibre() {
		assertFalse(etal.isEtalOccupe());
	}

	// ---------------------------------------------------------------
	// occuperEtal avec Gaulois
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un étal peut être occupé par un Gaulois")
	void testOccuperEtal_avecGaulois() {
		etal.occuperEtal(vendeur, "pain", 5);
		assertTrue(etal.isEtalOccupe());
	}

	@Test
	@DisplayName("Le vendeur est stocké après occupation")
	void testOccuperEtal_vendeurStocke() {
		etal.occuperEtal(vendeur, "pain", 5);
		assertEquals("Asterix", etal.getVendeur());
	}

	@Test
	@DisplayName("Le produit est stocké après occupation")
	void testOccuperEtal_produitStocke() {
		etal.occuperEtal(vendeur, "pain", 5);
		assertEquals("pain", etal.getProduit());
	}

	@Test
	@DisplayName("La quantité est stockée après occupation")
	void testOccuperEtal_quantiteStockee() {
		etal.occuperEtal(vendeur, "pain", 5);
		assertEquals(5, etal.getQuantite());
	}

	// ---------------------------------------------------------------
	// occuperEtal avec String
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un étal peut être occupé avec un nom de vendeur en String")
	void testOccuperEtal_avecString() {
		etal.occuperEtal("Asterix", "pain", 5);
		assertTrue(etal.isEtalOccupe());
	}

	@Test
	@DisplayName("Le vendeur String est stocké correctement")
	void testOccuperEtal_vendeurStringStocke() {
		etal.occuperEtal("Asterix", "pain", 5);
		assertEquals("Asterix", etal.getVendeur());
	}

	// ---------------------------------------------------------------
	// isEtalOccupe
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un étal non occupé retourne false")
	void testIsEtalOccupe_nonOccupe() {
		assertFalse(etal.isEtalOccupe());
	}

	@Test
	@DisplayName("Un étal occupé retourne true")
	void testIsEtalOccupe_occupe() {
		etal.occuperEtal(vendeur, "pain", 5);
		assertTrue(etal.isEtalOccupe());
	}

	// ---------------------------------------------------------------
	// contientProduit
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Un étal vide ne contient pas de produit")
	void testContientProduit_etalVide() {
		assertFalse(etal.contientProduit("pain"));
	}

	@Test
	@DisplayName("Un étal contient le produit vendu")
	void testContientProduit_contient() {
		etal.occuperEtal(vendeur, "pain", 5);
		assertTrue(etal.contientProduit("pain"));
	}

	@Test
	@DisplayName("Un étal ne contient pas un autre produit")
	void testContientProduit_neContientPas() {
		etal.occuperEtal(vendeur, "pain", 5);
		assertFalse(etal.contientProduit("fleurs"));
	}

	@Test
	@DisplayName("La recherche de produit est sensible à la casse")
	void testContientProduit_sensibleCasse() {
		etal.occuperEtal(vendeur, "pain", 5);
		assertTrue(etal.contientProduit("pain"));
		assertFalse(etal.contientProduit("Pain"));
		assertFalse(etal.contientProduit("PAIN"));
	}

	// ---------------------------------------------------------------
	// acheterProduit
	// ---------------------------------------------------------------

	@Test
	@DisplayName("On peut acheter une partie du produit")
	void testAcheterProduit_achatPartiel() {
		etal.occuperEtal(vendeur, "pain", 5);
		int quantiteAchetee = etal.acheterProduit(2);
		assertEquals(2, quantiteAchetee);
	}

	@Test
	@DisplayName("La quantité restante diminue après achat")
	void testAcheterProduit_quantiteRestante() {
		etal.occuperEtal(vendeur, "pain", 5);
		etal.acheterProduit(2);
		assertEquals(3, etal.getQuantite());
	}

	@Test
	@DisplayName("On ne peut pas acheter plus que disponible")
	void testAcheterProduit_quantiteDisponible() {
		etal.occuperEtal(vendeur, "pain", 5);
		int quantiteAchetee = etal.acheterProduit(10);
		assertEquals(5, quantiteAchetee);
	}

	@Test
	@DisplayName("Après achat de tout le stock, la quantité est 0")
	void testAcheterProduit_toutLeStock() {
		etal.occuperEtal(vendeur, "pain", 5);
		int quantiteAchetee = etal.acheterProduit(5);
		assertEquals(5, quantiteAchetee);
		assertEquals(0, etal.getQuantite());
	}

	@Test
	@DisplayName("On ne peut rien acheter si stock est vide")
	void testAcheterProduit_stockVide() {
		etal.occuperEtal(vendeur, "pain", 0);
		int quantiteAchetee = etal.acheterProduit(5);
		assertEquals(0, quantiteAchetee);
	}

	@Test
	@DisplayName("Plusieurs achats réduisent progressivement le stock")
	void testAcheterProduit_multiplexAchats() {
		etal.occuperEtal(vendeur, "pain", 10);
		etal.acheterProduit(2);
		etal.acheterProduit(3);
		etal.acheterProduit(1);
		assertEquals(4, etal.getQuantite());
	}

	// ---------------------------------------------------------------
	// libererEtal
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Libérer un étal le rend libre")
	void testLibererEtal_etalLibre() {
		etal.occuperEtal(vendeur, "pain", 5);
		etal.libererEtal();
		assertFalse(etal.isEtalOccupe());
	}

	@Test
	@DisplayName("Libérer un étal retourne null")
	void testLibererEtal_retourneNull() {
		etal.occuperEtal(vendeur, "pain", 5);
		String[] donnees = etal.libererEtal();
		assertNull(donnees);
	}

	@Test
	@DisplayName("Un étal libéré peut être réoccupé")
	void testLibererEtal_peutEtreReoccupe() {
		etal.occuperEtal(vendeur, "pain", 5);
		etal.libererEtal();
		
		Gaulois nouveauVendeur = new Gaulois("Obelix", 12);
		etal.occuperEtal(nouveauVendeur, "fleurs", 3);
		
		assertTrue(etal.isEtalOccupe());
		assertEquals("Obelix", etal.getVendeur());
	}

	// ---------------------------------------------------------------
	// etatEtal
	// ---------------------------------------------------------------

	@Test
	@DisplayName("L'état d'un étal vide indique qu'il n'est pas occupé")
	void testEtatEtal_etalVide() {
		String[] etat = etal.etatEtal();
		assertEquals("false", etat[0]);
	}

	@Test
	@DisplayName("L'état d'un étal occupé a 5 éléments")
	void testEtatEtal_etalOccupe() {
		etal.occuperEtal(vendeur, "pain", 5);
		String[] etat = etal.etatEtal();
		assertEquals(5, etat.length);
	}

	@Test
	@DisplayName("L'état contient true pour un étal occupé")
	void testEtatEtal_contientOccupation() {
		etal.occuperEtal(vendeur, "pain", 5);
		String[] etat = etal.etatEtal();
		assertEquals("true", etat[0]);
	}

	@Test
	@DisplayName("L'état contient le nom du vendeur")
	void testEtatEtal_contientVendeur() {
		etal.occuperEtal(vendeur, "pain", 5);
		String[] etat = etal.etatEtal();
		assertEquals("Asterix", etat[1]);
	}

	@Test
	@DisplayName("L'état contient le produit")
	void testEtatEtal_contientProduit() {
		etal.occuperEtal(vendeur, "pain", 5);
		String[] etat = etal.etatEtal();
		assertEquals("pain", etat[2]);
	}

	@Test
	@DisplayName("L'état contient la quantité initiale")
	void testEtatEtal_contientQuantiteInitiale() {
		etal.occuperEtal(vendeur, "pain", 5);
		String[] etat = etal.etatEtal();
		assertEquals("5", etat[3]);
	}

	@Test
	@DisplayName("L'état contient la quantité vendue (0 au départ)")
	void testEtatEtal_contientQuantiteVendue() {
		etal.occuperEtal(vendeur, "pain", 5);
		String[] etat = etal.etatEtal();
		assertEquals("0", etat[4]);
	}

	@Test
	@DisplayName("L'état reflète les achats effectués")
	void testEtatEtal_refleteAchats() {
		etal.occuperEtal(vendeur, "pain", 5);
		etal.acheterProduit(2);
		String[] etat = etal.etatEtal();
		assertEquals("2", etat[4]); // 2 vendus
	}
}