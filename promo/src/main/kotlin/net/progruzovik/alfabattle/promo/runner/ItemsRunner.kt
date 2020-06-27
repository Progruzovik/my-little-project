package net.progruzovik.alfabattle.promo.runner

import net.progruzovik.alfabattle.promo.model.domain.Item
import org.apache.commons.csv.CSVFormat
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.cache.CacheManager
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.InputStreamReader

@Component
class ItemsRunner(private val cacheManager: CacheManager) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        InputStreamReader(ClassPathResource("items.csv").inputStream).use { reader ->
            CSVFormat.DEFAULT.parse(reader)
                .forEachIndexed { i, record ->
                    if (i == 0) return@forEachIndexed
                    val item = Item(
                        id = record[ID],
                        name = record[NAME],
                        groupId = record[GROUP_ID],
                        price = record[PRICE].toBigDecimal()
                    )
                    cacheManager.getCache("items")!!.put(item.id, item)
                }
        }
    }

    companion object {
        private const val ID = 0
        private const val NAME = 1
        private const val GROUP_ID = 2
        private const val PRICE = 3
    }
}
