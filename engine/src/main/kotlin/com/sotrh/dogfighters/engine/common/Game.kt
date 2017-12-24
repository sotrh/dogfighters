package com.sotrh.dogfighters.engine.common

import com.sotrh.dogfighters.engine.display.Display
import com.sotrh.dogfighters.engine.display.Input

abstract class Game(defaultSettings: Settings) {
    val display = Display(defaultSettings.window)
    val input = Input(display)

    fun run() {
        setup()
        while (!display.shouldClose) {
            display.pollEvents()
            loop()
            display.swapBuffers()
        }
        cleanup()
    }

    fun close() {
        display.close()
    }

    abstract protected fun setup()

    abstract protected fun loop()

    open protected fun cleanup() {
        display.cleanup()
        input.cleanup()
    }
}