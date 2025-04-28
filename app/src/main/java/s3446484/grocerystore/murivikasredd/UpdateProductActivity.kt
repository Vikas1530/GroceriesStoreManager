package s3446484.grocerystore.murivikasredd

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class UpdateProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdateProductActivityScreen()
        }
    }
}

object SelectedProduct {
    var productItem = ProductData()
}

@Composable
fun UpdateProductActivityScreen() {
    var productName by remember { mutableStateOf(SelectedProduct.productItem.name) }
    var category by remember { mutableStateOf(SelectedProduct.productItem.category) }
    var brand by remember { mutableStateOf(SelectedProduct.productItem.brand) }
    var description by remember { mutableStateOf(SelectedProduct.productItem.description) }
    var expiryDate by remember { mutableStateOf(SelectedProduct.productItem.expiryDate) }
    var quantity by remember { mutableStateOf(SelectedProduct.productItem.quantity) }
    var price by remember { mutableStateOf(SelectedProduct.productItem.price) }
    var stock by remember { mutableStateOf(SelectedProduct.productItem.stock) }


    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _, y, m, d ->
//            expiryDate = String.format("%04d-%02d-%02d", y, m + 1, d)

            expiryDate = String.format("%02d-%02d-%04d", d, m + 1, y)

        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )


    //TODO Add all the fields

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
                        (context as Activity).finish()
                    },
                painter = painterResource(id = R.drawable.iv_back_arrow),
                contentDescription = "Back Arrow Icon"
            )

            Spacer(modifier = Modifier.width(12.dp))


            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Add Product",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Brand") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(50.dp)
                    .clickable {
                        // Handle the click event, e.g., show a date picker
                    }
                    .background(Color.LightGray, MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = expiryDate.ifEmpty { "Expiry Date" },
                    color = if (expiryDate.isEmpty()) Color.Gray else Color.Black
                )
                Icon(
                    imageVector = Icons.Default.DateRange, // Replace with your desired icon
                    contentDescription = "Calendar Icon",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(24.dp)
                        .clickable {
                            datePicker.show()
                        },
                    tint = Color.DarkGray
                )
            }


            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {

                    }
                    .background(
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 8.dp)
                    .clickable {
                        if (productName.isEmpty()) {
                            Toast
                                .makeText(context, "Enter all fileds", Toast.LENGTH_SHORT)
                                .show()
                        } else {


                            val updateProduct = mapOf(
                                "productId" to SelectedProduct.productItem.productId,
                                "name" to productName,
                                "category" to category,
                                "brand" to brand,
                                "description" to description,
                                "expiryDate" to expiryDate,
                                "quantity" to quantity,
                                "price" to price,
                                "stock" to stock,
                            )

                            updateProduct(
                                SelectedProduct.productItem.productId,
                                updateProduct,
                                context
                            )
                        }
                    },
                text = "Update Product",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }
}

fun updateProduct(productId: String, updatedData: Map<String, Any>, context: Context) {

    try {
        val emailKey = GroceryStoreData.readMail(context)
            .replace(".", ",")
        val path = "Products/$emailKey/$productId"
        FirebaseDatabase.getInstance().getReference(path).updateChildren(updatedData)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Product Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                (context as Activity).finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
    } catch (e: Exception) {
        Log.e("Test", "Error Message : ${e.message}")
    }
}