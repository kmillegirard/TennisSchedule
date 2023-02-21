package fr.isen.girardbonnefond.tennisschedule.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.girardbonnefond.tennisschedule.databinding.ListTimeBinding

class HourAdapter:RecyclerView.Adapter<HourAdapter.HourViewHolder>() {
    class HourViewHolder(binding:ListTimeBinding):RecyclerView.ViewHolder(binding.root){
        val hour = binding.hour
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = ListTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return (22 - 7 + 1 ) * 8
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hour = position / 8
        val weekDay = position % 8
        val days  = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
        if (hour == 0 && weekDay != 0) {
            holder.hour.text = days[weekDay-1]
            holder.hour.rotation = -60F
        } else if(weekDay == 0 && hour != 0) {
            holder.hour.text = "${hour.toInt()+8}h-${hour.toInt()+9}h"
        } else  if(hour == 0 && weekDay == 0) {

        } else {
            holder.hour.text = "X"
        }
    }


}