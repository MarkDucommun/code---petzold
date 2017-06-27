package io.ducommun.code.circuits

import io.ducommun.code.results.WithMessage

sealed class DisconnectionError : WithMessage {

    object NotConnected : DisconnectionError() { override val message: String = "Not connected to anything" }
}