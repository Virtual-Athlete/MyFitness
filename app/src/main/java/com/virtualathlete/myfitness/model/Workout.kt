package com.virtualathlete.myfitness.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

/**
 * Created by haris on 2018-01-19.
 */
@IgnoreExtraProperties
class Workout constructor() : BaseWorkout() {

    constructor(name: String, date: String, workoutType: WorkoutType) : this() {
        this.name = name
        this.date = date
        this.type = workoutType
    }

    lateinit var name: String
    lateinit var date: String
    lateinit var type: WorkoutType
}