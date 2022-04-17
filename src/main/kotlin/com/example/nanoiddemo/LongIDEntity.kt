package com.example.nanoiddemo

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import org.hashids.Hashids
import org.hibernate.annotations.GenericGenerator
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class LongIDEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String? = null
)
data class HashIDEntity(val id: String, val name: String)
@Entity
class NanoIDEntity(
    @Id
    @GeneratedValue(generator = "nano-generator")
    @GenericGenerator(name = "nano-generator", strategy = "com.example.nanoiddemo.NanoIDGenerator")
    var id: String? = null,
    var name: String? = null
)

@Repository
interface LongIDEntityRepository : JpaRepository<LongIDEntity, Long> {
}

@Repository
interface NanoIDEntityRepository : JpaRepository<NanoIDEntity, String> {
}

class NanoIDGenerator : IdentifierGenerator {
    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    private val nanoIDSize = 8
    override fun generate(session: SharedSessionContractImplementor?, obj: Any?): Serializable {
        val id: Serializable? =
            session?.getEntityPersister(null, obj)?.classMetadata?.getIdentifier(obj, session)

        return if (id != null) (id as String) else NanoIdUtils.randomNanoId(
            NanoIdUtils.DEFAULT_NUMBER_GENERATOR, allowedChars.toCharArray(), nanoIDSize
        )

    }
}

@Service
class HashIDProvider {
    private val provider = Hashids("YOUR_SUPER_SECURE_SALT_HERE", 8)
    fun getID(hashID: String) = provider.decode(hashID).first()
    fun getHashID(id: Long) = provider.encode(id)
}
