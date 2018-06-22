package ox.softeng.document.core.dsl

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 07/06/2018
 */
trait Api {

    abstract DocumentBuilder getBuilder()

    void callClosure(Closure closure, def delegate, int resolveStrategy = Closure.DELEGATE_FIRST) {
        if (closure) {
            closure.resolveStrategy = resolveStrategy
            closure.delegate = delegate
            closure.call()
        }
    }
}
