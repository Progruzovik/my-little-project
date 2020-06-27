package net.progruzovik.alfabattle.promo.model.dto

import java.math.BigDecimal

data class ItemsDataDTO(
    val total: BigDecimal,
    val discount: BigDecimal,
    val positions: List<ItemDTO>
)
