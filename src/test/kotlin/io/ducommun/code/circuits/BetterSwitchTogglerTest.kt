package io.ducommun.code.circuits

import org.junit.Test

class BetterSwitchTogglerTest : BulbTest() {

    val groundOne = Ground()
    val groundTwo = Ground()

    val circuitTwoSwitch = PermanentSwitch(closedInitially = false, pluggedIn = bulb)

    val circuitOneToggler = BetterSwitchToggler(switch = circuitTwoSwitch, pluggedIn = groundOne)

    val circuitOneSwitch = PermanentSwitch(closedInitially = false, pluggedIn = circuitOneToggler)

    val powerOne = Power()
    val powerTwo = Power()

    init {
        powerOne.connect(circuitOneSwitch)
        powerTwo.connect(circuitTwoSwitch)
        bulb.connect(groundTwo)
    }

    @Test
    fun `it is a toggler`() {

        bulbIsOff()

        circuitTwoSwitch.toggle()

        bulbIsOn()

        circuitTwoSwitch.toggle()

        bulbIsOff()

        circuitOneSwitch.toggle()

        bulbIsOn()
    }
}