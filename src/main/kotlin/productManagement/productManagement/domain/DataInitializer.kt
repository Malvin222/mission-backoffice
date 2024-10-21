package productManagement.productManagement.domain

import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import productManagement.productManagement.domain.entity.Brand
import productManagement.productManagement.domain.entity.Category
import productManagement.productManagement.domain.entity.Product
import productManagement.productManagement.domain.entity.Stock
import productManagement.productManagement.domain.repository.BrandRepository
import productManagement.productManagement.domain.repository.CategoryRepository
import productManagement.productManagement.domain.repository.ProductRepository
import productManagement.productManagement.domain.repository.StockRepository

@Component
@Profile(value = ["default"])
class DataInitializer(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val brandRepository: BrandRepository,
    private val stockRepository: StockRepository,
    private val logger: Logger = LoggerFactory.getLogger(DataInitializer::class.java)
) {
    @PostConstruct
    fun initializeData() {
        logger.debug("스프링이 실행되었습니다. 데이터를 초기화합니다.")

        // 카테고리

        val foodCategory = Category(categoryName = "음식", useYn = "Y")
        val waterCategory = Category(categoryName = "음료", useYn = "Y")
        val bakeryCategory = Category(categoryName = "제과", useYn = "Y")


        // 브랜드
        val samyang = Brand(brandName = "삼양", useYn = "Y")
        val lotte = Brand(brandName = "롯데", useYn = "Y")
        val pulmuone = Brand(brandName = "풀무원", useYn = "Y")


        // 제품 저장
        val product1 = Product(
            productName = "맛있는우유",
            taxYn = "Y",
            useYn = "Y",
            id = 1,
            brand = samyang,
            category = foodCategory
        )
        product1.addCategory(waterCategory)
        product1.addBrand(samyang)

        val product2 = Product(
            productName = "맛있는 과자",
            taxYn = "Y",
            useYn = "Y",
            id = 2,
            brand = lotte,
            category = waterCategory
        )
        product2.addCategory(foodCategory)
        product2.addBrand(lotte)


        val product3 = Product(
            productName = "맛있는 빵",
            taxYn = "Y",
            useYn = "Y",
            id = 3,
            brand = pulmuone,
            category = bakeryCategory
        )

        productRepository.saveAll(mutableListOf(product1, product2, product3))

        // 재고 저장
        val stock1 = Stock(
            receivingCount = 5,
            receivingPrice = 500,
            stockCount = 0
        )
        stock1.addProduct(product1)

        stockRepository.save(stock1)
        logger.debug("재고 저장 완료")
    }
}