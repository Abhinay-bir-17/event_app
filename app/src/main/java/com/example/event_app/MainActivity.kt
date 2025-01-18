package com.example.event_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.event_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: EventsDatabaseHelper
    private lateinit var eventsAdapter:  EventsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = EventsDatabaseHelper(this)
        //set up adapter
        eventsAdapter = EventsAdapter(db.getAllNotes(),  this)

        binding.eventsRecyclerView.layoutManager= LinearLayoutManager( this)
        binding.eventsRecyclerView.adapter = eventsAdapter
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onResume() {
        super.onResume()
        eventsAdapter.refreshData(db.getAllNotes())
    }
}