package io.ducommun.code.circuits

import com.sun.org.apache.xpath.internal.operations.Bool
import io.ducommun.code.results.Failure
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

open class BasicComponent : Connectible {

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        if (output != null) return Failure(ConnectionError.AlreadyConnected)
        output = other
        other.applyCurrent(current)
        return Success(Unit)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        if (output == null) return Failure(DisconnectionError.NotConnected)
        removeCurrent()
        return Success(Unit)
    }

    override fun applyCurrent(appliedCurrent: Current?) {
        current = appliedCurrent
        output?.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent() {
        current = null
        output?.removeCurrent()
        powerOff()
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