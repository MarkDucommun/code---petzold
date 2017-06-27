package io.ducommun.code.circuits

import io.ducommun.code.results.Result

interface Receiver {

    fun connect(other: Pluggable): Result<ConnectionError, Unit>

    fun disconnect(): Result<DisconnectionError, Unit>
}