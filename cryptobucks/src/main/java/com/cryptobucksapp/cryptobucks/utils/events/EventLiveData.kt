package com.cryptobucksapp.cryptobucks.utils.events

/**
 * Used as a wrapper for mexperiencesStoreResponse that is exposed via a LiveData that represents an event.
 * Se utiliza como un contenedor para los datos que se exponen a través de un LiveData que representa un evento.
 */
open class EventLiveData<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     * Devuelve el contenido y evita su uso nuevamente.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     * Devuelve el contenido, incluso si ya ha sido usado.
     */
    fun peekContent(): T = content
}