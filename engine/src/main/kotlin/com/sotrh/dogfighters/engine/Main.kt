package com.sotrh.dogfighters.engine

import com.sotrh.dogfighters.engine.common.Game
import com.sotrh.dogfighters.engine.common.Settings
import com.sotrh.dogfighters.engine.display.Input
import com.sotrh.dogfighters.engine.io.IO
import org.lwjgl.glfw.GLFW

fun main(args: Array<String>) {
    val defaultSettings = IO.internal.loadFileAsObject("defaultSettings.json", Settings::class.java)
    object : Game(defaultSettings) {
        override fun setup() {
            input.addKeyListener(object : Input.KeyListener {
                override fun onKeyEvent(key: Int, scancode: Int, action: Int, mods: Int) {
                    if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) close()
                }
            })
        }

        override fun loop() {

        }
    }.run()
}