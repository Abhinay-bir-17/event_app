package com.example.event_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.event_app.databinding.ActivityAddAttendeeBinding
import com.example.event_app.databinding.ActivityAddEventBinding

class AddAttendeeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAttendeeBinding
    private lateinit var db: EventsDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAttendeeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val eventId = intent.getIntExtra( "event_id",-1)
        val eventTitle = intent.getStringExtra("event_title")
        val eventContent = intent.getStringExtra("event_content")
        if (eventId == -1 ){
            Toast.makeText( this,  "Event ID not found!", Toast.LENGTH_LONG).show()
        }else if(eventTitle == null){
            Toast.makeText( this,  "Event title not found", Toast.LENGTH_LONG).show()

        }else if(eventContent==null) {
            Toast.makeText( this,  "Event desc not found!", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText( this,  "everything's good", Toast.LENGTH_LONG).show()
        }
        // set the textViews with event.name nd event.content as description
        binding.textView4.setText(eventTitle)
        binding.textView5.setText(eventContent)
        binding.saveAttButton.setOnClickListener {
            val name = binding.addAttName.text.toString().trim()
            val ph = binding.addAttPh.text.toString().trim()
            val mail = binding.addAttMail.text.toString().trim()
            if (name.isEmpty()) {
                binding.addAttName.error = "Event name is required"
                binding.addAttName.requestFocus()
            } else if (ph.isEmpty()) {
                binding.addAttPh.error = "Event description is required"
                binding.addAttPh.requestFocus()
            } else if (mail.isEmpty()) {
                binding.addAttMail.error = "Event description is required"
                binding.addAttMail.requestFocus()
            } else {
                val attendee = Attendee(0, eventId, name,ph,mail)// id will start from 0
                db.insertAttendee(attendee)
                finish()
                Toast.makeText(this, "Attendee Saved", Toast.LENGTH_SHORT).show()
            }
        }

    }
}