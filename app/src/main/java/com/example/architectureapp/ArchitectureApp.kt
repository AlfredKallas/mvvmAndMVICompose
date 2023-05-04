package com.example.architectureapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.architectureapp.mviSharedFlow.GreetingsMVISharedScreen
import com.example.architectureapp.mvistateflow.GreetingsMVIStateScreen
import com.example.architectureapp.mvvmflow.GreetingsMVVMScreen
import com.example.architectureapp.ui.theme.ArchitectureAppTheme

@ExperimentalMaterial3Api
@Composable
fun ArchitectureApp() {
    ArchitectureAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Content()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Content() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = MAIN_SCREEN_ROUTE) {

        navigation(
            startDestination = MAIN_SCREEN,
            route = MAIN_SCREEN_ROUTE
        ) {
            composable(MAIN_SCREEN) {
                MainScreen(
                    onMVISharedScreenClicked = {
                        navController.navigate(GREETING_MVI_SHARED)
                    },
                    onMVIStateScreenClicked = {
                        navController.navigate(GREETING_MVI_STATE)
                    },
                    onMVVMScreenClicked = {
                        navController.navigate(GREETING_MVVM)
                    }
                )
            }

            composable(GREETING_MVI_SHARED) {
                GreetingsMVISharedScreen(
                    mviSharedViewModel = hiltViewModel(),
                    navigateToEmailValidated = {
                        navController.navigate(EMAIL_VALIDATED_SCREEN)
                    }
                )
            }

            composable(GREETING_MVI_STATE) {
                GreetingsMVIStateScreen(
                    mviStateViewModel = hiltViewModel(),
                    navigateToEmailValidated = {
                        navController.navigate(EMAIL_VALIDATED_SCREEN)
                    }
                )
            }

            composable(GREETING_MVVM) {
                GreetingsMVVMScreen(
                    mvvmStateViewModel = hiltViewModel(),
                    navigateToEmailValidated = {
                        navController.navigate(EMAIL_VALIDATED_SCREEN)
                    }
                )
            }

            composable(EMAIL_VALIDATED_SCREEN) {
                EmailVerified()
            }
        }
    }
}