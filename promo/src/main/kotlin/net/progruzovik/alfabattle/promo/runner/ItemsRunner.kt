package net.progruzovik.alfabattle.promo.runner

import net.progruzovik.alfabattle.promo.model.domain.Item
import org.apache.commons.csv.CSVFormat
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.cache.CacheManager
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.InputStreamReader
import java.math.BigDecimal

@Component
class ItemsRunner(private val cacheManager: CacheManager) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        InputStreamReader(ClassPathResource("items.csv").inputStream).use { reader ->
            CSVFormat.DEFAULT.parse(reader)
                .map { Item(
                    id = it[ID],
                    name = it[NAME],
                    groupId = it[GROUP_ID],
                    price = try {
                        it[PRICE].toBigDecimal()
                    } catch (e: NumberFormatException) {
                        log.error("Exception for: ${it[PRICE]}", e)
                        BigDecimal.ZERO
                    }
                ) }
                .forEach { cacheManager.getCache("items")!!.put(it.id, it) }
        }
    }

    companion object {
        private const val ID = 0
        private const val NAME = 1
        private const val GROUP_ID = 2
        private const val PRICE = 3

        private val log = LoggerFactory.getLogger(ItemsRunner::class.java)
    }
}
