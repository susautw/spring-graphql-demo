package com.susautw.net.springdemo.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.dataloader.BatchLoaderEnvironment
import org.springframework.core.CoroutinesUtils
import org.springframework.graphql.execution.BatchLoaderRegistry
import kotlin.coroutines.EmptyCoroutineContext

fun <K, V> BatchLoaderRegistry.RegistrationSpec<K, V>.registerSuspendMappedBatchLoader(
    loader: suspend (
        Set<K>, BatchLoaderEnvironment
    ) -> Map<K, V>
) {
    return this.registerMappedBatchLoader { keys, env ->
        CoroutinesUtils.deferredToMono(CoroutineScope(EmptyCoroutineContext).async {
            loader(keys, env)
        })
    }
}