package ox.softeng.document.core.dsl

import groovy.transform.TypeChecked

/**
 * @since 07/06/2018
 */
@TypeChecked
interface Api {

    void callClosure(Closure closure, def delegate)

    void callClosure(Closure closure, def delegate, int resolveStrategy)
}
