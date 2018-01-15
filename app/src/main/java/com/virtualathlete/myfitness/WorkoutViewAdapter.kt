package com.virtualathlete.myfitness

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_workout.view.*

/**
 * Created by haris on 2018-01-12.
 */
class WorkoutViewAdapter : RecyclerView.Adapter<WorkoutViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WorkoutViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: WorkoutViewAdapter.ViewHolder, position: Int) {
        holder.bindItems("")
    }

    override fun getItemCount(): Int {
        return 100
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(items: String) {
            itemView.title_workout_text_view.text = "Intervals"
            itemView.title_workout_type_text_view.text = "80% Metabolic"
            itemView.workout_constraint_layout.setOnClickListener({ view ->
                (view.context as Activity).fragmentManager.beginTransaction().replace(R.id.fragment_frame_layout, WorkoutDetailFragment()).commit();
            })
        }
    }
}

private fun RecyclerView.ViewHolder.bindItems(any: Any) {}
