package net.progruzovik.alfabattle.atm.websocket

import net.progruzovik.alfabattle.atm.client.AlfikStompClient
import net.progruzovik.alfabattle.atm.model.dto.AlfikResponseDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Component
import java.lang.reflect.Type

@Component
class WebSocketSessionHandler(private val client: AlfikStompClient) : StompSessionHandler {

    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        session.subscribe("/topic/alfik", this)
        client.stompSession = session
    }

    override fun handleException(
        session: StompSession,
        command: StompCommand?,
        headers: StompHeaders,
        payload: ByteArray,
        exception: Throwable
    ) {
        log.error("", exception)
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
        log.error("", exception)
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        println(payload)
        if (payload is AlfikResponseDTO) {
            client.onResponse(payload)
        }
    }

    override fun getPayloadType(headers: StompHeaders): Type = AlfikResponseDTO::class.java

    companion object {
        private val log: Logger = LoggerFactory.getLogger(WebSocketSessionHandler::class.java)
    }
}
