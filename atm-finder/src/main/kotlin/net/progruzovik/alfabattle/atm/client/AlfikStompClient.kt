package net.progruzovik.alfabattle.atm.client

import net.progruzovik.alfabattle.atm.model.dto.AlfikRequestDTO
import net.progruzovik.alfabattle.atm.model.dto.AlfikResponseDTO
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

@Component
class AlfikStompClient {

    var stompSession: StompSession? = null

    private val requests = ConcurrentHashMap<Int, CompletableFuture<AlfikResponseDTO>>()

    fun onResponse(payload: AlfikResponseDTO) {
        requests.get(payload.deviceId)?.complete(payload)
    }

    fun getAtmAlfiks(deviceId: Int): AlfikResponseDTO {
        val session: StompSession = stompSession ?: throw RuntimeException("no WebSocket session")
        val futureResponse = CompletableFuture<AlfikResponseDTO>()
        requests[deviceId] = futureResponse
        session.send("/", AlfikRequestDTO(deviceId))
        return futureResponse.get() //TODO: coroutines will be better, but have no time
    }
}
