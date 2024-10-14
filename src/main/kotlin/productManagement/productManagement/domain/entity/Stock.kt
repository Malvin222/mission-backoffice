package productManagement.productManagement.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Stock(
    stockCount: Int?,
    receivingCount: Int?,
    receivingPrice: Int?,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    var id: Long? = null

    @ManyToOne(targetEntity = Product::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product? = null

    var stockCount: Int? = stockCount
    var receivingCount: Int? = receivingCount
    var receivingPrice: Int? = receivingPrice
    var receivingDate: LocalDateTime = createDateTime

    //TODO 서비스단에서 처리할것.
    fun updateStockCount(newReceivingCount: Int) {
        this.receivingCount = newReceivingCount
        this.stockCount = this.stockCount?.plus(newReceivingCount)
    }

    fun addProduct(product: Product) {
        this.product = product
    }
}