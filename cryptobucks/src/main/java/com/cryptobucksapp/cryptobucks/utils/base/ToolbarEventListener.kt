package com.cryptobucksapp.cryptobucks.utils.base

import com.cryptobucksapp.cryptobucks.utils.events.ToolbarEvent

interface ToolbarEventListener {
    fun onNewEvent(event: ToolbarEvent)
}