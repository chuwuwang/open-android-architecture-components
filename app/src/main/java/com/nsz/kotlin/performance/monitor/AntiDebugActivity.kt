package com.nsz.kotlin.performance.monitor

import com.nsz.kotlin.ViewBindingActivity
import com.nsz.kotlin.databinding.ActivityPerformanceDebugBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AntiDebugActivity : ViewBindingActivity<ActivityPerformanceDebugBinding>() {

    override fun init() {
        CoroutineScope(Dispatchers.Main).launch {
            val debugInfo = checkDebugInfo()
            binding.textDebugInfo.text = debugInfo
        }
    }

    private suspend fun checkDebugInfo(): String {
        val debugInfoBuilder = StringBuilder()

        val debuggerConnected = AntiDebugManager.isDebuggerConnected()
        val waitingForDebugger = AntiDebugManager.isWaitingForDebugger()

        // Get trackerId
        val tracerPid = AntiDebugManager.getTracerPid()
        // Get debug status from /proc/self/stat
        val debugStatus = AntiDebugManager.getProcStatStatus()
        // Get the wchan trace identifier
        val wchanStatus = AntiDebugManager.getWchanStatus()
        // ro.debuggable
        val isDeviceDebuggable = AntiDebugManager.isDeviceDebuggable()

        // Use the coroutine IO thread when detecting JDWP ports
        val jdwpDetected = withContext(Dispatchers.IO) {
            AntiDebugManager.detectJDWP()
        }

        debugInfoBuilder.append("Debugging Information:\n")
        debugInfoBuilder.append("Debugger Connected: ").append(debuggerConnected).append("\n")
        debugInfoBuilder.append("Waiting for Debugger: ").append(waitingForDebugger).append("\n")
        debugInfoBuilder.append("JDWP Port (Debugger Attached): ").append(jdwpDetected).append("\n")
        debugInfoBuilder.append("TracerPid: ").append(tracerPid).append("\n")
        debugInfoBuilder.append("Status: ").append(debugStatus).append("\n")
        debugInfoBuilder.append("Wchan Status: ").append(wchanStatus).append("\n")
        debugInfoBuilder.append("ro.debuggable: ").append(isDeviceDebuggable).append("\n")

        if ( debuggerConnected || waitingForDebugger || tracerPid != 0 || jdwpDetected || debugStatus == "Stop (may be in debugging state)" || wchanStatus.contains("trace") ) {
            debugInfoBuilder.append("\nApp is being debugged!\n")
        } else {
            debugInfoBuilder.append("\nApp is not being debugged.\n")
        }

        return debugInfoBuilder.toString()
    }

}