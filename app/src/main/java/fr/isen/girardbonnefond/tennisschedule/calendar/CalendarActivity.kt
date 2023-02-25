package fr.isen.girardbonnefond.tennisschedule.calendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import fr.isen.girardbonnefond.tennisschedule.databinding.ActivityCalendarBinding
import java.text.DateFormatSymbols
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var thisDayOfWeek :String

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.recyclerView.visibility = View.INVISIBLE
        binding.terrain1Text.visibility = View.INVISIBLE
        binding.terrain2Text.visibility = View.INVISIBLE

        binding.pickDateButton.setOnClickListener {
            binding.jour.visibility = View.VISIBLE

            val dpd = DatePickerDialog(/* context = */ this, /* listener = */
                { _, year, month, day ->
                binding.pickDateButton.text = "$day/${month+1}/$year"
                    thisDayOfWeek = getDayOfWeek(day, month+1, year)
                    if(thisDayOfWeek == "Saturday")
                        Toast.makeText(this, "   Vous avez sélectionné un Samedi,\n Sachez que les horaires sont différents", Toast.LENGTH_SHORT).show()

                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.adapter = HourAdapter(thisDayOfWeek)

                    binding.recyclerView.visibility = View.VISIBLE
                    binding.terrain1Text.visibility = View.VISIBLE
                    binding.terrain2Text.visibility = View.VISIBLE
                }, /* year = */
                year, /* month = */
                month, /* dayOfMonth = */
                day,
                )
            dpd.show()
        }

    }


    public override fun onStart() {
        super.onStart()
    }
    public override fun onPause() {
        super.onPause()
    }
    public override fun onDestroy() {
        super.onDestroy()
    }

    fun getDayOfWeek(day:Int, month:Int, year:Int):String{
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return DateFormatSymbols().weekdays[dayOfWeek]
    }
}