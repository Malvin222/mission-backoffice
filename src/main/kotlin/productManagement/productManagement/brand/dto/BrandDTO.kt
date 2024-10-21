package productManagement.productManagement.brand.dto

import jakarta.validation.constraints.NotBlank
import productManagement.productManagement.domain.entity.Brand


data class BrandDTO(
    @field:NotBlank(message = "필수값입니다.")
    val brandName: String,

    val useYn: String
) {
    constructor(brand: Brand) : this(
        brandName = brand.brandName,
        useYn = brand.useYn
    )

    fun toEntity(): Brand {
        return Brand(
            brandName = this.brandName,
            useYn = this.useYn
        )
    }
}