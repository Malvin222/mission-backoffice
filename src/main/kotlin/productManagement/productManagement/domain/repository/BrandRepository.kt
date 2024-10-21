package productManagement.productManagement.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import productManagement.productManagement.domain.entity.Brand

interface BrandRepository : JpaRepository<Brand, Long> {

    fun findAllByUseYn(useYn: String): List<Brand>

}