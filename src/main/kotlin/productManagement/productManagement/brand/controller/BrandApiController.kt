package productManagement.productManagement.brand.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import productManagement.productManagement.brand.dto.BrandDTO
import productManagement.productManagement.brand.service.BrandService
import productManagement.productManagement.category.dto.CategoryDTO
import productManagement.productManagement.data.ApiResponse
import productManagement.productManagement.domain.entity.Brand

@RestController
@RequestMapping("/api/brands")
class BrandApiController(
    private val brandService: BrandService
) {
    @GetMapping
    fun getBrand(): List<BrandDTO> {
        return brandService.getBrand()
    }

    @GetMapping("/{brand_id}")
    fun getBrandById(@PathVariable brand_id: Long): BrandDTO {
        return brandService.getBrandById(brand_id)
    }

    @PostMapping
    fun postBrand(@RequestBody @Validated brandDTO: BrandDTO): ResponseEntity<Any>{
        brandService.save(brandDTO)

        return ResponseEntity(ApiResponse.successCreate(),HttpStatus.CREATED)
    }

    @PutMapping("/{brand_id}")
    fun updateBrand(@PathVariable brand_id: Long, @RequestBody @Validated brandDTO: BrandDTO): ResponseEntity<Any> {
        brandService.updateBrand(brand_id, brandDTO)
        return ResponseEntity.ok(ApiResponse.successUpdate())
    }

    @PatchMapping("/{brand_id}/disable")
    fun disableBrand(@PathVariable brand_id: Long): ResponseEntity<Any> {
        brandService.disableBrand(brand_id)
        return ResponseEntity.ok(ApiResponse.successDelete())
    }

}