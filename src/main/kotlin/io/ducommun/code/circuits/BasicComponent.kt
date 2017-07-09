package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.ConnectionError.PluggableAlreadyConnected
import io.ducommun.code.circuits.errors.ConnectionError.ReceiverAlreadyConnected
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.circuits.errors.DisconnectionError.NotConnected
import io.ducommun.code.results.*

open class BasicComponent : Connectible {

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        if (output != null) return Failure(ReceiverAlreadyConnected)
        output = other
        return other.applyCurrent(current)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return output
                ?.let { it.removeCurrent().map { output = null } }
                ?: Failure(NotConnected as DisconnectionError)
    }

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
        if (current != null) return Failure(PluggableAlreadyConnected)
        current = appliedCurrent
        return output?.applyCurrent(appliedCurrent) ?: Success(Unit)
    }

    override fun removeCurrent(): Result<DisconnectionError, Unit> {
        return output
                ?.removeCurrent()
                ?.apply {
                    powerOff()
                    current = null
                }
                ?: Success(Unit)
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