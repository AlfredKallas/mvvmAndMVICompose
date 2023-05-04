package com.example.architectureapp.mvvmflow

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectureapp.utils.EmailValidityFormat
import com.example.architectureapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MVVMViewModel @Inject constructor(): ViewModel() {

    private val mutableEmail = MutableStateFlow("")
    val email: StateFlow<String> = mutableEmail.asStateFlow()

    private val mutableEmailValidityFormat = MutableStateFlow<EmailValidityFormat>(EmailValidityFormat.DefaultFormat)
    val emailValidityFormat: StateFlow<EmailValidityFormat> = mutableEmailValidityFormat.asStateFlow()

    private val _showToastSingleLiveEvent: SingleLiveEvent<String?> = SingleLiveEvent()
    val showToastSingleLiveEvent:LiveData<String?> = _showToastSingleLiveEvent

    private val _navigateToEmailValidatedSingleLiveEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val navigateToEmailValidatedSingleLiveEvent:LiveData<Unit> = _navigateToEmailValidatedSingleLiveEvent

    fun validEmail(email: String) {
        if (TextUtils.isEmpty(email)) {
            mutableEmail.update {
                email
            }
            mutableEmailValidityFormat.update {
                 EmailValidityFormat.EmptyFormat
            }
        } else {
            val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            if (!isValid) {
                mutableEmail.update {
                    email
                }
                mutableEmailValidityFormat.update {
                    EmailValidityFormat.InvalidFormat
                }
            } else {
                mutableEmail.update {
                    email
                }
                mutableEmailValidityFormat.update {
                    EmailValidityFormat.ValidFormat
                }
            }
        }
    }

    fun showToast(message: String) {
        if (message.isNotEmpty()) {
            viewModelScope.launch {
                _showToastSingleLiveEvent.value = message
            }
        }
    }

    fun navigateToEmailValidated() {
        viewModelScope.launch {
            _navigateToEmailValidatedSingleLiveEvent.value = null
        }
    }
}