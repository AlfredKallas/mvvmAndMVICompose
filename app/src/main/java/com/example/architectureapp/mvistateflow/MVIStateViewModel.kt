package com.example.architectureapp.mvistateflow

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectureapp.mvistateflow.events.consumed
import com.example.architectureapp.mvistateflow.events.triggered
import com.example.architectureapp.utils.EmailValidityFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MVIStateViewModel @Inject constructor(): ViewModel(), EmailContract {
    private val mutableState = MutableStateFlow(EmailContract.State())
    override val state: StateFlow<EmailContract.State> = mutableState.asStateFlow()

    private val effectFlow = MutableStateFlow(EmailContract.Effect())
    override val effect: StateFlow<EmailContract.Effect> = effectFlow.asStateFlow()

    override fun event(event: EmailContract.Event) =
        when(event) {
            is EmailContract.Event.NavigateToEmailValidated -> navigateToEmailValidated()
            is EmailContract.Event.ShowToast -> showToast(event.message)
            is EmailContract.Event.OnValidEmailClicked -> validEmail(email = event.email)
        }

    private fun validEmail(email: String) {
        viewModelScope.launch {
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
    }

    private fun showToast(message: String) {
        if (message.isNotEmpty()) {
            viewModelScope.launch {
                effectFlow.update {
                    it.copy(
                        showToast = triggered(
                            content = message
                        )
                    )
                }
            }
        }
    }

    private fun navigateToEmailValidated() {
        viewModelScope.launch {
            effectFlow.update {
                it.copy(
                    navigateToEmailValidated = triggered
                )
            }
        }
    }

    override fun setEventEffectsConsumed() {
        viewModelScope.launch {
            effectFlow.update {
                it.copy(
                    showToast = consumed(),
                    navigateToEmailValidated = consumed
                )
            }
        }
    }
}