package example.micronaut.txwithr2dbc.shared

import io.micronaut.core.propagation.ThreadPropagatedContextElement
import org.slf4j.MDC

data class MdcPropagationContext(
    val state: MutableMap<String, String> = MDC.getCopyOfContextMap()
) : ThreadPropagatedContextElement<MutableMap<String, String>> {

    override fun updateThreadContext(): MutableMap<String, String> {
        val oldState = MDC.getCopyOfContextMap()
        setCurrent(state)
        return oldState
    }

    override fun restoreThreadContext(oldState: MutableMap<String, String>?) {
        setCurrent(oldState)
    }

    private fun setCurrent(contextMap: MutableMap<String, String>?) {
        if (contextMap == null) {
            MDC.clear()
        } else {
            MDC.setContextMap(contextMap)
        }
    }
}