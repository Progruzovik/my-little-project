package net.progruzovik.alfabattle.atm.service

import net.progruzovik.alfabattle.atm.client.AlfaClient
import net.progruzovik.alfabattle.atm.client.AlfikStompClient
import net.progruzovik.alfabattle.atm.exception.AtmNotFountException
import net.progruzovik.alfabattle.atm.mapper.AtmMapper
import net.progruzovik.alfabattle.atm.model.domain.AtmData
import net.progruzovik.alfabattle.atm.model.dto.AtmResponseDTO
import org.springframework.stereotype.Service
import kotlin.math.sqrt

@Service
class AtmService(
    private val alfaClient: AlfaClient,
    private val stompClient: AlfikStompClient,
    private val atmMapper: AtmMapper
) {

    fun findAtm(id: Int): AtmResponseDTO = alfaClient.getAtms()
        .read<List<Map<String, Any>>>("$.data.atms[?(@.deviceId == $id)]")
        .firstOrNull()
        ?.let { atmMapper.atmDataToDomain(it, id) }
        ?.let { atmMapper.domainToDto(it) }
        ?: throw AtmNotFountException()

    fun findNearestAtm(latitude: Double, longitude: Double, withPayments: Boolean): AtmResponseDTO {
        return findNearestAtms(latitude = latitude, longitude = longitude, withPayments = withPayments)
            .firstOrNull()
            ?.let { atmMapper.domainToDto(it) }
            ?: throw AtmNotFountException()
    }

    fun findNearestAtmsWithAlfiks(latitude: Double, longitude: Double, requiredAlfiks: Int): List<AtmResponseDTO> {
        return findNearestAtms(latitude = latitude, longitude = longitude, requiredAlfiks = requiredAlfiks)
            .map { atmMapper.domainToDto(it) }
    }

    private fun findNearestAtms(
        latitude: Double,
        longitude: Double,
        withPayments: Boolean = false,
        requiredAlfiks: Int = 0
    ): List<AtmData> {
        val atms: List<AtmData> = alfaClient.getAtms()
            .read<List<Map<String, Any>>>("$.data.atms")
            .map { atmMapper.atmDataToDomain(it) }
            .sortedBy {
                if (it.latitude == null || it.longitude == null || withPayments && !it.payments) return@sortedBy Double.MAX_VALUE
                val latDistance = it.latitude.toDouble() - latitude
                val longDistance = it.longitude.toDouble() - longitude
                sqrt(latDistance * latDistance + longDistance * longDistance)
            }

        return if (requiredAlfiks == 0) {
             listOf(atms.first())
        } else {
            var indexLimit = 0
            var foundAlfiks = 0
            for (atm in atms) {
                indexLimit++
                foundAlfiks += stompClient.getAtmAlfiks(atm.deviceId).alfik
                if (foundAlfiks >= requiredAlfiks) break
            }
            atms.subList(0, indexLimit)
        }
    }
}
