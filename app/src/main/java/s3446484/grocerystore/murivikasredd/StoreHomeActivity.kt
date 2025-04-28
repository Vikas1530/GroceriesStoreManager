package s3446484.grocerystore.murivikasredd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class StoreHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreHomeScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoreHomeScreenP() {
    StoreHomeScreen()
}

@Composable
fun StoreHomeScreen() {

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = "Grocery Store Manager",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        context.startActivity(Intent(context, StoreProfileActivity::class.java))

                    },
                painter = painterResource(id = R.drawable.store_profile),
                contentDescription = "Store Profile"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .background(
                    color = colorResource(id = R.color.bg1),
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.bg1),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(8.dp)
        ) {

            Spacer(modifier = Modifier.width(20.dp))

            Image(
                modifier = Modifier
                    .size(86.dp),
                painter = painterResource(id = R.drawable.iv_grocery),
                contentDescription = "Store Icon"
            )

            Spacer(modifier = Modifier.width(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier,
                    text = "Products List",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "See list of products that are available",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    modifier = Modifier
                        .clickable {
                            context.startActivity(Intent(context, ViewProductsActivity::class.java))
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
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    text = "View Products",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Manage Store",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ManageItem(itemName = "Add\nProducts", itemImage = R.drawable.iv_grocery, itemCount = 1)

            ManageItem(itemName = "Manage\nStock", itemImage = R.drawable.iv_grocery, itemCount = 3)
            ManageItem(
                itemName = "Update\nProducts",
                itemImage = R.drawable.iv_grocery,
                itemCount = 4
            )

            ManageItem(
                itemName = "Add\nOffers",
                itemImage = R.drawable.iv_grocery,
                itemCount = 5
            )


        }

        Spacer(modifier = Modifier.height(12.dp))
        AboutStore()


    }
}


@Composable
fun ManageItem(itemName: String, itemImage: Int, itemCount: Int) {
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .clickable {
                when (itemCount) {
                    1 -> {
                        context.startActivity(Intent(context, AddProductsActivity::class.java))
                    }

                    3 -> {
                        context.startActivity(Intent(context, ManageStockActivity::class.java))
                    }
                    4 -> {
                        context.startActivity(
                            Intent(
                                context,
                                UpdateProductsListActivity::class.java
                            )
                        )
                    }

                    5 -> {
                        context.startActivity(
                            Intent(
                                context,
                                OffersActivity::class.java
                            )
                        )
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(58.dp)
                .background(
                    color = colorResource(id = R.color.bg1),
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.bg1),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(12.dp),
            painter = painterResource(id = itemImage),
            contentDescription = "Store Icon"
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            text = itemName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun AboutStore() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFFFFF), colorResource(id = R.color.bg1))
                )
            )
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Contact Us", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text("Name: Vikas Kumar Reddy")
                Text("Email: murivikasreddy@gmail.com")
                Text("Student ID: S3446484")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("About Us", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text(
                    "Welcome to the Groceries Store Stock Manager App Created by Vikas Kumar Reddy.  This app is designed with simplicity and productivity in mind. Whether you're managing a small grocery shop or a larger retail outlet, our goal is to help you keep track of inventory, monitor stock levels, and streamline your day-to-day operations – all from your mobile device.\n" +
                            "Thank you for choosing us!\n"
                )
            }
        }
    }
}
