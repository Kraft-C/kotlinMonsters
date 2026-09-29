import dresseur.Entraineur
import dresseur.joueur
import item.Item
import item.MonsterKube
import jeu.CombatMonstre
import monstre.EspeceMonstre
import monstre.IndividuMonstre
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CombatMonstreTest {

    private val especeTest = EspeceMonstre(
        id = 1,
        nom = "Springleaf",
        type = "Graine",
        baseAttaque = 10,
        baseDefense = 10,
        baseVitesse = 10,
        baseAttaqueSpe = 10,
        baseDefenseSpe = 10,
        basePv = 50,
        modAttaque = 5.0,
        modDefense = 5.0,
        modVitesse = 5.0,
        modAttaqueSpe = 5.0,
        modDefenseSpe = 5.0,
        modPv = 20.0
    )

    private fun withInput(input: String, block: () -> Unit) {
        val originalIn: InputStream = System.`in`
        try {
            System.setIn(ByteArrayInputStream(input.toByteArray()))
            block()
        } finally {
            System.setIn(originalIn)
        }
    }

    @Test
    fun testActionJoueurGameOver() {
        val trainer = Entraineur(1, "Testeur", 100)
        val m1 = IndividuMonstre(1, "M1", especeTest, entraineur = trainer)
        m1.pv = 0
        trainer.equipeMonstre.add(m1)
        val sauvage = IndividuMonstre(2, "Sauvage", especeTest, entraineur = null)

        val combat = CombatMonstre(m1, sauvage)
        val result = combat.actionJoueur()
        assertFalse(result)
    }

    @Test
    fun testActionJoueurAttaquer() {
        val trainer = Entraineur(1, "Testeur", 100)
        val m1 = IndividuMonstre(1, "M1", especeTest, entraineur = trainer)
        trainer.equipeMonstre.add(m1)
        val sauvage = IndividuMonstre(2, "Sauvage", especeTest, entraineur = null)
        val pvAvant = sauvage.pv

        val combat = CombatMonstre(m1, sauvage)
        withInput("1\n") {
            val result = combat.actionJoueur()
            assertTrue(result)
            assertTrue(sauvage.pv < pvAvant)
        }
    }

    @Test
    fun testActionJoueurUtiliserObjetCaptureReussie() {
        joueur.sacAItems.clear()
        joueur.equipeMonstre.clear()
        val m1 = IndividuMonstre(1, "M1", especeTest, entraineur = joueur)
        joueur.equipeMonstre.add(m1)
        val kube = MonsterKube(1, "MasterKube", "Capture à 100%", 1000.0)
        joueur.sacAItems.add(kube)
        val sauvage = IndividuMonstre(2, "Sauvage", especeTest, entraineur = null)

        val combat = CombatMonstre(m1, sauvage)
        withInput("2\n0\nNouveauNom\n") {
            val result = combat.actionJoueur()
            assertFalse(result)
            assertEquals(joueur, sauvage.entraineur)
        }
    }

    @Test
    fun testActionJoueurUtiliserObjetNonUtilisable() {
        val trainer = Entraineur(1, "Testeur", 100)
        val m1 = IndividuMonstre(1, "M1", especeTest, entraineur = trainer)
        trainer.equipeMonstre.add(m1)
        val nonUtilisable = Item(1, "Pierre", "Juste une pierre")
        trainer.sacAItems.add(nonUtilisable)
        val sauvage = IndividuMonstre(2, "Sauvage", especeTest, entraineur = null)

        val combat = CombatMonstre(m1, sauvage)
        withInput("2\n0\n") {
            val result = combat.actionJoueur()
            assertTrue(result)
        }
    }

    @Test
    fun testActionJoueurChangerDeMonstreValide() {
        val trainer = Entraineur(1, "Testeur", 100)
        val m1 = IndividuMonstre(1, "M1", especeTest, entraineur = trainer)
        val m2 = IndividuMonstre(2, "M2", especeTest, entraineur = trainer)
        trainer.equipeMonstre.add(m1)
        trainer.equipeMonstre.add(m2)
        val sauvage = IndividuMonstre(3, "Sauvage", especeTest, entraineur = null)

        val combat = CombatMonstre(m1, sauvage)
        withInput("3\n1\n") {
            val result = combat.actionJoueur()
            assertTrue(result)
            assertEquals(m2, combat.monstreJoueur)
        }
    }

    @Test
    fun testActionJoueurChangerDeMonstreKO() {
        val trainer = Entraineur(1, "Testeur", 100)
        val m1 = IndividuMonstre(1, "M1", especeTest, entraineur = trainer)
        val m2 = IndividuMonstre(2, "M2", especeTest, entraineur = trainer)
        m2.pv = 0
        trainer.equipeMonstre.add(m1)
        trainer.equipeMonstre.add(m2)
        val sauvage = IndividuMonstre(3, "Sauvage", especeTest, entraineur = null)

        val combat = CombatMonstre(m1, sauvage)
        withInput("3\n1\n") {
            val result = combat.actionJoueur()
            assertTrue(result)
            assertEquals(m1, combat.monstreJoueur)
        }
    }
}
