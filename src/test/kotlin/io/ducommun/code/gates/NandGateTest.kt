package io.ducommun.code.gates

import io.ducommun.code.circuits.Bulb
import io.ducommun.code.circuits.Ground
import io.ducommun.code.circuits.Power
import io.ducommun.code.circuits.SimpleSwitch
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import org.junit.Test

class NandGateTest {

    val powerOne = Power()
    val powerTwo = Power()

    val switchOne = SimpleSwitch(closedInitially = false)
    val switchTwo = SimpleSwitch(closedInitially = false)

    val orGate = NandGate()

    val bulb = Bulb()

    val ground = Ground()

    @Before
    fun setUp() {
        powerOne.connect(switchOne)
        switchOne.connect(orGate.connectionOne)

        powerTwo.connect(switchTwo)
        switchTwo.connect(orGate.connectionTwo)

        orGate.connect(bulb)
        bulb.connect(ground)
    }

    @Test
    fun `one side powered on still powers the output`() {

        assertThat(bulb.powered).isTrue()

        switchOne.toggle()

        assertThat(bulb.powered).isTrue()
    }

    @Test
    fun `the other side still powered on powers the output`() {

        assertThat(bulb.powered).isTrue()

        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()
    }

    @Test
    fun `both sides powered on powers off the output`() {

        assertThat(bulb.powered).isTrue()

        switchOne.toggle()
        switchTwo.toggle()

        assertThat(bulb.powered).isFalse()

        switchTwo.toggle()
        switchOne.toggle()

        assertThat(bulb.powered).isTrue()
    }
}