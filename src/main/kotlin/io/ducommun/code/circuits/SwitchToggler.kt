package io.ducommun.code.circuits

interface SwitchToggler : Connectible {

    var switch: MutableSwitch
}

interface ImmutableSwitchToggler : Pluggable {

    val pluggedIn: Pluggable
}