package com.example.architectureapp.mvistateflow

import com.example.architectureapp.mvistateflow.events.StateEvent
import com.example.architectureapp.mvistateflow.events.StateEventWithContent
import com.example.architectureapp.mvistateflow.events.consumed
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

    data class Effect (
        val showToast: StateEventWithContent<String> = consumed(),
        val navigateToEmailValidated: StateEvent = consumed
    )
}