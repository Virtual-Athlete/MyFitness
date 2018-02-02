package com.virtualathlete.myfitness.model

/**
 * Created by haris on 2018-01-25.
 */

class WorkoutSection constructor() : BaseWorkout(){
    constructor(date: Long) : this() {
        this.date = date
    }

    var date: Long? = null
}
