package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.ConnectionError.PluggableAlreadyConnected
import io.ducommun.code.circuits.errors.ConnectionError.ReceiverAlreadyConnected
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.circuits.errors.DisconnectionError.NotConnected
import io.ducommun.code.results.Failure
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

open class BasicComponent : Connectible {

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        if (output != null)
            return Failure(ReceiverAlreadyConnected)
        output = other
        other.applyCurrent(current)
        return Success(Unit)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        if (output == null) return Failure(NotConnected)
        output?.removeCurrent()
        output = null
        return Success(Unit)
    }

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
        if (current != null) return Failure(PluggableAlreadyConnected)
        current = appliedCurrent
        return output?.applyCurrent(appliedCurrent) ?: Success(Unit)
    }

    override fun removeCurrent(): Result<DisconnectionError, Unit> {
        current = null
        output?.removeCurrent()
        powerOff()
        return Success(Unit)
    }

    override fun powerOn() {
        output?.powerOn()
    }

    override fun powerOff() {
        output?.powerOff()
    }

    protected var current: Current? = null

    protected var output: Pluggable? = null

    override val powered: Boolean get() = current?.complete ?: false
}