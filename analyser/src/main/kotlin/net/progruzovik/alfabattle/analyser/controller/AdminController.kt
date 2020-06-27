package net.progruzovik.alfabattle.analyser.controller

import net.progruzovik.alfabattle.analyser.model.dto.StatusDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController {

    @GetMapping("/health")
    fun getHealth(): StatusDTO = StatusDTO("UP") //TODO: actuator will be better
}
