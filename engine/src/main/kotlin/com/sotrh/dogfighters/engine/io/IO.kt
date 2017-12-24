package com.sotrh.dogfighters.engine.io

import com.squareup.moshi.Moshi
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object IO {

    internal val moshi = Moshi.Builder().build()

    val internal = object : FileLoader {
        override fun loadFileAsString(filename: String): String {
            var output = ""
            val br = loadFileAsBufferedReader(filename)
            br.lines().forEach { output += it }
            return output
        }

        override fun loadFileAsBufferedReader(filename: String): BufferedReader {
            val inputStream = loadFileAsInputStream(filename)
            return BufferedReader(InputStreamReader(inputStream))
        }

        override fun loadFileAsInputStream(filename: String): InputStream {
            return this::class.java.classLoader.getResourceAsStream(filename)
        }

        override fun <T> loadFileAsObject(filename: String, clazz: Class<T>): T {
            val json = loadFileAsString(filename)
            return moshi.adapter(clazz).fromJson(json)!!
        }
    }

    interface FileLoader{
        fun loadFileAsString(filename: String): String
        fun loadFileAsBufferedReader(filename: String): BufferedReader
        fun loadFileAsInputStream(filename: String): InputStream
        fun <T> loadFileAsObject(filename: String, clazz: Class<T>): T
    }
}