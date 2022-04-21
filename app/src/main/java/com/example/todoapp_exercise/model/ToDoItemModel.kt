package com.example.todoapp_exercise.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class ToDoItemModel {
    lateinit var id: String
    lateinit var title: String
    lateinit var startTime: String
    lateinit var endTime: String
    lateinit var note: String
    var isDone: Boolean = false

    constructor(
        id: String,
        title: String,
        startTime: String,
        endTime: String,
        note: String,
        isDone: Boolean
    ) {
        this.id = id
        this.title = title
        this.startTime = startTime
        this.endTime = endTime
        this.note = note
        this.isDone = isDone
    }

    constructor() {

    }

    fun setIsDone(value: Boolean): Unit {
        isDone = value
    }

    fun getIsDone(): Boolean = isDone

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "startTime" to startTime,
            "endTime" to endTime,
            "note" to note,
            "isDone" to isDone
        )
    }
}