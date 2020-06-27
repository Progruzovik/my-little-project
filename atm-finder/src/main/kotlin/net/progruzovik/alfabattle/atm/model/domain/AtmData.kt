package net.progruzovik.alfabattle.atm.model.domain

data class AtmData(
    val deviceId: Int,
    val latitude: String?,
    val longitude: String?,
    val city: String,
    val location: String,
    val payments: Boolean
)
