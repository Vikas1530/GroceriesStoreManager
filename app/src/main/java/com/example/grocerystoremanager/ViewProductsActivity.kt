package com.example.grocerystoremanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ViewProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockListScreen()
        }
    }
}

@Composable
fun StockListScreen() {
    val stockList = listOf(
        StockItem("Milk", 20, "2025-05-01", "Dairy", "Available"),
        StockItem("Eggs", 50, "2025-04-30", "Poultry", "Available"),
        StockItem("Yogurt", 10, "2025-04-20", "Dairy", "Low Stock"),
        StockItem("Chicken Breast", 15, "2025-04-18", "Meat", "Available"),
        StockItem("Apples", 30, "2025-04-22", "Fruits", "Available"),
        StockItem("Bananas", 5, "2025-04-17", "Fruits", "Low Stock"),
        StockItem("Spinach", 12, "2025-04-19", "Vegetables", "Available"),
        StockItem("Bread", 25, "2025-04-21", "Bakery", "Available"),
        StockItem("Butter", 8, "2025-06-15", "Dairy", "Low Stock"),
        StockItem("Orange Juice", 18, "2025-05-10", "Beverages", "Available"),
        StockItem("Cheese", 6, "2025-05-05", "Dairy", "Low Stock"),
        StockItem("Tomatoes", 20, "2025-04-20", "Vegetables", "Available"),
        StockItem("Onions", 40, "2025-04-25", "Vegetables", "Available"),
        StockItem("Rice", 100, "2026-01-01", "Grains", "Available"),
        StockItem("Sugar", 60, "2026-03-01", "Pantry", "Available")
    )


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
                        //Call finish here
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


        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(stockList.size) { item ->
                StockItemCard(stockList[item])
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}

@Composable
fun StockItemCard(stockItem: StockItem) {
    Card( modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stockItem.itemName, fontWeight = FontWeight.Bold)
            Text("Quantity: ${stockItem.quantity}")
            Text("Expiry Date: ${stockItem.expiryDate}")
            Text("Category: ${stockItem.category}")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                val color = when (stockItem.stockStatus) {
                    "Available" -> Color.Green
                    "Low Stock" -> Color.Red
                    else -> Color.Gray
                }
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(color, CircleShape)
                )
                Text("  ${stockItem.stockStatus}", color = color)
            }
        }
    }
}

data class StockItem(
    val itemName: String,
    val quantity: Int,
    val expiryDate: String,
    val category: String,
    val stockStatus: String
)
