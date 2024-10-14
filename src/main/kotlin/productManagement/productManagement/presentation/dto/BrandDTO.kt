package productManagement.productManagement.presentation.dto

import productManagement.productManagement.domain.entity.Brand

data class BrandDTO(
    val brandName: String
) {
    constructor(brand: Brand) : this(
        brandName = brand.brandName
    )
}