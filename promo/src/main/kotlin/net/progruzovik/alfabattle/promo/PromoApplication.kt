package net.progruzovik.alfabattle.promo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class PromoApplication

fun main(args: Array<String>) {
    runApplication<PromoApplication>(*args)
}
