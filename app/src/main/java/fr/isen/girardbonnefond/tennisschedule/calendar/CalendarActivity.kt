package fr.isen.girardbonnefond.tennisschedule.calendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import fr.isen.girardbonnefond.tennisschedule.databinding.ActivityCalendarBinding
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.pickDateButton.setOnClickListener {
            val dpd = DatePickerDialog(/* context = */ this, /* listener = */
                { _, mYear, mMonth, mDay ->
                binding.pickDateButton.text = "$mDay/$mMonth/$mYear"
            }, /* year = */
                year, /* month = */
                month, /* dayOfMonth = */
                day)
            dpd.show()
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 8)
        binding.recyclerView.adapter = HourAdapter()
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

}