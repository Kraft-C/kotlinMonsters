import dresseur.Entraineur
import dresseur.joueur
import item.MonsterKube
import monstre.EspeceMonstre
import monstre.IndividuMonstre
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MonsterKubeTest {

    private val especeTest = EspeceMonstre(
        id = 1,
        nom = "Springleaf",
        type = "Graine",
        baseAttaque = 9,
        baseDefense = 11,
        baseVitesse = 10,
        baseAttaqueSpe = 12,
        baseDefenseSpe = 14,
        basePv = 60,
        modAttaque = 6.5,
        modDefense = 9.0,
        modVitesse = 8.0,
        modAttaqueSpe = 7.0,
        modDefenseSpe = 10.0,
        modPv = 34.0
    )

    @Test
    fun testCannotCaptureIfAlreadyTrained() {
        val trainer = Entraineur(2, "Ondine", 50)
        val monstre = IndividuMonstre(1, "Springleaf", especeTest, entraineur = trainer)
        val kube = MonsterKube(1, "Kube", "Test", 100.0)

        val result = kube.utiliser(monstre)
        assertFalse(result)
        assertEquals(trainer, monstre.entraineur)
    }

    @Test
    fun testSuccessfulCaptureAddsToTeam() {
        joueur.equipeMonstre.clear()
        joueur.boiteMonstre.clear()

        val monstre = IndividuMonstre(1, "Sauvage", especeTest, entraineur = null)
        val kube = MonsterKube(1, "Master Kube", "Test", 1000.0) // 100% chance

        val result = kube.utiliser(monstre)
        assertTrue(result)
        assertEquals(joueur, monstre.entraineur)
        assertTrue(joueur.equipeMonstre.contains(monstre))
        assertEquals(1, joueur.equipeMonstre.size)
    }

    @Test
    fun testSuccessfulCaptureAddsToBoxWhenTeamFull() {
        joueur.equipeMonstre.clear()
        joueur.boiteMonstre.clear()

        for (i in 1..6) {
            joueur.equipeMonstre.add(IndividuMonstre(i, "Monstre$i", especeTest, entraineur = joueur))
        }

        val monstre = IndividuMonstre(7, "Sauvage7", especeTest, entraineur = null)
        val kube = MonsterKube(1, "Master Kube", "Test", 1000.0)

        val result = kube.utiliser(monstre)
        assertTrue(result)
        assertEquals(joueur, monstre.entraineur)
        assertEquals(6, joueur.equipeMonstre.size)
        assertTrue(joueur.boiteMonstre.contains(monstre))
        assertEquals(1, joueur.boiteMonstre.size)
    }
}
