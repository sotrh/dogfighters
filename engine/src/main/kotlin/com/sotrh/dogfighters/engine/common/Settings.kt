package com.sotrh.dogfighters.engine.common

data class Settings(
        val window: WindowSettings
)

data class WindowSettings(
        val width: Int = 800,
        val height: Int = 600,
        val title: String,
        val fullscreen: Boolean = false
)