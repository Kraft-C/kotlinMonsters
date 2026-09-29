package jeu

import dresseur.joueur
import item.Utilisable
import monstre.IndividuMonstre

class CombatMonstre(
    var monstreJoueur: IndividuMonstre,
    var monstreSauvage: IndividuMonstre
) {
    var round: Int = 1

    /**
     * Vérifie si le joueur a perdu le combat.
     *
     * Condition de défaite :
     * - Aucun monstre de l'équipe du joueur n'a de PV > 0.
     *
     * @return `true` si le joueur a perdu, sinon `false`.
     */
    fun gameOver(): Boolean {
        return monstreJoueur.entraineur?.equipeMonstre?.none { it.pv > 0 } ?: true
    }

    /**
     * Vérifie si le joueur a gagné le combat.
     *
     * Conditions de victoire :
     * - Le monstre sauvage a ses PV à 0.
     * - Le monstre sauvage a été capturé.
     *
     * Le monstre du joueur gagne de l'expérience seulement
     * si le monstre sauvage est vaincu.
     *
     * @return `true` si le joueur a gagné, sinon `false`.
     */
    fun joueurGagne(): Boolean {
        if (monstreSauvage.pv <= 0) {
            println("${monstreJoueur.nom} a gagné !")

            val gainExp = monstreSauvage.exp * 0.20
            monstreJoueur.exp += gainExp

            println("${monstreJoueur.nom} gagne $gainExp exp")

            return true
        }

        if (monstreSauvage.entraineur == monstreJoueur.entraineur) {
            println("${monstreSauvage.nom} a été capturé !")
            return true
        }

        return false
    }


    fun actionAdversaire() {
        if (monstreSauvage.pv <= 0){
            monstreSauvage.attaquer(monstreJoueur)
        }
    }


    fun actionJoueur() {
        if (gameOver()) {
            println("Vous avez perdu !")
        } else {
            println("Choisir une action (1 = attaque, 2 = objet, 3 = changement) :")
            var action = readln().toInt()

            while (action !in 1..3) {
                println("Action invalide. Choisissez 1 pour attaque, 2 pour objet, 3 pour changement :")
                action = readln().toInt()
            }
            if (action == 1) {
                monstreJoueur.attaquer(monstreSauvage)
            }
            if (action == 2) {

            }
            if (action == 3) {
                println("Equipes :")

            }

        }
    }
}