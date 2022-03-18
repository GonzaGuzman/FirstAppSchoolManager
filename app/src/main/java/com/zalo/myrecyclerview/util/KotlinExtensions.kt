package com.zalo.myrecyclerview.util

import android.content.Context
import android.widget.Toast
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

fun Completable.subscribeAndLogErrors(block: () -> Unit): Disposable {
    return this.subscribe(
        { block() },
        { println(it.message) }
    )
}

fun <T> Single<T>.subscribeAndLogErrors(block: (T) -> Unit): Disposable {
    return this.subscribe(
        { block(it) },
        { println(it.message) }
    )
}

fun String.showMessage(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}