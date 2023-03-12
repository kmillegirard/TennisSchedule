package fr.isen.girardbonnefond.tennisschedule.calendar

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import fr.isen.girardbonnefond.tennisschedule.R
import fr.isen.girardbonnefond.tennisschedule.databinding.ListTimeBinding
import fr.isen.girardbonnefond.tennisschedule.logadmin.DataBaseHelper
import java.util.*
import android.annotation.SuppressLint as SuppressLint1

class HourAdapter(thisDayOfWeek : String, private val uuid: String, private val date: String, private val listener:ItemCount):RecyclerView.Adapter<HourAdapter.HourViewHolder>() {
    private val day = thisDayOfWeek
    var countClick : Int =0
    class HourViewHolder(binding: ListTimeBinding, adapter: HourAdapter) : RecyclerView.ViewHolder(binding.root) {
        val hour = binding.hour
        val imageView1 : ImageView = binding.buttonSelect1
        val imageView2 : ImageView = binding.buttonSelect2
        val buttonOne : LinearLayout = binding.button1
        val buttonTwo : LinearLayout = binding.button2

        private val bookedImage = R.drawable.booked
        private val notavailableImage = R.drawable.notavailable

        init{
            //var countClick = adapter.CountClick
            buttonOne.setOnClickListener {

                val builder = AlertDialog.Builder(this@HourViewHolder.itemView.context)
                builder.setTitle("Confirmation")
                builder.setMessage("Voulez-vous vraiment faire la réservation à ${getTime()} ?")
                builder.setPositiveButton("Oui") { _, _ ->

                    checkReservationWithTime(1, getTime(), adapter.date, adapter.uuid)
                    imageView1.setImageResource(bookedImage)
                    //Picasso.get().load(bookedImage).into(imageView1)

                    val position = adapterPosition
                        adapter.listener.onItemClick(position,  buttonTwo, buttonOne)
                }
                builder.setNegativeButton("Non", null)
                builder.show()
            }

            buttonTwo.setOnClickListener {
                val builder = AlertDialog.Builder(this@HourViewHolder.itemView.context)
                builder.setTitle("Confirmation")
                builder.setMessage("Voulez-vous vraiment faire la réservation à ${getTime()} ?")
                builder.setPositiveButton("Oui") { _, _ ->

                    checkReservationWithTime(2, getTime(), adapter.date, adapter.uuid)
                    imageView2.setImageResource(bookedImage)
                    //Picasso.get().load(bookedImage).into(imageView2)
                    //imageView2.setColorFilter(blue)

                    val position = adapterPosition
                    adapter.listener.onItemClick(position,  buttonTwo, buttonOne)
                }
                builder.setNegativeButton("Non", null)
                builder.show()
            }
/*
            if(adapter.countClick == 2){
                .isEnabled = false
                if (view is ViewGroup) {
                    for (i in 0 until view.childCount) {
                        val child = view.getChildAt(i)
                        disableClicks(child)
                    }
                }
            }*/

        }

        private fun getTime() : String{
            val position = adapterPosition
            return "${position+7}:00"
        }

