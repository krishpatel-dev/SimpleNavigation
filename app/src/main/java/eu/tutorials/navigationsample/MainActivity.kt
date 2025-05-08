package eu.tutorials.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.navigationsample.ui.theme.NavigationSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "firstscreen") {
        composable("firstscreen") {
            FirstScreen { name, age ->
                val safeName = if (name.isNotEmpty()) name else "default"
                val safeAge = age.toIntOrNull() ?: 0 // Default to 0 if input is invalid
                navController.navigate("secondscreen/$safeName/$safeAge")
            }
        }
        composable("secondscreen/{name}/{age}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "no name"
            val age = backStackEntry.arguments?.getString("age")?.toIntOrNull() ?: 0
            SecondScreen(
                name,
                age,
                navigationToFirstScreen = {
                    navController.navigate("firstscreen")
                },
                navigationToThirdScreen = {
                    navController.navigate("thirdscreen")
                }
            )
        }
        composable("thirdscreen") {
            ThirdScreen {
                navController.navigate("firstscreen")
            }
        }
    }
}