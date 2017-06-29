package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Failure
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class Battery : SingleVoltageSource, VoltageSink {

    override fun power() {
        output?.powerOn()
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        if (output != null) return Failure(ConnectionError.ReceiverAlreadyConnected)
        other.applyCurrent(current)
        output = other
        return Success(Unit)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        if (output == null) return Failure(DisconnectionError.NotConnected)
        output?.removeCurrent()
        output = null
        return Success(Unit)
    }

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
        if (appliedCurrent != null && appliedCurrent == current) {
            appliedCurrent.complete = true
            this.appliedCurrent = appliedCurrent
        }
        return Success(Unit)
    }

    override fun removeCurrent() {
        appliedCurrent = null
        output?.powerOff()
        current.complete = false
    }

    override fun powerOn() {
    }

    override fun powerOff() {
    }

    var current: Current = SimpleCurrent(this)

    var appliedCurrent: Current? = null

    var output: Pluggable? = null

    override val powered: Boolean get() = current.complete
}