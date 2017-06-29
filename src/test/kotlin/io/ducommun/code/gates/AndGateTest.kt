package io.ducommun.code.gates

import io.ducommun.code.circuits.Bulb
import io.ducommun.code.circuits.Ground
import io.ducommun.code.circuits.Power
import io.ducommun.code.circuits.SimpleSwitch
import io.ducommun.code.gates.AndGate
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class AndGateTest {

    @Test
    fun `the AND gate works`() {

        val powerOne = Power()
        val powerTwo = Power()

        val switchOne = SimpleSwitch(closedInitially = false)
        val switchTwo = SimpleSwitch(closedInitially = false)

        val andGate = AndGate()

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