package com.virtualathlete.myfitness.feed

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by haris on 2018-01-12.
 */
class WorkoutViewAdapter : RecyclerView.Adapter<WorkoutViewAdapter.BaseViewHolder>() {

    private var baseWorkouts: List<BaseWorkout>? = null

    private val headerView = 0
    private val workoutView = 1
    private val restView = 2
    private var event: OnClickItemListener? = null

    override fun getItemViewType(position: Int): Int {
        return if (baseWorkouts?.get(position) is WorkoutSection)
            headerView;
        else if(baseWorkouts?.get(position) is Workout && (baseWorkouts?.get(position) as Workout).type == WorkoutType.REST)
            restView
        else
            workoutView
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return when (viewType) {
            workoutView -> {
                WorkoutViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout, parent, false))
            }
            headerView -> {
                HeaderViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout_header, parent, false))
            }
            restView -> {
                RestViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_rest, parent, false))
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
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
            val date = format.parse(workoutSection.date);
            if(DateUtils.isToday(date.time)){
                itemView.day_of_week_text_view.text = "Today"
                itemView.day_of_month_text_view.visibility = View.GONE
            } else if(DateUtils.isToday(date.time - DateUtils.DAY_IN_MILLIS)){
                itemView.day_of_week_text_view.text = "Tomorrow"
                itemView.day_of_month_text_view.visibility = View.GONE
            } else if(DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS)){
                itemView.day_of_week_text_view.text = "Yesterday"
                itemView.day_of_month_text_view.visibility = View.GONE
            } else {
                itemView.day_of_week_text_view.text = ""
                itemView.day_of_month_text_view.text = workoutSection.date
                itemView.day_of_month_text_view.visibility = View.VISIBLE
            }
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

    inner class RestViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(baseWorkout: BaseWorkout?) {

        }
    }

    open inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        open fun bindItems(baseWorkout: BaseWorkout?) {}
    }

    interface OnClickItemListener{
        fun onClickItem(view: View)
    }
}
