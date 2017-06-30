package io.ducommun.code.circuits

import io.ducommun.code.results.WithMessage

sealed class ToggleError : WithMessage {

    object ConnectionError : ToggleError() {
        override val message: String = "Connection failed"
    }

    object DisconnectionError : ToggleError() {
        override val message: String = "Disconnection failed"
    }

    object UnknownError : ToggleError() {
        override val message: String = "Unknown"
    }
}