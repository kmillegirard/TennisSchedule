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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var thisDayOfWeek :String
        val uuid = intent.getStringExtra("uuid")

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.recyclerView.visibility = View.INVISIBLE
        binding.terrain1Text.visibility = View.INVISIBLE
        binding.terrain2Text.visibility = View.INVISIBLE

        binding.pickDateButton.setOnClickListener {
            binding.jour.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.INVISIBLE

            val dpd = DatePickerDialog(/* context = */ this, /* listener = */
                { _, year, month, day ->
                binding.pickDateButton.text = "$day/${month+1}/$year"
                    thisDayOfWeek = getDayOfWeek(day, month+1, year).toString()
                    //if(thisDayOfWeek == "Saturday")
                        Toast.makeText(this, "   Vous avez sélectionné un $thisDayOfWeek,\n Sachez que les horaires sont différents", Toast.LENGTH_SHORT).show()

                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.adapter = HourAdapter(thisDayOfWeek,uuid.toString(), getDate(day, month,year))

                    binding.recyclerView.visibility = View.VISIBLE
                    binding.terrain1Text.visibility = View.VISIBLE
                    binding.terrain2Text.visibility = View.VISIBLE
                }, /* year = */
                year, /* month = */
                month, /* dayOfMonth = */
                day,
                )

            dpd.datePicker.minDate = calendar.timeInMillis

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

    private fun getDayOfWeek(day:Int, month:Int, year:Int):String{
        val calendar = Calendar.getInstance()
        calendar.set(year, month-1, day)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return DateFormatSymbols().weekdays[dayOfWeek]
    }

    private fun getDate(day:Int, month: Int, year: Int):String{
        return "$year-$month-$day"
    }

    data class Reservation(
        val reservationId:String? =null,
        val terrain: Int? = null,
        val date: String? = null,
        val time: String? = null,
        val userId: String? = null
    ) {
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }

}