package net.progruzovik.alfabattle.atm.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.core.io.Resource

@ConfigurationProperties("alfa-integration")
@ConstructorBinding
data class AlfaIntegrationProperties(
    val alfaUrl: String,
    val stompUrl: String,
    val client: ClientProperties
) {

    data class ClientProperties(
        val id: String,
        val certificate: String,
        val keyStore: Resource,
        val keyStorePassword: String
    )
}
