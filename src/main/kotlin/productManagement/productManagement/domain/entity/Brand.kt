package productManagement.productManagement.domain.entity

import jakarta.persistence.*

@Entity
class Brand(
    brandName: String,
    useYn: Boolean
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    var id :Long? = null

    var brandName: String = brandName
    var useYn: Boolean = useYn

}