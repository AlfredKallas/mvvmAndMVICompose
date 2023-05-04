package com.example.architectureapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.architectureapp.ui.theme.ArchitectureAppTheme
import com.example.architectureapp.utils.EmailValidityFormat
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArchitectureApp()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun CommonGreeting(
    textValue: String,
    emailValidityFormat: EmailValidityFormat,
    textModified: (String) -> Unit,
    onButtonClicked: () -> Unit,
    onShowMessageButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textValue,
            onValueChange = textModified
        )
        AnimatedVisibility(visible = emailValidityFormat != EmailValidityFormat.ValidFormat && emailValidityFormat != EmailValidityFormat.DefaultFormat) {
            val text = when(emailValidityFormat) {
                EmailValidityFormat.EmptyFormat -> "Email is Empty"
                EmailValidityFormat.InvalidFormat -> "Invalid Email Format"
                else -> ""
            }
            Text(
                text = text,
                color = Color.Red
            )
        }
        Button(
            enabled = emailValidityFormat == EmailValidityFormat.ValidFormat,
            onClick = onButtonClicked,
            content = {
                Text(text = "Check Email")
            }
        )
        Button(
            onClick = onShowMessageButtonClicked,
            content = {
                Text(text = "Show Message")
            }
        )

    }
}

@Composable
fun EmailVerified() {
    Text(text = "Hurray Email Validated")
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArchitectureAppTheme {
        CommonGreeting(
            textValue = "",
            emailValidityFormat = EmailValidityFormat.DefaultFormat,
            textModified = { },
            onButtonClicked = { },
            onShowMessageButtonClicked = { }
        )
    }
}