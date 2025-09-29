package com.nsz.kotlin.performance.monitor

import android.os.Debug
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.net.InetSocketAddress
import java.net.Socket

object AntiDebugManager {

    fun isDebuggerConnected(): Boolean {
        return Debug.isDebuggerConnected()
    }

    fun isWaitingForDebugger(): Boolean {
        return Debug.waitingForDebugger()
    }

    /**
     * Returns the value of TracerPid
     */
    fun getTracerPid(): Int {
        try {
            val reader = FileReader("/proc/self/status")
            BufferedReader(reader).use { reader ->
                var line: String ?
                while (reader.readLine().also { line = it } != null) {
                    val bool = line != null && line.startsWith("TracerPid:")
                    if (bool) {
                        val strings = line.split(":")
                        return strings[1].trim().toInt()
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        // If TracerPid is not found, return -1 to indicate it is not being debugged.
        return -1
    }

    /**
     * Detection debug port (JDWP), running in a background thread
     */
    fun detectJDWP(): Boolean {
        try {
            Socket().use { socket ->
                val endpoint = InetSocketAddress("127.0.0.1", 8700)
                socket.connect(endpoint, 1000)
            }
            return true
        } catch (e: Throwable) {
            e.printStackTrace()
            // No debugger connected
        }
        return false
    }

    /**
     * Get the wait state of the process from /proc/self/wchan
     */
    fun getWchanStatus(): String {
        try {
            val file = File("/proc/self/wchan")
            if (file.exists()) {
                return file.readText().trim()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return "Unable to get wchan status"
    }

    /**
     * Parse /proc/self/stat to get process status
     */
    fun getProcStatStatus(): String {
        try {
            // Read the /proc/self/stat file
            val statFile = File("/proc/self/stat")
            val statContent = statFile.readText()
            // The content format of /proc/self/stat is space-separated fields, the third field is the process status
            val statFields = statContent.split(" ")
            if (statFields.size > 2) {
                val processState = statFields[2] // Process status field
                return when (processState) {
                    "R" -> "Running"
                    "S" -> "Sleeping"
                    "D" -> "Uninterrupted sleep"
                    "T" -> "Stop (may be in debugging state)"
                    "Z" -> "Zombie processes"
                    else -> "Unknown status: $processState"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "Unable to get debug status"
    }

    fun isDeviceDebuggable(): Boolean {
        try {
            // Execute the getprop command to get the ro.debuggable property
            val process = Runtime.getRuntime().exec("getprop ro.debuggable")
            val reader = process.inputStream.bufferedReader()
            val result = reader.readLine()
            // Check if the result is "1"
            return result == "1"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

}