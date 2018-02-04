package com.virtualathlete.myfitness.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

/**
 * Created by haris on 2018-02-03.
 */
@IgnoreExtraProperties
class WorkoutSet constructor() {
    constructor(name: String, sets: Int, load: Int, workoutId: String) : this() {
        this.name = name
        this.sets = sets
        this.load = load
        this.workoutId = workoutId
    }

    var name: String? = null
    var sets: Int? = null
    var load: Int? = null
    var workoutId: String? = null
}