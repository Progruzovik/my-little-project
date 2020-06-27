package net.progruzovik.alfabattle.promo.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/promo")
class PromoController {

    @PostMapping
    fun postPromo(): Map<Any, Any> = emptyMap()
}
