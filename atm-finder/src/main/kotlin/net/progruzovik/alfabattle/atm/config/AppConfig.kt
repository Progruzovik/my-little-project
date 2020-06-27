package net.progruzovik.alfabattle.atm.config

import net.progruzovik.alfabattle.atm.config.properties.AlfaIntegrationProperties
import net.progruzovik.alfabattle.atm.websocket.WebSocketSessionHandler
import org.apache.http.conn.ssl.DefaultHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.ssl.SSLContexts
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient

@Configuration
class AppConfig(private val properties: AlfaIntegrationProperties) {

    @Bean
    fun stompClient(webSocketSessionHandler: WebSocketSessionHandler): WebSocketStompClient {
        return WebSocketStompClient(StandardWebSocketClient())
            .also { it.messageConverter = MappingJackson2MessageConverter() }
            .also { it.connect(properties.stompUrl, webSocketSessionHandler) }
    }

    @Bean
    fun restTemplateCustomizer(): RestTemplateCustomizer = RestTemplateCustomizer { restTemplate ->
        val password: String = properties.client.keyStorePassword
        val sslContext = SSLContexts.custom()
            .loadKeyMaterial(properties.client.keyStore.url, password.toCharArray(), password.toCharArray())
            .loadTrustMaterial(properties.client.keyStore.url, password.toCharArray(), TrustSelfSignedStrategy())
            .setProtocol("TLS")
            .build()
        val connectionFactory = SSLConnectionSocketFactory(sslContext, DefaultHostnameVerifier())
        val httpClient: CloseableHttpClient = HttpClientBuilder.create()
            .setSSLSocketFactory(connectionFactory)
            .build()
        restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
    }
}
