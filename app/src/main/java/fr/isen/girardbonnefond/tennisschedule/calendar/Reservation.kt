package fr.isen.girardbonnefond.tennisschedule.calendar

data class Reservation(val date: String, val hours: Map<String, String>) {
}