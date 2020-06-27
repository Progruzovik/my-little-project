package net.progruzovik.alfabattle.promo.service

import net.progruzovik.alfabattle.promo.model.dto.PromoDTO
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service

@Service
class PromoService(private val cacheManager: CacheManager) {

    fun setUpPromo(promo: PromoDTO?) {
        val cache: Cache = cacheManager.getCache("promo")!!
        cache.clear()
        if (promo?.loyaltyCardRules == null) return
        promo.loyaltyCardRules.forEach { cache.put(it.shopId, it.discount) }
    }
}