        private fun checkReservationWithTime(terrain: Int, time: String, date: String, uuid: String){
            val reservationRef = DataBaseHelper.database.getReference("Reservation/terrain$terrain/$date/")
            reservationRef.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot){
                        if(dataSnapshot.exists()){
                            Log.d(TAG, "Il y a déjà une réservation")
                        }else{
                            Log.d(TAG, "Il n'y a pas de une réservation")
                            createReservation(uuid,date, time, terrain)
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError){
                        Log.w(TAG, "Erreur lors de la récupération des scores : $databaseError")
                    }
                })
        }

        fun createReservation(uuid: String,date: String, time:String, terrain: Int) {
            val hours = mapOf(time to uuid)
            val reservation = Reservation(date, hours)
            DataBaseHelper.database.reference.child("Reservations").child("terrain$terrain")
                .child(reservation.date).setValue(reservation)
            if(terrain == 1) imageView1.setImageResource(bookedImage)
            if (terrain == 2) imageView2.setImageResource(bookedImage)
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = ListTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourViewHolder(binding, this)
    }

    override fun getItemCount(): Int {
        return 22 - 7
    }


    @SuppressLint1("SetTextI18n")
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.hour.text = "${position + 7}h-${position + 8}h"

        if (day != "Saturday") {
            checkReservationTWO(date, uuid, holder, position)
            checkReservation2TWO(date, uuid, holder, position)
            //checkReservationTerrain1(date, uuid, holder, position)
            //checkReservationTerrain2(date, uuid, holder, position)
        }else {
            saturdayFun(position)
            Log.d(TAG, "Position : $position")
            if(position + 7 in 10..18) {
                holder.imageView1.setImageResource(R.drawable.notavailable)
                holder.imageView2.setImageResource(R.drawable.notavailable)
            }

            checkReservationTWO(date, uuid, holder, position)
            checkReservation2TWO(date, uuid, holder, position)
            //checkReservationTerrain1(date, uuid, holder, position)
            //checkReservationTerrain2(date, uuid, holder, position)

        }
    }
    /*
    private fun checkReservationTerrain1(date: String, userId: String, holder: HourViewHolder, position: Int){
        val  time: String = (position + 7).toString() + ":00"

        val reservationRef = DataBaseHelper.database.getReference("terrain1")
        reservationRef.orderByChild("time").equalTo(time)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if(dataSnapshot.exists()){
                        holder.imageView1.setImageResource(R.drawable.notavailable)
                        holder.buttonOne.isClickable = false;
                        Log.d(TAG, "Il y a déjà une réservation")

                    }else{
                        holder.imageView1.setImageResource(R.drawable.available)
                        Log.d(TAG, "Il n'y a pas de une réservation")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError){
                    Log.w(TAG, "Erreur lors de la récupération des scores : $databaseError")
                }
            })
    }
    private fun checkReservationTerrain2( date: String, userId: String, holder: HourViewHolder, position: Int){
        val  time: String = (position + 7).toString() + ":00"

        val reservationRef = DataBaseHelper.database.getReference("terrain2")
        reservationRef.orderByChild("time").equalTo(time)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if(dataSnapshot.exists()){
                        holder.imageView2.setImageResource(R.drawable.notavailable)
                        Log.d(TAG, "Il y a déjà une réservation")
                        holder.buttonTwo.isClickable = false;
                    }else{
                        holder.imageView2.setImageResource(R.drawable.available)
                        Log.d(TAG, "Il n'y a pas de une réservation")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError){
                    Log.w(TAG, "Erreur lors de la récupération des scores : $databaseError")
                }
            })
    }*/
    private fun checkReservationTWO(date: String, uuid: String, holder: HourViewHolder, position: Int) {
        val time: String = (position + 7).toString() + ":00"

        val reservationRef = DataBaseHelper.database.getReference("Reservations/terrain1/$date/hours/$time")
        reservationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userId = dataSnapshot.getValue(String::class.java)
                if (userId == null) {
                    Log.d(TAG, "Il n'y a pas de réservations à $time le $date")
                }else {
                    if (userId == uuid){
                        holder.imageView1.setImageResource(R.drawable.booked)
                        Log.d(TAG, "l'user $uuid a fait une réservation à $time le $date")
                        countClick=+1

                    }else{
                        holder.imageView1.setImageResource(R.drawable.notavailable)
                        holder.buttonOne.isEnabled = false
                        Log.d(TAG, "un autre user ($uuid) a fait une réservation à $time le $date")
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Erreur lors de la lecture des données : ${databaseError.message}")
            }
        })
    }
    private fun checkReservation2TWO(date: String, uuid: String, holder: HourViewHolder, position: Int) {
        val time: String = (position + 7).toString() + ":00"

        val reservationRef = DataBaseHelper.database.getReference("Reservations/terrain2/$date/hours/$time")
        reservationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userId = dataSnapshot.getValue(String::class.java)
                if (userId == null) {
                    Log.d(TAG, "Il n'y a pas de réservations à $time le $date")
                }else {
                    if (userId == uuid){
                        holder.imageView2.setImageResource(R.drawable.booked)
                        Log.d(TAG, "l'user $uuid a fait une réservation à $time le $date")
                        countClick=+1

                    }else{
                        holder.imageView2.setImageResource(R.drawable.notavailable)
                        holder.buttonTwo.isEnabled = false
                        Log.d(TAG, "un autre user ($uuid) a fait une réservation à $time le $date")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Erreur lors de la lecture des données : ${databaseError.message}")
            }
        })
    }

    private fun saturdayFun(position: Int){
        var position7 = position + 7
        if(position + 7 in 10..18) {
            var time = "$position7:00"
            val hours = mapOf(time to "cours de tennis")
            val reservation = Reservation(date, hours)
            DataBaseHelper.database.reference.child("Reservations").child("terrain1")
                .child(reservation.date).setValue(reservation)
            DataBaseHelper.database.reference.child("Reservations").child("terrain2")
                .child(reservation.date).setValue(reservation)
        }
    }






}