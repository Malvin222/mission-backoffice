package productManagement.productManagement.product.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import productManagement.productManagement.product.dto.ProductDTO
import productManagement.productManagement.domain.repository.ProductRepository

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    @Transactional
    fun getProduct():List<ProductDTO>{
        val products = productRepository.findAllByUseYn("Y")

        return products.map { ProductDTO(it) }
    }

    @Transactional(readOnly = true)
    fun getProductById(productId: Long): ProductDTO? {
        return productRepository.findById(productId).map { ProductDTO(it) }.orElse(null)
    }

    @Transactional
    fun save(productDTO: ProductDTO) {
        productRepository.save(productDTO.toEntity())
    }

    @Transactional
    fun updateProduct(productId: Long, productDTO: ProductDTO): ProductDTO? {
        return productRepository.findById(productId).map { product ->
            val updatedProduct = productDTO.toEntity().apply {
                this.id = product.id
            }
            productRepository.save(updatedProduct)
            ProductDTO(updatedProduct)
        }.orElse(null)
    }

    @Transactional
    fun deleteProduct(productId: Long) {
        productRepository.deleteById(productId)
    }
}