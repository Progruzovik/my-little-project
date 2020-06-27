package net.progruzovik.alfabattle.analyser

import net.progruzovik.alfabattle.analyser.config.properties.KafkaProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(KafkaProperties::class)
class AnalyserApplication

fun main(args: Array<String>) {
    runApplication<AnalyserApplication>(*args)
}
