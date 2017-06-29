package io.ducommun.code.circuits.errors

import io.ducommun.code.results.WithMessage

sealed class ConnectionError : WithMessage {

    object ReceiverAlreadyConnected : ConnectionError() { override val message: String = "Already connected to another" }

    object PluggableAlreadyConnected : ConnectionError() { override val message: String = "Already connected to another" }
}