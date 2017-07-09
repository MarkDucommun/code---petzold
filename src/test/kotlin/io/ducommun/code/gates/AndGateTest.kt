package io.ducommun.code.gates

import io.ducommun.code.circuits.*
import io.ducommun.code.results.flatMap
import io.ducommun.code.succeeded
import org.junit.Before
import org.junit.Test

class AndGateTest : BulbTest() {

    val powerOne = Power()
    val powerTwo = Power()

    val switchOne = SimpleSwitch(closedInitially = false)
    val switchTwo = SimpleSwitch(closedInitially = false)

    val andGate = AndGate()

    val ground = Ground()

    @Before
    fun setUp() {
        powerOne
                .connect(switchOne)
                .flatMap { powerTwo.connect(switchTwo) }
                .flatMap { switchOne.connect(andGate.connectionOne) }
                .flatMap { switchTwo.connect(andGate.connectionTwo) }
                .flatMap { andGate.connect(bulb) }
                .flatMap { bulb.connect(ground) }
                .succeeded()
    }

    @Test
    fun `the bulb is off when neither input is powered`() {

        bulbIsOff()
    }

    @Test
    fun `the bulb is off when input one is powered`() {

        switchOne.toggle().succeeded()

        bulbIsOff()
    }

    @Test
    fun `the bulb is off when input two is powered`() {

        switchTwo.toggle().succeeded()

        bulbIsOff()
    }

    @Test
    fun `the bulb is powered on when both inputs are powered`() {

        switchOne.toggle().succeeded()
        switchTwo.toggle().succeeded()

        bulbIsOn()
    }

    @Test
    fun `the AND gate works`() {

        bulbIsOff()

        switchOne.toggle()

        bulbIsOff()

        switchTwo.toggle()

        bulbIsOn()

        switchOne.toggle()

        bulbIsOff()

        switchTwo.toggle()

        bulbIsOff()
    }
}