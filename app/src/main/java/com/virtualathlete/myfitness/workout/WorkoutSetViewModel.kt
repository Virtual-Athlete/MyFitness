package com.virtualathlete.myfitness.workout

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.virtualathlete.myfitness.model.WorkoutSet

/**
 * Created by haris on 2018-02-02.
 */

class WorkoutSetViewModel: ViewModel(){
    var workoutSets: MutableLiveData<MutableList<WorkoutSet>> = MutableLiveData()

    fun getWorkoutSets(workoutId: String) : LiveData<MutableList<WorkoutSet>> {
        if (workoutSets.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("workout_sets")
                    .orderByChild("workoutId")
                    .equalTo(workoutId)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val mutableWorkoutSets = ArrayList<WorkoutSet>()
                                dataSnapshot.children
                                        .map { it.getValue<WorkoutSet>(WorkoutSet::class.java) }
                                        .forEach { workout -> workout?.let { mutableWorkoutSets.add(it) } }
                                workoutSets.postValue(mutableWorkoutSets)
                            }
                        }
                        override fun onCancelled(p0: DatabaseError?) {
                            // Crash, lets notify fail somehow!
                        }
                    })
        }
        return workoutSets
    }

    fun addWorkoutSet(workoutSets: WorkoutSet){
        FirebaseDatabase.getInstance()
                .getReference("workout_sets")
                .push()
                .setValue(workoutSets)
    }
}