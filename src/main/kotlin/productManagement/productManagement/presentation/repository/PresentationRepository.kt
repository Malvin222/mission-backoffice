package productManagement.productManagement.presentation.repository

import org.springframework.stereotype.Repository
import productManagement.productManagement.domain.entity.Brand
import productManagement.productManagement.domain.entity.Category
import productManagement.productManagement.domain.entity.Product
import productManagement.productManagement.domain.entity.Stock
import productManagement.productManagement.domain.repository.BrandRepository
import productManagement.productManagement.domain.repository.CategoryRepository
import productManagement.productManagement.domain.repository.ProductRepository
import productManagement.productManagement.domain.repository.StockRepository

@Repository
class PresentationRepository(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val stockRepository: StockRepository
) {
    fun getActiveProducts(): List<Product> {
        return productRepository.findAllByUseYn("Y")
    }

    fun getActiveBrands(): List<Brand> {
        return brandRepository.findAllByUseYn("Y")
    }

    fun getActivesCategories(): List<Category> {
        return categoryRepository.findAllByUseYn("Y")
    }

    fun getActiveStocks(): List<Stock> {
        return stockRepository.findAll()
    }
}