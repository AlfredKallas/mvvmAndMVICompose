package com.example.architectureapp.utils

sealed class EmailValidityFormat {
    object DefaultFormat: EmailValidityFormat()
    object EmptyFormat: EmailValidityFormat()
    object InvalidFormat: EmailValidityFormat()
    object ValidFormat: EmailValidityFormat()
}