package com.sotrh.dogfighters.engine.display

import com.sotrh.dogfighters.engine.common.WindowSettings
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL

class Display(private val initialWindowSettings: WindowSettings) {

    var width: Int = initialWindowSettings.width
        private set
    var height: Int = initialWindowSettings.height
        private set
    var window: Long = 0L
        private set
    var title: String = initialWindowSettings.title
        private set

    val shouldClose: Boolean get() {
        assert(window != 0L) { "Cannot check for closing on NULL window" }
        return GLFW.glfwWindowShouldClose(window)
    }

    init {
        if (window != 0L) cleanup()
        initGLFW()
        initCallbacks()
    }


    //region initialization
    private fun initGLFW() {
        assert(GLFW.glfwInit()) { "Unable to initialize GLFW" }
        GLFW.glfwSwapInterval(1)
        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE)

        window = GLFW.glfwCreateWindow(width, height, title, 0L, 0L)
        assert(window != 0L) { "Unable to create GLFW Window" }

        initCallbacks()

        makeContextCurrent()
        GL.createCapabilities()
    }

    private val frameBufferSize = BehaviorSubject.create<Display>()


    fun frameBufferSizeObservable(): Observable<Display> = frameBufferSize.hide()
    private fun initCallbacks() {
        GLFW.glfwSetFramebufferSizeCallback(window) { _, width, height ->
            this.width = width
            this.height = height
            frameBufferSize.onNext(this)
        }
    }
    //endregion


    fun cleanup() {
        assert(window != 0L) { "Cannot cleanup uninitialized Display" }
        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)
        window = 0L
    }


    fun makeContextCurrent() {
        assert(window != 0L) { "Cannot makeContextCurrent on uninitialized Display" }
        GLFW.glfwMakeContextCurrent(window)
    }

    fun swapBuffers() {
        assert(window != 0L) { "Cannot swapBuffers on unitialized Display" }
        GLFW.glfwSwapBuffers(window)
    }

    fun pollEvents() {
        assert(window != 0L) { "Cannot pollEvents on unitialized Display" }
        GLFW.glfwPollEvents()
    }

    fun close() {
        assert(window != 0L) { "Cannot close unitialized Display"}
        GLFW.glfwSetWindowShouldClose(window, true)
    }
}