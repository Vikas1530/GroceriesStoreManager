package s3446484.grocerystore.murivikasredd

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class UpdateProductsListActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdateManageStockListScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateManageStockListScreen() {
    val context = LocalContext.current as Activity
    val userEmail = GroceryStoreData.readMail(context)
    var productsList by remember { mutableStateOf(listOf<ProductData>()) }
    var loadProducts by remember { mutableStateOf(true) }

    var searchQuery by remember { mutableStateOf("") }
    var filterExpiry by remember { mutableStateOf(false) }
    var filterLowStock by remember { mutableStateOf(false) }

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
        }
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
                text = "Update Products",
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

            // Chip Filters Row
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
                                        UpdateProductItemCard(product)
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
fun UpdateProductItemCard(stockItem: ProductData) {

    var selectedProduct by remember { mutableStateOf<ProductData?>(null) }
    var newStockValue by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

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

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
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

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Update",
                    color = Color.White,
                    modifier = Modifier
                        .wrapContentHeight()
                        .clickable {
                            SelectedProduct.productItem = stockItem
                            context.startActivity(
                                Intent(
                                    context,
                                    UpdateProductActivity::class.java
                                )
                            )

                        }
                        .background(
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(
                                6.dp
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 4.dp
                        )
                )

            }

            if (showDialog && selectedProduct != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Update Stock") },
                    text = {
                        Column {
                            Text("Product: ${selectedProduct!!.name}")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = newStockValue,
                                onValueChange = { newStockValue = it },
                                label = { Text("New Stock") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            val newStock = newStockValue.toIntOrNull()
                            if (newStock != null) {

                                val updatedData = mapOf(
                                    "productId" to selectedProduct!!.productId,
                                    "name" to selectedProduct!!.name,
                                    "category" to selectedProduct!!.category,
                                    "brand" to selectedProduct!!.brand,
                                    "description" to selectedProduct!!.description,
                                    "expiryDate" to selectedProduct!!.expiryDate,
                                    "quantity" to selectedProduct!!.quantity,
                                    "price" to selectedProduct!!.price,
                                    "stock" to newStock.toString()
                                )


                                updateStock(selectedProduct!!.productId, updatedData, context)
                            }
                            showDialog = false
                        }) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}