package io.ducommun.code.circuits

interface Switch {

    val closed: Boolean

    fun toggle(): Boolean
}

interface MutableSwitch : Connectible, Switch

interface ImmutableReceiverSwitch : Pluggable, Switch {

    val pluggedIn: Pluggable
}