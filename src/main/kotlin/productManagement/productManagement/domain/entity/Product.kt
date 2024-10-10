package productManagement.productManagement.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Product(
    productName: String,
    taxYn: Boolean,
    useYn: Boolean,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    var id: Long? = null

    var productName: String = productName

    @ManyToOne(targetEntity = Category::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category? = null

    @ManyToOne(targetEntity = Brand::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    var brand: Brand? = null

    var taxYn: Boolean = taxYn
    var useYn: Boolean = useYn

    var createDate: LocalDateTime = createDateTime

    fun addCategory(category: Category?) {
        this.category = category
    }

    fun addBrand(brand: Brand?) {
        this.brand = brand
    }
}
