package s3446484.grocerystore.murivikasredd.offers

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class OfferViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        OfferDatabase::class.java,
        "offers_db"
    ).build()

    val offers = db.offerDao().getAllOffers().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addOffer(title: String, imageUri: Uri) {
        viewModelScope.launch {
            db.offerDao().insertOffer(
                OfferEntity(title = title, imageUri = imageUri.toString())
            )
        }
    }
}
