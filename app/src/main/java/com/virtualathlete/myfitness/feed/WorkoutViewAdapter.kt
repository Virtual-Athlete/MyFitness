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
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by haris on 2018-01-12.
 */
class WorkoutViewAdapter : RecyclerView.Adapter<WorkoutViewAdapter.BaseViewHolder>() {

    private lateinit var baseWorkouts: List<BaseWorkout>

    companion object {
        private const val HEADER_VIEW = 0
        private const val REST_VIEW = 1
        private const val WORKOUT_VIEW = 2
    }

    private var event: OnClickItemListener? = null

    override fun getItemViewType(position: Int): Int {
        return if (baseWorkouts[position] is WorkoutSection)
            HEADER_VIEW;
        else if(baseWorkouts[position] is Workout && (baseWorkouts[position] as Workout).type == WorkoutType.REST)
            REST_VIEW
        else
            WORKOUT_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return when (viewType) {
            HEADER_VIEW -> {
                HeaderViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout_header, parent, false))
            }
            REST_VIEW -> {
                RestViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_rest, parent, false))
            }
            WORKOUT_VIEW -> {
                WorkoutViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout, parent, false))
            }
            else -> WorkoutViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_workout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindItems(baseWorkouts[position])
    }

    override fun getItemCount() = baseWorkouts.size

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
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            val date = Date(workoutSection.date!!);
            when {
                DateUtils.isToday(date.time) -> {
                    itemView.day_of_week_text_view.text = "Today"
                    itemView.day_of_month_text_view.visibility = View.GONE
                }
                DateUtils.isToday(date.time - DateUtils.DAY_IN_MILLIS) -> {
                    itemView.day_of_week_text_view.text = "Tomorrow"
                    itemView.day_of_month_text_view.text = format.format(workoutSection.date)
                }
                else -> {
                    val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
                    val dayOfTheWeek = sdf.format(date)
                    itemView.day_of_week_text_view.text = dayOfTheWeek
                    itemView.day_of_month_text_view.text = format.format(workoutSection.date)
                    itemView.day_of_month_text_view.visibility = View.VISIBLE
                }
            }
        }
    }

    inner class RestViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(baseWorkout: BaseWorkout?) {
            // Bind vector drawable
        }
    }

    inner class WorkoutViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bindItems(baseWorkout: BaseWorkout?) {
            val workout = baseWorkout as Workout
            itemView.title_workout_text_view.text = workout.name
            val exerciseTypes = itemView.context.resources.getStringArray(R.array.exercise_types)
            when (workout.type) {
                WorkoutType.WEIGHTLIFTING -> itemView.title_workout_type_text_view.text = exerciseTypes[0]
                WorkoutType.METABOLIC -> itemView.title_workout_type_text_view.text = exerciseTypes[1]
                WorkoutType.GYMNASTIC -> itemView.title_workout_type_text_view.text = exerciseTypes[2]
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
