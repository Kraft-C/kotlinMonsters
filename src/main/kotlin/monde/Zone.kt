package monde

import monstre.EspeceMonstre

class Zone(
    var id: Int,
    var nom: String,
    var expZone: Int,
    var especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedante: Zone? = null
) {
    //TODO faire la méthode genereMonstre()
    //TODO faire la méthode rencontreMonstre()
    lateinit var zonePrecedente: Zone
}