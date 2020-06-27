@file:Suppress("UNCHECKED_CAST")

package net.progruzovik.alfabattle.atm.service

import net.progruzovik.alfabattle.atm.client.AlfaClient
import net.progruzovik.alfabattle.atm.exception.AtmNotFountException
import net.progruzovik.alfabattle.atm.model.dto.AtmResponseDTO
import org.springframework.stereotype.Service

@Service
class AtmService(private val alfaClient: AlfaClient) {

    fun findAtm(id: Long): AtmResponseDTO = alfaClient.getAtms()
        .read<List<Map<String, Any>>>("$.data.atms[?(@.deviceId == $id)]")
        .firstOrNull()
        ?.let {
            AtmResponseDTO(
                deviceId = id,
                latitude = (it.getValue("coordinates") as Map<String, String>).getValue("latitude"),
                longitude = (it.getValue("coordinates") as Map<String, String>).getValue("longitude"),
                city = (it.getValue("address") as Map<String, String>).getValue("city"),
                location = (it.getValue("address") as Map<String, String>).getValue("location"),
                payments = (it.getValue("services") as Map<String, String>).getValue("payments") == "Y"
            )
        }
        ?: throw AtmNotFountException()
}
