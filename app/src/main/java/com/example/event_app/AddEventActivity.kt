package com.example.event_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.event_app.databinding.ActivityAddEventBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pickDateButton.setOnClickListener {
            showDatePickerDialog()
        }
        binding.pickTimeButton.setOnClickListener {
            showTimePickerDialog()
        }

    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                try {
                    // Format the selected date
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    calendar.set(selectedYear, selectedMonth, selectedDay)

                    // Log the selected date for debugging purposes
                    Log.d("DatePicker", "Selected date: ${calendar.time}")

                    val formattedDate = sdf.format(calendar.time)

                    // Display the formatted date in TextView
                    binding.selectedDateTextView.text = "Selected date:$formattedDate"
                } catch (e: Exception) {
                    // Log error if something goes wrong
                    Log.e("DatePicker", "Error formatting or setting the date", e)
                }
            },
            year, month, day
        )
        // Show the DatePickerDialog
        datePickerDialog.show()
    }
    private fun showTimePickerDialog(){
        // Get the current time
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create the TimePickerDialog
        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                // Format the selected time
                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)

                // Format the time and update TextView
                val formattedTime = timeFormat.format(calendar.time)
                binding.selectedTimeTextView.text = "Selected date:$formattedTime" // Display the selected time
            },
            hour, minute, false // false for 24-hour format, true for AM/PM format
        )
        // Show the TimePickerDialog
        timePickerDialog.show()
    }

}