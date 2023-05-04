package com.example.architectureapp.mvistateflow

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.architectureapp.CommonGreeting
import com.example.architectureapp.mvistateflow.events.EventEffect

@ExperimentalMaterial3Api
@Composable
fun GreetingsMVIStateScreen(mviStateViewModel: MVIStateViewModel, navigateToEmailValidated: () -> Unit) {
    val state by mviStateViewModel.state.collectAsStateWithLifecycle()
    val effects by mviStateViewModel.effect.collectAsStateWithLifecycle()
    val context = LocalContext.current

    EventEffect(
        event = effects.showToast,
        onConsumed = mviStateViewModel::setEventEffectsConsumed,
        action = { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    )

    EventEffect(
        event = effects.navigateToEmailValidated,
        onConsumed = mviStateViewModel::setEventEffectsConsumed,
        action = {
            navigateToEmailValidated()
        }
    )

    CommonGreeting(
        textValue = state.email,
        emailValidityFormat = state.validityFormat,
        textModified = {
            mviStateViewModel.event(EmailContract.Event.OnValidEmailClicked(it))
        },
        onButtonClicked = {
            mviStateViewModel.event(EmailContract.Event.NavigateToEmailValidated)
        },
        onShowMessageButtonClicked = {
            mviStateViewModel.event(EmailContract.Event.ShowToast(state.email))
        }
    )
}