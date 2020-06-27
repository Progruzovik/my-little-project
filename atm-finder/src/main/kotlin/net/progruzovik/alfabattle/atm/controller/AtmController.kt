package net.progruzovik.alfabattle.atm.controller

import net.progruzovik.alfabattle.atm.exception.AtmNotFountException
import net.progruzovik.alfabattle.atm.model.dto.AtmResponseDTO
import net.progruzovik.alfabattle.atm.model.dto.ErrorResponseDTO
import net.progruzovik.alfabattle.atm.service.AtmService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/atms")
class AtmController(private val atmService: AtmService) {

    @GetMapping("/{id}")
    fun getAtm(@PathVariable id: Int): AtmResponseDTO = atmService.findAtm(id)

    @GetMapping("/nearest")
    fun getNearestAtm(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam payments: Boolean = false
    ): AtmResponseDTO {
        return atmService.findNearestAtm(latitude, longitude, payments)
    }

    @GetMapping("/nearest-with-alfik")
    fun getNearestAtmsWithAlfiks(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam alfik: Int
    ): List<AtmResponseDTO> {
        return atmService.findNearestAtmsWithAlfiks(latitude, longitude, alfik)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AtmNotFountException::class)
    fun atmNotFound(): ErrorResponseDTO = ErrorResponseDTO("atm not found")
}
