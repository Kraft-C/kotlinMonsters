package monde

import monstre.EspeceMonstre

class Ville(
    id: Int,
    nom: String,
    expZone: Int,
    especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    zoneSuivante: Zone? = null,
    zonePrecedante: Zone? = null,
    var arene: Arene? = null,
    var lignesMagasin: MutableList<LigneMagasin> = mutableListOf()
) : Zone(
    id = id,
    nom = nom,
    expZone = expZone,
    especesMonstres = especesMonstres,
    zoneSuivante = zoneSuivante,
    zonePrecedante = zonePrecedante
)