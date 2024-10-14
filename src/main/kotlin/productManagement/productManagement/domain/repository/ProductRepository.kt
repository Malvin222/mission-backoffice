package productManagement.productManagement.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import productManagement.productManagement.domain.entity.Product
import java.util.*

interface ProductRepository : JpaRepository<Product, Long> {

    fun findAllByUseYn(useYn: String): List<Product>

    override fun findById(id: Long): Optional<Product>
}