package productManagement.productManagement.presentation.dto

import productManagement.productManagement.domain.entity.Stock
import java.time.LocalDateTime

data class StockDTO(
    val stockCount: Int?,
    val receivingCount: Int?,
    val receivingPrice: Int?,
    val product: ProductDTO,
    val receivingDate: LocalDateTime,
) {
    constructor(stock: Stock) : this(
        stockCount = stock.stockCount,
        receivingCount = stock.receivingCount,
        receivingPrice = stock.receivingPrice,
        product = stock.product?.let { ProductDTO(it) } ?: throw IllegalArgumentException("Product cannot be null"),
        receivingDate = stock.createDateTime
    )
}