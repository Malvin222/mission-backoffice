package productManagement.productManagement.domain.entity

import jakarta.persistence.*

@Entity
class Category(
    categoryName: String,
    useYn: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    var id: Long? = null

    var categoryName: String = categoryName
    var useYn: String = useYn

    fun disable() {
        this.useYn = "N"
    }

    fun update(categoryName: String) {
        this.categoryName = categoryName
    }
}