package io.ducommun.code.gates

import io.ducommun.code.circuits.*
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.junctions.Join
import io.ducommun.code.results.Result
import io.ducommun.code.results.flatMap

abstract class SeriesGate(switchesInitiallyClosed: Boolean) : Join {

    private val power: SingleVoltageSource = Power()

    private val switchOne: MutableSwitch = SimpleSwitch(closedInitially = switchesInitiallyClosed)
    private val switchTwo: MutableSwitch = SimpleSwitch(closedInitially = switchesInitiallyClosed)

    override val connectionOne: Pluggable = BetterSwitchToggler(switch = switchOne, pluggedIn = Ground())
    override val connectionTwo: Pluggable = BetterSwitchToggler(switch = switchTwo, pluggedIn = Ground())

    init {
        power.connect(switchOne).flatMap { switchOne.connect(switchTwo) }
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return switchTwo.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return switchTwo.disconnect()
    }

    override val powered: Boolean get() = switchTwo.powered
}