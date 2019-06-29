package com.example.todoapp

import android.graphics.drawable.Drawable
import java.util.ArrayList


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object TaskContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<Task> = ArrayList()


    data class Task(val title: String,
                    val description: String,
                    val date: String,
                    val priority: String,
                    val icon: Drawable) {
        override fun toString(): String = title
    }
}
