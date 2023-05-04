package com.example.architectureapp.mviSharedFlow

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectureapp.utils.EmailValidityFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MVISharedViewModel @Inject constructor(): ViewModel(), EmailContract {
    private val mutableState = MutableStateFlow(EmailContract.State())
    override val state: StateFlow<EmailContract.State> = mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<EmailContract.Effect>()
    override val effect: SharedFlow<EmailContract.Effect> = effectFlow.asSharedFlow()

    override fun event(event: EmailContract.Event) =
        when(event) {
            is EmailContract.Event.NavigateToEmailValidated -> navigateToEmailValidated()
            is EmailContract.Event.ShowToast -> showToast(event.message)
            is EmailContract.Event.OnValidEmailClicked -> validEmail(email = event.email)
        }

    private fun validEmail(email: String) {
        if (TextUtils.isEmpty(email)) {
            mutableState.update {
                it.copy(
                    email = email,
                    validityFormat = EmailValidityFormat.EmptyFormat
                )
            }
        } else {
            val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            if (!isValid) {
                mutableState.update {
                    it.copy(
                        email = email,
                        validityFormat = EmailValidityFormat.InvalidFormat
                    )
                }
            } else {
                mutableState.update {
                    it.copy(
                        email = email,
                        validityFormat = EmailValidityFormat.ValidFormat
                    )
                }
            }
        }
    }

    private fun showToast(message: String) {
        if (message.isNotEmpty()) {
            viewModelScope.launch {
                effectFlow.emit(
                    EmailContract.Effect.ShowToast(message = message)
                )
            }
        }
    }

    private fun navigateToEmailValidated() {
        viewModelScope.launch {
            effectFlow.emit(EmailContract.Effect.NavigateToEmailValidated)
        }
    }

}