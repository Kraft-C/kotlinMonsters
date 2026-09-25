package item

import dresseur.joueur
import monstre.IndividuMonstre
import kotlin.random.Random

class MonsterKube(
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double,
) : Item(id, nom, description), Utilisable {
    override fun utiliser(cible: IndividuMonstre): Boolean {
        println("Vous lancez le Monster Kube !")

        if (cible.entraineur != null) {
            println("Le monstre ne peut pas être capturé.")
            return false
        }

        val ratioVie = cible.pv.toDouble() / cible.pvMax
        var chanceEffective = chanceCapture * (1.5 - ratioVie)
        chanceEffective = chanceEffective.coerceAtLeast(5.0)

        val nbAleatoire = Random.nextDouble(0.0, 100.0)

        if (nbAleatoire < chanceEffective) {
            println("Le monstre est capturé !")
            println("Entrez un nouveau nom :")
            val nouveauNom = readlnOrNull()
            if (!nouveauNom.isNullOrBlank()) {
                cible.nom = nouveauNom
            }

            if (joueur.equipeMonstre.size >= 6) {
                joueur.boiteMonstre.add(cible)
            } else {
                joueur.equipeMonstre.add(cible)
            }

            cible.entraineur = joueur
            return true
        } else {
            println("Presque ! Le Kube n'a pas pu capturer le monstre !")
            return false
        }
    }
}
