package com.example.todoapp_exercise.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.todoapp_exercise.model.ToDoItemModel

///Document: https://developer.android.com/training/data-storage/sqlite

class FunctionDatabase(context: Context) {
    private val context: Context = context
    private val sqLiteHelper: SQLiteHelper = SQLiteHelper(this.context)

    public fun Close(): Unit {
        sqLiteHelper.close()
    }

    public fun Insert(item: ToDoItemModel): Boolean {
        val db = sqLiteHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(Database.DatabaseEntry.TITLE, item.title)
            put(Database.DatabaseEntry.START_TIME, item.startTime)
            put(Database.DatabaseEntry.END_TIME, item.endTime)
            put(Database.DatabaseEntry.NOTES, item.note)
            put(Database.DatabaseEntry.DONE, if (item.isDone) 1L else 0L)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(Database.DatabaseEntry.TABLE_NAME, null, values)
        return newRowId == 1L
    }

    public fun Select(): List<ToDoItemModel> {
        val db = sqLiteHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            BaseColumns._ID,
            Database.DatabaseEntry.TITLE,
            Database.DatabaseEntry.START_TIME,
            Database.DatabaseEntry.END_TIME,
            Database.DatabaseEntry.NOTES,
            Database.DatabaseEntry.DONE
        )

        // Filter results WHERE "title" = 'My Title'
        val selection = "${Database.DatabaseEntry.TITLE} = ?"

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${BaseColumns._ID} DESC"

        val cursor = db.query(
            Database.DatabaseEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val items = mutableListOf<ToDoItemModel>()
        with(cursor) {
            while (moveToNext()) {
                val item = ToDoItemModel(
                    getString(getColumnIndexOrThrow(BaseColumns._ID)),
                    getString(getColumnIndexOrThrow(Database.DatabaseEntry.TITLE)),
                    getString(getColumnIndexOrThrow(Database.DatabaseEntry.START_TIME)),
                    getString(getColumnIndexOrThrow(Database.DatabaseEntry.END_TIME)),
                    getString(getColumnIndexOrThrow(Database.DatabaseEntry.NOTES)),
                    getLong(getColumnIndexOrThrow(Database.DatabaseEntry.DONE)) == 1L
                )
                items.add(item)
            }
        }
        cursor.close()

        return items
    }

    fun Update(item: ToDoItemModel): Boolean {
        val db = sqLiteHelper.writableDatabase

        // New value for one column
        val values = ContentValues().apply {
            put(Database.DatabaseEntry.TITLE, item.title)
            put(Database.DatabaseEntry.START_TIME, item.startTime)
            put(Database.DatabaseEntry.END_TIME, item.endTime)
            put(Database.DatabaseEntry.NOTES, item.note)
            put(Database.DatabaseEntry.DONE, if (item.isDone) 1L else 0L)
        }

        // Which row to update, based on the title
        val selection = "${BaseColumns._ID} = ${item.id}"
        val count = db.update(
            Database.DatabaseEntry.TABLE_NAME,
            values,
            selection,
            null
        )

        return count == 1
    }

    fun Delete(item: ToDoItemModel): Boolean {
        val db = sqLiteHelper.writableDatabase
        // Define 'where' part of query.
        val selection = "${BaseColumns._ID} = ${item.id}"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf("MyTitle")
        // Issue SQL statement.
        val deletedRows = db.delete(Database.DatabaseEntry.TABLE_NAME, selection, null)
        return deletedRows == 1
    }
}