package net.progruzovik.alfabattle.promo.mapper

import net.progruzovik.alfabattle.promo.model.domain.ItemWithCount
import net.progruzovik.alfabattle.promo.model.dto.ItemDTO
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class ItemMapper {

    fun domainWithCountToDto(item: ItemWithCount, discount: BigDecimal): ItemDTO {
        val price: BigDecimal = item.first.price * item.second.toBigDecimal()
        return ItemDTO(
            id = item.first.id,
            name = item.first.name,
            price = (price - price * discount).setScale(2, RoundingMode.HALF_EVEN),
            regularPrice = price.setScale(2, RoundingMode.HALF_EVEN)
        )
    }
}
