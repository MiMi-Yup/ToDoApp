package com.example.todoapp_exercise.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.todoapp_exercise.R
import com.example.todoapp_exercise.adapter.ToDoAdapter
import com.example.todoapp_exercise.database.FirebaseRealtime
import com.example.todoapp_exercise.model.ToDoItemModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    private val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val reader = FirebaseRealtime()

        val r: Runnable = object : Runnable {
            override fun run() {
                if (FirebaseRealtime.dataHasChanged) {
                    val objects: List<ToDoItemModel> = reader.Select()
                    Log.e("getdata", "${objects.size}")
                    val lvToDoItem: ListView? = view?.findViewById<ListView>(R.id.lvToDoItem)
                    val txtStartToDo: LinearLayout? = view?.findViewById<LinearLayout>(R.id.llStartToDo)
                    if (objects.size > 0) {
                        val adapter: ToDoAdapter = ToDoAdapter(lvToDoItem!!.context, objects)
                        lvToDoItem?.adapter = adapter
                        txtStartToDo!!.visibility = View.GONE
                    } else {
                        txtStartToDo!!.visibility = View.VISIBLE
                        txtStartToDo!!.setOnClickListener(addItemEvent)
                    }

                    FirebaseRealtime.dataHasChanged = false
                }

                handler.postDelayed(this, 2000)
            }
        }

        handler.postDelayed(r, 2000)

        val fabToDoAdd = view.findViewById<FloatingActionButton>(R.id.fabAddToDo)
        fabToDoAdd.setOnClickListener(addItemEvent)
        fabToDoAdd.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                //TODO("Not yet implemented")
                FirebaseRealtime.dataHasChanged = true
                return true
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        FirebaseRealtime.dataHasChanged = true
    }

    private val addItemEvent: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(view: View?) {
            //TODO("Not yet implemented")
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_addFragment)
        }
    }
}