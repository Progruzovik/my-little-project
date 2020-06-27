package net.progruzovik.alfabattle.kassa.service

import net.progruzovik.alfabattle.kassa.exception.BranchNotFoundException
import net.progruzovik.alfabattle.kassa.model.entity.BranchEntity
import net.progruzovik.alfabattle.kassa.repository.BranchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BranchService(private val branchRepository: BranchRepository) {

    fun getBranch(id: Int):BranchEntity = branchRepository.findByIdOrNull(id) ?: throw BranchNotFoundException()
}
