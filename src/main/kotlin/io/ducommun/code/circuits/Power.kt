package io.ducommun.code.circuits

import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class Power : VoltageSource {

    override fun power() {
        output?.powerOn()
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        output = other
        other.applyCurrent(current)
        return Success(Unit)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        output?.removeCurrent()
        output = null
        return Success(Unit)
    }

    private val current: Current = SimpleCurrent(source = this)

    private var output: Pluggable? = null
}