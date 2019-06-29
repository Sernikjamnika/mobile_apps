package com.example.todoapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class AddTaskForm : Fragment() {

    companion object {
        fun newInstance() = AddTaskForm()
    }


    private lateinit var viewModel: AddTaskFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddTaskFormViewModel::class.java)

    }

    interface OnAddTaskAcceptationListener {

        fun onAddTaskAcceptation()

    }

}
