package com.example.architectureapp.mvvmflow

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.architectureapp.CommonGreeting
import com.example.architectureapp.mvistateflow.events.EventEffect

@ExperimentalMaterial3Api
@Composable
fun GreetingsMVVMScreen(mvvmStateViewModel: MVVMViewModel, navigateToEmailValidated: () -> Unit) {
    val email by mvvmStateViewModel.email.collectAsStateWithLifecycle()
    val emailValidityFormat by mvvmStateViewModel.emailValidityFormat.collectAsStateWithLifecycle()

    val context = LocalContext.current

    mvvmStateViewModel.showToastSingleLiveEvent.observe(LocalLifecycleOwner.current) { message ->
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    mvvmStateViewModel.navigateToEmailValidatedSingleLiveEvent.observe(LocalLifecycleOwner.current) {
        navigateToEmailValidated()
    }

    CommonGreeting(
        textValue = email,
        emailValidityFormat = emailValidityFormat,
        textModified = {
            mvvmStateViewModel.validEmail(it)
        },
        onButtonClicked = {
            mvvmStateViewModel.navigateToEmailValidated()
        },
        onShowMessageButtonClicked = {
            mvvmStateViewModel.showToast(email)
        }
    )
}