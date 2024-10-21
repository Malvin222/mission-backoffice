package productManagement.productManagement.brand.controller

import com.fasterxml.jackson.databind.ObjectMapper
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
import productManagement.productManagement.brand.dto.BrandDTO
import productManagement.productManagement.brand.service.BrandService

@SpringBootTest
@AutoConfigureMockMvc
class BrandApiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var brandService: BrandService

    @Test
    fun testGetBrand() {
        // given
        val brandDTOList = listOf(
            BrandDTO(brandName = "TestBrand1", useYn = "Y"),
            BrandDTO(brandName = "TestBrand2", useYn = "Y")
        )
        val uri = "/api/brands"

        // Mock the getBrand method
        whenever(brandService.getBrand()).thenReturn(brandDTOList)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].brandName").value("TestBrand1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].brandName").value("TestBrand2"))
    }

    @Test
    fun testGetBrandById() {
        // given
        val brandDTO = BrandDTO(brandName = "TestBrand", useYn = "Y")
        val uri = "/api/brands/1"

        // Mock the getBrandById method
        whenever(brandService.getBrandById(1L)).thenReturn(brandDTO)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").value("TestBrand"))
    }


    @Test
    fun testPostBrand() {
        //given
        val brandDTO = BrandDTO(brandName = "TestBrand", useYn = "Y")
        val uri = "/api/brands"

        // Mock the save method (which is void)
        doNothing().`when`(brandService).save(any<BrandDTO>())

        // When & Then
        mockMvc.perform(
            MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandDTO)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("데이터가 저장되었습니다."))
    }

    @Test
    fun testUpdateBrand() {
        // given
        val brandDTO = BrandDTO(brandName = "UpdatedBrand", useYn = "Y")
        val uri = "/api/brands/1"

        // Mock the updateBrand method (which is void)
        doNothing().`when`(brandService).updateBrand(any(), any())

        // when & then
        mockMvc.perform(
            MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandDTO))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("데이터가 수정되었습니다."))
    }

    @Test
    fun testDisableBrand() {
        // given
        val uri = "/api/brands/1/disable"

        // Mock the disableBrand method (which is void)
        doNothing().`when`(brandService).disableBrand(1L)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.patch(uri))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("데이터가 삭제되었습니다."))
    }
}
