package net.progruzovik.alfabattle.atm.client

import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.ReadContext
import net.progruzovik.alfabattle.atm.config.properties.AlfaIntegrationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Component
class AlfaClient(properties: AlfaIntegrationProperties, restTemplateBuilder: RestTemplateBuilder) {

    private val restTemplate: RestTemplate = restTemplateBuilder.rootUri(properties.alfaUrl)
        .defaultHeader(CLIENT_ID_HEADER, properties.client.id)
        .defaultHeader(CLIENT_CERTIFICATE_HEADER, properties.client.certificate)
        .build()

    fun getAtms(): ReadContext = restTemplate.getForObject<String>("/alfabank/alfadevportal/atm-service/atms")
        .let { JsonPath.parse(it) }

    companion object {
        private const val CLIENT_ID_HEADER = "x-ibm-client-id"
        private const val CLIENT_CERTIFICATE_HEADER = "x-client-certificate"
    }
}
