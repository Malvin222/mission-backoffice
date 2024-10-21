package productManagement.productManagement.category.dto

import jakarta.validation.constraints.NotBlank
import productManagement.productManagement.domain.entity.Category


data class CategoryDTO(
    @field:NotBlank(message = "필수값입니다.")
    val categoryName: String,
    val useYn: String
) {
    constructor(category: Category) : this(
        categoryName = category.categoryName,
        useYn = category.useYn
    )

    fun toEntity(): Category {
        return Category(
            categoryName = this.categoryName,
            useYn = this.useYn
        )
    }
}