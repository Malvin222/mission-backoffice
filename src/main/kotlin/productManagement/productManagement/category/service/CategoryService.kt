package productManagement.productManagement.category.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import productManagement.productManagement.category.dto.CategoryDTO
import productManagement.productManagement.domain.repository.CategoryRepository

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun getCategory():List<CategoryDTO>{
        val categories = categoryRepository.findAllByUseYn("Y")

        return categories.map { CategoryDTO(it) }
    }

    @Transactional
    fun getCategoryById(id: Long): CategoryDTO {
        val category = categoryRepository.findById(id)
            .orElseThrow { EntityNotFoundException("카테고리 정보를 찾을 수 없습니다.") }
        return CategoryDTO(category)
    }

    @Transactional
    fun save(categoryDTO: CategoryDTO) {
        categoryRepository.save(categoryDTO.toEntity())
    }

    @Transactional
    fun updateCategory(id: Long, categoryDTO: CategoryDTO) {
        val category = categoryRepository.findById(id)
            .orElseThrow { EntityNotFoundException("카테고리 정보를 찾을 수 없습니다.") }
        category.update(categoryDTO.categoryName)
    }

    @Transactional
    fun disableCategory(id: Long) {
        val category = categoryRepository.findById(id)
            .orElseThrow { EntityNotFoundException("카테고리 정보를 찾을 수 없습니다.")}
                category.disable()
            }
}