package monstre

import dresseur.Entraineur
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random

class IndividuMonstre(
    var id: Int,
    var nom: String,
    var espece: EspeceMonstre,
    var entraineur: Entraineur? = null,
    expInit: Double = 0.0
) {
    var entraîneur: Entraineur?
        get() = entraineur
        set(value) {
            entraineur = value
        }

    var niveau: Int = 1
    var attaque: Int = espece.baseAttaque + (-2..2).random()
    var defense: Int = espece.baseDefense + (-2..2).random()
    var défense: Int
        get() = defense
        set(value) {
            defense = value
        }
    var vitesse: Int = espece.baseVitesse + (-2..2).random()
    var attaqueSpe: Int = espece.baseAttaqueSpe + (-2..2).random()
    var defenseSpe: Int = espece.baseDefenseSpe + (-2..2).random()
    var défenseSpe: Int
        get() = defenseSpe
        set(value) {
            defenseSpe = value
        }
    var pvMax: Int = espece.basePv + (-5..5).random()
    val potentiel: Double = Random.nextDouble(0.5, 2.0)
    var exp: Double = 0.0
        get() = field
        set(value) {
            field = value
            val estNiveau1 = niveau == 1
            while (field >= palierExp(niveau)) {
                levelUp()
                if (estNiveau1 == false) {
                    println("Le monstre $nom est maintenant niveau $niveau !")
                }
            }
        }

    /**
     * @property pv Points de vie actuels.
     * Ne peut pas être inférieur à 0 ni supérieur à [pvMax].
     */
    var pv: Int = pvMax
        get() = field
        set(nouveauPv) {
            field = when {
                nouveauPv < 0 -> 0
                nouveauPv > pvMax -> pvMax
                else -> nouveauPv
            }
        }

    init {
        this.exp = expInit
    }

    /**
     * Calcule l'expérience totale nécessaire pour atteindre un niveau donné.
     *
     * @param niveau Niveau cible.
     * @return Expérience cumulée nécessaire pour atteindre ce niveau.
     */
    fun palierExp(niveau: Int): Double {
        return 100.0 * (niveau - 1).toDouble().pow(2.0)
    }

    /**
     * Augmente le niveau du monstre et recalcule ses caractéristiques.
     */
    fun levelUp() {
        niveau += 1
        attaque += (espece.modAttaque * potentiel).roundToInt() + (-2..2).random()
        defense += (espece.modDefense * potentiel).roundToInt() + (-2..2).random()
        vitesse += (espece.modVitesse * potentiel).roundToInt() + (-2..2).random()
        attaqueSpe += (espece.modAttaqueSpe * potentiel).roundToInt() + (-2..2).random()
        defenseSpe += (espece.modDefenseSpe * potentiel).roundToInt() + (-2..2).random()

        val gainPvMax = (espece.modPv * potentiel).roundToInt() + (-5..5).random()
        pvMax += gainPvMax
        pv += gainPvMax
    }
}
