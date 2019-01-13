package com.virtualathlete.myfitness.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.icu.util.UniversalTimeScale.toLong
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.virtualathlete.myfitness.model.BaseWorkout
import com.virtualathlete.myfitness.model.Workout
import com.virtualathlete.myfitness.model.WorkoutSection
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by haris on 2018-01-20.
 */

class FeedViewModel: ViewModel(){
    var workouts: MutableLiveData<MutableList<BaseWorkout>> = MutableLiveData()

    fun getWorkouts() : LiveData<MutableList<BaseWorkout>> {
        if (workouts.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("workouts")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val mutableWorkouts = ArrayList<Workout>()
                                dataSnapshot.children
                                        .map { it.getValue<Workout>(Workout::class.java) }
                                        .forEach { workout -> workout?.let { mutableWorkouts.add(it) } }
                                workouts.postValue(sortWorkoutsByDate(mutableWorkouts))
                            }
                        }

                    })
        }
        return workouts
    }

    fun addWorkout(workout: Workout){
        FirebaseDatabase.getInstance()
                .getReference("workouts")
                .push()
                .setValue(workout)
    }

    private fun sortWorkoutsByDate(workouts: ArrayList<Workout>) : MutableList<BaseWorkout>{

        workouts.sortedBy { workout -> workout.date }
        //workouts.filter { workout -> (Date(workout.date) }

        val sortedWorkouts: TreeMap<String, MutableList<Workout>> = TreeMap()
        workouts.forEach{ workout -> run {
                var workoutsByDate: MutableList<Workout>? = sortedWorkouts[Date(workout.date!!).time.toString()]
                if (workoutsByDate == null) {
                    workoutsByDate = ArrayList<Workout>()
                    val newDateKey: String = Date(workout.date!!).time.toString()
                    sortedWorkouts[newDateKey] = workoutsByDate
                }
            workoutsByDate.add(workout)
            }
        }


        val sortedBaseWorkouts: MutableList<BaseWorkout> = ArrayList()
        sortedWorkouts.forEach{ workouts ->
            run {
                sortedBaseWorkouts.add(WorkoutSection(workouts.key.toLong()))
                workouts.value.forEach{
                    sortedBaseWorkouts.add(Workout(it.name!!, Date(it.date!!).time, it.type!!))
                }
            }
        }

        return sortedBaseWorkouts
    }
}