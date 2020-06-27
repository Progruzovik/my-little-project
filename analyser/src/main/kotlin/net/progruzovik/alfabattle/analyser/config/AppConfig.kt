package net.progruzovik.alfabattle.analyser.config

import net.progruzovik.alfabattle.analyser.config.properties.KafkaProperties
import net.progruzovik.alfabattle.analyser.model.domain.KafkaConfiguration
import net.progruzovik.alfabattle.analyser.model.dto.KafkaRecordDTO
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun kafkaConsumer(properties: KafkaProperties): KafkaConsumer<String, KafkaRecordDTO> {
        val consumer = KafkaConsumer<String, KafkaRecordDTO>(KafkaConfiguration(properties))
        properties.topics
            .map { TopicPartition(it.topic, it.partition) }
            .also { consumer.assign(it) }
        return consumer
    }
}
