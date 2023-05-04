package com.example.architectureapp.mviSharedFlow

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.architectureapp.CommonGreeting
import com.example.architectureapp.utils.collectInLaunchedEffect

@ExperimentalMaterial3Api
@Composable
fun GreetingsMVISharedScreen(mviSharedViewModel:MVISharedViewModel, navigateToEmailValidated: () -> Unit) {
    val state by mviSharedViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    mviSharedViewModel.effect.collectInLaunchedEffect(function = { effect ->
        when(effect) {
            is EmailContract.Effect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
            is EmailContract.Effect.NavigateToEmailValidated -> navigateToEmailValidated()
        }
    })
    CommonGreeting(
        textValue = state.email,
        emailValidityFormat = state.validityFormat,
        textModified = {
            mviSharedViewModel.event(EmailContract.Event.OnValidEmailClicked(it))
        },
        onButtonClicked = {
            mviSharedViewModel.event(EmailContract.Event.NavigateToEmailValidated)
        },
        onShowMessageButtonClicked = {
            mviSharedViewModel.event(EmailContract.Event.ShowToast(state.email))
        }
    )
}