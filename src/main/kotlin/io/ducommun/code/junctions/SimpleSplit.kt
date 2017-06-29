package io.ducommun.code.junctions

import io.ducommun.code.circuits.*
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class SimpleSplit : Split {

    private val powerOne = object : VoltageSource {
        override fun power() {
            outputOne?.powerOn()
            incomingCurrent?.source?.power()
            incomingCurrent?.complete = true
        }
    }
    private val powerTwo = object : VoltageSource {
        override fun power() {
            outputTwo?.powerOn()
            incomingCurrent?.source?.power()
            incomingCurrent?.complete = true
        }
    }

    private var incomingCurrent: Current? = null
    private var outgoingCurrentOne: Current = SimpleCurrent(powerOne)
    private var outgoingCurrentTwo: Current = SimpleCurrent(powerTwo)

    private var outputOne: Pluggable? = null
    private var outputTwo: Pluggable? = null

    override fun applyCurrent(appliedCurrent: Current?) : Result<ConnectionError, Unit> {
        incomingCurrent = appliedCurrent
        if (incomingCurrent != null) {
            outputOne?.applyCurrent(outgoingCurrentOne)
            outputTwo?.applyCurrent(outgoingCurrentTwo)
        }
        return Success(Unit)
    }

    override fun removeCurrent() {
        incomingCurrent = null
        outputOne?.removeCurrent()
        outputTwo?.removeCurrent()
        powerOff()
    }

    override fun powerOn() {}

    override fun powerOff() {
        outputOne?.powerOff()
        outputTwo?.powerOff()
    }

    override fun connectOne(other: Pluggable): Result<ConnectionError, Unit> {
        outputOne = other
        if (incomingCurrent != null) {
            other.applyCurrent(outgoingCurrentOne)
        }
        return Success(Unit)
    }

    override fun connectTwo(other: Pluggable): Result<ConnectionError, Unit> {
        outputTwo = other
        if (incomingCurrent != null) {
            other.applyCurrent(outgoingCurrentTwo)
        }
        return Success(Unit)
    }

    override fun disconnectOne(): Result<DisconnectionError, Unit> {
        outputOne?.removeCurrent()
        outputOne = null
        return Success(Unit)
    }

    override fun disconnectTwo(): Result<DisconnectionError, Unit> {
        outputTwo?.removeCurrent()
        outputTwo = null
        return Success(Unit)
    }

    override val powered: Boolean
        get() = TODO("not implemented")
}