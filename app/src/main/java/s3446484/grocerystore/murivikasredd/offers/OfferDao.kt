package s3446484.grocerystore.murivikasredd.offers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OfferDao {
    @Insert
    suspend fun insertOffer(offer: OfferEntity)

    @Query("SELECT * FROM offers")
    fun getAllOffers(): Flow<List<OfferEntity>>
}
