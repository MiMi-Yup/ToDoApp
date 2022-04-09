package com.example.todoapp_exercise.fragment

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.todoapp_exercise.R
import com.example.todoapp_exercise.adapter.ToDoAdapter
import com.example.todoapp_exercise.database.FunctionDatabase
import com.example.todoapp_exercise.model.ToDoItemModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    private lateinit var adapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val reader = FunctionDatabase(view?.context)
        val objects: List<ToDoItemModel> = reader.Select()
        reader.Close()

        if (objects.size > 0) {
            val lvToDoItem = view.findViewById<ListView>(R.id.lvToDoItem)
            adapter = ToDoAdapter(lvToDoItem.context, objects)
            lvToDoItem.adapter = adapter
        } else {
            val txtStartToDo = view.findViewById<LinearLayout>(R.id.llStartToDo)
            txtStartToDo.visibility = View.VISIBLE
            txtStartToDo.setOnClickListener(addItemEvent)
        }

        val fabToDoAdd = view.findViewById<FloatingActionButton>(R.id.fabAddToDo)
        fabToDoAdd.setOnClickListener(addItemEvent)

        return view
    }

    private val addItemEvent: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(view: View?) {
            //TODO("Not yet implemented")
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_addFragment)
        }
    }
}