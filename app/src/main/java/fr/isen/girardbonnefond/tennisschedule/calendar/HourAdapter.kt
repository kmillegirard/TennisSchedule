package fr.isen.girardbonnefond.tennisschedule.calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.girardbonnefond.tennisschedule.databinding.ListTimeBinding
import java.util.*

class HourAdapter(thisDayOfWeek : String):RecyclerView.Adapter<HourAdapter.HourViewHolder>() {
    private val day = thisDayOfWeek
    class HourViewHolder(binding: ListTimeBinding) : RecyclerView.ViewHolder(binding.root) {
        val hour = binding.hour
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = ListTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (day != "Saturday") 22 - 7
        else 18 - 10
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        if (day != "Saturday")
            holder.hour.text = "${position + 7}h-${position + 8}h"
        else {
            holder.hour.text = "${position+10}h-${position+11}h"
        }
    }

}