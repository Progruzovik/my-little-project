package net.progruzovik.alfabattle.promo.controller

import net.progruzovik.alfabattle.promo.model.dto.ItemsDataDTO
import net.progruzovik.alfabattle.promo.model.dto.ItemsReceiptDTO
import net.progruzovik.alfabattle.promo.service.ItemService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/receipt")
class ReceiptController(private val itemService: ItemService) {

    @PostMapping
    fun postReceipt(@RequestBody receipt: ItemsReceiptDTO): ItemsDataDTO = itemService.getItemsForReceipt(receipt)
}
