package com.example.todoapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_task_list.*

class MainActivity : AppCompatActivity(), TaskFragment.OnListFragmentInteractionListener {

    private var tasks = TaskContent
    private val TASK_CREATED = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, TASK_CREATED)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            sortData(checkedId)
        }

        ToDoList.adapter = ToDoListRecyclerViewAdapter(tasks.ITEMS,  this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        if (requestCode == TASK_CREATED) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                val adapter = ToDoList.adapter as ToDoListRecyclerViewAdapter
                val title = data!!.getStringExtra("title")
                val description = data.getStringExtra("description")
                val priority = data.getStringExtra("priority")
                val date = data.getStringExtra("date")
                val icon = getDrawable(data.getIntExtra("icon", 0))
                if(icon != null){
                    adapter.addItem(TaskContent.Task(title, description, date, priority, icon))

                }
            }
        }
    }

    override fun getButtonAndSortData() {
        val buttonID = radioGroup.checkedRadioButtonId
        sortData(buttonID)
    }

    private fun sortData(buttonID: Int) {
        when (buttonID) {
            PriorityRadioButton.id -> tasks.ITEMS.sortByDescending { it.priority }
            else -> {
                tasks.ITEMS.sortBy { it.date }
            }
        }
        ToDoList.adapter = ToDoListRecyclerViewAdapter(tasks.ITEMS, this)
    }

    override fun onListFragmentInteraction(item: TaskContent.Task?) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(item?.title)
        alertDialog.setMessage(item?.description)
        alertDialog.setPositiveButton("CLOSE"
        ) { dialog, _ -> dialog.cancel() }

        val dialog = alertDialog.create()
        dialog.show()
    }

    override fun onListFragmentLongInteraction(item: TaskContent.Task?, position: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Task Deletion")
        alertDialog.setMessage("Are you sure you want to delete the task?")
        alertDialog.setPositiveButton("CANCEL"
        ) { dialog, _ -> dialog.cancel() }
        alertDialog.setNegativeButton("YES") { dialog, _ ->
            val adapter = ToDoList.adapter as ToDoListRecyclerViewAdapter
            adapter.removeItem(position)
            dialog.cancel()
        }

        val dialog = alertDialog.create()
        dialog.show()    }
}
