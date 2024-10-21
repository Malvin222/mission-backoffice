package productManagement.productManagement.stock.dto

import productManagement.productManagement.domain.entity.Brand
import productManagement.productManagement.domain.entity.Stock
import productManagement.productManagement.product.dto.ProductDTO
import java.time.LocalDateTime

data class StockDTO(
    var stockCount: Int?,
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

    fun toEntity(): Stock {
        return Stock(
            stockCount = this.stockCount,
            receivingCount = this.receivingCount,
            receivingPrice = this.receivingPrice
        )
    }
}