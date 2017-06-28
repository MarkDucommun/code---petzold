package io.ducommun.code.circuits

import io.ducommun.code.results.Failure
import io.ducommun.code.results.Result

class PoweredAndGate : AndGate {

    private val power: VoltageSource = Power()

    private val switchOne: Switch = SimpleSwitch(closedInitially = false)
    private val switchTwo: Switch = SimpleSwitch(closedInitially = false)

    private val togglerOne: SwitchToggler = SimpleSwitchToggler(switchOne)
    private val togglerTwo: SwitchToggler = SimpleSwitchToggler(switchTwo)

    private val groundOne: VoltageSink = Ground()
    private val groundTwo: VoltageSink = Ground()

    private var firstReceiver: Receiver? = null
    private var secondReceiver: Receiver? = null

    init {
        power.connect(switchOne)
        switchOne.connect(switchTwo)
        togglerOne.connect(groundOne)
        togglerTwo.connect(groundTwo)
    }

    override fun connectInOne(other: Receiver): Result<ConnectionError, Unit> {
        if (firstReceiver != null) return Failure(ConnectionError.AlreadyConnected)
        firstReceiver = other
        return other.connect(togglerOne)
    }

    override fun disconnectInOne(): Result<DisconnectionError, Unit> {
        return firstReceiver?.disconnect() ?: Failure<DisconnectionError, Unit>(DisconnectionError.NotConnected)
    }

    override fun connectInTwo(other: Receiver): Result<ConnectionError, Unit> {
        if (secondReceiver != null) return Failure(ConnectionError.AlreadyConnected)
        secondReceiver = other
        return other.connect(togglerTwo)
    }

    override fun disconnectInTwo(): Result<DisconnectionError, Unit> {
        return secondReceiver?.disconnect() ?: Failure<DisconnectionError, Unit>(DisconnectionError.NotConnected)
    }

    override fun connectOut(other: Pluggable): Result<ConnectionError, Unit> {
        return switchTwo.connect(other)
    }

    override fun disconnectOut(): Result<DisconnectionError, Unit> {
        return switchTwo.disconnect()
    }

    override val powered: Boolean
        get() = switchTwo.powered

}