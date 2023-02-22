package fr.isen.girardbonnefond.tennisschedule.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.girardbonnefond.tennisschedule.databinding.ListTimeBinding

class HourAdapter:RecyclerView.Adapter<HourAdapter.HourViewHolder>() {
    class HourViewHolder(binding: ListTimeBinding) : RecyclerView.ViewHolder(binding.root) {
        val hour = binding.hour
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = ListTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return (22 - 7)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {

        /*val hour = position / 8
        val weekDay = position % 8
        val days  = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
        if(weekDay == 0 && hour != 0) { // Ligne d'affichage legénde Gauche
            holder.hour.text = "${hour.toInt()+8}h-${hour.toInt()+9}h"
        }
*/
        holder.hour.text = "${position+7}h-${position+8}h"
    }

}