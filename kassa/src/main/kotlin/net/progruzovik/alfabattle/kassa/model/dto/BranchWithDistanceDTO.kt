package net.progruzovik.alfabattle.kassa.model.dto

data class BranchWithDistanceDTO(
    val id: Int,

    val title: String,

    val lon: Double,

    val lat: Double,

    val address: String,

    val distance: Int //there is was no distance in Swagger api definition :(
)
