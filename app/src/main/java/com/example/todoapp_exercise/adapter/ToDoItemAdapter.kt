package com.example.todoapp_exercise.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.example.todoapp_exercise.R
import com.example.todoapp_exercise.database.FunctionDatabase
import com.example.todoapp_exercise.model.ToDoItemModel

class ToDoAdapter(context: Context, items: List<ToDoItemModel>) : BaseAdapter() {
    private var context: Context = context
    private var items: List<ToDoItemModel> = items

    override fun getCount(): Int {
        //TODO("Not yet implemented")
        return items.size
    }

    override fun getItem(position: Int): Any {
        //TODO("Not yet implemented")
        return items.get(position)
    }

    override fun getItemId(position: Int): Long {
        //TODO("Not yet implemented")
        return items.get(position).id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //TODO("Not yet implemented")
        val view: View = LayoutInflater.from(context).inflate(R.layout.to_do_row, parent, false)

        val txtTitle: TextView = view.findViewById<TextView>(R.id.txtTitle)
        val txtStartTime: TextView = view.findViewById<TextView>(R.id.txtStartTime)
        val txtEndTime: TextView = view.findViewById<TextView>(R.id.txtEndTime)
        val txtNotes: TextView = view.findViewById<TextView>(R.id.txtNotes)
        val imgIsDone: ImageView = view.findViewById<ImageView>(R.id.imgIsDone)
        val cbMarkDone: CheckBox = view.findViewById<CheckBox>(R.id.cbMarkDone)

        txtTitle.text = items.get(position).title
        txtStartTime.text = items.get(position).startTime
        txtEndTime.text = items.get(position).endTime
        txtNotes.text = items.get(position).note
        imgIsDone.visibility = if (items.get(position).isDone) View.VISIBLE else View.GONE
        cbMarkDone.visibility = if (items.get(position).isDone) View.GONE else View.VISIBLE

        cbMarkDone.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton?, isChecked: Boolean) {
                //TODO("Not yet implemented")
                items.get(position).isDone = isChecked
                imgIsDone.visibility = if (isChecked) View.VISIBLE else View.GONE
                cbMarkDone.visibility = if (isChecked) View.GONE else View.VISIBLE

                val update = FunctionDatabase(view.context)
                update.Update(items.get(position))
                update.Close()
            }
        })

        view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //TODO("Not yet implemented")
                if (items.get(position).isDone) {
                    Toast.makeText(
                        view?.context,
                        "${items.get(position).title} has done",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val bundle: Bundle = Bundle()
                    bundle.putLong("id", items.get(position).id)
                    bundle.putString("title", items.get(position).title)
                    bundle.putString("startTime", items.get(position).startTime)
                    bundle.putString("endTime", items.get(position).endTime)
                    bundle.putString("note", items.get(position).note)
                    view?.findNavController()
                        ?.navigate(R.id.action_homeFragment_to_addFragment, bundle)
                }
            }
        })

        return view
    }
}