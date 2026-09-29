import dresseur.Entraineur
import dresseur.joueur
import item.Item
import item.MonsterKube
import jeu.CombatMonstre
import monstre.EspeceMonstre
import monstre.IndividuMonstre
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream
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

    @Test
    fun testAfficheCombat() {
        val trainer = Entraineur(1, "Testeur", 100)
        val m1 = IndividuMonstre(1, "Springleaf", especeTest, entraineur = trainer)
        val sauvage = IndividuMonstre(2, "Springleaf", especeTest, entraineur = null)
        val combat = CombatMonstre(m1, sauvage)
        combat.round = 3

        val originalOut = System.out
        val outContent = ByteArrayOutputStream()
        try {
            System.setOut(PrintStream(outContent))
            combat.afficheCombat()
        } finally {
            System.setOut(originalOut)
        }

        val output = outContent.toString()
        assertTrue(output.contains("======== Début Round : 3 ========"))
        assertTrue(output.contains("Niveau : ${sauvage.niveau}"))
        assertTrue(output.contains("PV : ${sauvage.pv} / ${sauvage.pvMax}"))
        assertTrue(output.contains("Niveau : ${m1.niveau}"))
        assertTrue(output.contains("PV : ${m1.pv} / ${m1.pvMax}"))
    }
}
