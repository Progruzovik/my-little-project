@file:Suppress("UNCHECKED_CAST")

package net.progruzovik.alfabattle.atm.mapper

import net.progruzovik.alfabattle.atm.model.domain.AtmData
import net.progruzovik.alfabattle.atm.model.dto.AtmResponseDTO
import org.springframework.stereotype.Component

@Component
class AtmMapper {

    fun atmDataToDomain(data: Map< String, Any>, id: Long? = null): AtmData = AtmData(
        deviceId = id ?: (data.getValue("deviceId") as Int).toLong(),
        latitude = (data.getValue("coordinates") as Map<String, String>).getValue("latitude"),
        longitude = (data.getValue("coordinates") as Map<String, String>).getValue("longitude"),
        city = (data.getValue("address") as Map<String, String>).getValue("city"),
        location = (data.getValue("address") as Map<String, String>).getValue("location"),
        payments = (data.getValue("services") as Map<String, String>).getValue("payments") == "Y"
    )

    fun domainToDto(atmData: AtmData): AtmResponseDTO = AtmResponseDTO(
        deviceId = atmData.deviceId,
        latitude = atmData.latitude,
        longitude = atmData.longitude,
        city = atmData.city,
        location = atmData.location,
        payments = atmData.payments
    )
}
