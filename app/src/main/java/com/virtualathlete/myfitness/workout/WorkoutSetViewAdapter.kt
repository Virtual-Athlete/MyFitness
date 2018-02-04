package com.virtualathlete.myfitness.workout

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.model.WorkoutSet
import kotlinx.android.synthetic.main.list_item_workout_set.view.*

/**
 * Created by hariskljajic on 2018-01-15.
 */
class WorkoutSetViewAdapter : RecyclerView.Adapter<WorkoutSetViewAdapter.ViewHolder>(){

    private lateinit var workoutSets: List<WorkoutSet>

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout_set, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(workoutSets[position])
    }

    override fun getItemCount(): Int = workoutSets.size

    fun swapWorkoutSets(workoutSets: List<WorkoutSet>){
        this.workoutSets = workoutSets
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(workoutSet: WorkoutSet) {
            val exerciseViewAdapter = ExerciseViewAdapter()
            itemView.title_workout_set.text = workoutSet.name?.toUpperCase()
            itemView.workout_set.text = "%s WORK SETS".format(workoutSet.sets.toString())
            itemView.workout_load.text = "%s%% WORK LOAD".format(workoutSet.load.toString())
            itemView.list_exercise_recycler_view.layoutManager = LinearLayoutManager(itemView.context, LinearLayout.VERTICAL, false)
            itemView.list_exercise_recycler_view.adapter = exerciseViewAdapter
            itemView.list_exercise_recycler_view.setHasFixedSize(true)
        }
    }
}