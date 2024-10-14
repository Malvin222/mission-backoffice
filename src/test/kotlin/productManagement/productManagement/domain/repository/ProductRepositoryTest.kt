package productManagement.productManagement.domain.repository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import productManagement.productManagement.domain.entity.Brand
import productManagement.productManagement.domain.entity.Product
import kotlin.test.Test

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryTest(
    @Autowired val productRepository: ProductRepository
) {
    val DATA_SIZE = 10

    private fun createProduct(n: Int): Product {
        val product = Product(
            productName = "product ${n}",
            taxYn = true,
            useYn = true
        )

        val brands = mutableListOf<Brand>()
        for (i in 1..n) {
            val brand = Brand(brandName = "brand ${i}", useYn = true)
            brands.add(brand)
        }

        product.addBrand(brands[n-1])
        return product
    }

    @BeforeAll
    fun beforeAll() {
        println("----- 테스트 데이터 초기화 이전 조회 시작 -----")
        val beforeInitialize = productRepository.findAll()
        assertThat(beforeInitialize).hasSize(0)
        println("----- 테스트 데이터 초기화 이전 조회 종료 -----")

        println("----- 테스트 데이터 초기화 시작-----")
        val products = mutableListOf<Product>()
        for(i in 1.. DATA_SIZE){
            val product = createProduct(i)
            products.add(product)
        }
        productRepository.saveAll(products)
        println("----- 테스트 데이터 초기화 종료 -----")
    }

    @Test
    fun testFindAll(){
        println("----- findAll 테스트 시작 -----")
        val products = productRepository.findAll()
        assertThat(products).hasSize(DATA_SIZE)
        println("products.size: ${products.size}")

        println("----- findAll 테스트 종료 -----")
    }

    @Test
    fun testFindAllByUseYn(){
        println("----- findAllByUseYn 테스트 시작 -----")
        val products = productRepository.findAllByUseYn(true)
        assertThat(products).hasSize(DATA_SIZE)
        println("products.size: ${products.size}")

        println("----- findAllByUseYn 테스트 종료 -----")
    }

}