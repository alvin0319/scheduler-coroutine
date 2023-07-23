package dev.minjae.pnx.coroutine.plugin

import cn.nukkit.plugin.PluginBase
import dev.minjae.pnx.coroutine.Scheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExampleCoroutinePlugin : PluginBase() {

    override fun onEnable() {
        CoroutineScope(Dispatchers.Scheduler).launch {
            logger.info("Hello World from Scheduler Coroutine!")
            logger.info("Current thread is ${Thread.currentThread().name}, isPrimaryThread: ${server.isPrimaryThread}")
            while (true) {
                delay(1000L)
                logger.info("Current thread is ${Thread.currentThread().name}, isPrimaryThread: ${server.isPrimaryThread}")
            }
        }
    }
}
