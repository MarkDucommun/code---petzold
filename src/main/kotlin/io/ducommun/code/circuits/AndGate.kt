package io.ducommun.code.circuits

import io.ducommun.code.results.Result

interface AndGate {

    val connectionOne: Connectible

    val connectionTwo: Connectible

    fun connect(other: Pluggable): Result<ConnectionError, Unit>

    fun disconnect(): Result<DisconnectionError, Unit>

    val powered: Boolean
}