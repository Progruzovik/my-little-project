package net.progruzovik.alfabattle.promo.service

import net.progruzovik.alfabattle.promo.mapper.ItemMapper
import net.progruzovik.alfabattle.promo.model.PromoApplicationConstants.GLOBAL_PROMO
import net.progruzovik.alfabattle.promo.model.domain.Item
import net.progruzovik.alfabattle.promo.model.domain.ItemWithCount
import net.progruzovik.alfabattle.promo.model.dto.ItemsDataDTO
import net.progruzovik.alfabattle.promo.model.dto.ItemsReceiptDTO
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class ItemService(private val itemMapper: ItemMapper, private val cacheManager: CacheManager) {

    fun getItemsForReceipt(receipt: ItemsReceiptDTO): ItemsDataDTO {
        val itemsWithCount: List<ItemWithCount> = receipt.positions
            .map { cacheManager.getCache("items")!!.get(it.itemId, Item::class.java) to it.quantity }

        val discount: BigDecimal = getDiscount(receipt.loyaltyCard, receipt.shopId)
        val total: BigDecimal = itemsWithCount.fold(initial = BigDecimal.ZERO) { acc, (item, count) ->
            acc + (item.price - item.price * discount) * count.toBigDecimal()
        }
        return ItemsDataDTO(
            total = total.setScale(2, RoundingMode.HALF_EVEN),
            discount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN),
            positions = itemsWithCount.map { itemMapper.domainWithCountToDto(it, discount) }
        )
    }

    private fun getDiscount(hasCard: Boolean, shopId: Int): BigDecimal = if (hasCard) {
        val globalDiscount: BigDecimal? = cacheManager.getCache("promo")?.get(GLOBAL_PROMO, BigDecimal::class.java)
        val shopDiscount: BigDecimal? = cacheManager.getCache("promo")?.get(shopId, BigDecimal::class.java)
        if (globalDiscount != null && shopDiscount != null) {
            shopDiscount + globalDiscount
        } else if (globalDiscount == null && shopDiscount != null) {
            shopDiscount
        } else if (globalDiscount != null && shopDiscount == null) {
            globalDiscount
        } else {
            BigDecimal.ZERO
        }
    } else {
        BigDecimal.ZERO
    }
}
