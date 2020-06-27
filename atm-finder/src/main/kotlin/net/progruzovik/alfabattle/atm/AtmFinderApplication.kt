package net.progruzovik.alfabattle.atm

import net.progruzovik.alfabattle.atm.config.properties.AlfaIntegrationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AlfaIntegrationProperties::class)
class AtmFinderApplication

fun main(args: Array<String>) {
    runApplication<AtmFinderApplication>(*args)
}
