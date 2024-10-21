package productManagement.productManagement.product.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import productManagement.productManagement.product.dto.ProductDTO
import productManagement.productManagement.product.service.ProductService
import productManagement.productManagement.data.ApiResponse

@RestController
@RequestMapping("/api/products")
class ProductApiController(
    private val productService: ProductService
) {
    @GetMapping
    fun getProducts(): List<ProductDTO> {
        return productService.getProduct()
    }


    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: Long): ResponseEntity<ProductDTO> {
        val product = productService.getProductById(productId)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun postProduct(@RequestBody @Validated productDTO: ProductDTO): ResponseEntity<Any>{
        productService.save(productDTO)

        return ResponseEntity(ApiResponse.successCreate(),HttpStatus.CREATED)
    }


    @PutMapping("/{productId}")
    fun updateProduct(
        @PathVariable productId: Long,
        @RequestBody @Validated productDTO: ProductDTO
    ): ResponseEntity<Any> {
        val updatedProduct = productService.updateProduct(productId, productDTO)
        return if (updatedProduct != null) {
            ResponseEntity.ok(updatedProduct)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Any> {
        productService.deleteProduct(productId)
        return ResponseEntity.noContent().build()
    }

}