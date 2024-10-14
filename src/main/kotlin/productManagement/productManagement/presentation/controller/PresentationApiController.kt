package productManagement.productManagement.presentation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import productManagement.productManagement.presentation.dto.BrandDTO
import productManagement.productManagement.presentation.dto.CategoryDTO
import productManagement.productManagement.presentation.dto.ProductDTO
import productManagement.productManagement.presentation.dto.StockDTO
import productManagement.productManagement.presentation.service.PresentationService

@RestController
@RequestMapping("/api")
class PresentationApiController(
    private val presentationService: PresentationService
) {

    @GetMapping("/v1/products")
    fun getProducts(): List<ProductDTO> {
        return presentationService.getProduct()
    }

    @GetMapping("/v1/categories")
    fun getCategories(): List<CategoryDTO> {
        return presentationService.getCategory()
    }

    @GetMapping("/v1/brands")
    fun getBrands(): List<BrandDTO> {
        return presentationService.getBrand()
    }

    @GetMapping("/v1/stocks")
    fun getStocks(): List<StockDTO> {
        return presentationService.getStock()
    }


}