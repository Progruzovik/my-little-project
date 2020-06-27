package net.progruzovik.alfabattle.atm.websocket

import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Component
import java.lang.reflect.Type

@Component
class WebSocketSessionHandler : StompSessionHandler {

    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        TODO("Not yet implemented")
    }

    override fun handleException(
        session: StompSession,
        command: StompCommand?,
        headers: StompHeaders,
        payload: ByteArray,
        exception: Throwable
    ) {
        TODO("Not yet implemented")
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
        TODO("Not yet implemented")
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        TODO("Not yet implemented")
    }

    override fun getPayloadType(headers: StompHeaders): Type {
        TODO("Not yet implemented")
    }
}
