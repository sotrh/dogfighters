package com.sotrh.dogfighters.engine.display

import org.lwjgl.glfw.GLFW

class Input(display: Display) {

    private val keyListenerList = mutableListOf<KeyListener>()
    private val mouseListenerList = mutableListOf<MouseListener>()

    init {
        // key events
        GLFW.glfwSetKeyCallback(display.window) { _, key, scancode, action, mods ->
            keyListenerList.forEach { it.onKeyEvent(key, scancode, action, mods) }
        }

        // mouse events
        GLFW.glfwSetCursorEnterCallback(display.window) { _, entered ->
            mouseListenerList.forEach { it.onMouseEnter(entered) }
        }
        GLFW.glfwSetCursorPosCallback(display.window) { _, xpos, ypos ->
            mouseListenerList.forEach { it.onMouseMove(xpos, ypos) }
        }
        GLFW.glfwSetMouseButtonCallback(display.window) { _, button, action, mods ->
            mouseListenerList.forEach { it.onMouseButton(button, action, mods) }
        }
        GLFW.glfwSetScrollCallback(display.window) { _, xoffset, yoffset ->
            mouseListenerList.forEach { it.onMouseScroll(xoffset, yoffset) }
        }
    }

    fun addKeyListener(keyListener: KeyListener) {
        keyListenerList.add(keyListener)
    }

    fun addMouseListener(mouseListener: MouseListener) {
        mouseListenerList.add(mouseListener)
    }

    fun cleanup() {
        keyListenerList.clear()
        mouseListenerList.clear()
    }

    interface KeyListener {
        fun onKeyEvent(key: Int, scancode: Int, action: Int, mods: Int)
    }

    interface MouseListener {
        fun onMouseEnter(entered: Boolean)
        fun onMouseMove(xpos: Double, ypos: Double)
        fun onMouseButton(button: Int, action: Int, mods: Int)
        fun onMouseScroll(xoffset: Double, yoffset: Double)
    }
}