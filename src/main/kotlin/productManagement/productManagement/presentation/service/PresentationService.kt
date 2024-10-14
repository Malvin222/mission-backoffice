package productManagement.productManagement.presentation.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import productManagement.productManagement.presentation.dto.BrandDTO
import productManagement.productManagement.presentation.dto.CategoryDTO
import productManagement.productManagement.presentation.dto.ProductDTO
import productManagement.productManagement.presentation.dto.StockDTO
import productManagement.productManagement.presentation.repository.PresentationRepository

@Service
class PresentationService(
    private val presentationRepository: PresentationRepository
) {
    @Transactional(readOnly = true)
    fun getProduct(): List<ProductDTO> {
        val products = presentationRepository.getActiveProducts()
        return products.map { ProductDTO(it) }
    }

    @Transactional(readOnly = true)
    fun getBrand(): List<BrandDTO> {
        val brands = presentationRepository.getActiveBrands()

        return brands.map { BrandDTO(it) }
    }

    @Transactional(readOnly = true)
    fun getCategory(): List<CategoryDTO> {
        val categories = presentationRepository.getActivesCategories()

        return categories.map { CategoryDTO(it) }
    }

    @Transactional(readOnly = true)
    fun getStock(): List<StockDTO> {
        val stocks = presentationRepository.getActiveStocks()

        return stocks.map { StockDTO(it) }
    }
}