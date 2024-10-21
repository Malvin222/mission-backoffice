package productManagement.productManagement.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import productManagement.productManagement.domain.entity.Category
import java.util.*

interface CategoryRepository : JpaRepository<Category, Long> {

    fun findAllByUseYn(useYn: String): List<Category>

}