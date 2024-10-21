package productManagement.productManagement.product.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.doNothing
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import productManagement.productManagement.brand.dto.BrandDTO
import productManagement.productManagement.category.dto.CategoryDTO
import productManagement.productManagement.product.dto.ProductDTO
import productManagement.productManagement.product.service.ProductService

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiControllerTest{
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var productService: ProductService

    @Test
    fun testGetProduct() {
        // Given
        val productDTO = ProductDTO(
            id = 1L,
            productName = "testProduct",
            taxYn = "Y",
            useYn = "Y",
            brand = BrandDTO(brandName = "testBrand", useYn = "Y"),
            category = CategoryDTO(categoryName = "testCategory", useYn = "Y")
        )
        given(productService.getProduct()).willReturn(listOf(productDTO))

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("testProduct"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].taxYn").value("Y"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].useYn").value("Y"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun testGetProductById() {
        // Given
        val productDTO = ProductDTO(
            id = 1L,
            productName = "testProduct",
            taxYn = "Y",
            useYn = "Y",
            brand = BrandDTO(brandName = "testBrand", useYn = "Y"),
            category = CategoryDTO(categoryName = "testCategory", useYn = "Y")
        )
        given(productService.getProductById(1L)).willReturn(productDTO)

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("testProduct"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.taxYn").value("Y"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.useYn").value("Y"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun testPostProduct() {
        //given
        val productDTO = ProductDTO(
            id = 1,
            productName = "testProduct",
            taxYn = "Y",
            useYn = "Y",
            brand = BrandDTO(brandName = "testBrand", useYn = "Y"),
            category = CategoryDTO(categoryName = "testCategory", useYn = "Y")
        )
        val uri = "/api/products"

        // Mock the save method (which is void)
        doNothing().`when`(productService).save(any<ProductDTO>())

        // When & Then
        mockMvc.perform(
            MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("데이터가 저장되었습니다."))
    }

    @Test
    fun testUpdateProduct() {
        // Given
        val productDTO = ProductDTO(
            id = 1L,
            productName = "updatedProduct",
            taxYn = "N",
            useYn = "Y",
            brand = BrandDTO(brandName = "updatedBrand", useYn = "Y"),
            category = CategoryDTO(categoryName = "updatedCategory", useYn = "Y")
        )

        // Mock the update method
        given(productService.updateProduct(eq(1L), any<ProductDTO>())).willReturn(productDTO)

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("updatedProduct"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.taxYn").value("N"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun testDeleteProduct() {
        // Mock the delete method (which is void)
        doNothing().`when`(productService).deleteProduct(1L)

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }
}