package com.example.architectureapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    onMVISharedScreenClicked: () -> Unit,
    onMVIStateScreenClicked: () -> Unit,
    onMVVMScreenClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onMVISharedScreenClicked,
            content = {
                Text(text = "MVI Shared Screen")
            }
        )
        Button(
            onClick = onMVIStateScreenClicked,
            content = {
                Text(text = "MVI State Screen")
            }
        )
        Button(
            onClick = onMVVMScreenClicked,
            content = {
                Text(text = "MVVM Screen")
            }
        )
    }
}