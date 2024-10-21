package productManagement.productManagement.stock.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import productManagement.productManagement.data.ApiResponse
import productManagement.productManagement.stock.dto.StockDTO
import productManagement.productManagement.stock.service.StockService

@RestController
@RequestMapping("/api/stocks")
class StockApiController(
    private val stockService: StockService
) {
    @GetMapping
    fun getStocks(): List<StockDTO> {
        return stockService.getStock()
    }

    @GetMapping("/{stock_id}")
    fun getStockById(@PathVariable("stock_id") stockId: Long): ResponseEntity<StockDTO>{
        val stockDTO = stockService.getStockById(stockId)
        return ResponseEntity(stockDTO, HttpStatus.OK)
    }

    @PostMapping
    fun postStock(@RequestBody @Validated stockDTO: StockDTO): ResponseEntity<Any>{
        stockService.save(stockDTO)
        return ResponseEntity(ApiResponse.successCreate(),HttpStatus.CREATED)
    }

}