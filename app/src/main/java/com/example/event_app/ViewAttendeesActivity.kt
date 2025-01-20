package com.example.event_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.event_app.databinding.ActivityMainBinding
import com.example.event_app.databinding.ActivityViewAttendeesBinding

class ViewAttendeesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewAttendeesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityViewAttendeesBinding.inflate(layoutInflater)
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
        binding.titleAtt.setText(eventTitle)
        binding.descAtt.setText(eventContent)
        // fetch all attendees with eventId from Attendee db
        binding.addAttendeeButton.setOnClickListener {
            val intent = Intent(this, AddAttendeeActivity::class.java).apply {
                // Pass both event_id and event_title as extras
                putExtra("event_id", eventId)
                putExtra("event_title", eventTitle)
                putExtra("event_content", eventContent)
            }
            startActivity(intent)
        }
    }
}