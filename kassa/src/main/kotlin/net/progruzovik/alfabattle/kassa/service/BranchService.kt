package net.progruzovik.alfabattle.kassa.service

import net.progruzovik.alfabattle.kassa.exception.BranchNotFoundException
import net.progruzovik.alfabattle.kassa.model.entity.BranchEntity
import net.progruzovik.alfabattle.kassa.repository.BranchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Service
class BranchService(private val branchRepository: BranchRepository) {

    fun getBranch(id: Int): BranchEntity = branchRepository.findByIdOrNull(id) ?: throw BranchNotFoundException()

    fun getNearestBranch(lat: Double, lon: Double): BranchEntity = branchRepository.findAll()
        .sortedBy {
            val latSin: Double = sin((it.lat - lat) / 2)
            val lonSin: Double = sin((it.lon - lon) / 2)
            2 * EARTH_RADIUS * asin(sqrt(latSin * latSin + cos(lat) * cos(lon) * lonSin * lonSin))
        }
        .first()

    companion object {
        private const val EARTH_RADIUS = 6371
    }
}
