package com.example.event_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.event_app.databinding.ActivityAddEventBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.content.Context

class AddEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEventBinding
    private lateinit var db: EventsDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = EventsDatabaseHelper(this)
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val content = binding.contentEditText.text.toString().trim()
            val time = binding.selectedTimeTextView.text.toString().trim()
            val date = binding.selectedDateTextView.text.toString().trim()
            if (title.isEmpty()) {
                binding.titleEditText.error = "Event name is required"
                binding.titleEditText.requestFocus()
            } else if (content.isEmpty()) {
                binding.contentEditText.error = "Event description is required"
                binding.contentEditText.requestFocus()
            } else if (date.isEmpty()) {
                Toast.makeText(this@AddEventActivity, "Please select a date", Toast.LENGTH_LONG).show()
            }
            else if (time.isEmpty()) {
                Toast.makeText(this@AddEventActivity, "Please select a time", Toast.LENGTH_LONG).show()
            } else {
                val event = Event(0, title, content,time,date)// id will start from 0
                db.insertEvent(event)
                finish()
                Toast.makeText(this, "Event Saved", Toast.LENGTH_SHORT).show()
            }
        }
        binding.pickDateButton.setOnClickListener {
            showDatePickerDialog()
        }
        binding.pickTimeButton.setOnClickListener {
            showTimePickerDialog()
        }
//        binding.pickLocation.setOnClickListener {
//            // focus abhinay bir you can do it...
//
//        }
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
                    binding.selectedDateTextView.text = "Selected Date$formattedDate"
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
                binding.selectedTimeTextView.text = "Selected Time:$formattedTime" // Display the selected time
            },
            hour, minute, false // false for 24-hour format, true for AM/PM format
        )
        // Show the TimePickerDialog
        timePickerDialog.show()
    }

}