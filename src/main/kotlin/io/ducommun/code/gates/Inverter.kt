package io.ducommun.code.gates

import io.ducommun.code.circuits.*
import io.ducommun.code.circuits.errors.ConnectionError
import io.ducommun.code.circuits.errors.DisconnectionError
import io.ducommun.code.results.Result

class Inverter : Connectible {

    private val power = Power()

    private val ground = Ground()

    private val switch = SimpleSwitch(closedInitially = true)

    private val electroMagnet = BetterSwitchToggler(switch = switch, pluggedIn = ground)

    init { power.connect(switch) }

    override fun connect(other: Pluggable): Result<ConnectionError, Unit> {
        return switch.connect(other)
    }

    override fun disconnect(): Result<DisconnectionError, Unit> {
        return switch.disconnect()
    }

    override fun applyCurrent(appliedCurrent: Current?) {
        electroMagnet.applyCurrent(appliedCurrent)
    }

    override fun removeCurrent() {
        electroMagnet.removeCurrent()
    }

    override fun powerOn() {
        electroMagnet.powerOn()
    }

    override fun powerOff() {
        electroMagnet.powerOff()
    }

    override val powered: Boolean
        get() = electroMagnet.powered
}