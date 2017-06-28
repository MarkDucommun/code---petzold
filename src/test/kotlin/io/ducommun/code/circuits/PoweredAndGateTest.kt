package io.ducommun.code.circuits

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

        switchOne.connect(andGate.connectionOne)
        switchTwo.connect(andGate.connectionTwo)

        andGate.connect(bulb)

        bulb.connect(ground)

        assertThat(bulb.powered).isFalse()

        switchOne.toggle()

        assertThat(bulb.powered).isFalse()

        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()

        switchOne.toggle()

        assertThat(bulb.powered).isFalse()

        switchTwo.toggle()

        assertThat(bulb.powered).isFalse()
    }
}