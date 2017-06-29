package io.ducommun.code.circuits.errors

import io.ducommun.code.results.WithMessage

sealed class ConnectionError : WithMessage {

    object AlreadyConnected : ConnectionError() { override val message: String = "Already connected to another" }
}