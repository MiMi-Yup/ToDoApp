package com.example.todoapp_exercise.database

import android.text.TextUtils
import android.util.Log
import com.example.todoapp_exercise.model.ToDoItemModel
import com.google.firebase.database.*


class FirebaseRealtime {
    private var database: DatabaseReference? = null
    private var firebase: FirebaseDatabase? = null
    private val TAG: String = "firebase"

    private val event = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            //TODO("Not yet implemented")
            items.clear()
            dataHasChanged = true
            for (item in snapshot.children) {
                val post = item.getValue(ToDoItemModel::class.java) as ToDoItemModel
                items.add(post)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            //TODO("Not yet implemented")
        }
    }

    constructor() {
        firebase = FirebaseDatabase.getInstance()
        FirebaseDatabase.getInstance().reference.keepSynced(true)
        database = firebase?.getReference()

        database?.addListenerForSingleValueEvent(event)
    }

    fun Insert(item: ToDoItemModel): Boolean {
        try {
            if (TextUtils.isEmpty(item.id)) {
                item.id = database?.push()?.key!!
            }

            database?.child(item.id!!)?.setValue(item)

            database?.child(item.id!!)?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataHasChanged = true
                    val user = dataSnapshot.getValue(
                        ToDoItemModel::class.java
                    )

                    // Check for null
                    if (user == null) {
                        Log.e(TAG, "User data is null!")
                        return
                    }
                    Log.e(TAG, "User data is changed! ${item.title}")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read user", error.toException())
                }
            })
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun Select(): List<ToDoItemModel> {
        return items
    }

    fun Update(item: ToDoItemModel): Boolean {
        val key = database?.child(item.id!!)?.key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return false
        }

        val postValues = item.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/$key" to postValues
        )

        database?.updateChildren(childUpdates)
        return true
    }

    fun Delete(item: ToDoItemModel): Boolean {
        database?.child(item.id!!)?.removeValue()
        return true
    }

    fun onExit(): Unit {
        database = null
        firebase = null
    }

    companion object {
        val items: ArrayList<ToDoItemModel> = ArrayList<ToDoItemModel>()
        var dataHasChanged: Boolean = false
    }
}