package com.example.architectureapp.mviSharedFlow

import com.example.architectureapp.utils.EmailValidityFormat

interface EmailContract :
    UnidirectionalViewModel<EmailContract.State, EmailContract.Event,
        EmailContract.Effect> {

    data class State(
       val email:String = "",
       val validityFormat: EmailValidityFormat = EmailValidityFormat.DefaultFormat
    )

    sealed class Event {
      data class OnValidEmailClicked(val email: String) : Event()
      data class ShowToast(val message: String) : Event()
      object NavigateToEmailValidated : Event()
    }

    sealed class Effect {
      data class ShowToast(val message: String) : Effect()
      object NavigateToEmailValidated : Effect()
    }
}

