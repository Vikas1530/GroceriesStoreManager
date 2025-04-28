package s3446484.grocerystore.murivikasredd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StoreStatusValidator(this)
        }
    }
}

@Composable
fun StoreStatusValidator(fragmentActivity: FragmentActivity) {
    val context = LocalContext.current as Activity
    var showSplash by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            showSplash = false
        }
        onDispose { job.cancel() }
    }

    if (showSplash) {
        SplashScreen()

    } else {

        if (GroceryStoreData.readLS(context)) {
            val biometricManager = BiometricManager.from(fragmentActivity)
            if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
                val executor = ContextCompat.getMainExecutor(fragmentActivity)
                val biometricPrompt =
                    BiometricPrompt(
                        fragmentActivity,
                        executor,
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                context.startActivity(
                                    Intent(
                                        context,
                                        StoreHomeActivity::class.java
                                    )
                                )
                                context.finish()
                            }

                            override fun onAuthenticationError(
                                errorCode: Int,
                                errString: CharSequence
                            ) {
                                super.onAuthenticationError(errorCode, errString)
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                                    .show()
                            }

                            override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })

                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("FingerPrint Verification")
                    .setSubtitle("Place your finger to continue")
                    .setNegativeButtonText("Close")
                    .build()

                biometricPrompt.authenticate(promptInfo)
            } else {
                Toast.makeText(
                    fragmentActivity,
                    "This device doesn't support fingerprint",
                    Toast.LENGTH_LONG
                ).show()
                context.startActivity(Intent(context, StoreHomeActivity::class.java))
                context.finish()
            }
        } else {
            context.startActivity(Intent(context, LoginActivity::class.java))
            context.finish()
        }

    }

}

@Composable
fun SplashScreen() {

//    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.p1)),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Grocery Store Manager",
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )


            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(16.dp),

                )
            {
                Image(
                    modifier = Modifier.size(200.dp, 200.dp),
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "Grocery Store Manager",
                )


            }
            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "By",
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Vikas Kumar Reddy",
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )


        }
    }

}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}