package fr.isen.girardbonnefond.tennisschedule.calendar

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

class HourAdapter(thisDayOfWeek : String, uuid: String, date: String):RecyclerView.Adapter<HourAdapter.HourViewHolder>() {
    private val date: String = date
    private val uuid: String = uuid
    private val day = thisDayOfWeek
    class HourViewHolder(binding: ListTimeBinding, adapter: HourAdapter) : RecyclerView.ViewHolder(binding.root) {
        val hour = binding.hour
        val imageView1 : ImageView = binding.buttonSelect1
        val imageView2 : ImageView = binding.buttonSelect2
        private val buttonOne : LinearLayout = binding.button1
        private val buttonTwo : LinearLayout = binding.button2

        private val bookedImage = R.drawable.booked
        private val notavailableImage = R.drawable.notavailable

        init{
            buttonOne.setOnClickListener {
                checkReservationWithTime(1,getTime(adapter.date),adapter.date,adapter.uuid)
                Picasso.get().load(bookedImage).into(imageView1)
            }
            buttonTwo.setOnClickListener {
                checkReservationWithTime(2,getTime(adapter.date),adapter.date,adapter.uuid)
                Picasso.get().load(bookedImage).into(imageView2)
            }
        }

        private fun getTime(day :String) : String{
            val position = adapterPosition
            return if (day != "Saturday")
                "${position+7}:00"
            else {
                "${position+10}:00"
            }
        }
        private fun checkReservationWithTime(terrain: Int, time: String, date: String, userId: String){
            val reservationRef = DataBaseHelper.database.getReference("terrain$terrain")
            reservationRef.orderByChild("time").equalTo(time)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot){
                        if(dataSnapshot.exists()){
                            Log.d(TAG, "Il y a déjà une réservation")
                        }else{
                            Log.d(TAG, "Il n'y a pas de une réservation")
                            writeNewReservation(terrain, date, time, userId)
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError){
                        Log.w(TAG, "Erreur lors de la récupération des scores : $databaseError")
                    }
                })
        }
        fun writeNewReservation(terrain: Int, date: String, time: String, userId:String) {
            val reservationId = UUID.randomUUID().toString()
            val reservation = CalendarActivity.Reservation(reservationId, terrain, date, time, userId)
            DataBaseHelper.database.reference.child("Terrains").child("terrain$terrain").child(date).child(reservationId).setValue(reservation)

            val userRef = DataBaseHelper.database.getReference("users/$userId")
            userRef.child("reservations").child(reservationId).setValue(reservationId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = ListTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourViewHolder(binding, this)
    }

    override fun getItemCount(): Int {
        if (day != "Saturday") return 22 - 7
        else return 18 - 10
    }

    @SuppressLint1("SetTextI18n")
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        if (day != "Saturday") {
            holder.hour.text = "${position + 7}h-${position + 8}h"
            checkReservationTerrain1(date, uuid, 7, holder, position)
            checkReservationTerrain2(date, uuid, 7, holder, position)
        }else {
            holder.hour.text = "${position+10}h-${position+11}h"
            checkReservationTerrain1(date, uuid, 10, holder, position)
            checkReservationTerrain2(date, uuid, 10, holder, position)
        }
    }
    private fun checkReservationTerrain1(date: String, userId: String, nbr:Int, holder: HourViewHolder, position: Int){
        val  time: String = (position + nbr).toString() + ":00"

        val reservationRef = DataBaseHelper.database.getReference("terrain1")
        reservationRef.orderByChild("time").equalTo(time)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if(dataSnapshot.exists()){
                        holder.imageView1.setImageResource(R.drawable.notavailable)
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
    private fun checkReservationTerrain2( date: String, userId: String, nbr:Int, holder: HourViewHolder, position: Int){
        val  time: String = (position + nbr).toString() + ":00"

        val reservationRef = DataBaseHelper.database.getReference("terrain2")
        reservationRef.orderByChild("time").equalTo(time)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if(dataSnapshot.exists()){
                        holder.imageView2.setImageResource(R.drawable.notavailable)
                        Log.d(TAG, "Il y a déjà une réservation")
                    }else{
                        holder.imageView2.setImageResource(R.drawable.available)
                        Log.d(TAG, "Il n'y a pas de une réservation")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError){
                    Log.w(TAG, "Erreur lors de la récupération des scores : $databaseError")
                }
            })
    }


}