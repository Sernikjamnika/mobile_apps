package com.example.todoapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.add_task_form_fragment.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import android.app.DatePickerDialog
import java.text.SimpleDateFormat


class AddTaskActivity : AppCompatActivity(), AddTaskForm.OnAddTaskAcceptationListener{

    private var previousCheckedId: Int = 0

    private var ImageButtonStyles: Map<Int, Pair<Int, Int>> = mapOf()

    private val myCalendar = Calendar.getInstance()

    override fun onAddTaskAcceptation() {
        val data = Intent()
//        val text = "Result to be returned...."
        packTask(data)
        setResult(RESULT_OK, data)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        AcceptButton.setOnClickListener {
            onAddTaskAcceptation()
        }
        setImageButtonStyles()
        previousCheckedId = ImageSelector.checkedRadioButtonId

        ImageSelector.setOnCheckedChangeListener { _, checkedId ->
            val newCheckedButton = findViewById<RadioButton>(checkedId)
            val previousCheckedButton = findViewById<RadioButton>(previousCheckedId)
            previousCheckedButton.background = getDrawable(ImageButtonStyles.getValue(previousCheckedId).first)
            newCheckedButton.background = getDrawable(ImageButtonStyles.getValue(checkedId).second)
            previousCheckedId = checkedId
        }

        val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        TaskDate.setOnClickListener {
                DatePickerDialog(
                    this,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        TaskDate.setText(sdf.format(myCalendar.time))
    }


    private fun packTask(data: Intent){
        val title = TaskTitle.text.toString()
        val description = TaskDesc.text.toString()
        val date = TaskDate.text.toString()
        val currentDateTime = LocalDateTime.now()
        val selectedIcon = ImageButtonStyles.getValue(previousCheckedId).first
        data.putExtra("title", if (title != "") title else "New task")
        data.putExtra("description", if (description != "") description else "No description given")
        data.putExtra("date", if (date != "") date else currentDateTime.format(DateTimeFormatter.ISO_DATE))
        data.putExtra("priority", TaskPriority.rating.toString())
        data.putExtra("icon", selectedIcon)
    }

    private fun setImageButtonStyles(){
        ImageButtonStyles = mapOf(
            ImageSelection1.id to Pair(R.drawable.pokeball, R.drawable.pokeball_red_border),
            ImageSelection2.id to Pair(R.drawable.jake, R.drawable.jake_red_border),
            ImageSelection3.id to Pair(R.drawable.vader, R.drawable.vader_red_border)
        )
    }
}
