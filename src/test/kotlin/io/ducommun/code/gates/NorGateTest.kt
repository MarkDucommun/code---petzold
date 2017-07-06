package io.ducommun.code.gates

import io.ducommun.code.circuits.BulbTest
import io.ducommun.code.circuits.Ground
import io.ducommun.code.circuits.Power
import io.ducommun.code.circuits.SimpleSwitch
import io.ducommun.code.results.flatMap
import io.ducommun.code.succeeded
import org.junit.Before
import org.junit.Test

class NorGateTest : BulbTest() {

    val powerOne = Power()
    val powerTwo = Power()

    val switchOne = SimpleSwitch(closedInitially = false)
    val switchTwo = SimpleSwitch(closedInitially = false)

    val subject = NorGate()

    val ground = Ground()

    @Before
    fun setUp() {

        powerOne.connect(switchOne)
                .flatMap { powerTwo.connect(switchTwo) }
                .flatMap { switchOne.connect(subject.connectionOne) }
                .flatMap { switchTwo.connect(subject.connectionTwo) }
                .flatMap { subject.connect(bulb) }
                .flatMap { bulb.connect(ground) }
                .succeeded()
    }

    @Test
    fun `the NOR gate is on when both inputs are off`() {

        bulbIsOn()
    }

    @Test
    fun `the NOR gate is off when input one is on`() {

        switchOne.toggle().succeeded()

        bulbIsOff()
    }

    @Test
    fun `the NOR gate is off when input two is on`() {

        switchTwo.toggle().succeeded()

        bulbIsOff()
    }

    @Test
    fun `the NOR gate is off when both inputs are on`() {

        switchOne
                .toggle()
                .flatMap { switchTwo.toggle() }
                .succeeded()


        bulbIsOff()
    }

    @Test
    fun `the NOR gate can be turned off and on with switch one`() {

        switchOne.toggle().succeeded()

        bulbIsOff()

        switchOne.toggle().succeeded()

        bulbIsOn()

        switchOne.toggle().succeeded()

        bulbIsOff()

        switchOne.toggle().succeeded()

        bulbIsOn()
    }

    @Test
    fun `the NOR gate can be turned off and on with switch two`() {

        switchTwo.toggle().succeeded()

        bulbIsOff()

        switchTwo.toggle().succeeded()

        bulbIsOn()

        switchTwo.toggle().succeeded()

        bulbIsOff()

        switchTwo.toggle().succeeded()

        bulbIsOn()
    }

    @Test
    fun `the NOR gate can be turned off and on with both switches`() {

        switchOne.toggle().succeeded()
        switchTwo.toggle().succeeded()

        bulbIsOff()

        switchOne.toggle().succeeded()
        switchTwo.toggle().succeeded()

        bulbIsOn()

        switchOne.toggle().succeeded()
        switchTwo.toggle().succeeded()

        bulbIsOff()

        switchOne.toggle().succeeded()
        switchTwo.toggle().succeeded()

        bulbIsOn()
    }

}