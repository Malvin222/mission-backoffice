package productManagement.productManagement.brand.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import productManagement.productManagement.brand.dto.BrandDTO
import productManagement.productManagement.domain.repository.BrandRepository

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {
    @Transactional
    fun getBrand():List<BrandDTO>{
        val brands = brandRepository.findAllByUseYn("Y")

        return brands.map { BrandDTO(it) }
    }

    @Transactional
    fun getBrandById(id: Long): BrandDTO {
        val brand = brandRepository.findById(id)
            .orElseThrow { EntityNotFoundException("브랜드 정보를 찾을 수 없습니다.") }
        return BrandDTO(brand)
    }

    @Transactional
    fun save(brandDTO: BrandDTO) {
        brandRepository.save(brandDTO.toEntity())
    }

    @Transactional
    fun updateBrand(id: Long, brandDTO: BrandDTO) {
        val brand = brandRepository.findById(id)
            .orElseThrow { EntityNotFoundException("브랜드 정보를 찾을 수 없습니다.") }
        brand.update(brandDTO.brandName)
    }

    @Transactional
    fun disableBrand(id: Long) {
        val brand = brandRepository.findById(id)
            .orElseThrow { EntityNotFoundException("브랜드 정보를 찾을 수 없습니다.") }
        brand.disable()
    }

}