package productManagement.productManagement.category.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import productManagement.productManagement.category.dto.CategoryDTO
import productManagement.productManagement.category.service.CategoryService
import productManagement.productManagement.data.ApiResponse
import productManagement.productManagement.stock.dto.StockDTO

@RestController
@RequestMapping("/api/categories")
class CategoryApiController(
    private val categoryService: CategoryService
) {

    @GetMapping
    fun getCategory(): List<CategoryDTO> {
        return categoryService.getCategory()
    }

    @GetMapping("/{category_id}")
    fun getCategoryById(@PathVariable category_id: Long): CategoryDTO {
        return categoryService.getCategoryById(category_id)
    }

    @PostMapping
    fun postCategory(@RequestBody @Validated categoryDTO: CategoryDTO): ResponseEntity<Any>{
        categoryService.save(categoryDTO)

        return ResponseEntity(ApiResponse.successCreate(),HttpStatus.CREATED)
    }

    @PutMapping("/{category_id}")
    fun updateCategory(@PathVariable category_id: Long, @RequestBody @Validated categoryDTO: CategoryDTO): ResponseEntity<Any> {
        categoryService.updateCategory(category_id, categoryDTO)
        return ResponseEntity.ok(ApiResponse.successUpdate())
    }

    @PatchMapping("/{category_id}/disable")
    fun disableCategory(@PathVariable category_id: Long): ResponseEntity<Any> {
        categoryService.disableCategory(category_id)
        return ResponseEntity.ok(ApiResponse.successDelete())
    }
}