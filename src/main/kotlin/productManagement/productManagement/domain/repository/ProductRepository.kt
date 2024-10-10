package productManagement.productManagement.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import productManagement.productManagement.domain.entity.Brand
import productManagement.productManagement.domain.entity.Category
import productManagement.productManagement.domain.entity.Product
import java.util.*

interface ProductRepository : JpaRepository<Product, Long> {

    fun findAllByUseYn(useYn: Boolean): List<Product>

    override fun findById(id: Long): Optional<Product>
}