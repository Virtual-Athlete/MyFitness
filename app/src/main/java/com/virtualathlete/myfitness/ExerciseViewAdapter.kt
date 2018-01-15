package com.virtualathlete.myfitness

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_exercise.view.*

/**
 * Created by hariskljajic on 2018-01-15.
 */
class ExerciseViewAdapter : RecyclerView.Adapter<ExerciseViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_exercise, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems("")
    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(items: String) {
            itemView.exercise_play_image_view.setImageBitmap(BitmapFactory.decodeResource(itemView.context.resources, R.mipmap.exercise))
            itemView.exercise_amount_text_view.text = "100 M"
            itemView.exercise_name_text_view.text = "RUN"
        }
    }
}