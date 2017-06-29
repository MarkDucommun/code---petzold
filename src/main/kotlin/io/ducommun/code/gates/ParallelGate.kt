package io.ducommun.code.gates

import io.ducommun.code.circuits.*
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.junctions.Join
import io.ducommun.code.junctions.SimpleJoin
import io.ducommun.code.results.Result

abstract class ParallelGate(switchesInitiallyClosed: Boolean) : Join {

    private val powerTwo: SingleVoltageSource = Power()
    private val powerOne: SingleVoltageSource = Power()

    private val switchOne: MutableSwitch = SimpleSwitch(closedInitially = switchesInitiallyClosed)
    private val switchTwo: MutableSwitch = SimpleSwitch(closedInitially = switchesInitiallyClosed)

    override val connectionOne: Pluggable = BetterSwitchToggler(switch = switchOne, pluggedIn = Ground())
    override val connectionTwo: Pluggable = BetterSwitchToggler(switch = switchTwo, pluggedIn = Ground())

    private val join = SimpleJoin()

    init {
        powerOne.connect(switchOne)
        switchOne.connect(join.connectionOne)

        powerTwo.connect(switchTwo)
        switchTwo.connect(join.connectionTwo)
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return join.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return join.disconnect()
    }

    override val powered: Boolean
        get() = join.powered
}