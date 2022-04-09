package com.example.todoapp_exercise.database

import android.provider.BaseColumns

const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${Database.DatabaseEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${Database.DatabaseEntry.TITLE} TEXT," +
            "${Database.DatabaseEntry.START_TIME} TEXT," +
            "${Database.DatabaseEntry.END_TIME} TEXT," +
            "${Database.DatabaseEntry.NOTES} TEXT," +
            "${Database.DatabaseEntry.DONE} INTEGER)"

const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${Database.DatabaseEntry.TABLE_NAME}"