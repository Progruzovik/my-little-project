package net.progruzovik.alfabattle.kassa.controller

import net.progruzovik.alfabattle.kassa.exception.BranchNotFoundException
import net.progruzovik.alfabattle.kassa.model.dto.ErrorResponseDTO
import net.progruzovik.alfabattle.kassa.model.entity.BranchEntity
import net.progruzovik.alfabattle.kassa.service.BranchService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/branches")
class BranchController(private val branchService: BranchService) {

    @GetMapping("/{id}")
    fun getBranch(@PathVariable id: Int): BranchEntity = branchService.getBranch(id)

    @GetMapping
    fun getNearestBranch(@RequestParam lat: Double, @RequestParam lon: Double): BranchEntity {
        return branchService.getNearestBranch(lat, lon)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BranchNotFoundException::class)
    fun atmNotFound(): ErrorResponseDTO = ErrorResponseDTO("branch not found")
}
