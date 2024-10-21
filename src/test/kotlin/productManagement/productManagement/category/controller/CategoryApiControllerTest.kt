package productManagement.productManagement.category.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
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
import productManagement.productManagement.category.dto.CategoryDTO
import productManagement.productManagement.category.service.CategoryService

@SpringBootTest
@AutoConfigureMockMvc
class CategoryApiControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var categoryService: CategoryService

    @Test
    fun testGetCategory() {
        // given
        val categoryDTOList = listOf(
            CategoryDTO(categoryName = "TestCategory1", useYn = "Y"),
            CategoryDTO(categoryName = "TestCategory2", useYn = "Y")
        )
        val uri = "/api/categories"

        // Mock the getCategory method
        whenever(categoryService.getCategory()).thenReturn(categoryDTOList)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryName").value("TestCategory1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].categoryName").value("TestCategory2"))
    }

    @Test
    fun testGetCategoryById() {
        // given
        val categoryDTO = CategoryDTO(categoryName = "TestCategory", useYn = "Y")
        val uri = "/api/categories/1"

        // Mock the getCategoryById method
        whenever(categoryService.getCategoryById(1L)).thenReturn(categoryDTO)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("TestCategory"))
    }

    @Test
    fun testPostCategory() {
        //given
        val categoryDTO = CategoryDTO(categoryName = "TestCategory", useYn = "Y")
        val uri = "/api/categories"

        // Mock the save method (which is void)
        doNothing().`when`(categoryService).save(any<CategoryDTO>())

        // When & Then
        mockMvc.perform(
            MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("데이터가 저장되었습니다."))
    }

    @Test
    fun testUpdateCategory() {
        // given
        val categoryDTO = CategoryDTO(categoryName = "UpdatedCategory", useYn = "Y")
        val uri = "/api/categories/1"

        // Mock the updateCategory method (which is void)
        doNothing().`when`(categoryService).updateCategory(any(), any())

        // when & then
        mockMvc.perform(
            MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("데이터가 수정되었습니다."))
    }

    @Test
    fun testDisableCategory() {
        // given
        val uri = "/api/categories/1/disable"

        // Mock the disableCategory method (which is void)
        doNothing().`when`(categoryService).disableCategory(1L)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.patch(uri))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("데이터가 삭제되었습니다."))
    }

}