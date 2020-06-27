package net.progruzovik.alfabattle.analyser.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("kafka")
@ConstructorBinding
data class KafkaProperties(val bootstrapServers: List<String>, val groupId: String, val topics: List<TopicProperties>) {

    data class TopicProperties(val topic: String, val partition: Int)
}
