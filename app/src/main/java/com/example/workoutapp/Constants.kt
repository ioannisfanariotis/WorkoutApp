package com.example.workoutapp

object Constants {

    fun getExercises(): ArrayList<Exercises> {
        val exerciseList = ArrayList<Exercises>()
        val e1 = Exercises(1, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
        exerciseList.add(e1)
        val e2 = Exercises(2, "High Knees", R.drawable.ic_high_knees_running_in_place, false, false)
        exerciseList.add(e2)
        val e3 = Exercises(3, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
        exerciseList.add(e3)
        val e4 = Exercises(4, "Lunges", R.drawable.ic_lunge, false, false)
        exerciseList.add(e4)
        val e5 = Exercises(5, "Planks", R.drawable.ic_plank, false, false)
        exerciseList.add(e5)
        val e6 = Exercises(6, "Push Ups", R.drawable.ic_push_up, false, false)
        exerciseList.add(e6)
        val e7 = Exercises(7, "Rotated Push Ups", R.drawable.ic_push_up_and_rotation, false, false)
        exerciseList.add(e7)
        val e8 = Exercises(8, "Side Planks", R.drawable.ic_side_plank, false, false)
        exerciseList.add(e8)
        val e9 = Exercises(9, "Squats", R.drawable.ic_squat, false, false)
        exerciseList.add(e9)
        val e10 =
            Exercises(10, "Step Up Onto Chair", R.drawable.ic_step_up_onto_chair, false, false)
        exerciseList.add(e10)
        val e11 =
            Exercises(11, "Triceps Dip on Chair", R.drawable.ic_triceps_dip_on_chair, false, false)
        exerciseList.add(e11)
        val e12 = Exercises(12, "Wall Sit", R.drawable.ic_wall_sit, false, false)
        exerciseList.add(e12)

        return exerciseList
    }
}