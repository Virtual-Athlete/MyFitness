package com.virtualathlete.myfitness.domain

class WorkoutExercise {

    var id: String? = null
    var exerciseId: String? = null
    //var workout: String? = null
    var repsType: RepsType? = null
    var repsMale: Int? = null
    var repsFemale: Int? = null
    var timed: String? = null
    var isPaced: Boolean? = null
    var pace: String? = null
    var weightType: WeightType? = null
    var percentage: Int? = null
    var weightMale: Double? = null
    var weightFemale: Double? = null
    var weightUnit: WeightUnit? = null
}

enum class RepsType {
    Reps,
    Max
}

enum class WeightType {
    Fix,
    PercentOfBodyWeight,
    PercentOfMax,
    Max,
    Optional
}

enum class WeightUnit {
    Kg,
    Lbs
}