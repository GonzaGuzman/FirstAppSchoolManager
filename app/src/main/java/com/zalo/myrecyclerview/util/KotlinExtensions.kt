package com.zalo.myrecyclerview.util

import android.widget.Toast
import com.zalo.myrecyclerview.GeneralActivity
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

fun String.showMessage(context: GeneralActivity) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}