package sigint.core

typealias Subscriber<T> = (T) -> Unit

object EventBus {
    private val subscribers = mutableMapOf<String, MutableList<Subscriber<Any>>>()

    fun <T: Any> subscribe(topic: String, handler: Subscriber<T>) {
        subscribers.getOrPut(topic) { mutableListOf() }.add(handler as Subscriber<Any>)
    }

    fun publish(topic: String, event: Any) {
        subscribers[topic]?.forEach { it(event) }
    }
}