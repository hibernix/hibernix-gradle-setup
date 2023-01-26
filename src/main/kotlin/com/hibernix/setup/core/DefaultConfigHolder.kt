package com.hibernix.setup.core

abstract class DefaultConfigHolder<T> {
    var default: T? = null

    fun T.asDefault() : T {
        default = this
        return this
    }

    abstract fun newInstance(): T
    abstract fun copyInstance(instance: T): T
}

inline fun <reified T> DefaultConfigHolder<T>.fromDefault(block: T.() -> Unit): T =
    requireNotNull(default?.let { copyInstance(it) }?.apply(block)) {
        "No default value set for ${T::class.simpleName}!"
    }

inline fun <reified T> DefaultConfigHolder<T>.default(configBlock: T.() -> Unit) {
    newInstance().apply(configBlock).asDefault()
}

