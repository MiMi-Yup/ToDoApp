package com.example.todoapp_exercise.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.todoapp_exercise.R
import com.example.todoapp_exercise.database.FunctionDatabase
import com.example.todoapp_exercise.model.ToDoItemModel
import java.time.LocalDateTime

class AddFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add, container, false)

        val txtTitle = view.findViewById<EditText>(R.id.txtTitle)
        val txtStartTime = view.findViewById<TextView>(R.id.txtStartTime)
        val txtEndTime = view.findViewById<TextView>(R.id.txtEndTime)
        val txtNote = view.findViewById<EditText>(R.id.txtNote)

        if (arguments != null) {
            val btnDelete = view.findViewById<Button>(R.id.btnDelete)
            btnDelete.visibility = View.VISIBLE
            btnDelete.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    //TODO("Not yet implemented")
                    val deleter = FunctionDatabase(view!!.context)
                    val item = ToDoItemModel(arguments!!.getLong("id"), "", "", "", "", false)
                    deleter.Delete(item)
                    deleter.Close()
                    view?.findNavController()?.navigate(R.id.action_addFragment_to_homeFragment)
                }
            })

            txtTitle.setText(arguments?.getString("title"), TextView.BufferType.EDITABLE)
            txtStartTime.setText(arguments?.getString("startTime"))
            txtEndTime.setText(arguments?.getString("endTime"))
            txtNote.setText(arguments?.getString("note"), TextView.BufferType.EDITABLE)
        }

        val btnSave = view.findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //TODO("Not yet implemented")
                val writer = FunctionDatabase(view!!.context)
                val item = ToDoItemModel(
                    if (arguments != null) arguments!!.getLong("id") else 1,
                    txtTitle.text.toString(),
                    txtStartTime.text.toString(),
                    txtEndTime.text.toString(),
                    txtNote.text.toString(),
                    false
                )
                if (arguments != null) writer.Update(item)
                else writer.Insert(item)
                writer.Close()

                view?.findNavController()?.navigate(R.id.action_addFragment_to_homeFragment)
            }
        })

        txtStartTime.setOnClickListener(dateTimerPickerListener)

        txtEndTime.setOnClickListener(dateTimerPickerListener)

        return view
    }

    private val dateTimerPickerListener = object : View.OnClickListener {
        override fun onClick(view: View?) {
            //TODO("Not yet implemented")
            val lastDateTime = (view as TextView).text.toString()
            var dayOfMonth = 0
            var monthOfYear = 0
            var year = 0
            var hourOfDay = 0
            var minute = 0

            val split = lastDateTime.split(' ')
            if (split.size == 2) {
                val time = split[1].split(':')
                if (time.size == 2) {
                    hourOfDay = time[0].toInt()
                    minute = time[1].toInt()
                }
            }
            if (split.size > 0) {
                val date = split[0].split('/')
                if (date.size == 3) {
                    dayOfMonth = date[0].toInt()
                    monthOfYear = date[1].toInt()
                    year = date[2].toInt()
                }
            }

            if (year == 0) {
                val date = LocalDateTime.now()
                year = date.year
                monthOfYear = date.monthValue
                dayOfMonth = date.dayOfMonth

                hourOfDay = date.hour
                minute = date.minute
            }

            val dateListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    viewDatePicker: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    //TODO("Not yet implemented")
                    (view as TextView).text = "$dayOfMonth/$monthOfYear/$year"
                }
            }

            val timeListener = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(
                    viewTimePicker: TimePicker?,
                    hourOfDay: Int,
                    minute: Int
                ) {
                    //TODO("Not yet implemented")
                    (view as TextView).text =
                        (view as TextView).text.toString() + " $hourOfDay:$minute"
                }
            }

            val datePickerDialog =
                DatePickerDialog(view!!.context, dateListener, year, monthOfYear, dayOfMonth)
            datePickerDialog.setButton(
                DialogInterface.BUTTON_NEUTRAL,
                "Set time",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        //TODO("Not yet implemented")
                        val timePickerDialog =
                            TimePickerDialog(view!!.context, timeListener, hourOfDay, minute, false)
                        datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).callOnClick()
                        timePickerDialog.show()
                    }
                })

            datePickerDialog.show()
        }
    }
}