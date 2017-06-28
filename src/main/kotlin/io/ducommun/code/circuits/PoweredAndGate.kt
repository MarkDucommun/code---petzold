package io.ducommun.code.circuits

import io.ducommun.code.results.Result

class PoweredAndGate : AndGate {

    private val power: VoltageSource = Power()

    private val switchOne: MutableSwitch = SimpleSwitch(closedInitially = false)
    private val switchTwo: MutableSwitch = SimpleSwitch(closedInitially = false)

    override val connectionOne: Connectible = SimpleSwitchToggler(switchOne)
    override val connectionTwo: Connectible = SimpleSwitchToggler(switchTwo)

    private val groundOne: VoltageSink = Ground()
    private val groundTwo: VoltageSink = Ground()

    init {
        power.connect(switchOne)
        switchOne.connect(switchTwo)
        connectionOne.connect(groundOne)
        connectionTwo.connect(groundTwo)
    }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return switchTwo.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return switchTwo.disconnect()
    }

    override val powered: Boolean get() = switchTwo.powered
}