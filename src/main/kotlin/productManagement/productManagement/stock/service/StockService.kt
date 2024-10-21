package productManagement.productManagement.stock.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import productManagement.productManagement.stock.dto.StockDTO
import productManagement.productManagement.domain.repository.StockRepository

@Service
class StockService(
    private val stockRepository: StockRepository
) {
    @Transactional
    fun getStock():List<StockDTO>{
        val stocks = stockRepository.findAll()

        return stocks.map { StockDTO(it) }
    }

    @Transactional
    fun getStockById(stockId: Long): StockDTO{
        val stock = stockRepository.findById(stockId)
            .orElseThrow{ NoSuchElementException("재고 정보를 찾을 수 없습니다.")}
        return StockDTO(stock)
    }

    @Transactional
    fun save(stockDTO: StockDTO) {
        val existingStock = stockRepository.findByProductId(stockDTO.product.id!!)
            ?: throw NoSuchElementException("재고 정보를 찾을 수 없습니다. ${stockDTO.product.id}")

        val updatedStockCount = (existingStock.stockCount ?: 0) + (stockDTO.receivingCount ?: 0)
        existingStock.stockCount = updatedStockCount
        existingStock.receivingCount = stockDTO.receivingCount

        stockRepository.save(existingStock)
    }

}