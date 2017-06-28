package io.ducommun.code.circuits

import io.ducommun.code.failsWithReason
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class PoweredAndGateTest {


    @Test
    fun `the AND gate works`() {

        val powerOne = Power()
        val powerTwo = Power()

        val switchOne = SimpleSwitch(closedInitially = false)
        val switchTwo = SimpleSwitch(closedInitially = false)

        val andGate = PoweredAndGate()

        val bulb = Bulb()

        val ground = Ground()

        powerOne.connect(switchOne)
        powerTwo.connect(switchTwo)

        andGate.connectInOne(switchOne)
        andGate.connectInTwo(switchTwo)

        andGate.connectOut(bulb)

        bulb.connect(ground)

        assertThat(bulb.powered).isFalse()

        switchOne.toggle()

        assertThat(bulb.powered).isFalse()

        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()

        switchOne.toggle()

        assertThat(bulb.powered).isFalse()
    }

    @Test
    fun `cannot plug something into an IN already occupied`() {

        val andGate = PoweredAndGate()

        andGate.connectInOne(Bulb())
        andGate.connectInOne(Bulb()) failsWithReason ConnectionError.AlreadyConnected

        andGate.connectInTwo(Bulb())
        andGate.connectInTwo(Bulb()) failsWithReason ConnectionError.AlreadyConnected
    }

    @Test
    fun `cannot unplug something from an unoccupied socket`() {

        val andGate = PoweredAndGate()

        andGate.disconnectInOne() failsWithReason DisconnectionError.NotConnected

        andGate.disconnectInTwo() failsWithReason DisconnectionError.NotConnected
    }
}