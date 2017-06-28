package io.ducommun.code.circuits

import io.ducommun.code.results.Result

class AndGate : Join {

    private val power: SingleVoltageSource = Power()

    private val switchOne: MutableSwitch = SimpleSwitch(closedInitially = false)
    private val switchTwo: MutableSwitch = SimpleSwitch(closedInitially = false)

    override val connectionOne: Pluggable = BetterSwitchToggler(switch = switchOne, pluggedIn = Ground())
    override val connectionTwo: Pluggable = BetterSwitchToggler(switch = switchTwo, pluggedIn = Ground())

    init {
        power.connect(switchOne)
        switchOne.connect(switchTwo)
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return switchTwo.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return switchTwo.disconnect()
    }

    override val powered: Boolean get() = switchTwo.powered
}