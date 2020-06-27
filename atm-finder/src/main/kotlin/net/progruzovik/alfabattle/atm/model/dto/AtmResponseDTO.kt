package net.progruzovik.alfabattle.atm.model.dto

data class AtmResponseDTO(
    val deviceId: Int,
    val latitude: String?,
    val longitude: String?,
    val city: String,
    val location: String,
    val payments: Boolean
)
