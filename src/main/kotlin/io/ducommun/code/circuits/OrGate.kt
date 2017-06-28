package io.ducommun.code.circuits

import io.ducommun.code.results.Result

class OrGate : Join {

    private val powerOne: SingleVoltageSource = Power()
    private val powerTwo: SingleVoltageSource = Power()

    private val switchOne: MutableSwitch = SimpleSwitch(closedInitially = false)
    private val switchTwo: MutableSwitch = SimpleSwitch(closedInitially = false)

    override val connectionOne: Pluggable = BetterSwitchToggler(switch = switchOne, pluggedIn = Ground())
    override val connectionTwo: Pluggable = BetterSwitchToggler(switch = switchTwo, pluggedIn = Ground())

    private val joiner = Joiner()

    init {
        powerOne.connect(switchOne)
        switchOne.connect(joiner.connectionOne)

        powerTwo.connect(switchTwo)
        switchTwo.connect(joiner.connectionTwo)
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return joiner.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return joiner.disconnect()
    }

    override val powered: Boolean
        get() = joiner.powered

}