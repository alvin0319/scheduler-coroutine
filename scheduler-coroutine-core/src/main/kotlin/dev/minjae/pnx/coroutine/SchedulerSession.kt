package dev.minjae.pnx.coroutine

import cn.nukkit.plugin.Plugin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

internal class SchedulerSession(
    val plugin: Plugin,
) {
    val dispatcher: CoroutineDispatcher = SchedulerDispatcher(this)

    val supervisorJob = SupervisorJob()

    var isValid = true
        private set

    fun cancel() {
        if (!isValid) return

        isValid = false
        supervisorJob.cancel()
    }
}

internal class SchedulerDispatcher(
    private val session: SchedulerSession,
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!session.isValid) {
            return
        }
        val plugin = session.plugin
        val server = plugin.server
        if (server.isPrimaryThread) {
            block.run()
        } else {
            session.plugin.server.scheduler.scheduleTask(plugin, block)
        }
    }
}
