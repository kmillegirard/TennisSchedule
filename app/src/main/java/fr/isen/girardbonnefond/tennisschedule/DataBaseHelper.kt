package fr.isen.girardbonnefond.tennisschedule

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DataBaseHelper {
    companion object {
        val database = Firebase.database("https://tennisschedule-ecba9-default-rtdb.europe-west1.firebasedatabase.app/")
    }
}