package com.virtualathlete.myfitness.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.virtualathlete.myfitness.model.BaseWorkout
import com.virtualathlete.myfitness.model.Workout
import com.virtualathlete.myfitness.model.WorkoutSection
import java.util.function.Consumer


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
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val mutableWorkouts = ArrayList<Workout>()
                                dataSnapshot.children
                                        .map { it.getValue<Workout>(Workout::class.java) }
                                        .forEach { workout -> workout?.let { mutableWorkouts.add(it) } }
                                workouts.postValue(sortWorkoutsByDate(mutableWorkouts))
                            }
                        }
                        override fun onCancelled(p0: DatabaseError?) {
                            // Crash, lets notify fail somehow!
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

        val sortedWorkouts: HashMap<String, MutableList<Workout>> = HashMap()
        workouts.forEach{ workout -> run {
                var workoutsByDate: MutableList<Workout>? = sortedWorkouts[workout.date.format()]
                if (workoutsByDate == null) {
                    workoutsByDate = ArrayList<Workout>()
                    val newDateKey: String = workout.date
                    sortedWorkouts[newDateKey] = workoutsByDate
                }
            workoutsByDate.add(workout)
            }
        }


        val sortedBaseWorkouts: MutableList<BaseWorkout> = ArrayList()
        sortedWorkouts.forEach{ workouts ->
            run {
                sortedBaseWorkouts.add(WorkoutSection(workouts.key))
                workouts.value.forEach{
                    sortedBaseWorkouts.add(Workout(it.name, it.date, it.type))
                }
            }
        }

        return sortedBaseWorkouts
    }
}