package io.ducommun.code.circuits

import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.*

class SimpleSwitch(closedInitially: Boolean = false) : MutableSwitch {

    private val incomingConnectible: Connectible = BasicComponent()

    private val outgoingConnectible: Connectible = BasicComponent()

    private var closedState: Boolean = closedInitially

    init {
        if (closedInitially) incomingConnectible.connect(outgoingConnectible)
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return outgoingConnectible.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return outgoingConnectible.disconnect()
    }

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
        return incomingConnectible.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent(): Result<DisconnectionError, Unit> {
        incomingConnectible.removeCurrent()
        return Success(Unit)
    }

    override fun powerOn() {
        incomingConnectible.powerOn()
    }

    override fun powerOff() {
        incomingConnectible.powerOff()
    }

    override fun toggle(): Result<ToggleError, Unit> {

        val result = if (closedState)
            incomingConnectible.disconnect()
        else
            incomingConnectible.connect(outgoingConnectible)

        return result
                .map { closedState = !closedState }
                .mapError {
                    when (it) {
                        is ConnectionError -> ToggleError.ConnectionError
                        is DisconnectionError -> ToggleError.DisconnectionError
                        else -> ToggleError.UnknownError
                    }
                }
    }

    override val closed: Boolean get() = closedState

    override val powered: Boolean get() = outgoingConnectible.powered
}

class BetterSimpleSwitch(closedInitially: Boolean = false) : MutableSwitch {

    private val incoming: Connectible = BasicComponent()

    private val outgoing: Connectible = BasicComponent()

    private var closedState: Boolean = closedInitially

    init {
        if (closedInitially) incoming.connect(outgoing)
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return outgoing.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return outgoing.disconnect()
    }

    override fun applyCurrent(appliedCurrent: Current?): Result<ConnectionError, Unit> {
        return incoming.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent(): Result<DisconnectionError, Unit> {
        return incoming.removeCurrent()
    }

    override fun powerOn() {
        incoming.powerOn()
    }

    override fun powerOff() {
        incoming.powerOff()
    }

    override fun toggle(): Result<ToggleError, Unit> {

        val result = if (closedState)
            incoming.disconnect()
        else
            incoming.connect(outgoing)

        return result
                .map { closedState = !closedState }
                .mapError {
                    when (it) {
                        is ConnectionError -> ToggleError.ConnectionError
                        is DisconnectionError -> ToggleError.DisconnectionError
                        else -> ToggleError.UnknownError
                    }
                }
    }

    override val closed: Boolean get() = closedState

    override val powered: Boolean get() = outgoing.powered
}