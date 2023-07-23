package dev.minjae.pnx.coroutine

import cn.nukkit.Server
import cn.nukkit.plugin.Plugin
import cn.nukkit.plugin.PluginClassLoader

internal object Downstream {
    private val classLoaderFields
        get() = PluginClassLoader::class.java.declaredFields.filter {
            ClassLoader::class.java.isAssignableFrom(it.type)
        }.onEach { field -> field.isAccessible = true }

    private val ClassLoader.internalLoaders: List<ClassLoader>
        get() = classLoaderFields.map { it.get(this) }.filterIsInstance<ClassLoader>()

    fun pullPlugin(): Plugin {
        val classLoader = Downstream::class.java.classLoader

        return Server.getInstance().pluginManager.plugins.values.find { it ->
            val pluginClassLoader = it.javaClass.classLoader

            pluginClassLoader == classLoader || pluginClassLoader.internalLoaders.any { it == classLoader }
        } ?: error("Coroutines should be called from ${PluginClassLoader::class.java.simpleName}")
    }
}
