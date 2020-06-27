package net.progruzovik.alfabattle.atm.service

import net.progruzovik.alfabattle.atm.client.AlfaClient
import net.progruzovik.alfabattle.atm.exception.AtmNotFountException
import net.progruzovik.alfabattle.atm.mapper.AtmMapper
import net.progruzovik.alfabattle.atm.model.domain.AtmData
import net.progruzovik.alfabattle.atm.model.dto.AtmResponseDTO
import org.springframework.stereotype.Service
import kotlin.math.sqrt

@Service
class AtmService(private val alfaClient: AlfaClient, private val atmMapper: AtmMapper) {

    fun findAtm(id: Long): AtmResponseDTO = alfaClient.getAtms()
        .read<List<Map<String, Any>>>("$.data.atms[?(@.deviceId == $id)]")
        .firstOrNull()
        ?.let { atmMapper.atmDataToDomain(it, id) }
        ?.let { atmMapper.domainToDto(it) }
        ?: throw AtmNotFountException()

    fun findNearestAtm(latitude: Double, longitude: Double, withPayments: Boolean): AtmResponseDTO {
        val atms: List<AtmData> = alfaClient.getAtms()
            .read<List<Map<String, Any>>>("$.data.atms")
            .map { atmMapper.atmDataToDomain(it) }

        var nearestAtm: Pair<AtmData, Double>? = null
        for (atm in atms) {
            if (atm.latitude == null || atm.longitude == null || withPayments && !atm.payments) continue

            val latDistance = atm.latitude.toDouble() - latitude
            val longDistance = atm.longitude.toDouble() - longitude
            val distance = sqrt(latDistance * latDistance + longDistance * longDistance)
            if (nearestAtm == null || nearestAtm.second > distance) {
                nearestAtm = atm to distance
            }
        }
        return nearestAtm?.first
            ?.let { atmMapper.domainToDto(it) }
            ?: throw AtmNotFountException()
    }
}
