package com.example.architectureapp.mvistateflow

import kotlinx.coroutines.flow.StateFlow

interface UnidirectionalViewModel<STATE, EVENT, EFFECT> {
    val state: StateFlow<STATE>
    val effect: StateFlow<EFFECT>
    fun event(event: EVENT)
    fun setEventEffectsConsumed()
}