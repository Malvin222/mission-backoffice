package productManagement.productManagement.product.dto

import productManagement.productManagement.brand.dto.BrandDTO
import productManagement.productManagement.category.dto.CategoryDTO
import productManagement.productManagement.domain.entity.Product

data class ProductDTO(
    val id: Long?,
    val productName: String,
    val taxYn: String,
    val useYn: String,
    val brand: BrandDTO?,
    val category: CategoryDTO?
) {
    constructor(product: Product) : this(
        id = product.id,
        productName = product.productName,
        taxYn = product.taxYn,
        useYn = product.useYn,
        brand = product.brand?.let { BrandDTO(it) },
        category = product.category?.let { CategoryDTO(it) })

    fun toEntity(): Product {
        return Product(
            id = this.id,
            productName = this.productName,
            taxYn = this.taxYn,
            useYn = this.useYn,
            brand = this.brand?.toEntity(),
            category = this.category?.toEntity()
        )
    }
}