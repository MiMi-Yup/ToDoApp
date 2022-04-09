package com.example.todoapp_exercise.database

import android.provider.BaseColumns

object Database {
    // Table contents are grouped together in an anonymous object.
    object DatabaseEntry : BaseColumns {
        const val TABLE_NAME = "ToDoList"
        const val TITLE = "title"
        const val START_TIME = "startTime"
        const val END_TIME = "endTime"
        const val NOTES = "notes"
        const val DONE = "isDone"
    }
}