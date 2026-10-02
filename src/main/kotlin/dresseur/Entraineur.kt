package dresseur

import item.Item
import monstre.IndividuMonstre

var joueur = Entraineur(1, "Sacha", 100)

/**
 * Représente un entraîneur dans le contexte du jeu.
 *
 * Un entraîneur est responsable de gérer une équipe de monstres, une boîte pour stocker des monstres supplémentaires
 * et un sac contenant des objets appelés MonsterKubes. L'entraîneur a également une somme d'argent associée.
 *
 * @property id L'identifiant unique de l'entraîneur.
 * @property nom Le nom de l'entraîneur.
 * @property argents La quantité d'argent en possession de l'entraîneur.

 */
class Entraineur(
    var id: Int,
    var nom: String,
    var argents:Int,
    var equipeMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var boiteMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var sacAItems: MutableList<Item> = mutableListOf()

) {
    /**
     * Affiche les détails de l'entraîneur, y compris son nom et la quantité d'argent en sa possession.
     *
     * Cette méthode affiche les informations de l'entraîneur sous la forme de deux lignes :
     * 1. Le nom de l'entraîneur.
     * 2. La somme d'argent qu'il possède.
     */
    fun afficheDetail(){
        println("Dresseur : ${this.nom}")
        println("Argents: ${this.argents} ")
    }

    /**
     * Soigne tous les monstres de l'équipe.
     *
     * Pour chaque monstre de l'équipe, ses PV actuels sont remis à ses PV maximum.
     */
    fun soigneEquipe() {
        equipeMonstre.forEach { monstre ->
            monstre.pv = monstre.pvMax
        }
    }

    /**
     * Permet de choisir un monstre valide dans l'équipe.
     * - Si un seul monstre est en état de combattre, il est choisi automatiquement.
     * - Sinon, on affiche un menu pour choisir.
     *
     * @return le monstre choisi
     */
    fun choisirMonstre(): IndividuMonstre {
        val monstresVivants = equipeMonstre.filter { monstre -> monstre.pv > 0 }

        if (monstresVivants.size == 1) {
            return monstresVivants.first()
        }

        println("Choisir un monstre de l'équipe")

        var choixMonstre: IndividuMonstre? = null

        while (choixMonstre == null) {
            monstresVivants.forEachIndexed { index, monstre ->
                println("${index + 1} - ${monstre.nom} | PV : ${monstre.pv} / ${monstre.pvMax}")
            }

            println("Entrez le numéro du monstre :")
            val choixIndex = readlnOrNull()?.toIntOrNull()?.minus(1)

            if (choixIndex != null && choixIndex in monstresVivants.indices) {
                choixMonstre = monstresVivants[choixIndex]
            } else {
                println("Choix invalide, réessayez.")
            }
        }

        return choixMonstre
    }
}
