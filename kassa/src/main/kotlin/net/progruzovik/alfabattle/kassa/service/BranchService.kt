package net.progruzovik.alfabattle.kassa.service

import net.progruzovik.alfabattle.kassa.exception.BranchNotFoundException
import net.progruzovik.alfabattle.kassa.mapper.BranchMapper
import net.progruzovik.alfabattle.kassa.model.dto.BranchWithDistanceDTO
import net.progruzovik.alfabattle.kassa.model.entity.BranchEntity
import net.progruzovik.alfabattle.kassa.repository.BranchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Service
class BranchService(private val branchMapper: BranchMapper, private val branchRepository: BranchRepository) {

    fun getBranch(id: Int): BranchEntity = branchRepository.findByIdOrNull(id) ?: throw BranchNotFoundException()

    fun getNearestBranch(lat: Double, lon: Double): BranchWithDistanceDTO = branchRepository.findAll()
        .map {
            val radLat: Double = Math.toRadians(lat)
            val destRadLat: Double = Math.toRadians(it.lat)
            val radLon: Double = Math.toRadians(lon)
            val destRadLon: Double = Math.toRadians(it.lon)
            val latSin: Double = sin((destRadLat - radLat) / 2)
            val lonSin: Double = sin((destRadLon - radLon) / 2)
            val distance: Double = 2 * EARTH_RADIUS * asin(sqrt(latSin * latSin + cos(radLat) * cos(radLon) * lonSin * lonSin))
            branchMapper.entityToDtoWithDistance(it, (distance * 1000).toInt())
        }
        .minBy { it.distance } ?: throw BranchNotFoundException()

    companion object {
        private const val EARTH_RADIUS = 6371
    }
}
