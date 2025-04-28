package s3446484.grocerystore.murivikasredd.offers

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [OfferEntity::class], version = 1)
abstract class OfferDatabase : RoomDatabase() {
    abstract fun offerDao(): OfferDao
}
