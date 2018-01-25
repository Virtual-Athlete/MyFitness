package com.virtualathlete.myfitness.feed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.model.BaseWorkout
import com.virtualathlete.myfitness.model.Workout
import com.virtualathlete.myfitness.model.WorkoutSection
import com.virtualathlete.myfitness.model.WorkoutType
import kotlinx.android.synthetic.main.list_item_workout.view.*
import kotlinx.android.synthetic.main.list_item_workout_header.view.*

/**
 * Created by haris on 2018-01-12.
 */
class WorkoutViewAdapter : RecyclerView.Adapter<WorkoutViewAdapter.BaseViewHolder>() {

    private var baseWorkouts: List<BaseWorkout>? = null

    private val headerView = 0
    private val workoutView = 1
    private var event: OnClickItemListener? = null

    override fun getItemViewType(position: Int): Int {
        if(baseWorkouts?.get(position) is WorkoutSection)
            return 0;
        else
            return 1;
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
        holder.bindItems(baseWorkouts?.get(position))
    }

    override fun getItemCount(): Int {
        return if(baseWorkouts != null){
            baseWorkouts!!.size
        } else {
            0
        }
    }

    fun setOnClickItemListener(event: OnClickItemListener) {
        this.event = event
    }

    fun swapWorkouts(baseWorkouts: List<BaseWorkout>){
        this.baseWorkouts = baseWorkouts
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder (itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(baseWorkout: BaseWorkout?) {
            val workoutSection = baseWorkout as WorkoutSection
            itemView.day_of_week_text_view.text = "Today"
            itemView.day_of_month_text_view.text = workoutSection.date
        }
    }

    inner class WorkoutViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(baseWorkout: BaseWorkout?) {
            val workout = baseWorkout as Workout
            itemView.title_workout_text_view.text = workout.name

            val exerciseTypes = itemView.context.resources.getStringArray(R.array.exercise_types)
            when (workout.type) {
                WorkoutType.WEIGHTLIFTING -> itemView.title_workout_type_text_view.text = exerciseTypes[0]
                WorkoutType.GYMNASTIC -> itemView.title_workout_type_text_view.text = exerciseTypes[1]
                WorkoutType.METABOLIC -> itemView.title_workout_type_text_view.text = exerciseTypes[2]
                WorkoutType.REST -> itemView.title_workout_type_text_view.text = exerciseTypes[3]
            }

            itemView.workout_constraint_layout.setOnClickListener({ view ->
                event?.onClickItem(view)
            })
        }
    }

    open inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        open fun bindItems(baseWorkout: BaseWorkout?) {}
    }

    interface OnClickItemListener{
        fun onClickItem(view: View)
    }
}
