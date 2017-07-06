package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class Power : SingleVoltageSource {

    override fun power() {
        output?.powerOn()
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return other.applyCurrent(current).apply { output = other }
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        output?.removeCurrent()
        output = null
        return Success(Unit)
    }

    private val current: Current = SimpleCurrent(source = this)

    private var output: Pluggable? = null
}