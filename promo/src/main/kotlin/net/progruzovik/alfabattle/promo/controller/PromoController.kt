package net.progruzovik.alfabattle.promo.controller

import net.progruzovik.alfabattle.promo.model.dto.PromoDTO
import net.progruzovik.alfabattle.promo.service.PromoService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/promo")
class PromoController(private val promoService: PromoService) {

    @PostMapping
    fun postPromo(@RequestBody(required = false) promo: PromoDTO?) {
        promoService.setUpPromo(promo)
    }
}
