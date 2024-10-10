package productManagement.productManagement.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import productManagement.productManagement.domain.entity.Brand
import productManagement.productManagement.domain.entity.Category
import productManagement.productManagement.domain.entity.Stock
import java.util.*

interface StockRepository : JpaRepository<Stock, Long> {

    override fun findById(id: Long): Optional<Stock>

    @Query("select s.stockCount from Stock s where s.product.id = :id")
    fun findStockQuantityById(@Param("id")id: Long): Int
}