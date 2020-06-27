package net.progruzovik.alfabattle.promo.service

import net.progruzovik.alfabattle.promo.mapper.ItemMapper
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

        val total: BigDecimal = itemsWithCount.fold(initial = BigDecimal.ZERO) { acc, (item, count) ->
            acc + item.price * count.toBigDecimal()
        }
        return ItemsDataDTO(
            total = total.setScale(2, RoundingMode.HALF_EVEN),
            discount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN),
            positions = itemsWithCount.map { itemMapper.domainWithCountToDto(it) }
        )
    }
}
