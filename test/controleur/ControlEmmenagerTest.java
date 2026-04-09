package controleur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import personnages.Chef;
import villagegaulois.Village;

/**
 * Tests unitaires pour ControlEmmenager.
 * Couvre : constructeur, isHabitant, ajouterGaulois, ajouterDruide,
 *          capacité maximale du village, cas limites.
 */
class ControlEmmenagerTest {

    private Village village;
    private Chef abraracourcix;
    private ControlEmmenager controlEmmenager;

    @BeforeEach
    public void initialiserSituation() {
        village = new Village("le village des irréductibles", 10, 5);
        abraracourcix = new Chef("Abraracourcix", 10, village);
        village.setChef(abraracourcix);
        controlEmmenager = new ControlEmmenager(village);
    }

    // ---------------------------------------------------------------
    // Constructeur
    // ---------------------------------------------------------------

    @Test
    void testControlEmmenager_constructeurRetourneObjetNonNull() {
        assertNotNull(controlEmmenager, "Le constructeur ne doit pas retourner null");
    }

    // ---------------------------------------------------------------
    // isHabitant
    // ---------------------------------------------------------------

    @Test
    void testIsHabitant_chefEstHabitant() {
        // Le chef est défini dans le village → il doit être reconnu
        assertTrue(controlEmmenager.isHabitant("Abraracourcix"),
                "Le chef doit être reconnu comme habitant");
    }

    @Test
    void testIsHabitant_habitantInexistant() {
        assertFalse(controlEmmenager.isHabitant("Inconnu"),
                "Un nom absent ne doit pas être reconnu");
    }

    @Test
    void testIsHabitant_apresAjoutGaulois() {
        controlEmmenager.ajouterGaulois("Bonemine", 10);
        assertTrue(controlEmmenager.isHabitant("Bonemine"),
                "Un gaulois ajouté doit être reconnu comme habitant");
    }

    @Test
    void testIsHabitant_apresAjoutDruide() {
        controlEmmenager.ajouterDruide("Panoramix", 10, 1, 5);
        assertTrue(controlEmmenager.isHabitant("Panoramix"),
                "Un druide ajouté doit être reconnu comme habitant");
    }

    @Test
    void testIsHabitant_nomVideNonReconnu() {
        assertFalse(controlEmmenager.isHabitant(""),
                "Une chaîne vide ne doit pas être reconnue comme habitant");
    }

    // ---------------------------------------------------------------
    // ajouterGaulois
    // ---------------------------------------------------------------

    @Test
    void testAjouterGaulois_gauloisAbsentAvantAjout() {
        assertFalse(controlEmmenager.isHabitant("Bonemine"));
    }

    @Test
    void testAjouterGaulois_gauloisPresentApresAjout() {
        controlEmmenager.ajouterGaulois("Bonemine", 10);
        assertTrue(controlEmmenager.isHabitant("Bonemine"));
    }

    @Test
    void testAjouterGaulois_plusieursGaulois() {
        controlEmmenager.ajouterGaulois("Bonemine", 10);
        controlEmmenager.ajouterGaulois("Asterix", 8);
        controlEmmenager.ajouterGaulois("Obelix", 15);

        assertTrue(controlEmmenager.isHabitant("Bonemine"));
        assertTrue(controlEmmenager.isHabitant("Asterix"));
        assertTrue(controlEmmenager.isHabitant("Obelix"));
        assertFalse(controlEmmenager.isHabitant("Getafix"));
    }

    @Test
    void testAjouterGaulois_capaciteMaximaleRespectee() {
        // Le village a une capacité de 10 villageois
        // Le chef occupe déjà la place de chef (pas dans le tableau villageois)
        // On ajoute 10 gaulois (capacité max)
        for (int i = 0; i < 10; i++) {
            controlEmmenager.ajouterGaulois("GAULOIS_" + i, 5);
        }
        // Les 10 premiers doivent être présents
        for (int i = 0; i < 10; i++) {
            assertTrue(controlEmmenager.isHabitant("GAULOIS_" + i),
                    "GAULOIS_" + i + " devrait être habitant");
        }
    }

    @Test
    void testAjouterGaulois_depassementCapaciteIgnore() {
        // On remplit le village à ras bord
        for (int i = 0; i < 10; i++) {
            controlEmmenager.ajouterGaulois("GAULOIS_" + i, 5);
        }
        // Tentative d'ajout au-delà de la capacité → doit être ignoré sans exception
        controlEmmenager.ajouterGaulois("SURPLUS", 5);
        assertFalse(controlEmmenager.isHabitant("SURPLUS"),
                "Un gaulois ajouté en surnombre ne doit pas être habitant");
    }

    // ---------------------------------------------------------------
    // ajouterDruide
    // ---------------------------------------------------------------

    @Test
    void testAjouterDruide_druideAbsentAvantAjout() {
        assertFalse(controlEmmenager.isHabitant("Panoramix"));
    }

    @Test
    void testAjouterDruide_druidePresentApresAjout() {
        controlEmmenager.ajouterDruide("Panoramix", 10, 1, 5);
        assertTrue(controlEmmenager.isHabitant("Panoramix"));
    }

    @Test
    void testAjouterDruide_plusieursCapacitesDePotions() {
        // Un druide avec des effets de potion différents
        controlEmmenager.ajouterDruide("Panoramix", 10, 1, 5);
        controlEmmenager.ajouterDruide("MiniDruide", 5, 2, 3);

        assertTrue(controlEmmenager.isHabitant("Panoramix"));
        assertTrue(controlEmmenager.isHabitant("MiniDruide"));
    }

    // ---------------------------------------------------------------
    // Combinaisons gaulois + druide
    // ---------------------------------------------------------------

    @Test
    void testMelange_gauloisEtDruideCohabitent() {
        controlEmmenager.ajouterGaulois("Bonemine", 10);
        controlEmmenager.ajouterDruide("Panoramix", 10, 1, 5);

        assertTrue(controlEmmenager.isHabitant("Bonemine"));
        assertTrue(controlEmmenager.isHabitant("Panoramix"));
        assertFalse(controlEmmenager.isHabitant("Obelix"));
    }
}