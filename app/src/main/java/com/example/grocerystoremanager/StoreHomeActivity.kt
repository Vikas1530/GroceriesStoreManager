package com.example.grocerystoremanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StoreHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreHomeActivityScreen()
        }
    }
}

@Composable
fun StoreHomeActivityScreen() {

//    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp),
            text = "Grocery Store Manager",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                        .clickable {

                        }
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.iv_grocery),
                        contentDescription = "Store Icon"
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Add New Stock",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                        .clickable {
                        }
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.iv_grocery),
                        contentDescription = "Store Icon"
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Add Vendors",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .clickable {
                        }
                        .weight(1f)
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 0.dp, vertical = 12.dp)


                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.iv_grocery),
                        contentDescription = "Store Icon"
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Manage Stock",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .clickable {
                        }
                        .weight(1f)
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 0.dp, vertical = 12.dp)

                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.iv_grocery),
                        contentDescription = "Store Icon"
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "My Profile",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }


        }

    }
}