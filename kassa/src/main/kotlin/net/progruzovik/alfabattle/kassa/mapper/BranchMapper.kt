package net.progruzovik.alfabattle.kassa.mapper

import net.progruzovik.alfabattle.kassa.model.dto.BranchWithDistanceDTO
import net.progruzovik.alfabattle.kassa.model.entity.BranchEntity
import org.springframework.stereotype.Component

@Component
class BranchMapper {

    fun entityToDtoWithDistance(branch: BranchEntity, distance: Int) = BranchWithDistanceDTO(
        id = branch.id,
        title = branch.title,
        lon = branch.lon,
        lat = branch.lat,
        address = branch.address,
        distance = distance
    )
}
