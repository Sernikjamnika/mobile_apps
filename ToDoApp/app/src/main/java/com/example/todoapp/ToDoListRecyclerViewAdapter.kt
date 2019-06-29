package com.example.todoapp

import android.support.v7.widget.RecyclerView
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import com.example.todoapp.TaskFragment.OnListFragmentInteractionListener

import com.example.todoapp.TaskContent.Task

import kotlinx.android.synthetic.main.fragment_task.view.*



class ToDoListRecyclerViewAdapter(
    private val mValues: MutableList<Task>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ToDoListRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val deleteClickListener: View.OnLongClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Task
            mListener?.onListFragmentInteraction(item)
        }

        deleteClickListener = View.OnLongClickListener { v ->
            val item = v.tag as Task
            mListener?.onListFragmentLongInteraction(item, mValues.indexOf(item))
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_task, parent, false)

        val orientation = parent.resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            val size = parent.measuredHeight / 5
            view.minimumHeight = size

        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.priority
        holder.mContentView.text = item.title
        holder.mDateView.text = item.date
        holder.mIconView.setImageDrawable(item.icon)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
            setOnLongClickListener(deleteClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun removeItem(position: Int){
        mValues.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mValues.size)
    }

    fun addItem(item: Task){
        mValues.add(item)
        mListener?.getButtonAndSortData()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.TaskPriority
        val mContentView: TextView = mView.TaskTitle
        val mDateView: TextView = mView.TaskDate
        val mIconView: ImageView = mView.TaskImage

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
