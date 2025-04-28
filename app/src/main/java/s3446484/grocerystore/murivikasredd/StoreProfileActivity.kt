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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

class StoreProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreProfileActivityScreen()
        }
    }
}

@Composable
fun StoreProfileActivityScreen() {

    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
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
                        (context as Activity).finish()
                    },
                painter = painterResource(id = R.drawable.iv_back_arrow),
                contentDescription = "Back Arrow Icon"
            )

            Spacer(modifier = Modifier.width(12.dp))


            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Store Profile",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))
            Image(painter = painterResource(id = R.drawable.store_profile), contentDescription = "")
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Store Name", modifier = Modifier.weight(1f))
                Text(text = ": ")
                Text(
                    text = "${GroceryStoreData.readUserName(context)}",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Store EmailId", modifier = Modifier.weight(1f))
                Text(text = ": ")
                Text(text = "${GroceryStoreData.readMail(context)}",modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Store Address", modifier = Modifier.weight(1f))
                Text(text = ": ")
                Text(text = "${GroceryStoreData.readAddress(context)}",modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                modifier = Modifier
                    .clickable {
                        GroceryStoreData.writeLS(context, false)
                        context.startActivity(Intent(context, LoginActivity::class.java))
                        (context as Activity).finish()
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
                text = "Logout",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}