package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result

interface ImmutableReceiver {

    fun destroy(): Result<DisconnectionError, Unit>
}