package com.virtualathlete.myfitness.feed

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.workout.WorkoutDetailActivity
import kotlinx.android.synthetic.main.list_item_workout.view.*
import kotlinx.android.synthetic.main.list_item_workout_header.view.*


/**
 * Created by haris on 2018-01-12.
 */
class WorkoutViewAdapter : RecyclerView.Adapter<WorkoutViewAdapter.BaseViewHolder>() {

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
        holder.bindItems("")
    }

    override fun getItemCount(): Int {
        return 19
    }

    fun setOnClickItemListener(event: OnClickItemListener) {
        this.event = event
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
                event?.onClickItem(view)
            })
        }
    }

    open inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        open fun bindItems(items: String) {}
    }

    interface OnClickItemListener{
        fun onClickItem(view: View)
    }
}
