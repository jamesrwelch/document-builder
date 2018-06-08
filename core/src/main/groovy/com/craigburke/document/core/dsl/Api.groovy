package com.craigburke.document.core.dsl

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 07/06/2018
 */
trait Api {

    abstract DocumentBuilder getBuilder()

    void callClosure(Closure closure, def delegate) {
        if (closure) {
            closure.resolveStrategy = Closure.DELEGATE_FIRST
            closure.delegate = delegate
            closure.call()
        }
    }
}
