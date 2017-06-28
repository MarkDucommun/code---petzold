package io.ducommun.code.circuits

import io.ducommun.code.results.Result

interface AndGate {

    fun connectInOne(other: Receiver): Result<ConnectionError, Unit>

    fun disconnectInOne(): Result<DisconnectionError, Unit>

    fun connectInTwo(other: Receiver): Result<ConnectionError, Unit>

    fun disconnectInTwo(): Result<DisconnectionError, Unit>

    fun connectOut(other: Pluggable): Result<ConnectionError, Unit>

    fun disconnectOut(): Result<DisconnectionError, Unit>

    val powered: Boolean
}

