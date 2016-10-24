package com.example.akarpenko.basekotlin.utils

import android.util.Log
import com.example.akarpenko.basekotlin.BuildConfig

object Logger {

    fun e(_throwable: Throwable) {
        if (BuildConfig.LOGGING_ENABLED) {
            Log.e("logger", _throwable.javaClass.name + ", cause - " + _throwable.cause + ", message - " + _throwable.message)
        }
    }

    fun d(_message: String) {
        if (BuildConfig.LOGGING_ENABLED) {
            Log.d("logger", _message)
        }
    }

    private fun getSimpleClassName(name: String): String {
        val lastIndex = name.lastIndexOf(".")
        return name.substring(lastIndex + 1)
    }

    private fun getStackOffset(trace: Array<StackTraceElement>): Int {
        for (i in 2..trace.size - 1 - 1) {
            val e = trace[i]
            val name = e.className
            if (name != Logger::class.java.name) {
                return i
            }
        }
        return -1
    }

    fun logStackTrace() {
        if (BuildConfig.LOGGING_ENABLED) {

            val trace = Thread.currentThread().stackTrace

            val index = getStackOffset(trace)

            val className = getSimpleClassName(trace[index].className)
            val methodName = trace[index].methodName
            val threadName = Thread.currentThread().name
            val line = "(" + trace[index].fileName + ":" + trace[index].lineNumber + ")"

            val builder = StringBuilder()
            builder.append(boundString(className, 30))
            builder.append(boundString(methodName, 40))
            builder.append(boundString(threadName, 40))
            builder.append(boundString(line, 20))

            Log.d("ThreadLogger", builder.toString())
        }

    }

    private fun boundString(message: String, bound: Int): String {
        val builder = StringBuilder()
        builder.append(message)

        var length = message.length
        while (length < bound) {
            builder.append(" ")
            length++
        }

        return builder.toString()
    }

}
