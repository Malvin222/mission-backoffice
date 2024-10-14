package productManagement.productManagement.presentation.controller

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.nio.charset.StandardCharsets
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("[API 컨트롤러 테스트]")
class PresentationApiControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
    @Test
    @DisplayName("Brands 조회")
    fun testGetBrands() {

        //given
        val uri = "/api/v1/brands"

        //when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)

        //then
        assertThat(jsonArray.length()).isPositive()
    }

    @Test
    @DisplayName("Categories 조회")
    fun testGetCategories() {

        //given
        val uri = "/api/v1/categories"

        //when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)

        //then
        assertThat(jsonArray.length()).isPositive()
    }

    @Test
    @DisplayName("Products 조회")
    fun testGetProducts() {

        //given
        val uri = "/api/v1/products"

        //when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)

        //then
        assertThat(jsonArray.length()).isPositive()
    }

    @Test
    @DisplayName("Stocks 조회")
    fun testGetStocks() {

        //given
        val uri = "/api/v1/stocks"

        //when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)

        //then
        assertThat(jsonArray.length()).isPositive()
    }

    private fun performGet(uri: String): MvcResult {
        return mockMvc
            .perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }
}