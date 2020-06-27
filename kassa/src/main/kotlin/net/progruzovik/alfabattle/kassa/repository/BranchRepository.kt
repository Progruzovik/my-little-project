package net.progruzovik.alfabattle.kassa.repository

import net.progruzovik.alfabattle.kassa.model.entity.BranchEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BranchRepository : JpaRepository<BranchEntity, Int>
