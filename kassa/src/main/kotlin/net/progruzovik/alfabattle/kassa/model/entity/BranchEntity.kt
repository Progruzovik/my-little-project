package net.progruzovik.alfabattle.kassa.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "branches")
data class BranchEntity(

    @Id
    val id: Int,

    val title: String,

    val lon: Double,

    val lat: Double,

    val address: String
)
