package com.ilsangtech.ilsang.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

@Stable
class ResultStore {
    val results = mutableMapOf<String, Any?>()

    inline fun <reified T> getResult(resultKey: String = T::class.toString()): T? {
        return results[resultKey] as T?
    }

    inline fun <reified T> setResult(resultKey: String = T::class.toString(), result: T) {
        results[resultKey] = result
    }

    inline fun <reified T> removeResult(resultKey: String = T::class.toString()) {
        results.remove(resultKey)
    }

    companion object {
        val saver = Saver<ResultStore, Map<String, Any?>>(
            save = { it.results.toMap() },
            restore = { ResultStore().apply { results.putAll(it) } }
        )
    }
}

@Composable
fun rememberResultStore(): ResultStore {
    return rememberSaveable(saver = ResultStore.saver) { ResultStore() }
}