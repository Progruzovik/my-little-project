package net.progruzovik.alfabattle.analyser.model.domain

import net.progruzovik.alfabattle.analyser.config.properties.KafkaProperties
import net.progruzovik.alfabattle.analyser.model.dto.KafkaRecordDTO
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

class KafkaConfiguration(properties: KafkaProperties) : HashMap<String, Any>(5) {

    init {
        this[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = properties.bootstrapServers
        this[ConsumerConfig.GROUP_ID_CONFIG] = properties.groupId
        this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        this[JsonDeserializer.VALUE_DEFAULT_TYPE] = KafkaRecordDTO::class.java
    }
}
