package net.progruzovik.alfabattle.analyser.service

import com.fasterxml.jackson.databind.ObjectMapper
import net.progruzovik.alfabattle.analyser.exception.UserNotFoundException
import net.progruzovik.alfabattle.analyser.model.domain.Payments
import net.progruzovik.alfabattle.analyser.model.domain.UserAnalytics
import net.progruzovik.alfabattle.analyser.model.dto.KafkaRecordDTO
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Duration

@Service
class AnalyserService(private val mapper: ObjectMapper, private val kafkaConsumer: KafkaConsumer<String, KafkaRecordDTO>) {

    fun getAnalytics(): List<UserAnalytics> = getRecordsByUsers().values.map { sumUserRecords(it) }

    fun getUserAnalytics(userId: String): UserAnalytics = getRecordsByUsers().get(userId)
        ?.let { sumUserRecords(it) }
        ?: throw UserNotFoundException()

    private fun getRecordsByUsers(): Map<String, List<KafkaRecordDTO>> {
        val records: List<KafkaRecordDTO> = kafkaConsumer.also { it.seekToBeginning(it.assignment()) }
            .poll(Duration.ofMinutes(1))
            .map { it.value() }

        val recordsByUsers = HashMap<String, MutableList<KafkaRecordDTO>>()
        for (record in records) {
            val userRecords: MutableList<KafkaRecordDTO> = recordsByUsers[record.userId] ?: ArrayList()
            userRecords.add(record)
            recordsByUsers[record.userId] = userRecords
        }
        return recordsByUsers
    }

    private fun sumUserRecords(userRecords: List<KafkaRecordDTO>): UserAnalytics {
        var totalSum = BigDecimal.ZERO
        val paymentsByCategories = HashMap<String, Payments>()
        for (record in userRecords) {
            val recordAmount: BigDecimal = record.amount
            totalSum += recordAmount

            val categoryId: String = record.categoryId.toString()
            val categoryPayments: Payments = paymentsByCategories[categoryId] ?: Payments()
            val min: BigDecimal? = categoryPayments.min
            if (min == null || recordAmount < min) {
                categoryPayments.min = recordAmount
            }
            val max: BigDecimal? = categoryPayments.max
            if (max == null || recordAmount > max) {
                categoryPayments.max = recordAmount
            }
            categoryPayments.sum += recordAmount
            paymentsByCategories[categoryId] = categoryPayments
        }
        return UserAnalytics(
            userId = userRecords.first().userId,
            totalSum = totalSum,
            analyticInfo = paymentsByCategories
        )
    }
}
