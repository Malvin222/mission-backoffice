package productManagement.productManagement.presentation.dto

import productManagement.productManagement.domain.entity.Product

data class ProductDTO(
    val productName: String,
    val taxYn: String,
    val useYn: String,
    val brand: BrandDTO?,
    val category: CategoryDTO?
) {
    constructor(product: Product) : this(productName = product.productName,
        taxYn = product.taxYn,
        useYn = product.useYn,
        brand = product.brand?.let { BrandDTO(it) },
        category = product.category?.let { CategoryDTO(it) })
}