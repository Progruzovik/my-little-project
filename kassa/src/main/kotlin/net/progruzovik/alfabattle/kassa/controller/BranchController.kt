package net.progruzovik.alfabattle.kassa.controller

import net.progruzovik.alfabattle.kassa.exception.BranchNotFoundException
import net.progruzovik.alfabattle.kassa.model.dto.ErrorResponseDTO
import net.progruzovik.alfabattle.kassa.service.BranchService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/branches")
class BranchController(private val branchService: BranchService) {

    @GetMapping("/{id}")
    fun getBranch(@PathVariable id: Int) = branchService.getBranch(id)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BranchNotFoundException::class)
    fun atmNotFound(): ErrorResponseDTO = ErrorResponseDTO("branch not found")
}
