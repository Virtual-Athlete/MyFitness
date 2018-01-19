package com.virtualathlete.myfitness.feed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.model.Workout
import kotlinx.android.synthetic.main.list_item_workout.view.*
import kotlinx.android.synthetic.main.list_item_workout_header.view.*


/**
 * Created by haris on 2018-01-12.
 */
class WorkoutViewAdapter : RecyclerView.Adapter<WorkoutViewAdapter.BaseViewHolder>() {

    private var workouts: List<Workout>? = null

    private val headerView = 0
    private val workoutView = 1
    private var event: OnClickItemListener? = null

    override fun getItemViewType(position: Int): Int {
        return position % 3
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return when (viewType) {
            workoutView -> {
                WorkoutViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout, parent, false))
            }
            headerView -> {
                HeaderViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout_header, parent, false))
            }
            else -> WorkoutViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindItems(workouts?.get(position))
    }

    override fun getItemCount(): Int {
        return if(workouts != null){
            workouts!!.size
        } else {
            0
        }
    }

    fun setOnClickItemListener(event: OnClickItemListener) {
        this.event = event
    }

    fun swapWorkouts(workouts: List<Workout>){
        this.workouts = workouts
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder (itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(items: Workout?) {
            itemView.day_of_week_text_view.text = "Today"
            itemView.day_of_month_text_view.text = items?.date
        }
    }

    inner class WorkoutViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(items: Workout?) {
            itemView.title_workout_text_view.text = items?.name
            itemView.title_workout_type_text_view.text = items?.type.toString()
            itemView.workout_constraint_layout.setOnClickListener({ view ->
                event?.onClickItem(view)
            })
        }
    }

    open inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        open fun bindItems(items: Workout?) {}
    }

    interface OnClickItemListener{
        fun onClickItem(view: View)
    }
}
