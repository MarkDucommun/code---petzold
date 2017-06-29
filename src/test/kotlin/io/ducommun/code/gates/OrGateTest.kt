package io.ducommun.code.gates

import io.ducommun.code.circuits.Bulb
import io.ducommun.code.circuits.Ground
import io.ducommun.code.circuits.Power
import io.ducommun.code.circuits.SimpleSwitch
import io.ducommun.code.gates.OrGate
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import org.junit.Test

class OrGateTest {

    val powerOne = Power()
    val powerTwo = Power()

    val switchOne = SimpleSwitch(closedInitially = false)
    val switchTwo = SimpleSwitch(closedInitially = false)
    val switchThree = SimpleSwitch(closedInitially = true)

    val orGate = OrGate()

    val bulbOne = Bulb()
    val bulbTwo = Bulb()
    val bulbThree = Bulb()

    val ground = Ground()

    @Before
    fun setUp() {
        powerOne.connect(switchOne)
        switchOne.connect(bulbOne)
        bulbOne.connect(orGate.connectionOne)

        powerTwo.connect(switchTwo)
        switchTwo.connect(bulbTwo)
        bulbTwo.connect(orGate.connectionTwo)

        orGate.connect(switchThree)
        switchThree.connect(bulbThree)
        bulbThree.connect(ground)
    }

    @Test
    fun `one side powered on powers the output`() {

        switchOne.toggle()

        assertThat(bulbThree.powered).isTrue()
    }

    @Test
    fun `the other side powered on powers the output`() {

        switchTwo.toggle()

        assertThat(bulbThree.powered).isTrue()
    }

    @Test
    fun `both sides powered on powers the output`() {

        switchOne.toggle()
        switchTwo.toggle()

        assertThat(bulbThree.powered).isTrue()

        switchTwo.toggle()

        assertThat(bulbThree.powered).isTrue()
    }
}