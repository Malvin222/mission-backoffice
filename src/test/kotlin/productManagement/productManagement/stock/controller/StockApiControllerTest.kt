package productManagement.productManagement.stock.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
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
import productManagement.productManagement.stock.dto.StockDTO

import productManagement.productManagement.stock.service.StockService
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class StockApiControllerTest{
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var stockService: StockService

    @Test
    fun testGetStocks() {
        // given
        val productDTO = ProductDTO(
            id = 1L,
            productName = "testProduct",
            taxYn = "Y",
            useYn = "Y",
            brand = BrandDTO(brandName = "testBrand", useYn = "Y"),
            category = CategoryDTO(categoryName = "testCategory", useYn = "Y")
        )
        val stockDTOList = listOf(
            StockDTO(stockCount = 100, receivingCount = 10, receivingPrice = 500, product = productDTO, receivingDate = LocalDateTime.now()),
            StockDTO(stockCount = 200, receivingCount = 20, receivingPrice = 1000, product = productDTO, receivingDate = LocalDateTime.now())
        )
        val uri = "/api/stocks"

        // Mock the getStock method
        whenever(stockService.getStock()).thenReturn(stockDTOList)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].stockCount").value(100))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].stockCount").value(200))
    }

    @Test
    fun getStockById() {
        // given
        val productDTO = ProductDTO(
            id = 1L,
            productName = "testProduct",
            taxYn = "Y",
            useYn = "Y",
            brand = BrandDTO(brandName = "testBrand", useYn = "Y"),
            category = CategoryDTO(categoryName = "testCategory", useYn = "Y")
        )
        val stockDTO = StockDTO(
            stockCount = 10,
            receivingCount = 5,
            receivingPrice = 500,
            product = productDTO,
            receivingDate = LocalDateTime.now()
        )

        // Mock the service to return a stockDTO when called with stockId
        `when`(stockService.getStockById(1L)).thenReturn(stockDTO)

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stocks/{stock_id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.stockCount").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath("$.receivingCount").value(5))
            .andExpect(MockMvcResultMatchers.jsonPath("$.receivingPrice").value(500))
            .andExpect(MockMvcResultMatchers.jsonPath("$.product.productName").value("testProduct"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.product.brand.brandName").value("testBrand"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.product.category.categoryName").value("testCategory"))
    }

    @Test
    fun testPostStock() {
        //given
        val stockDTO = StockDTO(
            stockCount = 0,
            receivingCount = 5,
            receivingPrice = 500,
            product = ProductDTO(
                id = 1,
                productName = "testProduct",
                taxYn = "Y",
                useYn = "Y",
                brand = BrandDTO(brandName = "testBrand", useYn = "Y"),
                category = CategoryDTO(categoryName = "testCategory", useYn = "Y")),
            receivingDate = LocalDateTime.now()

        )
        val uri = "/api/stocks"

        // Mock the save method (which is void)
        doNothing().`when`(stockService).save(any<StockDTO>())

        // When & Then
        mockMvc.perform(
            MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockDTO)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("데이터가 저장되었습니다."))
    }


}