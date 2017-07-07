package io.ducommun.code.junctions

import io.ducommun.code.circuits.*
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result
import io.ducommun.code.results.Success

class SimpleJoin : Join, SingleVoltageSource {

    private val outgoingCurrent = SimpleCurrent {
        incomingCurrentOne?.power?.invoke()
        incomingCurrentTwo?.power?.invoke()
        Success(Unit)
    }

    override val connectionOne: Connectible = BasicComponent()

    override val connectionTwo: Connectible = BasicComponent()

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {

        output = other

        connectionOne.connect(object : Pluggable {
            override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
                incomingCurrentOne = appliedCurrent
                if (appliedCurrent != null) {
                    output?.applyCurrent(outgoingCurrent)
                }
                return Success(Unit)
            }

            override fun removeCurrent(): Result<DisconnectionError, Unit> {
                incomingCurrentOne = null
                if (incomingCurrentTwo == null) {
                    output?.removeCurrent()
                    powerOff()
                }
                return Success(Unit)
            }

            override fun powerOn() {
                output?.powerOn()
            }

            override fun powerOff() {
                if (incomingCurrentTwo == null) {
                    output?.powerOff()
                }
            }

            override val powered: Boolean
                get() = incomingCurrentOne?.complete ?: false
        })

        connectionTwo.connect(object : Pluggable {
            override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
                incomingCurrentTwo = appliedCurrent
                if (appliedCurrent != null) {
                    output?.applyCurrent(outgoingCurrent)
                }
                return Success(Unit)
            }

            override fun removeCurrent(): Result<DisconnectionError, Unit> {
                incomingCurrentTwo = null
                if (incomingCurrentOne == null) {
                    output?.removeCurrent()
                    powerOff()
                }
                return Success(Unit)
            }

            override fun powerOn() {
                output?.powerOn()
            }

            override fun powerOff() {
                if (incomingCurrentOne == null) {
                    output?.powerOff()
                }
            }

            override val powered: Boolean
                get() = incomingCurrentTwo?.complete ?: false
        })

        return Success(Unit)
    }

    private var incomingCurrentOne: Current? = null

    private var incomingCurrentTwo: Current? = null

    private var output: Pluggable? = null

    override fun disconnect(): Result<DisconnectionError, Unit> {
        connectionOne.disconnect()
        connectionTwo.disconnect()
        return Success(Unit)
    }

    override val powered: Boolean
        get() = output?.powered ?: false
}