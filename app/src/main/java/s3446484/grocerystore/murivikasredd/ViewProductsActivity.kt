package s3446484.grocerystore.murivikasredd

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ViewProductsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockListScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockListScreen() {
    val context = LocalContext.current as Activity
    val userEmail = GroceryStoreData.readMail(context)
    var productsList by remember { mutableStateOf(listOf<ProductData>()) }
    var loadProducts by remember { mutableStateOf(true) }

    var searchQuery by remember { mutableStateOf("") }
    var filterExpiry by remember { mutableStateOf(false) }
    var filterLowStock by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var lowStockProducts by remember { mutableStateOf(listOf<ProductData>()) }

    val filteredProducts = productsList.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }.filter {
        val expiryFilter = if (filterExpiry) {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val expiryDate = try {
                LocalDate.parse(it.expiryDate, formatter)
            } catch (e: Exception) {
                null
            }
            expiryDate?.let {
                val today = LocalDate.now()
                ChronoUnit.DAYS.between(today, it) <= 30
            } ?: false
        } else true

        val stockFilter = if (filterLowStock) {
            it.stock.toInt() < 20
        } else true

        expiryFilter && stockFilter
    }


    LaunchedEffect(userEmail) {
        getProducts(userEmail) { orders ->
            productsList = orders
            loadProducts = false

            val lowStock = orders.filter { it.stock.toInt() < 20 }
            if (lowStock.isNotEmpty()) {
                lowStockProducts = lowStock
                showDialog = true
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Low Stock Alert") },
            text = {
                Column {
                    Text("The following products are low in stock:")
                    Spacer(modifier = Modifier.height(8.dp))
                    lowStockProducts.forEach {
                        Text("- ${it.name}: ${it.stock} left")
                    }
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        context.finish()
                    },
                painter = painterResource(id = R.drawable.iv_back_arrow),
                contentDescription = "Back Arrow Icon"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "View Products",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Product by Name") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                FilterChip(
                    selected = filterExpiry,
                    onClick = { filterExpiry = !filterExpiry },
                    label = { Text("Expiry Date") }
                )
                FilterChip(
                    selected = filterLowStock,
                    onClick = { filterLowStock = !filterLowStock },
                    label = { Text("Low Stock") }
                )
            }

            if (loadProducts) {
                LinearProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {

                if (filteredProducts.isNotEmpty()) {
                    LazyColumn {
                        items(filteredProducts.chunked(2)) { pair ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                pair.forEach { product ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        StockItemCard(product)
                                    }
                                }
                                if (pair.size < 2) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .padding(12.dp),
                        text = "No Products Found",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun StockItemCard(stockItem: ProductData) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(stockItem.name, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Quantity: ${stockItem.quantity}", color = Color.White)

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Expiry", color = Color.Gray)
                    Spacer(modifier = Modifier.weight(1f))
//                    Text(":")
//                    Spacer(modifier = Modifier.weight(1f))
                    Text("${stockItem.expiryDate}", color = Color.Black)
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Category", color = Color.Gray)
                    Spacer(modifier = Modifier.weight(1f))
//                    Text(":")
//                    Spacer(modifier = Modifier.weight(1f))
                    Text("${stockItem.category}", color = Color.Black)
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Stock", color = Color.Gray)
                    Spacer(modifier = Modifier.weight(1f))
//                    Text(":")
//                    Spacer(modifier = Modifier.weight(1f))
                    Text("${stockItem.stock}", color = Color.Black)
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    var stockStatus = "Low Stock"
                    if (stockItem.stock.toInt() > 20) {
                        stockStatus = "Available"
                    }

                    val color = when (stockStatus) {
                        "Available" -> colorResource(id = R.color.dark_green)
                        "Low Stock" -> Color.Red
                        else -> Color.Gray
                    }
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(color, CircleShape)
                    )
                    Text("  $stockStatus", color = color)
                }

            }
        }
    }
}

fun getProducts(storeMail: String, callback: (List<ProductData>) -> Unit) {

    val emailKey = storeMail.replace(".", ",")
    val databaseReference = FirebaseDatabase.getInstance().getReference("Products/$emailKey")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {


            val productsList = mutableListOf<ProductData>()

            for (stockSnapShot in snapshot.children) {
                val book = stockSnapShot.getValue(ProductData::class.java)
                book?.let { productsList.add(it) }
            }

            callback(productsList)
        }


        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}


