package net.progruzovik.alfabattle.promo.model.dto

data class ItemsReceiptDTO(
    val shopId: Int,
    val loyaltyCard: Boolean,
    val positions: List<PositionDTO>
)
