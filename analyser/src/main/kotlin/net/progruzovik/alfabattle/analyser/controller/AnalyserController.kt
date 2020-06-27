package net.progruzovik.alfabattle.analyser.controller

import net.progruzovik.alfabattle.analyser.exception.UserNotFoundException
import net.progruzovik.alfabattle.analyser.model.domain.UserAnalytics
import net.progruzovik.alfabattle.analyser.model.dto.StatusDTO
import net.progruzovik.alfabattle.analyser.service.AnalyserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/analytic")
class AnalyserController(private val analyserService: AnalyserService) {

    @GetMapping
    fun getAnalytics(): List<UserAnalytics> = analyserService.getAnalytics()

    @GetMapping("/{userId}")
    fun getUserAnalytics(@PathVariable userId: String): UserAnalytics = analyserService.getUserAnalytics(userId)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun atmNotFound(): StatusDTO = StatusDTO("user not found")
}
