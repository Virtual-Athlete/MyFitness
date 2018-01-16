package com.virtualathlete.myfitness

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_workout.view.*
import kotlinx.android.synthetic.main.list_item_workout_header.view.*

/**
 * Created by haris on 2018-01-12.
 */
class WorkoutViewAdapter : RecyclerView.Adapter<WorkoutViewAdapter.BaseViewHolder>() {

    private val HEADER_VIEW = 0
    private val WORKOUT_VIEW = 1

    override fun getItemViewType(position: Int): Int {
        return position % 3
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WorkoutViewAdapter.BaseViewHolder {
        return when (viewType) {
            WORKOUT_VIEW -> {
                WorkoutViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout, parent, false))
            }
            HEADER_VIEW -> {
                HeaderViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout_header, parent, false))
            }
            else -> WorkoutViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: WorkoutViewAdapter.BaseViewHolder, position: Int) {
        holder.bindItems("")
    }

    override fun getItemCount(): Int {
        return 9
    }

    inner class HeaderViewHolder (itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(items: String) {
            itemView.day_of_week_text_view.text = "Today"
            itemView.day_of_month_text_view.text = "17 Wed"
        }
    }

    inner class WorkoutViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(items: String) {
            itemView.title_workout_text_view.text = "Intervals"
            itemView.title_workout_type_text_view.text = "80% Metabolic"
            itemView.workout_constraint_layout.setOnClickListener({ view ->
                (view.context as Activity).fragmentManager.beginTransaction().replace(R.id.fragment_frame_layout, WorkoutDetailFragment()).addToBackStack(WorkoutDetailFragment::class.java.simpleName).commit();
            })
        }
    }

    open inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        open fun bindItems(items: String) {}
    }

}

private fun RecyclerView.ViewHolder.bindItems(any: Any) {}
