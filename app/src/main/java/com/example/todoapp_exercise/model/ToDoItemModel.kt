package com.example.todoapp_exercise.model

data class ToDoItemModel(
    var id: Long,
    var title: String,
    var startTime: String,
    var endTime: String,
    var note: String,
    var isDone: Boolean
)