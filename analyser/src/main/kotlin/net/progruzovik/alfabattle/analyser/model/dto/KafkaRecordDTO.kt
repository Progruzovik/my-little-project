package net.progruzovik.alfabattle.analyser.model.dto

data class KafkaRecordDTO(
    val ref: String,
    val categoryId: Int,
    val userId: String,
    val recipientId: String,
    val desc: String,
    val amount: Double
)
