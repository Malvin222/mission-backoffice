package productManagement.productManagement.presentation.dto

import productManagement.productManagement.domain.entity.Category

data class CategoryDTO(
    val categoryName: String
) {
    constructor(category: Category) : this(
        categoryName = category.categoryName
    )
}