package dev.minjae.pnx.coroutine

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.plugin.PluginDisableEvent
import cn.nukkit.plugin.Plugin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

private object SchedulerCoroutine {
    private val plugin: Plugin = Downstream.pullPlugin()

    private var session: SchedulerSession? = null

    fun session(): SchedulerSession {
        if (session == null) {
            synchronized(this) {
                if (session == null) {
                    require(plugin.isEnabled) { "Plugin is not enabled" }

                    val server = plugin.server

                    session = SchedulerSession(plugin).also { activity ->
                        server.pluginManager.registerEvents(
                            object : Listener {
                                @EventHandler
                                fun onPluginDisable(event: PluginDisableEvent) {
                                    synchronized(this@SchedulerCoroutine) {
                                        session = null
                                        activity.cancel()
                                    }
                                }
                            },
                            plugin,
                        )
                    }
                }
            }
        }
        return session!!.validate()
    }
}

private fun SchedulerSession.validate(): SchedulerSession {
    requireNotNull(this) { "Failed to create SchedulerSession" }
    require(isValid) { "Invalid SchedulerSession" }
    return this
}

/**
 * Returns a [CoroutineDispatcher] that runs on Nukkit's main heartbeat.
 *
 * This follows the lifecycle of the plugin that loaded this library.
 */
val Dispatchers.Scheduler: CoroutineDispatcher
    get() = SchedulerCoroutine.session().dispatcher

/**
 * Creates a [CoroutineScope] that has [Dispatchers.Scheduler] as a default dispatcher.
 *
 * This follows the lifecycle of the plugin that loaded this library.
 */
@Suppress("FunctionName")
fun SchedulerScope(): CoroutineScope = SchedulerCoroutine.session().let {
    CoroutineScope(it.dispatcher + Job(it.supervisorJob))
}
